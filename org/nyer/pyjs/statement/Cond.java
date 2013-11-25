package org.nyer.pyjs.statement;

import java.util.Arrays;
import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.AbstractFun;
import org.nyer.pyjs.primitive.Instrument;
import org.nyer.pyjs.primitive.operator.ValueOp;
import org.nyer.pyjs.primitive.type.Boolean;
import org.nyer.pyjs.primitive.type.Void;

public class Cond extends ValueOp {
	private List<Instrument> trueInstruments;
	private Instrument falseInstrument;
	public Cond(List<Instrument> trueInstruments, Instrument falseInstrument) {
		super("cond", new String[] {"boolean"});
		this.trueInstruments = trueInstruments;
		this.falseInstrument = falseInstrument;
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		boolean condition = checkBoolOperand(env, arguments[0]);
		Object ret = new Void();
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
