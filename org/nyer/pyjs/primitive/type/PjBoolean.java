/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs.primitive.type;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;


public class PjBoolean extends Value  {
	public PjBoolean(String bool) {
		super(bool);
	}
	
	@Override
	public String getTypeStr(Env env) {
		return "boolean";
	}
	
	@Override
	public Object toValue(Env env, Object rawValue) throws Exception {
		return Boolean.valueOf((String)rawValue);
	}
	
	@Override
	public Boolean getValue() {
		return (Boolean) super.getValue();
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}
	
	public static PjBoolean True = new PjBoolean("true");
	
	public static PjBoolean False = new PjBoolean("false");
	
	public static PjBoolean valueOf(boolean bool) {
		if (bool)
			return True;
		else
			return False;
	}
}
