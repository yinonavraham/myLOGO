package ynn.mylogo.model.runtime.predefined;


public interface PredefinedRuntimeActionVisitor
{

	void visit(GoForwardAction action);

	void visit(GoBackwardAction action);

	void visit(TurnRightAction action);

	void visit(TurnLeftAction action);

	void visit(RepeatAction action);

	void visit(GoHomeAction action);

	void visit(ClearAction action);

	void visit(PenUpAction action);

	void visit(PenDownAction action);

	void visit(ShowTurtleAction action);

	void visit(HideTurtleAction action);

	void visit(SetColorAction action);
	
}
