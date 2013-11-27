/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs.interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.Instrument;
import org.nyer.pyjs.Parser;
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
