package ynn.mylogo.parser.ast;

public interface ASTVisitor {

	void visit(Script script);

	void visit(ActionDefinitionStatement statement);

	void visit(ParameterDefinitionExpression expression);

	void visit(ErrorStatement statement);
	
	void visit(ActionCallStatement statement);
	
	void visit(IntegerValueExpression expression);

	void visit(StatementListValueExpression expression);

	void visit(ParameterValueExpression expression);

}
