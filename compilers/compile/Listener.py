#!/usr/bin/env python3

from TinyListener import TinyListener
from TinyParser import TinyParser
from Scope import Scope

import sys


class Listener(TinyListener):

    def __init__(self):
        self.scope = Scope("GLOBAL")
        self.symbolTables = [self.scope]
        self.blockCt = 0
        self.currVarType = None
        self.writeVar = True
        self.writeFlag = False
        self.readFlag = False
        self.registers = []
        self.register_counter = 0
        self.labelStack = []
        self.assembly_code = []

    def push(self):
        reg = "r%d" % (self.register_counter)
        self.registers.append(reg)
        self.register_counter += 1
        return reg

    def get_symbol_table(self):
        return self.symbolTables

    def getTypeByKey(self, table, key):
        if key in table.symbols.keys():
            return table.symbols[key][0]
        elif not table.parent:
            return
        return self.getTypeByKey(table.parent, key)

    def enterVar_decl(self, ctx: TinyParser.Var_declContext):
        self.writeVar = True

    def exitVar_decl(self, ctx: TinyParser.Var_declContext):
        self.writeVar = False

    def enterVar_type(self, ctx: TinyParser.Var_typeContext):
        self.currVarType = ctx.getText()

    def enterFunc_decl(self, ctx: TinyParser.Func_declContext):
        children = list(ctx.getChildren())
        self.scope = Scope(children[2], self.scope)
        self.symbolTables.append(self.scope)

    def exitFunc_decl(self, ctx: TinyParser.Func_declContext):
        self.scope = self.scope.parent

    def enterIf_stmt(self, ctx: TinyParser.If_stmtContext):
        self.blockCt += 1
        self.scope = Scope("BLOCK %d" % self.blockCt, self.scope)
        self.symbolTables.append(self.scope)
        # Labels for control statements
        if len(ctx.else_part().getText()) > 0:
            elselbl = "else%d" % self.blockCt
            outlbl = "out%d" % (self.blockCt)
            self.labelStack.append(outlbl)
            self.labelStack.append(elselbl)
        else:
            outlbl = "out%d" % (self.blockCt)
            self.labelStack.append(outlbl)


    def exitIf_stmt(self, ctx: TinyParser.If_stmtContext):
        label = self.labelStack.pop()
        self.assembly_code.append(["label %s" % (label)])

    def enterElse_part(self, ctx: TinyParser.Else_partContext):
        self.scope = self.scope.parent
        
        #self.assembly_code.append()
        if len(ctx.getText()) > 0:
            parent = self.scope.parent
            self.blockCt += 1
            self.scope = Scope("BLOCK %d" % self.blockCt, parent)
            self.symbolTables.append(self.scope)
            # Labels for control statements
            label = self.labelStack.pop()
            self.assembly_code.append(["jmp %s" % (self.labelStack[-1])])
            self.assembly_code.append(["label %s" % (label)])

    def exitElse_part(self, ctx: TinyParser.Else_partContext):
        if len(ctx.getText()) > 0:
            self.scope = self.scope.parent

    def enterWhile_stmt(self, ctx: TinyParser.While_stmtContext):
        self.blockCt += 1
        self.scope = Scope("BLOCK %d" % self.blockCt, self.scope)
        self.symbolTables.append(self.scope)
        # Labels for control statements
        out = "out%d" % self.blockCt
        repeat = "block%d" % self.blockCt
        self.assembly_code.append(["label %s" % (repeat)])
        self.labelStack.append(repeat)
        self.labelStack.append(out)

    def exitWhile_stmt(self, ctx: TinyParser.While_stmtContext):
        self.scope = self.scope.parent
        out = self.labelStack.pop()
        repeat = self.labelStack.pop()
        self.assembly_code.append(["jmp %s" % repeat])
        self.assembly_code.append(["label %s" % out])

    def enterCond(self, ctx:TinyParser.CondContext):
        ct = list(ctx.getChildren())
        cType = self.getTypeByKey(self.scope, ct[0].getText())
        rr = self.push()
        rl = self.push()
        if cType == 'INT':
            self.assembly_code.append(["cmpi %s %s" % (rl, rr)])
        elif cType == "FLOAT":
            self.assembly_code.append(["cmpr %s %s" % (rl, rr)])

    def exitCond(self, ctx:TinyParser.CondContext):
        con = list(ctx.getChildren())[1].getText()
        comp = None
        if con == "=":
            comp = "jne"
        elif con == "!=":
            comp = "jeq"
        elif con == "<":
            comp = "jge"
        elif con == ">":
            comp = "jle"
        elif con == "<=":
            comp = "jgt"
        elif con == ">=":
            comp = "jlt"
        if not comp:
            return "oops"
        self.assembly_code.append(["%s %s" % (comp, self.labelStack[-1])])


    def enterString_decl(self, ctx: TinyParser.String_declContext):
        children = list(ctx.getChildren())
        self.scope.symbols[children[1].getText()] = (children[0].getText(), children[3].getText())
        self.assembly_code.append(["str %s %s" % (children[1].getText(), children[3].getText())])

    def enterId_list(self, ctx: TinyParser.Id_listContext):
        values = ctx.getText().split(",")
        if self.writeFlag:
            for v in values:
                self.currVarType = self.getTypeByKey(self.scope, v)
                if self.currVarType == "INT":
                    self.assembly_code.append(["sys writei %s" % v])
                elif self.currVarType == "FLOAT":
                    self.assembly_code.append(["sys writer %s" % v])
                elif self.currVarType == "STRING":
                    self.assembly_code.append(["sys writes %s" % v])
        if self.readFlag:
            lineBlock = []
            for v in values:
                typ = self.getTypeByKey(self.scope, v)
                if typ == "INT":
                    self.assembly_code.append(["sys readi %s" % (v)])
                elif typ == "FLOAT":
                    self.assembly_code.append(["sys readf %s" % (v)])
        if self.writeVar:
            self.assembly_code.append([])
            for v in values:
                if self.currVarType == "INT":
                    self.assembly_code[-1].append("var %s" % v)
                elif self.currVarType == "FLOAT":
                    self.assembly_code[-1].append("var %s" % v)
                if v in self.scope.symbols:
                    #print("DECLARATION ERROR %s" % v)
                    sys.exit(0)
                self.scope.symbols[v] = (self.currVarType, None)

    def enterParam_decl(self, ctx: TinyParser.Param_declContext):
        ct = list(ctx.getChildren())
        self.scope.symbols[ct[1].getText()] = (ct[0].getText(), None)

    # Enter a parse tree produced by TinyParser#assign_expr.
    def enterAssign_expr(self, ctx:TinyParser.Assign_exprContext):
        self.assembly_code.append([])
        ct = list(ctx.getChildren())[0].getText()
        self.currVarType = self.getTypeByKey(self.scope, ct)
        tr = self.push()
        self.assembly_code[-1].append("move %s %s" % (tr, ct))

    # Enter a parse tree produced by TinyParser#write_stmt.
    def enterWrite_stmt(self, ctx: TinyParser.Write_stmtContext):
        self.writeFlag = True

    # Exit a parse tree produced by TinyParser#write_stmt.
    def exitWrite_stmt(self, ctx: TinyParser.Write_stmtContext):
        self.writeFlag = False

    # Enter a parse tree produced by TinyParser#read_stmt.
    def enterRead_stmt(self, ctx: TinyParser.Read_stmtContext):
        self.readFlag = True

    # Exit a parse tree produced by TinyParser#read_stmt.
    def exitRead_stmt(self, ctx: TinyParser.Read_stmtContext):
        self.readFlag = False

    def enterFactor_prefix(self, ctx: TinyParser.Factor_prefixContext):
        if not ctx.mulop():
            return
        op = list(ctx.getChildren())[2].getText()
        rf = self.registers.pop()
        rr = self.push()
        rl = self.push()
        code = ""
        if self.currVarType == "INT":
            if op == "*":
                code = "muli %s %s %s" % (rl, rr, rf)
            elif op == "/":
                code = "divi %s %s %s" % (rl, rr, rf)
        elif self.currVarType == "FLOAT":
            if op == "*":
                code = "mulr %s %s %s" % (rl, rr, rf)
            elif op == "/":
                code = "divr %s %s %s" % (rl, rr, rf)
        self.assembly_code[-1].append(code)

    def enterPrimary(self, ctx: TinyParser.PrimaryContext):
        line = None
        if ctx.ID():
            result = self.registers.pop()
            line = "move %s %s" % (ctx.ID(), result)
        elif ctx.INTLITERAL():
            result = self.registers.pop()
            line = "move %s %s" % (ctx.INTLITERAL(), result)
        elif ctx.FLOATLITERAL():
            result = self.registers.pop()
            line = "move %s %s" % (ctx.FLOATLITERAL(), result)
        
        if line:
            self.assembly_code[-1].append(line)

    # Enter a parse tree produced by TinyParser#expr_prefix.
    def enterExpr_prefix(self, ctx: TinyParser.Expr_prefixContext):
        if ctx.addop():
            op = list(ctx.getChildren())[2].getText()
            result = self.registers.pop()
            rr = self.push()
            rl = self.push()
            opper = ''
            if self.currVarType == 'INT':
                if op == '+':
                    opper = 'addi'
                elif op == '-':
                    opper = 'subi'
            elif self.currVarType == 'FLOAT':
                if op == '+':
                    opper = 'addr'
                elif op == '-':
                    opper = 'subr'
            self.assembly_code[-1].append("%s %s %s %s" % (opper, rl, rr, result))


