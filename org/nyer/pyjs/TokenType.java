/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

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
	
	PLUSPLUS,
	SUBSUB,
	
	PLUS,
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
