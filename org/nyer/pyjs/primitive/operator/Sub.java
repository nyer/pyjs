package org.nyer.pyjs.primitive.operator;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjFloat;
import org.nyer.pyjs.primitive.type.PjInteger;

public class Sub extends ValueOp {
	public Sub() {
		super(new String[] {"op1", "op2"});
	}
	
	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		if (arguments.size() == 1) {
			IFun arg1 = arguments.get(0);
			Number value = checkNumOperand(env, arg1);
			if (value instanceof Integer) {
				return new PjInteger(- value.intValue());
			} else {
				return new PjFloat(- value.floatValue());
			}
		} else {
			Number op1 = checkNumOperand(env, arguments.get(0));
			Number op2 = checkNumOperand(env, arguments.get(1));
			
			if (op1 instanceof Integer && op2 instanceof Integer) {
				Integer v1 = (Integer) op1;
				Integer v2 = (Integer) op2;
				
				return PjInteger.valueOf(v1 - v2);
			} else {
				if (op1 instanceof Float && op2 instanceof Float) {
					Float v1 = (Float) op1;
					Float v2 = (Float) op2;

					return PjFloat.valueOf(v1 - v2);
				} else if (op1 instanceof Float) {
					Float v1 = (Float) op1;
					Integer v2 = (Integer) op2;

					return PjFloat.valueOf(v1 - v2);
				} else {
					Integer v1 = (Integer) op1;
					Float v2 = (Float) op2;
					
					return PjFloat.valueOf(v1 - v2);
				}
			}
		}
	}
}
