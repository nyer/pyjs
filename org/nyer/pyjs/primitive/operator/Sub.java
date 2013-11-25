package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.Env;

public class Sub extends ValueOp {
	public Sub() {
		super("-", new String[] {"op1", "op2"});
	}
	
	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object arg1 = arguments[0];
		Object arg2 = arguments[1];
		
		Object op1 = checkNumOperand(env, arg1);
		
		Object op2 = checkNumOperand(env, arg2);
		
		if (op1 instanceof Integer && op2 instanceof Integer) {
			Integer v1 = (Integer) op1;
			Integer v2 = (Integer) op2;
			
			return org.nyer.pyjs.primitive.type.Integer.valueOf(v1 - v2);
		} else {
			if (op1 instanceof Float && op2 instanceof Float) {
				Float v1 = (Float) op1;
				Float v2 = (Float) op2;

				return org.nyer.pyjs.primitive.type.Float.valueOf(v1 - v2);
			} else if (op1 instanceof Float) {
				Float v1 = (Float) op1;
				Integer v2 = (Integer) op2;

				return org.nyer.pyjs.primitive.type.Float.valueOf(v1 - v2);
			} else {
				Integer v1 = (Integer) op1;
				Float v2 = (Float) op2;
				
				return org.nyer.pyjs.primitive.type.Float.valueOf(v1 - v2);
			}
		}
	}
}
