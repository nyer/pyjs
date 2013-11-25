package org.nyer.pyjs.primitive.operator;

import java.util.List;

import org.nyer.pyjs.Assignable;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.AbstractFun;

public class Assign extends AbstractFun {
	private Assignable assign;
	public Assign(Assignable assign) {
		super(new String[] {"identifier", "value or expression"});
		this.assign = assign;
	}
	
	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		return assign.assign(env, arguments);
	}
}
