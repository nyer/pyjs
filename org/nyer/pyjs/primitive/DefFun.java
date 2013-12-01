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

public class DefFun extends AbstractFun {
	private String[] parameters;
	private IFun body;
	public DefFun(String[] parameters, IFun body) {
		this.parameters = parameters;
		this.body = body;
	}
	
	@Override
	public IFun invoke(final Env closure) throws Exception {
		IFun func =new AnonymousFun(parameters, body);
		return func;
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "function [parameters=" + Arrays.toString(parameters) + "]";
	}
}
