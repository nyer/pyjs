package org.nyer.pyjs.primitive;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.Void;

public class Println extends AbstractFun {
	public Println() {
		super(new String[] {"op1"});
	}
	
	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		if (arguments.size() > 0)
			System.out.println(arguments.get(0));
		else
			System.out.println();
		return new Void();
	}
}
