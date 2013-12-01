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
import org.nyer.pyjs.primitive.type.PjString;
import org.nyer.pyjs.primitive.type.PjUndefined;

public class TypeOf extends AbstractFun {
	public TypeOf(IFun expr) {
		super(new IFun[] {expr});
	}

	@Override
	public IFun invoke(Env env) throws Exception {
		IFun arg = arguments[0];
		if (arg instanceof Identifier) {
			Identifier identifier = (Identifier) arg;
			IFun obj = env.lookUp(identifier.getValue());
			if (obj == null)
				arg = new PjUndefined();
		}
		
		return new PjString(arg.getTypeStr(env));
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
