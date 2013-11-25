package org.nyer.pyjs.statement;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.Instrument;
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
