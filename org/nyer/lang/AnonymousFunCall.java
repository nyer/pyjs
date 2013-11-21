package org.nyer.lang;

import java.util.List;

import org.nyer.lang.primitive.Instrument;
import org.nyer.lang.primitive.Void;

public class AnonymousFunCall extends AbstractFun {
	private List<Instrument> instruments;
	
	public AnonymousFunCall(DefFun fun) {
		super("anonymouse funcall", fun.getParameters());
		this.instruments = fun.getInstruments();
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		env = env.extend(getParameters(), arguments);
		Object ret = new Void();
		for (int i = 0, s = instruments.size();i < s; i ++) {
			ret = instruments.get(i).invoke(env);
		}
		return ret;
	}

}
