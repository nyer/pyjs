package org.nyer.pyjs.primitive;

import org.nyer.pyjs.AbstractFun;
import org.nyer.pyjs.Env;

public class Println extends AbstractFun{
	public Println() {
		super("println", new String[] {"op1"});
	}
	
	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object op1 = arguments[0];
		Object v = toValue(env, op1);
		System.out.println(v);
		
		return new Void();
	}
}
