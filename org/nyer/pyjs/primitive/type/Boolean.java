package org.nyer.pyjs.primitive.type;


public class Boolean extends Value  {
	public Boolean(boolean bool) {
		super("boolean","boolean str", bool);
	}

	
	public static Boolean True = new Boolean(true) {
		public Object invoke(org.nyer.pyjs.Env env, Object[] arguments) throws Exception {
			return true;
		};
	};
	
	public static Boolean False = new Boolean(false) {
		public Object invoke(org.nyer.pyjs.Env env, Object[] arguments) throws Exception {
			return false;
		};
	};
	
	public static Boolean valueOf(boolean bool) {
		if (bool)
			return True;
		else
			return False;
	}
}
