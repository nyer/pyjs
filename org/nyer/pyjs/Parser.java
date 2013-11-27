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
import org.nyer.pyjs.primitive.type.PjIdentifierRef;
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
	public List<Instrument> parse(String code) throws Exception {
		this.context = new Context();
		tokenizer = new Tokenizer(code);
		return parse();
	}
	
	private List<Instrument> parse() throws Exception {
		return statements();
	}
	
	private List<Instrument> statements() throws Exception {
		List<Instrument> instruments = new ArrayList<Instrument>();
		
		while (tokenizer.hasNext()) {
			Instrument instrument = statement();
			if (instrument != null)
				instruments.add(instrument);
		}
		
		return instruments;
	}
	
	private Instrument statement() throws Exception {
		Instrument instrument = null;
		 if (tokenizer.peek(IF)) {
				instrument = condClause();
				Instrument prevClause = instrument;
				Instrument falseInstrument = null;
				while (tokenizer.peek(ELIF)) {
					falseInstrument = condClause();
					Cond cond = (Cond) prevClause.getFun();
					cond.setFalseInstrument(falseInstrument);
					prevClause = falseInstrument;
				}
				
				if (tokenizer.peek(ELSE)) {
					falseInstrument = condClause();
					Cond cond = (Cond) prevClause.getFun();
					cond.setFalseInstrument(falseInstrument);
				}
			} else if (tokenizer.peek(WHILE)) {
				context.enterLoop();
				instrument = condClause();
				context.exitLoop();
			} else if (tokenizer.accept(FOR)) {
				context.enterLoop();
				tokenizer.expect(OPEN_PARENTHESIS);
				List<Instrument> init = new ArrayList<Instrument>();
				if (tokenizer.peek(SEMICOLON) == false) {
					init.add(expression());
					while (tokenizer.accept(COMMA)) {
						init.add(expression());
					}
				}
				tokenizer.expect(SEMICOLON);
				
				List<Instrument> condition = new ArrayList<Instrument>();
				if (tokenizer.peek(SEMICOLON) == false) {
					condition.add(expression());
					while (tokenizer.accept(COMMA)) {
						condition.add(expression());
					}
				}
				tokenizer.expect(SEMICOLON);
				
				List<Instrument> three = new ArrayList<Instrument>();
				if (tokenizer.peek(CLOSE_PARENTHESIS) == false) {
					three.add(expression());
					while (tokenizer.accept(COMMA)) {
						three.add(expression());
					}
				}
				tokenizer.expect(CLOSE_PARENTHESIS);
				
				List<Instrument> body = braceBody();
				
				instrument = new Instrument(new For(condition, three, body), init);
				context.exitLoop();
			} else if (tokenizer.accept(BREAK)) {
				if (context.allowBreak() == false)
					throw new Exception("illegal break statement");

				instrument = new Instrument(new Break());
			} else if (tokenizer.accept(RETURN)) {
				if (context.allowReturn() == false)
					throw new Exception("illegal return statement");
				
				Instrument returnValueInstrument = null;
				if (tokenizer.peek(SEMICOLON) == false
						&& tokenizer.peek(CLOSE_BRACE) == false)
					returnValueInstrument = expression();

				instrument = new Instrument(new Return(), returnValueInstrument);
			} else
				instrument = expression();
		 
		 tokenizer.cleanSemiColon();
		 return instrument;
	}
	
	private Instrument expression() throws Exception {
		return assign();
	}
	
	private Instrument assign() throws Exception {
		Instrument instrument = logicExp();
		if (tokenizer.accept(ASSIGN)) {
			IFun fun = instrument.getFun();
			if (fun instanceof Assignable == false)
				throw new Exception("Invalid left-hand side in assignment");
			instrument = instrument.toAssign(expression());
		}
		
		return instrument;
	}
	
	private Instrument logicExp() throws Exception {
		Instrument instrument = eqExp();
		while (tokenizer.peek(AND) || tokenizer.peek(OR)) {
			if (tokenizer.accept(AND)) {
				instrument = new Instrument(new And(), instrument, eqExp());
			} else if (tokenizer.accept(OR)) {
				instrument = new Instrument(new Or(), instrument, eqExp());
			}
		}
		return instrument;
	}
	
	private Instrument eqExp() throws Exception {
		Instrument instrument = relExp();
		while (tokenizer.peek(EQ) || tokenizer.peek(NE)) {
			if (tokenizer.accept(EQ)) {
				instrument = new Instrument(new EQ(), instrument, relExp());
			} else if (tokenizer.accept(NE)) {
				instrument = new Instrument(new NE(), instrument, relExp());
			}
		}
		return instrument;
	}

	private Instrument relExp() throws Exception {
		Instrument instrument = addSubExp();
		while (tokenizer.peek(LT) || tokenizer.peek(LTE)
				|| tokenizer.peek(GT) || tokenizer.peek(GTE)) {
			if (tokenizer.accept(LT)) {
				instrument = new Instrument(new LT(), instrument, addSubExp());
			} else if (tokenizer.accept(LTE)) {
				instrument = new Instrument(new LTE(), instrument, addSubExp());
			} else if (tokenizer.accept(GT)) {
				instrument = new Instrument(new GT(), instrument, addSubExp());
			} else if (tokenizer.accept(GTE)) {
				instrument = new Instrument(new GTE(), instrument, addSubExp());
			}
		}
		return instrument;
	}
	
	private Instrument addSubExp() throws Exception {
		Instrument instrument = multiDivExp();
		while (tokenizer.peek(PLUS) || tokenizer.peek(SUB)) {
			if (tokenizer.accept(PLUS)) {
				instrument = new Instrument(new Add(), instrument, multiDivExp());
			} else if (tokenizer.accept(SUB)) {
				instrument = new Instrument(new Sub(), instrument, multiDivExp());
			}
		}
		
		return instrument;
	}
	
	private Instrument multiDivExp() throws Exception {
		Instrument instrument = prefixExp();
		while (tokenizer.peek(MULTI) || tokenizer.peek(DIV)) {
			if (tokenizer.accept(MULTI)) {
				instrument = new Instrument(new Multi(), instrument, prefixExp());
			} else if (tokenizer.accept(DIV)){
				instrument = new Instrument(new Div(), instrument, prefixExp());
			}
		}
		
		return instrument;
	}
	
	private Instrument prefixExp() throws Exception {
		Instrument instrument = null;
		if (tokenizer.accept(PLUS)) {
			instrument = new Instrument(new Add(), prefixExp());
		} else if (tokenizer.accept(SUB)) {
			instrument = new Instrument(new Sub(), prefixExp());
		} else if (tokenizer.accept(NOT)) {
			instrument = new Instrument(new Not(), prefixExp());
		} else if (tokenizer.accept(PLUSPLUS)) {
			instrument = prefixExp();
			if (instrument.getFun() instanceof Assignable == false)
				throw new Exception("variable exepcted, but found: " + instrument.getFun());
			instrument = instrument.toAssign(new Instrument(new Add(), instrument, new Instrument(PjInteger.valueOf(1))));
		} else if (tokenizer.accept(SUBSUB)) {
			instrument = prefixExp();
			if (instrument.getFun() instanceof Assignable == false)
				throw new Exception("variable exepcted, but found: " + instrument.getFun());
			instrument = instrument.toAssign(new Instrument(new Sub(), instrument, new Instrument(PjInteger.valueOf(1))));
		} else {
			instrument = suffixExp();
		}
		
		return instrument;
	}
	
	private Instrument suffixExp() throws Exception {
		Instrument instrument = valueExp();
		if (instrument.getFun().getClass() == DefFun.class)
			return instrument;
		
		if (tokenizer.peek(OPEN_BRACKET) || tokenizer.peek(OPEN_PARENTHESIS)) {
			while (tokenizer.peek(OPEN_BRACKET) || tokenizer.peek(OPEN_PARENTHESIS)) {
				// array or map access
				if (tokenizer.peek(OPEN_BRACKET)) {
					while (tokenizer.accept(OPEN_BRACKET)) {
						Instrument posInstrument = expression();
						tokenizer.expect(CLOSE_BRACKET);
						
						instrument = new Instrument(new ArrayMap(), instrument, posInstrument);
					}
				}

				// funcall
				if (tokenizer.peek(OPEN_PARENTHESIS)) {
					while (tokenizer.accept(OPEN_PARENTHESIS)) {
						instrument = new Instrument(new FunCall(), instrument, arguments());
						tokenizer.expect(CLOSE_PARENTHESIS);
					}
				}
			}
		}
		
		if (tokenizer.accept(QUESTION)) {
			Instrument trueInstrument = expression();
			tokenizer.expect(COLON);
			Instrument falseInstrument = expression();
			instrument = new Instrument(new CondOperator(trueInstrument, falseInstrument), instrument);
		}
		
		if (tokenizer.accept(PLUSPLUS)) {
			if (instrument.getFun() instanceof Assignable == false)
				throw new Exception("variable exepcted, but found: " + instrument.getFun());
			instrument = new Instrument(new Sub(), 
					instrument.toAssign(new Instrument(new Add(), instrument, new Instrument(PjInteger.valueOf(1)))),
					new Instrument(PjInteger.valueOf(1)));
		} else if (tokenizer.accept(SUBSUB)) {
			if (instrument.getFun() instanceof Assignable == false)
				throw new Exception("variable exepcted, but found: " + instrument.getFun());
			instrument = new Instrument(new Add(), 
					instrument.toAssign(new Instrument(new Sub(), instrument, new Instrument(PjInteger.valueOf(1)))),
					new Instrument(PjInteger.valueOf(1)));
		}
		return instrument;
	}
	
	private Instrument valueExp() throws Exception {
		Instrument instrument = null;
		if (tokenizer.accept(OPEN_PARENTHESIS)) {
			instrument = expression();
			tokenizer.expect(CLOSE_PARENTHESIS);
			instrument = new Instrument(new Parenthesis(), instrument);
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
				List<Instrument> funBody = new ArrayList<Instrument>();
				while (tokenizer.peek(CLOSE_BRACE) == false) {
					funBody.add(statement());
				}
				context.exitFunc();
				tokenizer.expect(CLOSE_BRACE);
				
				instrument = new Instrument(new DefFun(parameters.toArray(new String[parameters.size()]), funBody));
				if (funName != null) {
					instrument = new Instrument(new Identifier(funName)).toAssign(instrument);
				}
				
				return instrument;
			} else if ("typeof".equals(token.getStr())) {
				tokenizer.expect(OPEN_PARENTHESIS);
				Instrument exp = expression();
				if (exp.getFun() instanceof Identifier) {
					Identifier identifier = (Identifier) exp.getFun();
					exp = new Instrument(new PjIdentifierRef(identifier));
				}
				
				instrument = new Instrument(new TypeOf(), exp);
				tokenizer.expect(CLOSE_PARENTHESIS);
			} else if ("undefined".equals(token.getStr())) {
				instrument = new Instrument(new PjUndefined());
			} else {
				instrument = new Instrument(new Identifier(token.getStr()));
			}
		} else if (tokenizer.peek(BOOLEAN)) {
			Token token = tokenizer.nextToken();
			instrument = new Instrument(new PjBoolean(java.lang.Boolean.valueOf(token.getStr())));
		} else if (tokenizer.peek(INTEGER)) {
			Token token = tokenizer.nextToken();
			instrument = new Instrument(new PjInteger(java.lang.Integer.valueOf(token.getStr())));
		} else if (tokenizer.peek(FLOAT)) {
			Token token = tokenizer.nextToken();
			instrument = new Instrument(new PjFloat(java.lang.Float.valueOf(token.getStr())));
		} else if (tokenizer.peek(STRING)) {
			Token token = tokenizer.nextToken();
			instrument = new Instrument(new PjString(token.getStr()));
		} else if (tokenizer.accept(OPEN_BRACKET)) {
			// array
			List<Instrument> arguments = new ArrayList<Instrument>();
			if (tokenizer.peek(CLOSE_BRACKET) == false) {
				arguments.add(expression());
				while (tokenizer.accept(COMMA)) {
					arguments.add(expression());
				}
			}
			tokenizer.expect(CLOSE_BRACKET);
			instrument = new Instrument(new PjArray(), arguments);
		} else if (tokenizer.accept(OPEN_BRACE)) {
			// map
			List<Instrument> arguments = new ArrayList<Instrument>();
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
			instrument = new Instrument(new PjMap(), arguments);
		}

		if (instrument != null)
			return instrument;
		
		if (tokenizer.hasNext() == false)
			throw new Exception("unexpected EOF");
		throw new Exception("unexpected token: " + tokenizer.nextToken());
	}

	private List<Instrument> arguments() throws Exception {
		List<Instrument> arguments = new ArrayList<Instrument>();
		if (tokenizer.peek(CLOSE_PARENTHESIS) == false) {
			arguments.add(expression());
			while (tokenizer.accept(COMMA)) {
				arguments.add(expression());
			}
		}
		
		return arguments;
	}

	private List<Instrument> braceBody() throws Exception {
		List<Instrument> body = new ArrayList<Instrument>();
		if (tokenizer.accept(OPEN_BRACE)) {
			while (tokenizer.peek(CLOSE_BRACE) == false) {
				body.add(statement());
			}
			tokenizer.expect(CLOSE_BRACE);
		} else {
			body.add(statement());
		}
		
		return body;
	}
	
	private Instrument condClause() throws Exception {
		Token token = tokenizer.nextToken();
		TokenType tokenType = token.getTokenType();
		Instrument condition = null;
		
		if (tokenType== ELSE) {
			condition = new Instrument(PjBoolean.True);
		} else if (tokenType == IF || tokenType == ELIF || tokenType == WHILE) {
			tokenizer.expect(OPEN_PARENTHESIS);
			condition = expression();
			tokenizer.expect(CLOSE_PARENTHESIS);
		} else
			throw new Exception("unexpected token,  " + token);
		
		List<Instrument> trueInstruments = braceBody();
		
		if (token.getTokenType() == WHILE) 
			return new Instrument(new While(condition, trueInstruments));
		else
			return new Instrument(new Cond(trueInstruments, null), condition);
	}
	
	public static void main(String[] args) throws Exception {
		String code = "a = 3; b = a + 5; c = function(e) {println(e)}; c(b)";
		
		Parser parser = new Parser();
		List<Instrument> instruments = parser.parse(code);
		for (Instrument instrument : instruments) {
			System.out.println(instrument);
		}
	}
}
