package org.nyer.pyjs.primitive;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.type.Value;

public class Identifier extends Value {

	public Identifier(String value) {
		super("identifier","identifier",  value);
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		String name = (String) getValue();
		Object arg = env.lookUp(name);
		if (arg == null)
			throw new Exception("undefined identifier,  " + name);
		
		return arg;
	}
}
