/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs;

import static org.nyer.pyjs.TokenType.AND;
import static org.nyer.pyjs.TokenType.ASSIGN;
import static org.nyer.pyjs.TokenType.BOOLEAN;
import static org.nyer.pyjs.TokenType.BREAK;
import static org.nyer.pyjs.TokenType.CLOSE_BRACE;
import static org.nyer.pyjs.TokenType.CLOSE_BRACKET;
import static org.nyer.pyjs.TokenType.CLOSE_PARENTHESIS;
import static org.nyer.pyjs.TokenType.COLON;
import static org.nyer.pyjs.TokenType.COMMA;
import static org.nyer.pyjs.TokenType.DIV;
import static org.nyer.pyjs.TokenType.ELIF;
import static org.nyer.pyjs.TokenType.ELSE;
import static org.nyer.pyjs.TokenType.EQ;
import static org.nyer.pyjs.TokenType.FLOAT;
import static org.nyer.pyjs.TokenType.FOR;
import static org.nyer.pyjs.TokenType.GT;
import static org.nyer.pyjs.TokenType.GTE;
import static org.nyer.pyjs.TokenType.IDENTIFIER;
import static org.nyer.pyjs.TokenType.IF;
import static org.nyer.pyjs.TokenType.INTEGER;
import static org.nyer.pyjs.TokenType.LT;
import static org.nyer.pyjs.TokenType.LTE;
import static org.nyer.pyjs.TokenType.MULTI;
import static org.nyer.pyjs.TokenType.NE;
import static org.nyer.pyjs.TokenType.NOT;
import static org.nyer.pyjs.TokenType.OPEN_BRACE;
import static org.nyer.pyjs.TokenType.OPEN_BRACKET;
import static org.nyer.pyjs.TokenType.OPEN_PARENTHESIS;
import static org.nyer.pyjs.TokenType.OR;
import static org.nyer.pyjs.TokenType.PLUS;
import static org.nyer.pyjs.TokenType.PLUSPLUS;
import static org.nyer.pyjs.TokenType.QUESTION;
import static org.nyer.pyjs.TokenType.RETURN;
import static org.nyer.pyjs.TokenType.SEMICOLON;
import static org.nyer.pyjs.TokenType.STRING;
import static org.nyer.pyjs.TokenType.SUB;
import static org.nyer.pyjs.TokenType.SUBSUB;
import static org.nyer.pyjs.TokenType.WHILE;

import java.util.ArrayList;
import java.util.List;

import org.nyer.pyjs.primitive.ArrayMap;
import org.nyer.pyjs.primitive.Assignable;
import org.nyer.pyjs.primitive.Body;
import org.nyer.pyjs.primitive.DefFun;
import org.nyer.pyjs.primitive.FunCall;
import org.nyer.pyjs.primitive.Identifier;
import org.nyer.pyjs.primitive.TypeOf;
import org.nyer.pyjs.primitive.operator.Add;
import org.nyer.pyjs.primitive.operator.And;
import org.nyer.pyjs.primitive.operator.CondOperator;
import org.nyer.pyjs.primitive.operator.Div;
import org.nyer.pyjs.primitive.operator.EQ;
import org.nyer.pyjs.primitive.operator.GT;
import org.nyer.pyjs.primitive.operator.GTE;
import org.nyer.pyjs.primitive.operator.LT;
import org.nyer.pyjs.primitive.operator.LTE;
import org.nyer.pyjs.primitive.operator.Multi;
import org.nyer.pyjs.primitive.operator.NE;
import org.nyer.pyjs.primitive.operator.Not;
import org.nyer.pyjs.primitive.operator.Or;
import org.nyer.pyjs.primitive.operator.Parenthesis;
import org.nyer.pyjs.primitive.operator.Sub;
import org.nyer.pyjs.primitive.type.PjArray;
import org.nyer.pyjs.primitive.type.PjBoolean;
import org.nyer.pyjs.primitive.type.PjFloat;
import org.nyer.pyjs.primitive.type.PjInteger;
import org.nyer.pyjs.primitive.type.PjMap;
import org.nyer.pyjs.primitive.type.PjString;
import org.nyer.pyjs.primitive.type.PjUndefined;
import org.nyer.pyjs.statement.Break;
import org.nyer.pyjs.statement.Cond;
import org.nyer.pyjs.statement.For;
import org.nyer.pyjs.statement.Return;
import org.nyer.pyjs.statement.While;

public class Parser {
	private Context context;
	private Tokenizer tokenizer;
	public List<IFun> parse(String code) throws Exception {
		this.context = new Context();
		tokenizer = new Tokenizer(code);
		return parse();
	}
	
	private List<IFun> parse() throws Exception {
		return statements();
	}
	
	private List<IFun> statements() throws Exception {
		List<IFun> funs = new ArrayList<IFun>();
		
		while (tokenizer.hasNext()) {
			IFun fun = statement();
			if (fun != null)
				funs.add(fun);
		}
		
		return funs;
	}
	
	private IFun statement() throws Exception {
		IFun fun = null;
		 if (tokenizer.peek(IF)) {
			   fun = condClause();
				IFun prevClause = fun;
				IFun falseFun = null;
				while (tokenizer.peek(ELIF)) {
					falseFun = condClause();
					Cond cond = (Cond) prevClause;
					cond.setFalseBody(falseFun);
					prevClause = falseFun;
				}
				
				if (tokenizer.peek(ELSE)) {
					falseFun = condClause();
					Cond cond = (Cond) prevClause;
					cond.setFalseBody(falseFun);
				}
			} else if (tokenizer.peek(WHILE)) {
				context.enterLoop();
				fun = condClause();
				context.exitLoop();
			} else if (tokenizer.accept(FOR)) {
				context.enterLoop();
				tokenizer.expect(OPEN_PARENTHESIS);
				List<IFun> init = new ArrayList<IFun>();
				if (tokenizer.peek(SEMICOLON) == false) {
					init.add(expression());
					while (tokenizer.accept(COMMA)) {
						init.add(expression());
					}
				}
				tokenizer.expect(SEMICOLON);
				IFun first = new Body(init.toArray(new IFun[init.size()]));
				
				List<IFun> condition = new ArrayList<IFun>();
				if (tokenizer.peek(SEMICOLON) == false) {
					condition.add(expression());
					while (tokenizer.accept(COMMA)) {
						condition.add(expression());
					}
				}
				tokenizer.expect(SEMICOLON);
				IFun second = new Body(condition.toArray(new IFun[condition.size()]));
				
				List<IFun> three = new ArrayList<IFun>();
				if (tokenizer.peek(CLOSE_PARENTHESIS) == false) {
					three.add(expression());
					while (tokenizer.accept(COMMA)) {
						three.add(expression());
					}
				}
				tokenizer.expect(CLOSE_PARENTHESIS);
				IFun last = new Body(three.toArray(new IFun[three.size()]));
				
				IFun body = braceBody();
				
				fun = new For(first, second, last, body);
				context.exitLoop();
			} else if (tokenizer.accept(BREAK)) {
				if (context.allowBreak() == false)
					throw new Exception("illegal break statement");

				fun = new Break();
			} else if (tokenizer.accept(RETURN)) {
				if (context.allowReturn() == false)
					throw new Exception("illegal return statement");
				
				IFun returnValue = null;
				if (tokenizer.peek(SEMICOLON) == false
						&& tokenizer.peek(CLOSE_BRACE) == false)
					returnValue = expression();

				fun = new Return(returnValue);
			} else
				fun = expression();
		 
		 tokenizer.cleanSemiColon();
		 return fun;
	}
	
	private IFun expression() throws Exception {
		return assign();
	}
	
	private IFun assign() throws Exception {
		IFun fun = questionExp();
		if (tokenizer.accept(ASSIGN)) {
			if (fun instanceof Assignable == false)
				throw new Exception("Invalid left-hand side in assignment");
			fun = ((Assignable)fun).toAssign(assign());
		}
		
		return fun;
	}
	
	private IFun questionExp() throws Exception {
		IFun fun = orExp();
		if (tokenizer.accept(QUESTION)) {
			IFun trueFun = questionExp();
			tokenizer.expect(COLON);
			IFun falseFun = questionExp();
			fun = new CondOperator(fun, trueFun, falseFun);
		}
		
		return fun;
	}
	
	private IFun orExp() throws Exception {
		IFun fun = andExp();
		while (tokenizer.accept(OR)) {
			fun = new Or(fun, andExp());
		}
		
		return fun;
	}
	
	private IFun andExp() throws Exception {
		IFun fun = eqExp();
		while (tokenizer.accept(AND)) {
			fun = new And(fun, eqExp());
		}
		
		return fun;
	}
	
	private IFun eqExp() throws Exception {
		IFun fun = relExp();
		while (tokenizer.peek(EQ) || tokenizer.peek(NE)) {
			if (tokenizer.accept(EQ)) {
				fun = new EQ(fun, relExp());
			} else if (tokenizer.accept(NE)) {
				fun = new NE(fun, relExp());
			}
		}
		return fun;
	}

	private IFun relExp() throws Exception {
		IFun fun = addSubExp();
		if (tokenizer.peek(LT) || tokenizer.peek(LTE)
				|| tokenizer.peek(GT) || tokenizer.peek(GTE)) {
			if (tokenizer.accept(LT)) {
				fun = new LT(fun, addSubExp());
			} else if (tokenizer.accept(LTE)) {
				fun = new LTE(fun, addSubExp());
			} else if (tokenizer.accept(GT)) {
				fun = new GT( fun, addSubExp());
			} else if (tokenizer.accept(GTE)) {
				fun = new GTE(fun, addSubExp());
			}
		}
		return fun;
	}
	
	private IFun addSubExp() throws Exception {
		IFun fun = multiDivExp();
		while (tokenizer.peek(PLUS) || tokenizer.peek(SUB)) {
			if (tokenizer.accept(PLUS)) {
				fun = new Add(fun, multiDivExp());
			} else if (tokenizer.accept(SUB)) {
				fun = new Sub(fun, multiDivExp());
			}
		}
		
		return fun;
	}
	
	private IFun multiDivExp() throws Exception {
		IFun fun = prefixExp();
		while (tokenizer.peek(MULTI) || tokenizer.peek(DIV)) {
			if (tokenizer.accept(MULTI)) {
				fun = new Multi(fun, prefixExp());
			} else if (tokenizer.accept(DIV)){
				fun = new Div(fun, prefixExp());
			}
		}
		
		return fun;
	}
	
	private IFun prefixExp() throws Exception {
		IFun fun = null;
		if (tokenizer.accept(PLUS)) {
			fun = new Add( prefixExp());
		} else if (tokenizer.accept(SUB)) {
			fun = new Sub(prefixExp());
		} else if (tokenizer.accept(NOT)) {
			fun = new Not(prefixExp());
		} else if (tokenizer.accept(PLUSPLUS)) {
			fun = prefixExp();
			if (fun instanceof Assignable == false)
				throw new Exception("variable exepcted, but found: " + fun);
			Assignable assign = (Assignable) fun;
			fun = assign.toAssign(new Add(fun, PjInteger.valueOf(1)));
		} else if (tokenizer.accept(SUBSUB)) {
			fun = prefixExp();
			if (fun instanceof Assignable == false)
				throw new Exception("variable exepcted, but found: " + fun);
			Assignable assign = (Assignable) fun;
			fun = assign.toAssign(new Sub(fun, PjInteger.valueOf(1)));
		} else {
			fun = suffixExp();

			if (tokenizer.accept(PLUSPLUS)) {
				if (fun instanceof Assignable == false)
					throw new Exception("variable exepcted, but found: " + fun);
				Assignable assign = (Assignable) fun;
				fun = new Sub(assign.toAssign(new Add(fun, PjInteger.valueOf(1))), 
						PjInteger.valueOf(1));
				
			} else if (tokenizer.accept(SUBSUB)) {
				if (fun instanceof Assignable == false)
					throw new Exception("variable exepcted, but found: " + fun);
				Assignable assign = (Assignable) fun;
				fun = new Add(assign.toAssign(new Sub(fun, PjInteger.valueOf(1))),
						PjInteger.valueOf(1));
			}
		}
		
		return fun;
	}
	
	private IFun suffixExp() throws Exception {
		IFun fun = valueExp();
		if (fun.getClass() == DefFun.class)
			return fun;
		
		while (tokenizer.peek(OPEN_BRACKET) || tokenizer.peek(OPEN_PARENTHESIS)) {
			// array or map access
			if (tokenizer.peek(OPEN_BRACKET)) {
				while (tokenizer.accept(OPEN_BRACKET)) {
					IFun posFun = expression();
					tokenizer.expect(CLOSE_BRACKET);
					
					fun = new ArrayMap(fun, posFun);
				}
			}

			// funcall
			if (tokenizer.peek(OPEN_PARENTHESIS)) {
				while (tokenizer.accept(OPEN_PARENTHESIS)) {
					fun = new FunCall(fun, arguments());
					tokenizer.expect(CLOSE_PARENTHESIS);
				}
			}
		}
		
		return fun;
	}
	
	private IFun valueExp() throws Exception {
		IFun fun = null;
		if (tokenizer.accept(OPEN_PARENTHESIS)) {
			fun = expression();
			tokenizer.expect(CLOSE_PARENTHESIS);
			fun = new Parenthesis(fun);
		} else if (tokenizer.peek(IDENTIFIER) 
				|| tokenizer.peek(PLUS) || tokenizer.peek(SUB)
				|| tokenizer.peek(MULTI) || tokenizer.peek(DIV)) {
			Token token = tokenizer.nextToken();
			if ("function".equals(token.getStr())) {
				String funName = null;
				token = tokenizer.acceptWithToken(IDENTIFIER);
				// function define
				if (token != null) {
					funName = token.getStr();
				}
				tokenizer.expect(OPEN_PARENTHESIS);
				List<String> parameters = new ArrayList<String>();
				if (tokenizer.peek(CLOSE_PARENTHESIS) == false) {
					parameters.add(tokenizer.expect(IDENTIFIER).getStr());
					while (tokenizer.accept(COMMA)) {
						parameters.add(tokenizer.expect(IDENTIFIER).getStr());
					}
				}
				tokenizer.expect(CLOSE_PARENTHESIS);
				
				tokenizer.expect(OPEN_BRACE);
				context.enterFunc();
				List<IFun> funBody = new ArrayList<IFun>();
				while (tokenizer.peek(CLOSE_BRACE) == false) {
					funBody.add(statement());
				}
				context.exitFunc();
				tokenizer.expect(CLOSE_BRACE);
				IFun body = new Body(funBody);
				
				fun = new DefFun(parameters.toArray(new String[parameters.size()]), body);
				if (funName != null) {
					fun = new Identifier(funName).toAssign(fun);
				}
				
				return fun;
			} else if ("typeof".equals(token.getStr())) {
				tokenizer.expect(OPEN_PARENTHESIS);
				IFun exp = expression();
				
				fun = new TypeOf(exp);
				tokenizer.expect(CLOSE_PARENTHESIS);
			} else if ("undefined".equals(token.getStr())) {
				fun = new PjUndefined();
			} else {
				fun = new Identifier(token.getStr());
			}
		} else if (tokenizer.peek(BOOLEAN)) {
			Token token = tokenizer.nextToken();
			fun = new PjBoolean(token.getStr());
		} else if (tokenizer.peek(INTEGER)) {
			Token token = tokenizer.nextToken();
			fun = new PjInteger(token.getStr());
		} else if (tokenizer.peek(FLOAT)) {
			Token token = tokenizer.nextToken();
			fun = new PjFloat(token.getStr());
		} else if (tokenizer.peek(STRING)) {
			Token token = tokenizer.nextToken();
			fun = new PjString(token.getStr());
		} else if (tokenizer.accept(OPEN_BRACKET)) {
			// array
			List<IFun> arguments = new ArrayList<IFun>();
			if (tokenizer.peek(CLOSE_BRACKET) == false) {
				arguments.add(expression());
				while (tokenizer.accept(COMMA)) {
					arguments.add(expression());
				}
			}
			tokenizer.expect(CLOSE_BRACKET);
			fun = new PjArray(arguments.toArray(new IFun[arguments.size()]));
		} else if (tokenizer.accept(OPEN_BRACE)) {
			// map
			List<IFun> arguments = new ArrayList<IFun>();
			if (tokenizer.peek(CLOSE_BRACE) == false) {
				arguments.add(expression());
				tokenizer.expect(COLON);
				arguments.add(expression());
				while (tokenizer.accept(COMMA)) {
					arguments.add(expression());
					tokenizer.expect(COLON);
					arguments.add(expression());
				}
			}
			tokenizer.expect(CLOSE_BRACE);
			fun = new PjMap(arguments.toArray(new IFun[arguments.size()]));
		}

		if (fun != null)
			return fun;
		
		if (tokenizer.hasNext() == false)
			throw new Exception("unexpected EOF");
		throw new Exception("unexpected token: " + tokenizer.nextToken());
	}

	private IFun[] arguments() throws Exception {
		List<IFun> arguments = new ArrayList<IFun>();
		if (tokenizer.peek(CLOSE_PARENTHESIS) == false) {
			arguments.add(expression());
			while (tokenizer.accept(COMMA)) {
				arguments.add(expression());
			}
		}
		
		return arguments.toArray(new IFun[arguments.size()]);
	}

	private IFun braceBody() throws Exception {
		List<IFun> body = new ArrayList<IFun>();
		if (tokenizer.accept(OPEN_BRACE)) {
			while (tokenizer.peek(CLOSE_BRACE) == false) {
				body.add(statement());
			}
			tokenizer.expect(CLOSE_BRACE);
		} else {
			body.add(statement());
		}
		
		return new Body(body.toArray(new IFun[body.size()]));
	}
	
	private IFun condClause() throws Exception {
		Token token = tokenizer.nextToken();
		TokenType tokenType = token.getTokenType();
		IFun condition = null;
		
		if (tokenType== ELSE) {
			condition = PjBoolean.True;
		} else if (tokenType == IF || tokenType == ELIF || tokenType == WHILE) {
			tokenizer.expect(OPEN_PARENTHESIS);
			condition = expression();
			tokenizer.expect(CLOSE_PARENTHESIS);
		} else
			throw new Exception("unexpected token,  " + token);
		
		IFun trueBody = braceBody();
		
		if (token.getTokenType() == WHILE) 
			return new While(condition, trueBody);
		else
			return new Cond(condition, trueBody, null);
	}
	
	public static void main(String[] args) throws Exception {
		String code = "a = 3; b = a + 5; c = function(e) {println(e)}; c(b)";
		
		Parser parser = new Parser();
		List<IFun> funs = parser.parse(code);
		for (IFun fun : funs) {
			System.out.println(fun);
		}
	}
}
