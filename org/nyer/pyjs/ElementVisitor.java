package org.nyer.pyjs;

import org.nyer.pyjs.primitive.ArrayMap;
import org.nyer.pyjs.primitive.ArrayMapAssign;
import org.nyer.pyjs.primitive.DefFun;
import org.nyer.pyjs.primitive.FunCall;
import org.nyer.pyjs.primitive.Identifier;
import org.nyer.pyjs.primitive.IdentifierAssign;
import org.nyer.pyjs.primitive.TypeOf;
import org.nyer.pyjs.primitive.operator.*;
import org.nyer.pyjs.primitive.type.PjArray;
import org.nyer.pyjs.primitive.type.PjBoolean;
import org.nyer.pyjs.primitive.type.PjFloat;
import org.nyer.pyjs.primitive.type.PjInteger;
import org.nyer.pyjs.primitive.type.PjMap;
import org.nyer.pyjs.primitive.type.PjString;
import org.nyer.pyjs.primitive.type.PjUndefined;
import org.nyer.pyjs.statement.*;


public interface ElementVisitor {
	void visit(ArrayMapAssign ele);
	void visit(ArrayMap ele);
	
	void visit(DefFun ele);
	void visit(FunCall ele);
	void visit(TypeOf ele);
	
	void visit(Identifier ele);
	void visit(IdentifierAssign ele);
	
	void visit(PjArray ele);
	void visit(PjBoolean ele);
	void visit(PjFloat ele);
	void visit(PjInteger ele);
	void visit(PjMap ele);
	void visit(PjString ele);
	void visit(PjUndefined ele);
	
	void visit(Add ele);
	void visit(Sub ele);
	void visit(Multi ele);
	void visit(Div ele);
	
	void visit(And ele);
	void visit(Or ele);
	
	void visit(EQ ele);
	void visit(GT ele);
	void visit(GTE ele);
	void visit(LT ele);
	void visit(LTE ele);
	void visit(NE ele);
	
	void visit(Not ele);
	
	void visit(Parenthesis ele);

	void visit(CondOperator ele);
	void visit(Cond ele);
	void visit(For ele);
	void visit(While ele);
	void visit(Break ele);
	void visit(Return ele);
}
