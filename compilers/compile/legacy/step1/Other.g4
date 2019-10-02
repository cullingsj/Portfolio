grammar Tiny;

/* Program */
program           : KEYWORD IDENTIFIER KEYWORD pgm_body KEYWORD;
pgm_body          : decl func_declarations;
decl		      : string_decl decl | var_decl decl | ;

/* Global String Declaration */
string_decl       : KEYWORD IDENTIFIER OPERATOR STRINGLITERAL OPERATOR ;
/*STR               : STRINGLITERAL;*/

/* Variable Declaration */
var_decl          : var_type id_list OPERATOR ;
var_type	      : KEYWORD | KEYWORD;
any_type          : var_type | KEYWORD;
id_list           : IDENTIFIER id_tail;
id_tail           : OPERATOR IDENTIFIER id_tail | ;

/* Function Paramater List */
param_decl_list   : OPERATOR param_decl param_decl_tail OPERATOR | OPERATOR OPERATOR | ;
param_decl        : var_type IDENTIFIER;
param_decl_tail   : OPERATOR param_decl param_decl_tail | ;

/* Function Declarations */
func_declarations : func_decl func_declarations | ;
func_decl         : KEYWORD any_type IDENTIFIER param_decl_list KEYWORD func_body KEYWORD;
func_body         : decl stmt_list;

/* Statement List */
stmt_list         : stmt stmt_list | ;
stmt              : base_stmt | if_stmt | while_stmt;
base_stmt         : assign_stmt | read_stmt | write_stmt | return_stmt;

/* Basic Statements */
assign_stmt       : assign_expr OPERATOR ;
assign_expr       : IDENTIFIER OPERATOR expr;
read_stmt         : KEYWORD OPERATOR id_list OPERATOR OPERATOR ;
write_stmt        : KEYWORD OPERATOR id_list OPERATOR OPERATOR ;
return_stmt       : KEYWORD expr OPERATOR ;

/* Expressions */
expr              : expr_prefix factor;
expr_prefix       : expr_prefix factor addop | ;
factor            : factor_prefix postfix_expr;
factor_prefix     : factor_prefix postfix_expr mulop | ;
postfix_expr      : primary | call_expr;
call_expr         : IDENTIFIER OPERATOR expr_list OPERATOR;
expr_list         : expr expr_list_tail | ;
expr_list_tail    : OPERATOR expr expr_list_tail | ;
primary           : OPERATOR expr OPERATOR | IDENTIFIER | INTLITERAL | FLOATLITERAL;
addop             : OPERATOR;
mulop             : OPERATOR;

/* Complex Statements and Condition */
if_stmt           : KEYWORD OPERATOR cond OPERATOR decl stmt_list else_part KEYWORD;
else_part         : KEYWORD decl stmt_list | ;
cond              : expr compop expr;
compop            : OPERATOR;


/* While statements */
while_stmt       : KEYWORD OPERATOR cond OPERATOR decl stmt_list KEYWORD;

start: .*? EOF;

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

IDENTIFIER				  : [A-Za-z]+[A-Za-z0-9]* ;


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
