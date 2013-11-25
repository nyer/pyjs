package org.nyer.pyjs.primitive;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjString;

public class TypeOf extends AbstractFun {

	public TypeOf() {
		super(new String[] {"expr"});
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		IFun arg = arguments.get(0);
		return new PjString(arg.getTypeStr(env));
	}
}
