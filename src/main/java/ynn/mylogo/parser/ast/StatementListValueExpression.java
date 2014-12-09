package ynn.mylogo.parser.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ynn.mylogo.parser.Token;

public class StatementListValueExpression extends AbstractValueExpression {
	
	private final Token closeBracketToken;
	private final List<? extends AbstractStatement> statements;

	public StatementListValueExpression(Token openBracketToken, Token closeBracketToken, List<? extends AbstractStatement> statements) {
		super(openBracketToken);
		this.closeBracketToken = closeBracketToken;
		this.statements = new ArrayList<AbstractStatement>(statements);
	}
	
	public Token getOpenBracketToken() {
		return getToken();
	}
	
	public Token getCloseBracketToken() {
		return this.closeBracketToken;
	}
	
	public List<? extends AbstractStatement> getStatements() {
		return Collections.unmodifiableList(this.statements);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		StringBuilder strStatementsBuilder = new StringBuilder();
		for (AbstractStatement statement : this.statements) {
			strStatementsBuilder.append(" ").append(statement);
		}
		String strStatements = strStatementsBuilder.length() > 0 ? strStatementsBuilder.substring(1) : "";
		return String.format("[%s]", strStatements);
	}

}
