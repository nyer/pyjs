package org.nyer.pyjs.primitive.type;

import org.nyer.pyjs.AbstractFun;
import org.nyer.pyjs.Env;

public class Number extends AbstractFun {

	public Number() {
		super("number", new String[] {"numberstr"});
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object ret = null;
		String arg = (String) arguments[0];
		if (arg.charAt(arg.length() -1) == '.') {
			arg = arg.substring(0, arg.length() - 1);
		}
		
		if (arg.indexOf('.') != -1) {
			// float
			ret = new Float(java.lang.Float.parseFloat(arg));
		} else {
			// integer
			ret = new Integer(java.lang.Integer.parseInt(arg));
		}
		
		return ret;
	}

}
