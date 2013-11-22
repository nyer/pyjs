package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.type.Float;
import org.nyer.pyjs.primitive.type.Integer;

public class Sub extends NumberOp {
	public Sub() {
		super("-", new String[] {"op1", "op2"});
	}
	
	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object arg1 = arguments[0];
		Object arg2 = arguments[1];
		
		Object op1 = checkOperand(env, arg1);
		
		Object op2 = checkOperand(env, arg2);
		
		if (op1 instanceof Integer && op2 instanceof Integer) {
			Integer int1 = (Integer) op1;
			Integer int2 = (Integer) op2;
			return new Integer(int1.getValue() - int2.getValue());
		} else {
			if (op1 instanceof Float && op2 instanceof Float) {
				Float float1 = (Float) op1;
				Float float2 = (Float) op2;
				return new Float(float1.getValue() - float2.getValue());
			} else if (op1 instanceof Float) {
				Float float1 = (Float) op1;
				Integer int2 = (Integer) op2;
				
				return new Float(float1.getValue() - int2.getValue());
			} else {
				Integer int1 = (Integer) op1;
				Float float2 = (Float) op2;
				return new Float(int1.getValue() - float2.getValue());
			}
		}
	}
}
