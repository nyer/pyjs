/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.nyer.pyjs.interpreter.Interpreter;

public class TestCase {
	static Interpreter interpreter;
	@BeforeClass
	public static void before() {
		interpreter = new Interpreter();
	}
	
	@Test
	public void defineVar() throws Exception {
		String code = "a = 3; println(a)";
		interpreter.run(code);
	}
	
	@Test
	public void testArithmeticExp() throws Exception {
		String code = "a = 3; b = a + 5 * 2 - 1; c = b + b; println(c)";
		interpreter.run(code);
	}
	
	@Test
	public void testCloure() throws Exception {
		String code = "a = function(e) { function() {println(e)}}; f = a(3); f()";
		interpreter.run(code);
	}
	
	@Test
	public void testAnonymous() throws Exception {
		String code = "(function (a) {println(a);}) (100)";
		interpreter.run(code);
	}
}
