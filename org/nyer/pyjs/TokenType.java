package org.nyer.pyjs;

public enum TokenType {
	DOT,
	COMMA,
	SEMICOLON,
	COLON,
	QUESTION,

	ASSIGN,
	OPEN_PARENTHESIS,
	CLOSE_PARENTHESIS,
	
	OPEN_BRACE,
	CLOSE_BRACE,
	
	OPEN_BRACKET,
	CLOSE_BRACKET,
	
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
	NOT,
	
	FLOAT,
	INTEGER, 
	BOOLEAN,
	STRING,
	
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
