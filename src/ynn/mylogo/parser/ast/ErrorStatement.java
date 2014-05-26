package ynn.mylogo.parser.ast;

import ynn.mylogo.parser.Token;

public class ErrorStatement extends AbstractStatement {
	
	private final String message;

	public ErrorStatement(Token token, String message) {
		super(token);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return String.format("%s(error statement)", getToken().getValue());
	}

}
