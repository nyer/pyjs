package org.nyer.pyjs.statement;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.AbstractFun;

public class Break extends AbstractFun {

	public Break() {
		super(new String[]{});
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		throw new Exception("break must be in a loop");
	}

}
