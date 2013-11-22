package org.nyer.pyjs.primitive;

import java.util.Arrays;
import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.type.Boolean;
import org.nyer.pyjs.primitive.type.Void;

public class Cond extends ControlFun {
	private List<Instrument> trueInstruments;
	private Instrument falseInstrument;
	public Cond(List<Instrument> trueInstruments, Instrument falseInstrument) {
		super("cond", new String[] {"boolean"});
		this.trueInstruments = trueInstruments;
		this.falseInstrument = falseInstrument;
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		if (arguments == null || arguments.length == 0)
			throw new Exception("a boolean expression expected");
		if (arguments.length > 1)
			throw new Exception("a boolean expression expected, but found: " + Arrays.toString(arguments));
		
		Object arg = arguments[0];
		Object value = toValue(env, arg);
		
		if (value instanceof Boolean == false)
			throw new Exception("Boolean exepected, but found: " + value);

		Object ret = new Void();
		Boolean b = (Boolean) value;
		if (b.getValue() == true) {
			for (int i = 0, s = trueInstruments.size();i < s;i ++) {
				ret = trueInstruments.get(i).invoke(env);
			}
		} else if (falseInstrument != null) {
			ret = falseInstrument.invoke(env);
		}
		
		return ret;
	}
}
