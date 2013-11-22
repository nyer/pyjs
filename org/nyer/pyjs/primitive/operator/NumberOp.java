package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.AbstractFun;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.Instrument;
import org.nyer.pyjs.primitive.type.Float;
import org.nyer.pyjs.primitive.type.Integer;

public abstract class NumberOp extends AbstractFun {

	public NumberOp(String name, String[] parameters) {
		super(name, parameters);
	}

	protected Object checkOperand(Env env, Object obj) throws Exception {
		if (obj instanceof Instrument) {
			Instrument instru = (Instrument) obj;
			if (instru.getFun() instanceof Assign)
				throw new Exception("assign cann't occured in arithmetic expression");
		}
		
		obj = toValue(env, obj);
		if (obj instanceof Integer == false && obj instanceof Float == false)
			throw new Exception("number expected, but " + obj + " founded");
		
		return obj;
	}
}
