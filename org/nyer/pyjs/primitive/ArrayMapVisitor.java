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
import java.util.Map;

import org.nyer.pyjs.Assignable;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjArray;
import org.nyer.pyjs.primitive.type.PjInteger;
import org.nyer.pyjs.primitive.type.PjMap;
import org.nyer.pyjs.primitive.type.Value;

public class ArrayMapVisitor extends AbstractFun implements Assignable {

	public ArrayMapVisitor() {
		super(new String[] {"array", "integer expr"});
	}

	@Override
	public IFun invoke(Env env, IFun[] arguments) throws Exception {
		IFun obj = arguments[0];
		if (obj instanceof PjArray == false && obj instanceof PjMap == false)
			throw new Exception("bracket access can only apply to array or map, " + obj);

		IFun idx = arguments[1];
		if (idx instanceof Value == false)
			throw new Exception("key must be an value, " + idx);
		
		Value key = (Value) idx;
		if (obj instanceof PjArray) {
			if (key instanceof PjInteger == false)
				throw new Exception("the subscript must be a integer, but found: " + idx);
			
			List<IFun> value = ((PjArray)obj).getValue();
			int idxV = ((PjInteger)key).getValue();
			if (idxV >= value.size())
				throw new Exception("Index out of bounds, index: " + idxV + ", size: " + value.size());
			
			return value.get(idxV);
		} else {
			Map<Value, IFun> map = ((PjMap)obj).getValue();
			return map.get(key);
		}
	}

	@Override
	public IFun assign(Env env, IFun[] arguments) throws Exception {
		IFun obj = arguments[0];
		if (obj instanceof PjArray == false && obj instanceof PjMap == false)
			throw new Exception("bracket access can only apply to array or map, " + obj);

		IFun right = arguments[2];
		IFun idx = arguments[1];
		if (idx instanceof Value == false)
			throw new Exception("key must be an value, " + idx);
		
		Value key = (Value) idx;
		if (obj instanceof PjArray) {
			if (key instanceof PjInteger == false)
				throw new Exception("the subscript must be a integer, but found: " + idx);
			
			List<IFun> value = ((PjArray)obj).getValue();
			int idxV = ((PjInteger)key).getValue();
			if (idxV >= value.size())
				throw new Exception("Index out of bounds, index: " + idxV + ", size: " + value.size());
			
			return value.set(idxV, right);
		} else {
			Map<Value, IFun> map = ((PjMap)obj).getValue();
			map.put(key, right);
		}
		
		return right;
	}
}
