package org.nyer.pyjs;

import static org.nyer.pyjs.TokenType.ADD;
import static org.nyer.pyjs.TokenType.AND;
import static org.nyer.pyjs.TokenType.ASSIGN;
import static org.nyer.pyjs.TokenType.BOOLEAN;
import static org.nyer.pyjs.TokenType.BREAK;
import static org.nyer.pyjs.TokenType.CLOSE_BRACE;
import static org.nyer.pyjs.TokenType.CLOSE_PARENTHESIS;
import static org.nyer.pyjs.TokenType.COMMA;
import static org.nyer.pyjs.TokenType.DIV;
import static org.nyer.pyjs.TokenType.ELIF;
import static org.nyer.pyjs.TokenType.ELSE;
import static org.nyer.pyjs.TokenType.EQ;
import static org.nyer.pyjs.TokenType.FLOAT;
import static org.nyer.pyjs.TokenType.GT;
import static org.nyer.pyjs.TokenType.GTE;
import static org.nyer.pyjs.TokenType.IDENTIFIER;
import static org.nyer.pyjs.TokenType.IF;
import static org.nyer.pyjs.TokenType.INTEGER;
import static org.nyer.pyjs.TokenType.LT;
import static org.nyer.pyjs.TokenType.LTE;
import static org.nyer.pyjs.TokenType.MULTI;
import static org.nyer.pyjs.TokenType.NE;
import static org.nyer.pyjs.TokenType.OPEN_BRACE;
import static org.nyer.pyjs.TokenType.OPEN_PARENTHESIS;
import static org.nyer.pyjs.TokenType.OR;
import static org.nyer.pyjs.TokenType.RETURN;
import static org.nyer.pyjs.TokenType.SEMICOLON;
import static org.nyer.pyjs.TokenType.SUB;
import static org.nyer.pyjs.TokenType.WHILE;

import java.util.ArrayList;
import java.util.List;

import org.nyer.pyjs.primitive.AnonymousFun;
import org.nyer.pyjs.primitive.AnonymousFunCall;
import org.nyer.pyjs.primitive.DefFun;
import org.nyer.pyjs.primitive.FunCall;
import org.nyer.pyjs.primitive.Identifier;
import org.nyer.pyjs.primitive.Instrument;
import org.nyer.pyjs.primitive.operator.Add;
import org.nyer.pyjs.primitive.operator.And;
import org.nyer.pyjs.primitive.operator.Assign;
import org.nyer.pyjs.primitive.operator.Div;
import org.nyer.pyjs.primitive.operator.EQ;
import org.nyer.pyjs.primitive.operator.GT;
import org.nyer.pyjs.primitive.operator.GTE;
import org.nyer.pyjs.primitive.operator.LT;
import org.nyer.pyjs.primitive.operator.LTE;
import org.nyer.pyjs.primitive.operator.Multi;
import org.nyer.pyjs.primitive.operator.NE;
import org.nyer.pyjs.primitive.operator.Or;
import org.nyer.pyjs.primitive.operator.Sub;
import org.nyer.pyjs.primitive.type.Boolean;
import org.nyer.pyjs.primitive.type.Float;
import org.nyer.pyjs.primitive.type.Integer;
import org.nyer.pyjs.statement.Break;
import org.nyer.pyjs.statement.Cond;
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

				instrument = new Instrument(new Return(returnValueInstrument));
			} else
				instrument = expression();
		 
		 tokenizer.cleanSemiColon();
		 return instrument;
	}
	
	
	private Instrument expression() throws Exception {
		return logicExp();
	}
	
//	private Instrument assign() throws Exception {
//		Instrument instrument = logicExp();
//	}
	
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
		while (tokenizer.peek(ADD) || tokenizer.peek(SUB)) {
			if (tokenizer.accept(ADD)) {
				instrument = new Instrument(new Add(), instrument, multiDivExp());
			} else if (tokenizer.accept(SUB)) {
				instrument = new Instrument(new Sub(), instrument, multiDivExp());
			}
		}
		
		return instrument;
	}
	
	private Instrument multiDivExp() throws Exception {
		Instrument instrument = valueExp();
		while (tokenizer.peek(MULTI) || tokenizer.peek(DIV)) {
			if (tokenizer.accept(MULTI)) {
				instrument = new Instrument(new Multi(), instrument, valueExp());
			} else if (tokenizer.accept(DIV)){
				instrument = new Instrument(new Div(), instrument, valueExp());
			}
		}
		
		return instrument;
	}
	
	private Instrument valueExp() throws Exception {
		if (tokenizer.accept(OPEN_PARENTHESIS)) {
			Instrument instrument = expression();
			tokenizer.expect(CLOSE_PARENTHESIS);
			if (tokenizer.peek(OPEN_PARENTHESIS)) {
				// 函数直接调用
				IFun fun = instrument.getFun();
				if (fun instanceof DefFun == false)
					throw new Exception("function definition expected");
				DefFun defFun = (DefFun) fun;
				instrument = new Instrument(new AnonymousFunCall(defFun), arguments());
			}
			
			return instrument;
		} else if (tokenizer.peek(IDENTIFIER) 
				|| tokenizer.peek(ADD) || tokenizer.peek(SUB)
				|| tokenizer.peek(MULTI) || tokenizer.peek(DIV)) {
			Instrument instrument = null;
			Token token = tokenizer.nextToken();
			if (tokenizer.accept(ASSIGN)) {
				if (token.getTokenType() != IDENTIFIER) {
					throw new Exception("the left of assign must be a identifier, but found: " + token);
				}
				
				instrument = new Instrument(new Assign(), 
						new Instrument(new Identifier(token.getStr())),
						expression());
			} else if ("function".equals(token.getStr())) {
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
				
				if (funName == null) {
					// anonymous function
					instrument = new Instrument(new AnonymousFun(parameters.toArray(new String[parameters.size()]), funBody));
				} else {
					instrument = new Instrument(new DefFun(funName, parameters.toArray(new String[parameters.size()]), funBody));
				}
			} else if (tokenizer.peek(OPEN_PARENTHESIS)) {
				// a funcall
				instrument = new Instrument(new FunCall(token.getStr()), arguments());
			}else {
				instrument = new Instrument(new Identifier(token.getStr()));
			}
			
			return instrument;
		}  else if (tokenizer.peek(BOOLEAN)) {
			Token token = tokenizer.nextToken();
			return new Instrument(new Boolean(java.lang.Boolean.valueOf(token.getStr())));
		} else if (tokenizer.peek(INTEGER)) {
			Token token = tokenizer.nextToken();
			return new Instrument(new Integer(java.lang.Integer.valueOf(token.getStr())));
		} else if (tokenizer.peek(FLOAT)) {
			Token token = tokenizer.nextToken();
			return new Instrument(new Float(java.lang.Float.valueOf(token.getStr())));
		}
		
		if (tokenizer.hasNext() == false)
			throw new Exception("unexpected EOF");
		throw new Exception("unexpected token: " + tokenizer.nextToken());
	}

	private Object[] arguments() throws Exception {
		tokenizer.expect(OPEN_PARENTHESIS);
		List<Object> arguments = new ArrayList<Object>();
		if (tokenizer.peek(CLOSE_PARENTHESIS) == false) {
			arguments.add(expression());
			while (tokenizer.accept(COMMA)) {
				arguments.add(expression());
			}
		}
		tokenizer.expect(CLOSE_PARENTHESIS);
		
		return arguments.toArray();
	}

	private Instrument  condClause() throws Exception {
		Token token = tokenizer.nextToken();
		TokenType tokenType = token.getTokenType();
		Instrument condition = null;
		
		if (tokenType== ELSE) {
			condition = new Instrument(Boolean.True);
		} else if (tokenType == IF || tokenType == ELIF || tokenType == WHILE) {
			tokenizer.expect(OPEN_PARENTHESIS);
			condition = expression();
			tokenizer.expect(CLOSE_PARENTHESIS);
		} else
			throw new Exception("unexpected token,  " + token);
		
		List<Instrument> trueInstruments = new ArrayList<Instrument>();
		if (tokenizer.accept(OPEN_BRACE)) {
			while (tokenizer.peek(CLOSE_BRACE) == false) {
				trueInstruments.add(statement());
			}
			tokenizer.expect(CLOSE_BRACE);
		} else {
			trueInstruments.add(statement());
		}
		
		if (token.getTokenType() == WHILE) 
			return new Instrument(new While(trueInstruments), condition);
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
