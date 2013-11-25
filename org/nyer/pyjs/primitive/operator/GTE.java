package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.Instrument;
import org.nyer.pyjs.primitive.type.Boolean;
import org.nyer.pyjs.primitive.type.Float;
import org.nyer.pyjs.primitive.type.Integer;

public class GTE extends ValueOp {

	public GTE() {
		super(">=", new String[] {"number", "number"});
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		IFun func = new GT();
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
