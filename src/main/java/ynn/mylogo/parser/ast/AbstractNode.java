package ynn.mylogo.parser.ast;

import ynn.mylogo.parser.Token;

public abstract class AbstractNode implements ASTVisitable {
	
	private final Token token;
	
	public AbstractNode(Token token) {
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}
	
}
