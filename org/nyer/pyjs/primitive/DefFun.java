/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs.primitive;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.Instrument;
import org.nyer.pyjs.primitive.type.Void;

public class DefFun extends AbstractFun {
	protected List<Instrument> instruments;
	public DefFun(String[] parameters, List<Instrument> instruments) {
		super(parameters);
		this.instruments = instruments;
	}
	
	@Override
	public IFun invoke(final Env closure, List<IFun> arguments) throws Exception {
		IFun func = new AbstractFun(getParameters()) {
			
			@Override
			public IFun invoke(Env env, List<IFun> arguments) throws Exception {
				Env newEnv = closure.extend(getParameters(), arguments);
				IFun ret = new Void();
				List<Instrument> instruments = DefFun.this.instruments;
				for (int i = 0, s = instruments.size();i < s;i ++) {
					Instrument instrument = instruments.get(i);
					ret = instrument.invoke(newEnv);
				}
				return ret;
			}
		};
		
		return func;
	}
	
	public List<Instrument> getInstruments() {
		return instruments;
	}
}
