package org.nyer.pyjs.primitive.operator;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjFloat;
import org.nyer.pyjs.primitive.type.PjInteger;
import org.nyer.pyjs.primitive.type.PjString;
import org.nyer.pyjs.primitive.type.Value;

public class Add extends ValueOp {
	public Add() {
		super(new String[] {"op1", "op2"});
	}
	
	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		if (arguments.size() == 1) {
			IFun arg1 = arguments.get(0);
			Number value = checkNumOperand(env, arg1);
			if (value instanceof Integer) {
				return new PjInteger(+ value.intValue());
			} else {
				return new PjFloat(+ value.floatValue());
			}
		} else {
			IFun arg1 = arguments.get(0);
			IFun arg2 = arguments.get(1);
			if (arg1 instanceof PjString || arg2 instanceof PjString) {
				checkOperand(env, arg1);
				Value v1 = (Value) arg1;
				checkOperand(env, arg2);
				Value v2 = (Value) arg2;
				
				return new PjString(String.valueOf(v1.getValue()) + String.valueOf(v2.getValue()));
			} else {
				Number op1 = checkNumOperand(env, arg1);
				Number op2 = checkNumOperand(env, arg2);
				
				if (op1 instanceof Integer && op2 instanceof Integer) {
					Integer v1 = (Integer) op1;
					Integer v2 = (Integer) op2;
					
					return PjInteger.valueOf(v1 + v2);
				} else {
					if (op1 instanceof Float && op2 instanceof Float) {
						Float v1 = (Float) op1;
						Float v2 = (Float) op2;
						
						return PjFloat.valueOf(v1 + v2);
					} else if (op1 instanceof Float) {
						Float v1 = (Float) op1;
						Integer v2 = (Integer) op2;

						return PjFloat.valueOf(v1 + v2);
					} else {
						Integer v1 = (Integer) op1;
						Float v2 = (Float) op2;
						
						return PjFloat.valueOf(v1 + v2);
					}
				}
			}
		}
	}
}
