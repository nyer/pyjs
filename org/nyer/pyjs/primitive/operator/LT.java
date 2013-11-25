package org.nyer.pyjs.primitive.operator;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjBoolean;

public class LT extends ValueOp {

	public LT() {
		super(new String[] {"number", "number"});
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		Number op1 = checkNumOperand(env, arguments.get(0));
		Number op2 = checkNumOperand(env, arguments.get(1));
		
		if (op1 instanceof Integer && op2 instanceof Integer) {
			Integer v1 = (Integer) op1;
			Integer v2 = (Integer) op2;
			
			return PjBoolean.valueOf(v1 < v2);
		} else {
			if (op1 instanceof Float && op2 instanceof Float) {
				Float v1 = (Float) op1;
				Float v2 = (Float) op2;

				return PjBoolean.valueOf(v1 < v2);
			} else if (op1 instanceof Float) {
				Float v1 = (Float) op1;
				Integer v2 = (Integer) op2;

				return PjBoolean.valueOf(v1 < v2);
			} else {
				Integer v1 = (Integer) op1;
				Float v2 = (Float) op2;

				return PjBoolean.valueOf(v1 < v2);
			}
		}
	}

}
