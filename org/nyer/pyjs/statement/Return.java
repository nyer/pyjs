package org.nyer.pyjs.statement;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.AbstractFun;
import org.nyer.pyjs.primitive.type.Void;

public class Return extends AbstractFun {
	public Return() {
		super(new String[] {"one or zero value"});
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		IFun ret = new Void();
		if (arguments.size() > 0)
			ret = arguments.get(0);
		
		return ret;
	}
}
