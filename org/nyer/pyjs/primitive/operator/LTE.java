package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public class LTE extends ValueOp {

	public LTE() {
		super("<=", new String[] {"number", "number"});
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		IFun func = new LT();
		boolean v = (java.lang.Boolean) func.invoke(env, arguments);
		if (v)
			return true;
		func = new EQ();
		v = (java.lang.Boolean) func.invoke(env, arguments);
		if (v)
			return true;
		return false;
		}
	}
