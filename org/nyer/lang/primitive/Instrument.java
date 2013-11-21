package org.nyer.lang.primitive;

import java.util.Arrays;

import org.nyer.lang.Env;
import org.nyer.lang.IFun;

public class Instrument {
	private IFun fun;
	private Object[] arguments;
	
	public Instrument(IFun fun, Object... arguments) {
		this.fun = fun;
		this.arguments = arguments;
	}
	
	public Object invoke(Env env) throws Exception {
		return fun.invoke(env, arguments);
	}

	@Override
	public String toString() {
		return "Instrument [fun=" + fun + ", arguments="
				+ Arrays.toString(arguments) + "]";
	}
	
	public IFun getFun() {
		return fun;
	}
}
