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

public class Cond extends ValueOp {
	private List<Instrument> trueInstruments;
	private Instrument falseInstrument;
	public Cond(List<Instrument> trueInstruments, Instrument falseInstrument) {
		super(new String[] {"boolean"});
		this.trueInstruments = trueInstruments;
		this.falseInstrument = falseInstrument;
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		boolean condition = checkBoolOperand(env, arguments.get(0));
		IFun ret = new Void();
		if (condition) {
			for (int i = 0, s = trueInstruments.size();i < s;i ++) {
				ret = trueInstruments.get(i).invoke(env);
			}
		} else if (falseInstrument != null) {
			ret = falseInstrument.invoke(env);
		}
		
		return ret;
	}
	
	public void setFalseInstrument(Instrument instrument) {
		this.falseInstrument = instrument;
	}
}
