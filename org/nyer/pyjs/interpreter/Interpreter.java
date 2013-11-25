package org.nyer.pyjs.interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.Parser;
import org.nyer.pyjs.primitive.Instrument;
import org.nyer.pyjs.primitive.Println;
import org.nyer.pyjs.primitive.operator.Add;
import org.nyer.pyjs.primitive.operator.Div;
import org.nyer.pyjs.primitive.operator.Multi;
import org.nyer.pyjs.primitive.operator.Sub;


public class Interpreter {
	private Env env = new Env();
	private Parser parser = new Parser();
	
	public Interpreter() {
		init();
	}
	
	private void init() {
		env.put("+", new Add());
		env.put("-", new Sub());
		env.put("/", new Div());
		env.put("*", new Multi());
		env.put("println", new Println());
	}
	
	public List<Instrument> parse(String code) throws Exception {
		return parser.parse(code);
	}
	
	public Object run(List<Instrument> instruments) throws Exception {
		InterpretContext context = new InterpretContext();
		Object ret = null;
		for (Instrument instrument : instruments) {
			ret = instrument.invoke(env);
		}
		
		return ret;
	}
	
	public Object run(String code) throws Exception {
		return run(parser.parse(code));
	}
	
	public Object run(File file) throws Exception {
		StringBuffer code = new StringBuffer();
		InputStream in = new FileInputStream(file);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				code.append(line);
			}
		} finally {
			reader.close();
		}
		
		return run(code.toString());
	}
	
	public static void main(String[] args) throws Exception {
		Interpreter interpreter = new Interpreter();
		
		interpreter.run(new File("/home/leiting/test.pj"));
	}
}
