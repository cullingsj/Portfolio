grammar Tiny;

/* Program **/
start: program EOF;
program           : 'PROGRAM' ID 'BEGIN' pgm_body 'END';
ID                : IDENTIFIER;
pgm_body          : decl func_declarations;
decl		      : string_decl decl | var_decl decl | ;

/* Global String Declaration */
string_decl       : 'STRING' ID ':=' STR ';' ;
STR               : STRINGLITERAL;

/* Variable Declaration */
var_decl          : var_type id_list ';' ;
var_type	      : 'FLOAT' | 'INT';
any_type          : var_type | 'VOID';
id_list           : ID id_tail;
id_tail           : ',' ID id_tail | ;

/* Function Paramater List */
param_decl_list   : '(' param_decl param_decl_tail ')' | '(' ')' | ;
param_decl        : var_type ID;
param_decl_tail   : ',' param_decl param_decl_tail | ;

/* Function Declarations */
func_declarations : func_decl func_declarations | ;
func_decl         : 'FUNCTION' any_type ID param_decl_list 'BEGIN' func_body 'END';
func_body         : decl stmt_list;

/* Statement List */
stmt_list         : stmt stmt_list | ;
stmt              : base_stmt | if_stmt | while_stmt;
base_stmt         : assign_stmt | read_stmt | write_stmt | return_stmt;

/* Basic Statements */
assign_stmt       : assign_expr ';' ;
assign_expr       : ID ':=' expr;
read_stmt         : 'READ' '(' id_list ')' ';' ;
write_stmt        : 'WRITE' '(' id_list ')' ';' ;
return_stmt       : 'RETURN' expr ';' ;

/* Expressions */
expr              : expr_prefix factor;
expr_prefix       : expr_prefix factor addop | ;
factor            : factor_prefix postfix_expr;
factor_prefix     : factor_prefix postfix_expr mulop | ;
postfix_expr      : primary | call_expr;
call_expr         : ID '(' expr_list ')';
expr_list         : expr expr_list_tail | ;
expr_list_tail    : ',' expr expr_list_tail | ;
primary           : '(' expr ')' | ID | INTLITERAL | FLOATLITERAL;
addop             : '+' | '-';
mulop             : '*' | '/';

/* Complex Statements and Condition */
if_stmt           : 'IF' '(' cond ')' decl stmt_list else_part 'ENDIF';
else_part         : 'ELSE' decl stmt_list | ;
cond              : expr compop expr;
compop            : '<' | '>' | '=' | '!=' | '<=' | '>=';


/* While statements */
while_stmt       : 'WHILE' '(' cond ')' decl stmt_list 'ENDWHILE';

WS: (' ' | '\t' | '\r' | '\n' ) -> skip;

INTLITERAL: [0-9]+;

FLOATLITERAL: [0-9]*'.'[0-9]+ ;

STRINGLITERAL: '"'.*?'"';

COMMENT: '--'.*?'\n' -> skip;

KEYWORD: 'PROGRAM'
    |'BEGIN'
    |'END'
    |'FUNCTION'
    |'READ'
    |'WRITE'
    |'IF'
    |'ELSE'
    |'ENDIF'
    |'WHILE'
    |'ENDWHILE'
    |'CONTINUE'
    |'BREAK'
    |'RETURN'
    |'INT'
    |'VOID'
    |'STRING'
    |'FLOAT';

IDENTIFIER: [A-Za-z]+[A-Za-z0-9]* ;

OPERATOR:':='
    |'+'
    |'-'
    |'*'
    |'/'
    |'='
    |'!='
    |'<'
    |'>'
    |'('
    |')'
    |';'
    |','
    |'<='
    |'>='
    ;
