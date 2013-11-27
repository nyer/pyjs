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
import org.nyer.pyjs.primitive.type.PjBoolean;

public class NE extends ValueOp {

	public NE() {
		super(new String[] {"number", "number"});
	}

	@Override
	public IFun invoke(Env env, IFun[] arguments) throws Exception {
		EQ eq = new EQ();
		boolean v = checkBoolOperand(env, eq.invoke(env, arguments)) ;
		if (v)
			return PjBoolean.False;
		return PjBoolean.True;
	}
}
