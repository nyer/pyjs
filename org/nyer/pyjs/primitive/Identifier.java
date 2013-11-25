package org.nyer.pyjs.primitive;

import java.util.List;

import org.nyer.pyjs.Assignable;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.Value;

public class Identifier extends Value implements Assignable {

	public Identifier(String value) {
		super("identifier",  value);
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		String name = getValue();
		IFun obj = env.lookUp(name);
		if (obj == null)
			throw new Exception("undefined identifier, " + name);
		
		return obj;
	}
	
	@Override
	public IFun assign(Env env, List<IFun> arguments) throws Exception {
		IFun value = arguments.get(0);
		env.put(getValue(), value);
		
		return value;
	}
	
	@Override
	public String getValue() {
		return (String) super.getValue();
	}
	
	@Override
	public String getTypeStr(Env env) throws Exception {
		if (env == null)
			return "unknow";
		return this.invoke(env, (List<IFun>)null).getTypeStr(env);
	}
}
