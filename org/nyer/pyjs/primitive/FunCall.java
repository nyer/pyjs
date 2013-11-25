package org.nyer.pyjs.primitive;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.Value;

public class FunCall extends AbstractFun {
	public FunCall() {
		super(null);
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		IFun func = arguments.get(0);
		if (func instanceof Value)
			throw new Exception("value cannot be invoked, " + func);
		
		return func.invoke(env, arguments.subList(1, arguments.size()));
	}
}
