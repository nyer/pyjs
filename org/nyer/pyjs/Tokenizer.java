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

import java.util.ArrayList;
import java.util.List;

import static org.nyer.pyjs.TokenType.*;

public class Tokenizer {
	private StringBuffer code;
	private List<Token> tokens;
	public Tokenizer(String code) {
		this.code = new StringBuffer(code);
		tokens = tokenize();
	}
	
	private List<Token> tokenize() {
		List<Token> tokens = new ArrayList<Token>();

		Token token = new Token();
		removePrefixSpaceChar(token);
		while (this.code.length() > 0) {
			next(token);
			tokens.add(token);
			
			token = new Token();
			removePrefixSpaceChar(token);
		}
		
		return tokens;
	}
	
	private void removePrefixSpaceChar(Token token) {
		if (code.length() > 0) {
			char ch = code.charAt(0);
			while (ch == ' ' || ch == '\t' || ch == '\b'  || ch == '\r' || ch == '\n') {
				code.deleteCharAt(0);

				if (code.length() == 0)
					break;
				ch = code.charAt(0);
			}
		}
	}
	
	private void next(Token token) {
		TokenType type = UNKNOW;
		StringBuilder tokenStr = new StringBuilder();
		
		char ch = code.charAt(0);
		if (Character.isDigit(ch) || ch == '.') {
			boolean dotExist = false;
			do {
				ch = code.charAt(0);
				if (Character.isDigit(ch))
					tokenStr.append(ch);
				else if (dotExist == false && ch == '.') {
					dotExist = true;
					tokenStr.append(ch);
				} else
					break;
				code.deleteCharAt(0);
			} while (code.length() > 0);
			if (tokenStr.equals("."))
				type = DOT;
			else if (dotExist)
				type = FLOAT;
			else
				type = INTEGER;
		} else if (ch == '+') {
			tokenStr.append(ch);
			type = ADD;
			code.deleteCharAt(0);
		} else if (ch == '-') {
			tokenStr.append(ch);
			type = SUB;
			code.deleteCharAt(0);
		} else if (ch == ',') {
			tokenStr.append(ch);
			type = COMMA;
			code.deleteCharAt(0);
		} else if (ch == ';') {
			tokenStr.append(ch);
			type = SEMICOLON;
			code.deleteCharAt(0);
		} else if (ch == ':') {
			tokenStr.append(ch);
			type = COLON;
			code.deleteCharAt(0);
		} else if (ch == '*') {
			tokenStr.append(ch);
			type = MULTI;
			code.deleteCharAt(0);
		} else if (ch == '"') {
			code.deleteCharAt(0);
			boolean escape = false;
			while (code.length() > 0) {
				ch = code.charAt(0);
				code.deleteCharAt(0);
				if (ch == '"') {
					if(escape == false) {
						type = STRING;
						break;
					}
				} else if (ch == '\\' && escape == false) {
					escape = true;
					continue;
				}
				escape = false;
				tokenStr.append(ch);
			}
		} else if (ch == '/') {
			code.deleteCharAt(0);
			while (code.length() > 0) {
				
			}
			tokenStr.append(ch);
			type = DIV;
			code.deleteCharAt(0);
		}  else if (ch == '(') {
			tokenStr.append(ch);
			type = OPEN_PARENTHESIS;
			code.deleteCharAt(0);
		} else if (ch == ')') {
			tokenStr.append(ch);
			type = CLOSE_PARENTHESIS;
			code.deleteCharAt(0);
		} else if (ch == '{') {
			tokenStr.append(ch);
			type = OPEN_BRACE;
			code.deleteCharAt(0);
		} else if (ch == '}') {
			tokenStr.append(ch);
			type = CLOSE_BRACE;
			code.deleteCharAt(0);
		} else if (ch == '[') {
			tokenStr.append(ch);
			type = OPEN_BRACKET;
			code.deleteCharAt(0);
		} else if (ch == ']') {
			tokenStr.append(ch);
			type = CLOSE_BRACKET;
			code.deleteCharAt(0);
		} else if (ch == '?') {
			tokenStr.append(ch);
			type = QUESTION;
			code.deleteCharAt(0);
		} else if (ch == '=') {
			tokenStr.append(ch);
			type = ASSIGN;
			code.deleteCharAt(0);
			if (code.length() > 0 && code.charAt(0) == '=') {
				type = EQ;
				tokenStr.append('=');
				code.deleteCharAt(0);
			}
		} else if (ch == '!') {
			tokenStr.append(ch);
			type = NOT;
			code.deleteCharAt(0);
		} else if (ch == '>') {
			tokenStr.append(ch);
			type = GT;
			code.deleteCharAt(0);
			if (code.length() > 0 && code.charAt(0) == '=') {
				type = GTE;
				tokenStr.append('=');
				code.deleteCharAt(0);
			}
		} else if (ch == '<') {
			tokenStr.append(ch);
			type = LT;
			code.deleteCharAt(0);
			if (code.length() > 0 && code.charAt(0) == '=') {
				type = LTE;
				tokenStr.append('=');
				code.deleteCharAt(0);
			}
		} else if (ch == '|') {
			tokenStr.append(ch);
			type = UNKNOW;
			code.deleteCharAt(0);
			if (code.length() > 0 && code.charAt(0) == '|') {
				type = OR;
				tokenStr.append('|');
				code.deleteCharAt(0);
			}
		} else if (ch == '&') {
			tokenStr.append(ch);
			type = UNKNOW;
			code.deleteCharAt(0);
			if (code.length() > 0 && code.charAt(0) == '&') {
				type = AND;
				tokenStr.append('&');
				code.deleteCharAt(0);
			}
		} else {
			do {
				ch = code.charAt(0);
				if (Character.isLetterOrDigit(ch)) {
					tokenStr.append(ch);
					code.deleteCharAt(0);
				}
				else
					break;
			} while (code.length() > 0);
			String str = tokenStr.toString();
			if ("if".equals(str)) {
				type = IF;
			} else if ("elif".equals(str)) {
				type = ELIF;
			} else if ("else".equals(str)) {
				type = ELSE;
			} else if ("true".equals(str)) {
				type = BOOLEAN;
			} else if ("false".equals(str)) {
				type = BOOLEAN;
			} else if ("while".equals(str)) {
				type = WHILE;
			} else if ("for".equals(str)) {
				type = FOR;
			} else if ("break".equals(str)) {
				type = BREAK;
			} else if ("return".equals(str)) {
				type = RETURN;
			} else {
				type = IDENTIFIER;
			}
		}

		token.setStr(tokenStr.toString());
		token.setTokenType(type);
	}
	
	public Token expect(TokenType tokenType) throws Exception {
		if (tokens.size() == 0)
			throw new Exception("unexpected EOF");
		
		if (peek(tokenType) == false)
			throw new Exception("expected: " + tokenType + ", but found: " + nextToken());
		return tokens.remove(0);
	}
	
	public Token acceptWithToken(TokenType tokenType) throws Exception {
		if (peek(tokenType)) {
			return tokens.remove(0);
		}
		return null;
	}
	
	public boolean accept(TokenType tokenType) throws Exception {
		if (peek(tokenType)) {
			tokens.remove(0);
			return true;
		}
		return false;
	}
	
	public boolean peek(TokenType tokenType) throws Exception {
		if (tokens.size() > 0 && tokens.get(0).getTokenType() == tokenType) {
			return true;
		}
		return false;
	}
	
	public boolean peek(TokenType... tokenTypes) throws Exception {
		if (tokens.size() > 0) {
			TokenType type = tokens.get(0).getTokenType();
			for (int i = 0, s = tokenTypes.length;i < s;i ++)
				if (tokenTypes[i] == type)
					return true;
		}
		return false;
	}
	
	public Token nextToken() throws Exception {
		if (tokens.size() > 0)
			return tokens.remove(0);
		throw new Exception("unexpected EOF");
	}
	
	public boolean hasNext() {
		return tokens.size() > 0;
	}
	
	public void cleanSemiColon() throws Exception {
		while (peek(SEMICOLON))
			nextToken();
	}
	
	public List<Token> getTokens() {
		return tokens;
	}
	
	public static void main(String[] args) {
		Tokenizer tokenizer = new Tokenizer("function hello(e) {}");
		List<Token> tokens = tokenizer.getTokens();
		for (Token token : tokens)
			System.out.println(token);
	}
}

class StringScanner {
	private String str;
	private int pos;
	public StringScanner(String str) {
		this.str = str;
	}
}
