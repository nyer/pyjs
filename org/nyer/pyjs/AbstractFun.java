package org.nyer.pyjs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.nyer.pyjs.primitive.Identifier;
import org.nyer.pyjs.primitive.Instrument;
import org.nyer.pyjs.primitive.type.ReturnResult;

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


	protected Object[] evalArguments(Env env, Object[] arguments) throws Exception{
		List<Object> evaledArgs = new ArrayList<Object>(arguments.length);
		for (int i = 0, s = arguments.length;i < s;i ++) {
			evaledArgs.add(toValue(env, arguments[i]));
		}
		
		return evaledArgs.toArray();
	}
	@Override
	public String toString() {
		return "Function [name=" + name + ", parameters="
				+ Arrays.toString(parameters) + "]";
	}
}
