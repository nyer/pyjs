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

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjUndefined;

public class Println extends AbstractFun {
	public Println(IFun argument) {
		super(argument);
	}
	
	@Override
	public IFun invoke(Env env) throws Exception {
		if (arguments.length > 0)
			System.out.println(arguments[0].invoke(env));
		else
			System.out.println();
		return new PjUndefined();
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
	}
}
