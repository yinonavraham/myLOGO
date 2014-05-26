package ynn.mylogo.parser.ast;

import ynn.mylogo.parser.Token;

public class IntegerValueExpression extends AbstractValueExpression {
	
	private final int value;

	public IntegerValueExpression(Token token) {
		super(token);
		value = Integer.parseInt(token.getValue());
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
