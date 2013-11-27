/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.nyer.pyjs.primitive.ArrayMapAssign;
import org.nyer.pyjs.primitive.Identifier;
import org.nyer.pyjs.primitive.IdentifierAssign;


public class Instrument implements Element {
	private IFun fun;
	private Instrument[] arguments;
	
	public Instrument(IFun fun, List<Instrument> arguments) {
		listInit(fun, arguments);
	}
	
	public Instrument(IFun fun, Instrument argument, List<Instrument> arguments) {
		this.fun = fun;
		arguments.add(0, argument);

		listInit(fun, arguments);
	}
	
	private void listInit(IFun fun, List<Instrument> arguments) {
		this.fun = fun;
		this.arguments = (Instrument[]) Array.newInstance(Instrument.class, arguments.size());
		for (int i = 0, s = arguments.size();i < s;i ++)
			this.arguments[i] = arguments.get(i);
	}
	
	public Instrument(IFun fun, Instrument... arguments) {
		this.fun = fun;
		this.arguments = arguments;
	}
	
	public Instrument(IFun fun, Instrument[] arguments, Instrument instrument) {
		this.fun = fun;
		this.arguments = (Instrument[]) Array.newInstance(Instrument.class, arguments.length + 1);
		for (int i = 0, s = arguments.length; i < s; i ++)
			this.arguments[i] = arguments[i];
		this.arguments[this.arguments.length -1] = instrument;
	}
	
	public Instrument(IFun fun) {
		this.fun = fun;
		this.arguments = (Instrument[]) Array.newInstance(Instrument.class, 0);
	}
	
	public IFun invoke(Env env) throws Exception {
		IFun[] funArgs = (IFun[]) Array.newInstance(IFun.class, arguments.length);
		for (int i = 0, s = arguments.length;i < s;i ++)
			funArgs[i] = arguments[i].invoke(env);
		
		return fun.invoke(env, funArgs);
	}

	public Instrument toAssign(Instrument argument) {
		IFun newFun = null;
		if (fun instanceof Identifier)
			newFun = new IdentifierAssign((Identifier)fun) ;
		else {
			newFun = new ArrayMapAssign();
		}

		Instrument[] arguments = Arrays.copyOf(this.arguments, this.arguments.length + 1);
		arguments[arguments.length -1] = argument;
		
		return new Instrument(newFun, arguments);
	}
	
	@Override
	public String toString() {
		return "Instrument [fun=" + fun + "]";
	}
	
	public IFun getFun() {
		return fun;
	}
	
	public Instrument[] getArguments() {
		return arguments;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
