package org.nyer.pyjs.primitive.operator;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjBoolean;

public class EQ extends ValueOp {

	public EQ() {
		super(new String[] {"number", "number"});
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		Object op1 = arguments.get(0);
		Object op2 = arguments.get(1);
		
		return PjBoolean.valueOf(op1.equals(op2));
	}

}
