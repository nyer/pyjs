package org.nyer.pyjs;

import java.util.ArrayList;
import java.util.List;


public class Instrument {
	private IFun fun;
	private List<Instrument> arguments;
	
	public Instrument(IFun fun, List<Instrument> arguments) {
		this.fun = fun;
		this.arguments = arguments;
	}
	
	public Instrument(IFun fun, Instrument argument, List<Instrument> arguments) {
		this.fun = fun;
		arguments.add(0, argument);
		this.arguments = arguments;
	}
	
	
	public Instrument(IFun fun, Instrument... arguments) {
		this.fun = fun;
		this.arguments = new ArrayList<Instrument>(arguments.length);
		for (int i = 0, s = arguments.length;i < s;i ++)
			this.arguments.add(arguments[i]);
	}
	
	public Instrument(IFun fun) {
		this.fun = fun;
		this.arguments = new ArrayList<Instrument>(0);
	}
	
	public IFun invoke(Env env) throws Exception {
		List<IFun> funArgs = new ArrayList<IFun>(arguments.size());
		for (int i = 0, s = arguments.size();i < s;i ++)
			funArgs.add(arguments.get(i).invoke(env));
		
		return fun.invoke(env, funArgs);
	}

	@Override
	public String toString() {
		return "Instrument [fun=" + fun + "]";
	}
	
	public IFun getFun() {
		return fun;
	}
	
	public List<Instrument> getArguments() {
		return arguments;
	}
}
