package org.nyer.pyjs.statement;

import java.util.Arrays;
import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.AbstractFun;
import org.nyer.pyjs.primitive.Instrument;
import org.nyer.pyjs.primitive.operator.ValueOp;
import org.nyer.pyjs.primitive.type.PjBoolean;
import org.nyer.pyjs.primitive.type.Void;

public class While extends ValueOp {
	private List<Instrument> trueInstruments;
	public While(List<Instrument> trueInstruments) {
		super(new String[] {"boolean expression"});
		this.trueInstruments = trueInstruments;
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		boolean condition = checkBoolOperand(env, arguments.get(0));

		IFun ret = new Void();
		if (condition) {
			while (condition) {
				for (int i = 0, s = trueInstruments.size();i < s;i ++) {
					Instrument instrument = trueInstruments.get(i);
					ret = instrument.invoke(env);
				}
				condition = checkBoolOperand(env, ret);
			}
		}
		
		return ret;
	}

}
