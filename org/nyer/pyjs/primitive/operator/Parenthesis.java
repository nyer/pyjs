/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs.primitive.operator;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public class Parenthesis extends ValueOp {

	public Parenthesis() {
		super(new String[] {"expr"});
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		return arguments.get(0);
	}
}