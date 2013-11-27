/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjFloat;
import org.nyer.pyjs.primitive.type.PjInteger;

public class Div extends ValueOp {
	public Div() {
		super(new String[] {"op1", "op2"});
	}
	
	@Override
	public IFun invoke(Env env, IFun[] arguments) throws Exception {
		Number op1 = checkNumOperand(env, arguments[0]);
		Number op2 = checkNumOperand(env, arguments[1]);
		
		if (op1 instanceof Integer && op2 instanceof Integer) {
			Integer v1 = (Integer) op1;
			Integer v2 = (Integer) op2;
			if (v2 == 0)
				throw new Exception("Divider cann't be zero");
			
			return PjInteger.valueOf(v1/v2);
		} else {
			if (op1 instanceof Float && op2 instanceof Float) {
				Float v1 = (Float) op1;
				Float v2 = (Float) op2;
				return PjFloat.valueOf(v1/v2);
			} else if (op1 instanceof Float) {
				Float v1 = (Float) op1;
				Integer v2 = (Integer) op2;

				if (v2 == 0)
					throw new Exception("Divider cann't be zero");
				return PjFloat.valueOf(v1/v2);
			} else {
				Integer v1 = (Integer) op1;
				Float v2 = (Float) op2;
				return PjFloat.valueOf(v1/v2);
			}
		}
	}
}
