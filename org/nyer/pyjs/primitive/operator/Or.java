package org.nyer.pyjs.primitive.operator;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjBoolean;

public class Or extends ValueOp {

	public Or() {
		super(new String[] {"boolean", "boolean"});
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		boolean v = checkBoolOperand(env, arguments.get(0));
		if (v == false) {
			v = checkBoolOperand(env, arguments.get(1));
			if (v == false) {
				return PjBoolean.False;
			}
		}
		return PjBoolean.True;
	}

}
