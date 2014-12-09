package ynn.mylogo.parser.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ynn.mylogo.parser.Token;

public class ActionDefinitionStatement extends AbstractStatement {
	
	private final Token actionNameToken;
	private final Token actionDefEndToken;
	private final List<ParameterDefinitionExpression> parameters;
	private final List<? extends AbstractStatement> statements;
	
	public ActionDefinitionStatement(Token actionDefStartToken, Token actionNameToken, Token actionDefEndToken, List<ParameterDefinitionExpression> parameters, List<? extends AbstractStatement> statements) {
		super(actionDefStartToken);
		this.actionNameToken = actionNameToken;
		this.actionDefEndToken = actionDefEndToken;
		this.parameters = parameters == null ? new ArrayList<ParameterDefinitionExpression>() : new ArrayList<ParameterDefinitionExpression>(parameters);
		this.statements = statements == null ? new ArrayList<AbstractStatement>() : new ArrayList<AbstractStatement>(statements);
	}
	
	public String getName() {
		return this.actionNameToken == null ? "<action_name>" : this.actionNameToken.getValue();
	}
	
	public Token getActionDefStartToken() {
		return getToken();
	}
	
	public Token getActionDefEndToken() {
		return actionDefEndToken;
	}
	
	public Token getActionNameToken() {
		return actionNameToken;
	}
	
	public List<ParameterDefinitionExpression> getParameters() {
		return Collections.unmodifiableList(this.parameters);
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
		StringBuilder sb;
		sb = new StringBuilder();
		for (ParameterDefinitionExpression param : this.parameters) {
			sb.append(" ").append(String.valueOf(param));
		}
		String strParams = sb.toString();
		sb = new StringBuilder();
		for (AbstractStatement statement : this.statements) {
			sb.append("\n   ").append(String.valueOf(statement));
		}
		String strStatements = sb.toString();
		return String.format("ED %s%s%s\nEND", getName(), strParams, strStatements);
	}

}
