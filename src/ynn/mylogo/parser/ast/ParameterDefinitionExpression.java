package ynn.mylogo.parser.ast;

import ynn.mylogo.parser.Token;

public class ParameterDefinitionExpression extends AbstractNode {

	public ParameterDefinitionExpression(Token token) {
		super(token);
	}
	
	public String getName() {
		return getToken().getValue().substring(1); // Remove leading ':'
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
