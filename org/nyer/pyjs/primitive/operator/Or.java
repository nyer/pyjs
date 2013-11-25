package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.Env;

public class Or extends ValueOp {

	public Or() {
		super("||", new String[] {"boolean", "boolean"});
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		boolean v = checkBoolOperand(env, arguments[0]);
		if (v == false) {
			v = checkBoolOperand(env, arguments[1]);
			if (v == false) {
				return org.nyer.pyjs.primitive.type.Boolean.False;
			}
		}
		return org.nyer.pyjs.primitive.type.Boolean.True;
	}

}
