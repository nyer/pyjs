package org.nyer.pyjs.primitive.operator;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjBoolean;

public class And extends ValueOp {

	public And() {
		super(new String[] {"boolean", "boolean"});
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		boolean v = checkBoolOperand(env, arguments.get(0));
		if (v) {
			v = checkBoolOperand(env, arguments.get(1));
			if (v) {
				return PjBoolean.True;
			}
		}
		
		return PjBoolean.False;
	}

}
