package org.nyer.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.nyer.lang.primitive.Instrument;
import org.nyer.lang.primitive.Println;


public class Interpreter {
	private Env env = new Env();
	private Parser parser = new Parser();
	
	public Interpreter() {
		env.put("println", new Println());
	}
	
	public List<Instrument> parse(String code) throws Exception {
		return parser.parse(code);
	}
	
	public Object run(List<Instrument> instruments) throws Exception {
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
