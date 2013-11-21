package test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.nyer.lang.Interpreter;

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
}
