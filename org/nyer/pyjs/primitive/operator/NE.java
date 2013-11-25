package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.type.Boolean;
import org.nyer.pyjs.primitive.type.Float;
import org.nyer.pyjs.primitive.type.Integer;

public class NE extends ValueOp {

	public NE() {
		super("!=", new String[] {"number", "number"});
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		EQ eq = new EQ();
		boolean v = (java.lang.Boolean) eq.invoke(env, arguments);
		if (v)
			return Boolean.False;
		return Boolean.True;
	}
}
