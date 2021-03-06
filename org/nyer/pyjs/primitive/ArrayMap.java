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

import java.util.Map;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjArray;
import org.nyer.pyjs.primitive.type.PjInteger;
import org.nyer.pyjs.primitive.type.PjMap;
import org.nyer.pyjs.primitive.type.PjUndefined;
import org.nyer.pyjs.primitive.type.Value;

public class ArrayMap extends AbstractFun implements Assignable {
	private IFun am;
	private IFun key;
	
	public ArrayMap(IFun am, IFun key) {
		this.am = am;
		this.key = key;
	}

	@Override
	public IFun invoke(Env env) throws Exception {
		IFun obj =am.invoke(env);
		if (obj instanceof PjArray == false && obj instanceof PjMap == false)
			throw new Exception("bracket access can only apply to array or map, " + obj);

		IFun idx = key.invoke(env);
		if (idx instanceof Value == false)
			throw new Exception("key must be an value, " + idx);
		
		Value key = (Value) idx;
		if (obj instanceof PjArray) {
			if (key instanceof PjInteger == false)
				throw new Exception("the subscript must be a integer, but found: " + idx);
			
			IFun[] value = ((PjArray)obj).getValue();
			int idxV = ((PjInteger)key).getValue();
			if (idxV >= value.length)
				throw new Exception("Index out of bounds, index: " + idxV + ", size: " + value.length);
			
			return value[idxV];
		} else {
			Map<Value, IFun> map = ((PjMap)obj).getValue();
			IFun value = map.get(key);
			if (value == null)
				value = new PjUndefined();
			return value;
		}
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public IFun toAssign(IFun value) {
		return new ArrayMapAssign(am, key, value);
	}
}
