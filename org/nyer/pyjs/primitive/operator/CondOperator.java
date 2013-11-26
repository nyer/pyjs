package org.nyer.pyjs.primitive.operator;

import java.util.List;

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
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		Boolean arg = checkBoolOperand(env, arguments.get(0));
		if (arg)
			return trueInstrument.invoke(env);
		else
			return falseInstrument.invoke(env);
	}

}
