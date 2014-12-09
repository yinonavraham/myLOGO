package ynn.mylogo.model.runtime;

public interface RuntimeModelVisitor
{
	
	void visit(Action action);
	
	void visit(CompositeAction action);
	
	void visit(ActionList value);
	
	void visit(NumericValue value);
	
	void visit(VariableArgumentValue value);
	
}
