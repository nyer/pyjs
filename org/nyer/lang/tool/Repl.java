package org.nyer.lang.tool;

import java.util.Scanner;

import org.nyer.lang.Interpreter;

public class Repl {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Interpreter interpreter = new Interpreter();
		System.out.print(">>");
		while (in.hasNext()) {
			String line = in.nextLine();
			try {
				System.out.println(interpreter.run(line));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.print(">>");
		}
	}
}
