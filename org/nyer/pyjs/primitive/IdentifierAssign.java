package org.nyer.pyjs.primitive;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public class IdentifierAssign extends AbstractFun {
	private Identifier identifier;
	private IFun value;
	public IdentifierAssign(Identifier identifier, IFun value) {
		this.identifier = identifier;
		this.value = value;
	}

	@Override
	public IFun invoke(Env env) throws Exception {
		IFun arg = value.invoke(env);
		env.put(identifier.getValue(), arg);
		
		return arg;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
	}

}
