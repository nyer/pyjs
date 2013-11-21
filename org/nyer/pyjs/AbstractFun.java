package org.nyer.pyjs;

import java.util.Arrays;

import org.nyer.pyjs.primitive.Identifier;
import org.nyer.pyjs.primitive.Instrument;

public abstract class AbstractFun implements IFun {
	public String name;
	public String[] parameters;
	
	public AbstractFun(String name, String[] parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String[] getParameters() {
		return parameters;
	}
	
	protected Object toValue(Env env, Object arg) throws Exception {
		if (arg instanceof Identifier) {
			Identifier identifier = (Identifier) arg;
			Object v = env.lookUp(identifier);
			if (v == null)
				throw new Exception("Undefined identifier: " + identifier.getName());
			return  v;
		} else if (arg instanceof Instrument) {
			Instrument apply = (Instrument) arg;
			return toValue(env, apply.invoke(env));
		}
		
		return arg;
	}


	@Override
	public String toString() {
		return "Function [name=" + name + ", parameters="
				+ Arrays.toString(parameters) + "]";
	}
}
