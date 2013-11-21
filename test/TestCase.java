package test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.nyer.pyjs.Interpreter;

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
