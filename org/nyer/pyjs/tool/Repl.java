/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

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
					e.printStackTrace();
				}
				System.out.print(">>");
			}
		}
	}
}
