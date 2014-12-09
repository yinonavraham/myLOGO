package ynn.mylogo.parser.ast;

import ynn.mylogo.parser.Token;

public class ParameterValueExpression extends AbstractValueExpression {

	public ParameterValueExpression(Token token) {
		super(token);
	}
	
	public String getName() {
		return getToken().getValue();
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
