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

import java.util.Arrays;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public class FunCall extends AbstractFun {
	private IFun func;
	private IFun[] arguments;
	public FunCall(IFun func, IFun[] arguments) {
		this.func = func;
		this.arguments = arguments;
	}

	@Override
	public IFun invoke(Env env) throws Exception {
		IFun func = this.func.invoke(env);
		if (func instanceof AnonymousFun == false)
			throw new Exception("function expected , but given:  " + func);
		AnonymousFun anonymousFun = (AnonymousFun) func;

		IFun[] evaled = evalArguments(env, arguments);
		
		String[] parameters = anonymousFun.getParameters();
		if (parameters.length != arguments.length)
			throw new Exception("expected " + parameters.length + ", but given " + Arrays.toString(evaled));
		
		Env closure = anonymousFun.getClosure();
		env = closure.extend(parameters, evaled);
		return func.invoke(env);
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
