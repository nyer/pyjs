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

public class Context {
	private boolean allowBreak = false;
	private boolean allowReturn = false;
	
	public boolean inFunc() {
		return allowReturn = true;
	}
	
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
