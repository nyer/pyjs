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
	
	public List<Token> tokenize() {
		List<Token> tokens = new ArrayList<Token>();
		Token token = next();
		while (token != null) {
			tokens.add(token);
			token = next();
		}
		
		return tokens;
	}
	
	private void removePrefixSpaceChar() {
		if (code.length() > 0) {
			char ch = code.charAt(0);
			while (ch == ' ' || ch == '\t' || ch == '\b'  || ch == '\r' || ch == ';') {
				code.deleteCharAt(0);

				if (code.length() == 0)
					break;
				ch = code.charAt(0);
			}
		}
	}
	
	private Token next() {
		removePrefixSpaceChar();
		
		if (code.length() > 0) {
			Token token = null;
			TokenType type;
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
				else
					type = NUMBER;
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
			} else if (ch == '*') {
				tokenStr.append(ch);
				type = MULTI;
				code.deleteCharAt(0);
			} else if (ch == '/') {
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
			} else if (ch == '=') {
				tokenStr.append(ch);
				type = ASSIGN;
				code.deleteCharAt(0);
				if (code.length() > 0 && code.charAt(0) == '=') {
					type = EQ;
					tokenStr.append('=');
					code.deleteCharAt(0);
				}
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
					if (Character.isLetterOrDigit(ch))
						tokenStr.append(ch);
					else
						break;
					code.deleteCharAt(0);
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

			token = new Token(tokenStr.toString(), type);
			return token;
		}
		
		return null;
	}
	
	public Token expect(TokenType tokenType) throws Exception {
		if (tokens.size() == 0)
			throw new Exception("unexpected EOF");
		
		if (peek(tokenType) == false)
			throw new Exception("expected: " + tokenType);
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
	
	public Token nextToken() throws Exception {
		if (tokens.size() > 0)
			return tokens.remove(0);
		throw new Exception("unexpected EOF");
	}
	
	public boolean hasNext() {
		return tokens.size() > 0;
	}
	
	public static void main(String[] args) {
		Tokenizer tokenizer = new Tokenizer("a = 3; b = a + 5; println(b); function() {}");
		List<Token> tokens = tokenizer.tokenize();
		for (Token token : tokens)
			System.out.println(token);
	}
}
