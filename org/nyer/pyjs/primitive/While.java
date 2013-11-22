package org.nyer.pyjs.primitive;

import java.util.Arrays;
import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.type.Boolean;
import org.nyer.pyjs.primitive.type.Void;

public class While extends ControlFun {
	private List<Instrument> trueInstruments;
	public While(List<Instrument> trueInstruments) {
		super("while", new String[] {"boolean expression"});
		this.trueInstruments = trueInstruments;
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
			while (b.getValue() == true) {
				for (int i = 0, s = trueInstruments.size();i < s;i ++) {
					Instrument instrument = trueInstruments.get(i);
					if (instrument.getFun() instanceof Break)
						return new Void();
					ret = instrument.invoke(env);
				}
				
				b = ((Boolean)toValue(env, arg));
			}
		}
		
		return ret;
	}

}
