package ynn.mylogo.parser.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ynn.mylogo.parser.Token;

public class ActionCallStatement extends AbstractStatement {
	
	private final List<? extends AbstractValueExpression> argumentValues;
	
	public ActionCallStatement(Token token, List<? extends AbstractValueExpression> argumentValues) {
		super(token);
		this.argumentValues = new ArrayList<AbstractValueExpression>(argumentValues);
	}
	
	public String getName() {
		return getToken().getValue();
	}
	
	public List<? extends AbstractValueExpression> getArgumentValues() {
		return Collections.unmodifiableList(this.argumentValues);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		StringBuilder argValues = new StringBuilder();
		for (AbstractValueExpression value : this.argumentValues) {
			argValues.append(" ").append(value);
		}
		return String.format("%s%s", getName(), argValues);
	}

}
