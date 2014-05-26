package ynn.mylogo.parser.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Script implements ASTVisitable{
	
	private final List<AbstractStatement> statements;
	
	public Script(List<AbstractStatement> statements) {
		this.statements = new ArrayList<AbstractStatement>(statements);
	}
	
	public List<AbstractStatement> getStatements() {
		return Collections.unmodifiableList(statements);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (AbstractStatement statement : statements) {
			sb.append(String.valueOf(statement)).append("\n");
		}
		return sb.toString();
	}
	
}
