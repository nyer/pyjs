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
import org.nyer.pyjs.primitive.type.Value;

public class FunCall extends AbstractFun {
	public FunCall() {
		super(null);
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		IFun func = arguments.get(0);
		if (func instanceof Value)
			throw new Exception("value cannot be invoked, " + func);
		
		return func.invoke(env, arguments.subList(1, arguments.size()));
	}
}
