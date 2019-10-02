# Generated from Tiny.g4 by ANTLR 4.7.2
from antlr4 import *
if __name__ is not None and "." in __name__:
    from .TinyParser import TinyParser
else:
    from TinyParser import TinyParser

# This class defines a complete listener for a parse tree produced by TinyParser.
class TinyListener(ParseTreeListener):

    # Enter a parse tree produced by TinyParser#start.
    def enterStart(self, ctx:TinyParser.StartContext):
        pass

    # Exit a parse tree produced by TinyParser#start.
    def exitStart(self, ctx:TinyParser.StartContext):
        pass


    # Enter a parse tree produced by TinyParser#program.
    def enterProgram(self, ctx:TinyParser.ProgramContext):
        pass

    # Exit a parse tree produced by TinyParser#program.
    def exitProgram(self, ctx:TinyParser.ProgramContext):
        pass


    # Enter a parse tree produced by TinyParser#pgm_body.
    def enterPgm_body(self, ctx:TinyParser.Pgm_bodyContext):
        pass

    # Exit a parse tree produced by TinyParser#pgm_body.
    def exitPgm_body(self, ctx:TinyParser.Pgm_bodyContext):
        pass


    # Enter a parse tree produced by TinyParser#decl.
    def enterDecl(self, ctx:TinyParser.DeclContext):
        pass

    # Exit a parse tree produced by TinyParser#decl.
    def exitDecl(self, ctx:TinyParser.DeclContext):
        pass


    # Enter a parse tree produced by TinyParser#string_decl.
    def enterString_decl(self, ctx:TinyParser.String_declContext):
        pass

    # Exit a parse tree produced by TinyParser#string_decl.
    def exitString_decl(self, ctx:TinyParser.String_declContext):
        pass


    # Enter a parse tree produced by TinyParser#var_decl.
    def enterVar_decl(self, ctx:TinyParser.Var_declContext):
        pass

    # Exit a parse tree produced by TinyParser#var_decl.
    def exitVar_decl(self, ctx:TinyParser.Var_declContext):
        pass


    # Enter a parse tree produced by TinyParser#var_type.
    def enterVar_type(self, ctx:TinyParser.Var_typeContext):
        pass

    # Exit a parse tree produced by TinyParser#var_type.
    def exitVar_type(self, ctx:TinyParser.Var_typeContext):
        pass


    # Enter a parse tree produced by TinyParser#any_type.
    def enterAny_type(self, ctx:TinyParser.Any_typeContext):
        pass

    # Exit a parse tree produced by TinyParser#any_type.
    def exitAny_type(self, ctx:TinyParser.Any_typeContext):
        pass


    # Enter a parse tree produced by TinyParser#id_list.
    def enterId_list(self, ctx:TinyParser.Id_listContext):
        pass

    # Exit a parse tree produced by TinyParser#id_list.
    def exitId_list(self, ctx:TinyParser.Id_listContext):
        pass


    # Enter a parse tree produced by TinyParser#id_tail.
    def enterId_tail(self, ctx:TinyParser.Id_tailContext):
        pass

    # Exit a parse tree produced by TinyParser#id_tail.
    def exitId_tail(self, ctx:TinyParser.Id_tailContext):
        pass


    # Enter a parse tree produced by TinyParser#param_decl_list.
    def enterParam_decl_list(self, ctx:TinyParser.Param_decl_listContext):
        pass

    # Exit a parse tree produced by TinyParser#param_decl_list.
    def exitParam_decl_list(self, ctx:TinyParser.Param_decl_listContext):
        pass


    # Enter a parse tree produced by TinyParser#param_decl.
    def enterParam_decl(self, ctx:TinyParser.Param_declContext):
        pass

    # Exit a parse tree produced by TinyParser#param_decl.
    def exitParam_decl(self, ctx:TinyParser.Param_declContext):
        pass


    # Enter a parse tree produced by TinyParser#param_decl_tail.
    def enterParam_decl_tail(self, ctx:TinyParser.Param_decl_tailContext):
        pass

    # Exit a parse tree produced by TinyParser#param_decl_tail.
    def exitParam_decl_tail(self, ctx:TinyParser.Param_decl_tailContext):
        pass


    # Enter a parse tree produced by TinyParser#func_declarations.
    def enterFunc_declarations(self, ctx:TinyParser.Func_declarationsContext):
        pass

    # Exit a parse tree produced by TinyParser#func_declarations.
    def exitFunc_declarations(self, ctx:TinyParser.Func_declarationsContext):
        pass


    # Enter a parse tree produced by TinyParser#func_decl.
    def enterFunc_decl(self, ctx:TinyParser.Func_declContext):
        pass

    # Exit a parse tree produced by TinyParser#func_decl.
    def exitFunc_decl(self, ctx:TinyParser.Func_declContext):
        pass


    # Enter a parse tree produced by TinyParser#func_body.
    def enterFunc_body(self, ctx:TinyParser.Func_bodyContext):
        pass

    # Exit a parse tree produced by TinyParser#func_body.
    def exitFunc_body(self, ctx:TinyParser.Func_bodyContext):
        pass


    # Enter a parse tree produced by TinyParser#stmt_list.
    def enterStmt_list(self, ctx:TinyParser.Stmt_listContext):
        pass

    # Exit a parse tree produced by TinyParser#stmt_list.
    def exitStmt_list(self, ctx:TinyParser.Stmt_listContext):
        pass


    # Enter a parse tree produced by TinyParser#stmt.
    def enterStmt(self, ctx:TinyParser.StmtContext):
        pass

    # Exit a parse tree produced by TinyParser#stmt.
    def exitStmt(self, ctx:TinyParser.StmtContext):
        pass


    # Enter a parse tree produced by TinyParser#base_stmt.
    def enterBase_stmt(self, ctx:TinyParser.Base_stmtContext):
        pass

    # Exit a parse tree produced by TinyParser#base_stmt.
    def exitBase_stmt(self, ctx:TinyParser.Base_stmtContext):
        pass


    # Enter a parse tree produced by TinyParser#assign_stmt.
    def enterAssign_stmt(self, ctx:TinyParser.Assign_stmtContext):
        pass

    # Exit a parse tree produced by TinyParser#assign_stmt.
    def exitAssign_stmt(self, ctx:TinyParser.Assign_stmtContext):
        pass


    # Enter a parse tree produced by TinyParser#assign_expr.
    def enterAssign_expr(self, ctx:TinyParser.Assign_exprContext):
        pass

    # Exit a parse tree produced by TinyParser#assign_expr.
    def exitAssign_expr(self, ctx:TinyParser.Assign_exprContext):
        pass


    # Enter a parse tree produced by TinyParser#read_stmt.
    def enterRead_stmt(self, ctx:TinyParser.Read_stmtContext):
        pass

    # Exit a parse tree produced by TinyParser#read_stmt.
    def exitRead_stmt(self, ctx:TinyParser.Read_stmtContext):
        pass


    # Enter a parse tree produced by TinyParser#write_stmt.
    def enterWrite_stmt(self, ctx:TinyParser.Write_stmtContext):
        pass

    # Exit a parse tree produced by TinyParser#write_stmt.
    def exitWrite_stmt(self, ctx:TinyParser.Write_stmtContext):
        pass


    # Enter a parse tree produced by TinyParser#return_stmt.
    def enterReturn_stmt(self, ctx:TinyParser.Return_stmtContext):
        pass

    # Exit a parse tree produced by TinyParser#return_stmt.
    def exitReturn_stmt(self, ctx:TinyParser.Return_stmtContext):
        pass


    # Enter a parse tree produced by TinyParser#expr.
    def enterExpr(self, ctx:TinyParser.ExprContext):
        pass

    # Exit a parse tree produced by TinyParser#expr.
    def exitExpr(self, ctx:TinyParser.ExprContext):
        pass


    # Enter a parse tree produced by TinyParser#expr_prefix.
    def enterExpr_prefix(self, ctx:TinyParser.Expr_prefixContext):
        pass

    # Exit a parse tree produced by TinyParser#expr_prefix.
    def exitExpr_prefix(self, ctx:TinyParser.Expr_prefixContext):
        pass


    # Enter a parse tree produced by TinyParser#factor.
    def enterFactor(self, ctx:TinyParser.FactorContext):
        pass

    # Exit a parse tree produced by TinyParser#factor.
    def exitFactor(self, ctx:TinyParser.FactorContext):
        pass


    # Enter a parse tree produced by TinyParser#factor_prefix.
    def enterFactor_prefix(self, ctx:TinyParser.Factor_prefixContext):
        pass

    # Exit a parse tree produced by TinyParser#factor_prefix.
    def exitFactor_prefix(self, ctx:TinyParser.Factor_prefixContext):
        pass


    # Enter a parse tree produced by TinyParser#postfix_expr.
    def enterPostfix_expr(self, ctx:TinyParser.Postfix_exprContext):
        pass

    # Exit a parse tree produced by TinyParser#postfix_expr.
    def exitPostfix_expr(self, ctx:TinyParser.Postfix_exprContext):
        pass


    # Enter a parse tree produced by TinyParser#call_expr.
    def enterCall_expr(self, ctx:TinyParser.Call_exprContext):
        pass

    # Exit a parse tree produced by TinyParser#call_expr.
    def exitCall_expr(self, ctx:TinyParser.Call_exprContext):
        pass


    # Enter a parse tree produced by TinyParser#expr_list.
    def enterExpr_list(self, ctx:TinyParser.Expr_listContext):
        pass

    # Exit a parse tree produced by TinyParser#expr_list.
    def exitExpr_list(self, ctx:TinyParser.Expr_listContext):
        pass


    # Enter a parse tree produced by TinyParser#expr_list_tail.
    def enterExpr_list_tail(self, ctx:TinyParser.Expr_list_tailContext):
        pass

    # Exit a parse tree produced by TinyParser#expr_list_tail.
    def exitExpr_list_tail(self, ctx:TinyParser.Expr_list_tailContext):
        pass


    # Enter a parse tree produced by TinyParser#primary.
    def enterPrimary(self, ctx:TinyParser.PrimaryContext):
        pass

    # Exit a parse tree produced by TinyParser#primary.
    def exitPrimary(self, ctx:TinyParser.PrimaryContext):
        pass


    # Enter a parse tree produced by TinyParser#addop.
    def enterAddop(self, ctx:TinyParser.AddopContext):
        pass

    # Exit a parse tree produced by TinyParser#addop.
    def exitAddop(self, ctx:TinyParser.AddopContext):
        pass


    # Enter a parse tree produced by TinyParser#mulop.
    def enterMulop(self, ctx:TinyParser.MulopContext):
        pass

    # Exit a parse tree produced by TinyParser#mulop.
    def exitMulop(self, ctx:TinyParser.MulopContext):
        pass


    # Enter a parse tree produced by TinyParser#if_stmt.
    def enterIf_stmt(self, ctx:TinyParser.If_stmtContext):
        pass

    # Exit a parse tree produced by TinyParser#if_stmt.
    def exitIf_stmt(self, ctx:TinyParser.If_stmtContext):
        pass


    # Enter a parse tree produced by TinyParser#else_part.
    def enterElse_part(self, ctx:TinyParser.Else_partContext):
        pass

    # Exit a parse tree produced by TinyParser#else_part.
    def exitElse_part(self, ctx:TinyParser.Else_partContext):
        pass


    # Enter a parse tree produced by TinyParser#cond.
    def enterCond(self, ctx:TinyParser.CondContext):
        pass

    # Exit a parse tree produced by TinyParser#cond.
    def exitCond(self, ctx:TinyParser.CondContext):
        pass


    # Enter a parse tree produced by TinyParser#compop.
    def enterCompop(self, ctx:TinyParser.CompopContext):
        pass

    # Exit a parse tree produced by TinyParser#compop.
    def exitCompop(self, ctx:TinyParser.CompopContext):
        pass


    # Enter a parse tree produced by TinyParser#while_stmt.
    def enterWhile_stmt(self, ctx:TinyParser.While_stmtContext):
        pass

    # Exit a parse tree produced by TinyParser#while_stmt.
    def exitWhile_stmt(self, ctx:TinyParser.While_stmtContext):
        pass


