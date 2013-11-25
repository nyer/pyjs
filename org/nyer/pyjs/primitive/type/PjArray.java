package org.nyer.pyjs.primitive.type;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public class PjArray extends Value {

	public PjArray() {
		super("array expr", null);
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		this.value = arguments;
		
		return this;
	}
	
	@Override
	public String getTypeStr(Env env) throws Exception {
		return "array";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IFun> getValue() {
		return (List<IFun>) super.getValue();
	}
	
	@Override
	public String toString() {
		try {
			return getTypeStr(null) + getValue();
		} catch (Exception e) {
		}
		return "";
	}
}
