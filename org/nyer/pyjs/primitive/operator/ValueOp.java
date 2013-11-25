package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.AbstractFun;
import org.nyer.pyjs.primitive.type.PjBoolean;
import org.nyer.pyjs.primitive.type.PjFloat;
import org.nyer.pyjs.primitive.type.PjInteger;
import org.nyer.pyjs.primitive.type.Value;

public abstract class ValueOp extends AbstractFun {

	public ValueOp(String[] parameters) {
		super(parameters);
	}

	protected void checkOperand(Env env, IFun argument) throws Exception {
		if (argument instanceof Value == false)
			throw new Exception("Value expected, but found: " + argument);
		
	}
	protected Number checkNumOperand(Env env, IFun argument) throws Exception {
		checkOperand(env, argument);
		if (argument instanceof PjInteger || argument instanceof PjFloat)
			return (Number) ((Value)argument).getValue();
		
		throw new Exception("number expected, but " + argument + " founded");
	}
	
	protected Boolean checkBoolOperand(Env env, IFun argument) throws Exception {
		checkOperand(env, argument);
		if (argument instanceof PjBoolean)
			return (Boolean) ((Value)argument).getValue();
		
		throw new Exception("boolean expected, but " + argument + " founded");
	}
}
