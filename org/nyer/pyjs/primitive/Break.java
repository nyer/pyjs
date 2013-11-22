package org.nyer.pyjs.primitive;

import org.nyer.pyjs.Env;

public class Break extends ControlFun {

	public Break() {
		super("break", new String[]{});
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		throw new Exception("break must be in a loop");
	}

}
