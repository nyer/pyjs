package org.nyer.pyjs.primitive.operator;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public class Parenthesis extends ValueOp {

	public Parenthesis() {
		super(new String[] {"expr"});
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		return arguments.get(0);
	}
}
