package org.nyer.pyjs;

public class Token {
	private String str;
	private TokenType tokenType;
	private int row;
	private int col;
	
	public Token() {
	}
	
	public String getStr() {
		return str;
	}

	public TokenType getTokenType() {
		return tokenType;
	}
	
	protected void setStr(String str) {
		this.str = str;
	}

	protected void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	
	protected void addRow() {
		this.row ++;
	}
	
	protected void addCol() {
		this.col ++;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public String toString() {
		return "Token [str=" + str + ", tokenType=" + tokenType + ", row="
				+ row + ", col=" + col + "]";
	}
}
