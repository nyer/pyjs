package org.nyer.pyjs;

public class Token {
	private String str;
	private TokenType tokenType;
	
	public Token(String str, TokenType tokenType) {
		this.str = str;
		this.tokenType = tokenType;
	}

	public String getStr() {
		return str;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	@Override
	public String toString() {
		return "Token [str: " + str + ", type: " + tokenType + "]";
	}
}
