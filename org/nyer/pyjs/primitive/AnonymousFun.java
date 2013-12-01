package org.nyer.pyjs.primitive;

import java.util.Arrays;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public class AnonymousFun extends AbstractFun {
	private String[] parameters;
	private IFun body;
	
	public AnonymousFun(String[] parameters, IFun body) {
		this.parameters = parameters;
		this.body = body;
	}
	
	@Override
	public IFun invoke(Env env) throws Exception {
		return body.invoke(env);
	}

	public String[] getParameters() {
		return parameters;
	}
	
	public IFun getBody() {
		return body;
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
	}

	@Override
	public String toString() {
		return "AnonymousFun [parameters=" + Arrays.toString(parameters) + "]";
	}
}
