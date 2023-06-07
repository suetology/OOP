grammar Simple;

@header {
    package antlr;
}

prog: (decl | expr)+ EOF;

decl: ID ':' INT_TYPE '=' NUM;

expr: expr '*' expr |
      expr '+' expr |
      ID |
      NUM;

ID: [a-z][a-zA-Z0-9_]*;
NUM: '0' | '-'?[1-9][0-9]*;
INT_TYPE: 'int';
WS: [ \t\n]+ -> skip;

/*program: line* EOF;

line: statement | ifBlock | whileBlock;

statement: (assignment | functionCall) ';';

ifBlock: 'if' expression block ('else' elseIfBlock)?;

elseIfBlock: block | ifBlock;

whileBlock: 'while' expression block;

assignment: IDENTIFIER '=' expression;

functionCall: IDENTIFIER '(' (expression (',' expression)*)? ')';

expression: 
    | constant
    | IDENTIFIER
    | functionCall
    | '(' expression ')'
    | '!' expression
    | expression multOp expression
    | expression addOp expression
    | expression compareOp expression
    | expression boolOp
    ;

multOp: '*' | '/' | '%';
addOp: '+' | '-';
compareOp: '==' | '!=' | '>' | '<' | '>=' | '<=';
boolOp: BOOL_OPERATOR;

BOOL_OPERATOR: 'or' | 'xor' | 'and';

constant: INTEGER | FLOAT | STRING | BOOL | NULL;

INTEGER: [0-9]+;
FLOAT: [0-9]+ '.' [0-9]+;
STRING: ('"' ~'"'* '"') | ('\'' ~'\''* '\'');
BOOL:  'true' | 'false';
NULL: 'null';

block: '{' line* '}';

WS: [ \t\r\n]+ -> skip;

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;*/
