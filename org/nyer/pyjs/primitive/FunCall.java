package org.nyer.pyjs.primitive;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public class FunCall extends AbstractFun {

	public FunCall(String name) {
		super(name, null);
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object obj = env.lookUp(getName());
		if (obj == null)
			throw new Exception("function " + getName() + " undefined");
		if (obj instanceof IFun == false)
			throw new Exception("a function expected, but " + obj + " founded");
		
		IFun fun = (IFun) obj;
		return fun.invoke(env, arguments);
	}
}
