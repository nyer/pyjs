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
