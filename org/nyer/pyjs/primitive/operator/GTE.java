package org.nyer.pyjs.primitive.operator;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjBoolean;

public class GTE extends ValueOp {

	public GTE() {
		super(new String[] {"number", "number"});
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		IFun func = new LT();
		boolean v = checkBoolOperand(env, func.invoke(env, arguments)) ;
		if (v)
			return PjBoolean.False;
		else
			return PjBoolean.True;
	}

}
