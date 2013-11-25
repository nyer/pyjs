package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.AbstractFun;
import org.nyer.pyjs.primitive.Identifier;

public class Assign extends AbstractFun {
	public Assign() {
		super("=", new String[] {"identifier", "value or expression"});
	}
	
	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object identifier =eval(env, arguments[0]);
		if (identifier instanceof Identifier == false)
			throw new Exception("the left of assign must be a identifier, but found: " + identifier);
		String name = (String) eval(env, arguments[0]);
		Object value = eval(env, arguments[1]);
		
		env.put(name, value);
		return value;
	}
}
