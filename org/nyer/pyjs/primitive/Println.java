package org.nyer.pyjs.primitive;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.operator.ValueOp;
import org.nyer.pyjs.primitive.type.Void;

public class Println extends ValueOp{
	public Println() {
		super("println", new String[] {"op1"});
	}
	
	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object obj = eval(env, arguments[0]);
		System.out.println(obj);
		return new Void();
	}
}
