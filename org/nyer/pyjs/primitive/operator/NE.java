package org.nyer.pyjs.primitive.operator;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjBoolean;

public class NE extends ValueOp {

	public NE() {
		super(new String[] {"number", "number"});
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		EQ eq = new EQ();
		boolean v = checkBoolOperand(env, eq.invoke(env, arguments)) ;
		if (v)
			return PjBoolean.False;
		return PjBoolean.True;
	}
}
