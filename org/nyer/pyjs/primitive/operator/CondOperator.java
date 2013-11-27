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
import org.nyer.pyjs.Instrument;

public class CondOperator extends ValueOp {
	private Instrument trueInstrument;
	private Instrument falseInstrument;
	
	public CondOperator(Instrument trueInstrument, Instrument falseInstrument) {
		super(new String[] {"boolean expr"});
		this.trueInstrument = trueInstrument;
		this.falseInstrument = falseInstrument;
	}

	@Override
	public IFun invoke(Env env, IFun[] arguments) throws Exception {
		Boolean arg = checkBoolOperand(env, arguments[0]);
		if (arg)
			return trueInstrument.invoke(env);
		else
			return falseInstrument.invoke(env);
	}

}
