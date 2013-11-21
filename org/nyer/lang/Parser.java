package org.nyer.lang;

import static org.nyer.lang.TokenType.*;

import java.util.ArrayList;
import java.util.List;

import org.nyer.lang.primitive.Add;
import org.nyer.lang.primitive.Assign;
import org.nyer.lang.primitive.FunCall;
import org.nyer.lang.primitive.Identifier;
import org.nyer.lang.primitive.Instrument;
import org.nyer.lang.primitive.Integer;
import org.nyer.lang.primitive.Multi;
import org.nyer.lang.primitive.Sub;
import org.nyer.lang.primitive.Value;

public class Parser {
	private Tokenizer tokenizer;
	public List<Instrument> parse(String code) throws Exception {
		tokenizer = new Tokenizer(code);
		return parse();
	}
	
	private List<Instrument> parse() throws Exception {
		return expressions();
	}
	
	private List<Instrument> expressions() throws Exception {
		List<Instrument> instruments = new ArrayList<Instrument>();
		
		while (tokenizer.hasNext()) {
			Instrument instrument = expression();
			if (instrument != null)
				instruments.add(instrument);
		}
		
		return instruments;
	}
	
	private Instrument expression() throws Exception {
		return addSubExp();
	}
	
	
	private void checkExpression(Instrument instrument) throws Exception {
		IFun fun = instrument.getFun();
		if (fun instanceof Assign || fun instanceof DefFun)
			throw new Exception(fun.getName() + " cann't occured in arithmetic expression");
	}
	
	private Instrument addSubExp() throws Exception {
		Instrument instrument = multiExp();
		while (tokenizer.peek(ADD) || tokenizer.peek(SUB)) {
			if (tokenizer.accept(ADD)) {
				Instrument multiInstrument = multiExp();
				checkExpression(multiInstrument);
				
				Instrument addInstrument = new Instrument(new Add(), instrument, multiInstrument);
				instrument = addInstrument;
			} else if (tokenizer.accept(SUB)) {
				Instrument multiInstrument = multiExp();
				checkExpression(multiInstrument);
				
				Instrument addInstrument = new Instrument(new Sub(), instrument, multiInstrument);
				instrument = addInstrument;
			}
		}
		
		return instrument;
	}
	
	private Instrument multiExp() throws Exception {
		Instrument instrument = valueExp();
		while (tokenizer.accept(MULTI)) {
			Instrument valueInstrument = valueExp();
			checkExpression(valueInstrument);
			
			Instrument multiInstrument = new Instrument(new Multi(), instrument, valueInstrument);
			instrument = multiInstrument;
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
		} else if (tokenizer.peek(IDENTIFIER)) {
			Instrument instrument = null;
			Token token = tokenizer.nextToken();
			if (tokenizer.accept(ASSIGN)) {
				if (token.getTokenType() != IDENTIFIER) {
					throw new Exception("the left of assign must be a identifier, but found: " + token);
				}
				
				instrument = new Instrument(new Assign(), new Identifier(token.getStr()), expression());
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
					while (tokenizer.accept(DOT)) {
						parameters.add(tokenizer.expect(IDENTIFIER).getStr());
					}
				}
				tokenizer.expect(CLOSE_PARENTHESIS);
				
				tokenizer.expect(OPEN_BRACE);
				List<Instrument> funBody = new ArrayList<Instrument>();
				while (tokenizer.peek(CLOSE_BRACE) == false) {
					funBody.add(expression());
				}
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
				instrument = new Instrument(new Value(), new Identifier(token.getStr()));
			}
			
			return instrument;
		} else if (tokenizer.peek(INTEGER)) {
			Token token = tokenizer.nextToken();
			return new Instrument(new Value(), new Integer(java.lang.Integer.parseInt(token.getStr())));
		}
		
		if (tokenizer.hasNext())
			throw new Exception("unexpected EOF");
		throw new Exception("unexpected token: " + tokenizer.nextToken());
	}

	private Object[] arguments() throws Exception {
		tokenizer.expect(OPEN_PARENTHESIS);
		List<Object> arguments = new ArrayList<Object>();
		if (tokenizer.peek(CLOSE_PARENTHESIS) == false) {
			arguments.add(expression());
			while (tokenizer.accept(DOT)) {
				arguments.add(expression());
			}
		}
		tokenizer.expect(CLOSE_PARENTHESIS);
		
		return arguments.toArray();
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
