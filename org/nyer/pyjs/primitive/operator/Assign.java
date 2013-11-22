package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.AbstractFun;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.Identifier;

public class Assign extends AbstractFun {
	public Assign() {
		super("=", new String[] {"identifier", "value or expression"});
	}
	
	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		String identifier = ((Identifier) arguments[0]).getName();
		Object value = toValue(env, arguments[1]);
		
		env.put(identifier, value);
		return value;
	}
}
