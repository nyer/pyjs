/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs.statement;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.Instrument;
import org.nyer.pyjs.primitive.operator.ValueOp;
import org.nyer.pyjs.primitive.type.Void;

public class While extends ValueOp {
	private Instrument conditionInstrument;
	private List<Instrument> trueInstruments;
	public While(Instrument conditionInstrument, List<Instrument> trueInstruments) {
		super(new String[] {"boolean expression"});
		this.conditionInstrument = conditionInstrument;
		this.trueInstruments = trueInstruments;
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		boolean condition = checkBoolOperand(env, conditionInstrument.invoke(env));

		IFun ret = new Void();
		while (condition) {
			for (int i = 0, s = trueInstruments.size();i < s;i ++) {
				Instrument instrument = trueInstruments.get(i);
				ret = instrument.invoke(env);
			}
			
			condition = checkBoolOperand(env, conditionInstrument.invoke(env));
		}
		
		return ret;
	}

}
