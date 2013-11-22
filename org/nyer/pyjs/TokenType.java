package org.nyer.pyjs;

public enum TokenType {
	DOT,
	COMMA,

	ASSIGN,
	OPEN_PARENTHESIS,
	CLOSE_PARENTHESIS,
	
	OPEN_BRACE,
	CLOSE_BRACE,
	
	OR,
	AND,
	
	EQ,
	NE,
	
	GT,
	GTE,
	LT,
	LTE,
	
	ADD,
	SUB,
	MULTI,
	DIV,
	
	NUMBER,
	BOOLEAN,
	
	IF,
	ELSE,
	ELIF,
	
	BREAK,
	RETURN,
	WHILE,
	FOR,

	IDENTIFIER,
	
	UNKNOW,
}
