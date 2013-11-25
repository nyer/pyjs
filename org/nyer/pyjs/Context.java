package org.nyer.pyjs;

public class Context {
	private boolean allowBreak = false;
	private boolean allowReturn = false;
	
	public void enterFunc() {
		allowReturn = true;
		allowBreak = false;
	}
	
	public void exitFunc() {
		allowReturn = false;
	}
	
	public void enterLoop() {
		allowBreak = true;
	}
	
	public void exitLoop() {
		allowBreak = false;
	}
	
	public boolean allowBreak() {
		return allowBreak;
	}
	
	public boolean allowReturn() {
		return allowReturn;
	}
}
