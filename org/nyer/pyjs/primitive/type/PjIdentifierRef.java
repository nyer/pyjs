package org.nyer.pyjs.primitive.type;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.Identifier;

public class PjIdentifierRef extends Value {

	public PjIdentifierRef(Identifier identifier) {
		super("identifier", identifier);
	}

	@Override
	public IFun invoke(Env env, IFun[] arguments) throws Exception {
		return getValue();
	}
	
	@Override
	public Identifier getValue() {
		return (Identifier) super.getValue();
	}
	
	@Override
	public String getTypeStr(Env env) throws Exception {
		return "identifierRef";
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
	}

}
