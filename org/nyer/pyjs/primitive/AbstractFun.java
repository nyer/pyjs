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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public abstract class AbstractFun implements IFun {
	public String[] parameters;
	
	public AbstractFun(String[] parameters) {
		this.parameters = parameters;
	}
	
	@Override
	public String[] getParameters() {
		return parameters;
	}
	
	@Override
	public String getTypeStr(Env env) throws Exception {
		return "function";
	}
	
	
	protected List<IFun> evalArguments(Env env, List<IFun> arguments) {
		List<IFun> args = new ArrayList<IFun>(arguments.size());
		return args;
	}
	@Override
	public String toString() {
		return "Function [parameters="
				+ Arrays.toString(parameters) + "]";
	}
}
