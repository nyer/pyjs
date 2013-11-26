package org.nyer.pyjs.primitive.operator;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjBoolean;

public class Not extends ValueOp {

	public Not() {
		super(new String[] {"boolean expr"});
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		IFun arg = arguments.get(0);
		Boolean value = checkBoolOperand(env, arg);
		
		return PjBoolean.valueOf(!value);
	}

}
