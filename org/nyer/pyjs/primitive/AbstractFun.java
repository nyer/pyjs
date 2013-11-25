package org.nyer.pyjs.primitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

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
	
	protected Object[] evalArguments(Env env, Object[] arguments) throws Exception{
		List<Object> evaledArgs = new ArrayList<Object>(arguments.length);
		for (int i = 0, s = arguments.length;i < s;i ++) {
			evaledArgs.add(eval(env, arguments[i]));
		}
		
		return evaledArgs.toArray();
	}
	
	protected Object eval(Env env, Object argument) throws Exception {
		Instrument instrument = (Instrument) argument;
		return instrument.invoke(env);
	}
	
	@Override
	public String toString() {
		return "Function [name=" + name + ", parameters="
				+ Arrays.toString(parameters) + "]";
	}
}
