package org.nyer.pyjs.primitive;

import org.nyer.pyjs.Env;

public class Return extends ControlFun {

	public Return() {
		super("return", new String[] {"one or zero value"});
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		throw new Exception("return must be in a function");
	}

}
