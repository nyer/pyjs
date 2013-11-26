package org.nyer.pyjs.tool;

import java.util.Scanner;

import org.nyer.pyjs.interpreter.Interpreter;

public class Repl {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Interpreter interpreter = new Interpreter();
		System.out.print(">>");
		while (in.hasNext()) {
			String line = in.nextLine();
			if (line.trim().length() > 0) {
				try {
					System.out.println("<< " + interpreter.run(line));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				System.out.print(">>");
			}
		}
	}
}
