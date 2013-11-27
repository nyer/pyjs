package org.nyer.pyjs.primitive;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public class IdentifierAssign extends AbstractFun {
	private Identifier identifier;
	public IdentifierAssign(Identifier identifier) {
		super(new String[] {"identifier"});
		this.identifier = identifier;
	}

	@Override
	public IFun invoke(Env env, IFun[] arguments) throws Exception {
		IFun value = arguments[0];
		env.put(identifier.getValue(), value);
		
		return value;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
	}

}
