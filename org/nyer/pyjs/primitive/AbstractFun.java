package org.nyer.pyjs.primitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public abstract class AbstractFun implements IFun {
	public String[] parameters;
	
	public AbstractFun(String[] parameters) {
		this.parameters = parameters;
	}
	
	@Override
	public String[] getParameters() {
		return parameters;
	}
	
	@Override
	public String getTypeStr(Env env) throws Exception {
		return "function";
	}
	
	
	protected List<IFun> evalArguments(Env env, List<IFun> arguments) {
		List<IFun> args = new ArrayList<IFun>(arguments.size());
		return args;
	}
	@Override
	public String toString() {
		return "Function [parameters="
				+ Arrays.toString(parameters) + "]";
	}
}
