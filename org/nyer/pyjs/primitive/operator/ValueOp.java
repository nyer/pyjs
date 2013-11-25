package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.AbstractFun;
import org.nyer.pyjs.primitive.Instrument;

public abstract class ValueOp extends AbstractFun {

	public ValueOp(String name, String[] parameters) {
		super(name, parameters);
	}

	protected Number checkNumOperand(Env env, Object argument) throws Exception {
		Instrument in = (Instrument) argument;
		Object v = eval(env, argument);
		if (v instanceof Number)
			return (Number) v;
		
		throw new Exception("number expected, but " + in.getFun() + " founded");
	}
	
	protected boolean checkBoolOperand(Env env, Object argument) throws Exception {
		Object v = eval(env, argument);
		if (v instanceof Boolean)
			return  (Boolean) v;
		
		throw new Exception("boolean expression expected, but " + v + " founded");
	}
}
