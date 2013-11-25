package org.nyer.pyjs.primitive.type;

import org.nyer.pyjs.Env;


public class PjBoolean extends Value  {
	public PjBoolean(boolean bool) {
		super("boolean str", bool);
	}
	
	@Override
	public String getTypeStr(Env env) {
		return "boolean";
	}
	
	@Override
	public Boolean getValue() {
		return (Boolean) super.getValue();
	}
	
	public static PjBoolean True = new PjBoolean(true);
	
	public static PjBoolean False = new PjBoolean(false);
	
	public static PjBoolean valueOf(boolean bool) {
		if (bool)
			return True;
		else
			return False;
	}
}
