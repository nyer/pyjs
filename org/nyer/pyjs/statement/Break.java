package org.nyer.pyjs.statement;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.AbstractFun;

public class Break extends AbstractFun {

	public Break() {
		super("break", new String[]{});
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		throw new Exception("break must be in a loop");
	}

}
