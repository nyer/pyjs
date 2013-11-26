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
