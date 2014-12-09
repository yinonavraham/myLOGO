package ynn.mylogo.ui.swt;

import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.ActionList;
import ynn.mylogo.model.runtime.CompositeAction;
import ynn.mylogo.model.runtime.NumericValue;
import ynn.mylogo.model.runtime.RuntimeModelVisitor;
import ynn.mylogo.model.runtime.VariableArgumentValue;
import ynn.mylogo.model.runtime.predefined.ClearAction;
import ynn.mylogo.model.runtime.predefined.GoBackwardAction;
import ynn.mylogo.model.runtime.predefined.GoForwardAction;
import ynn.mylogo.model.runtime.predefined.GoHomeAction;
import ynn.mylogo.model.runtime.predefined.HideTurtleAction;
import ynn.mylogo.model.runtime.predefined.PenDownAction;
import ynn.mylogo.model.runtime.predefined.PenUpAction;
import ynn.mylogo.model.runtime.predefined.PredefinedRuntimeActionVisitable;
import ynn.mylogo.model.runtime.predefined.PredefinedRuntimeActionVisitor;
import ynn.mylogo.model.runtime.predefined.RepeatAction;
import ynn.mylogo.model.runtime.predefined.SetColorAction;
import ynn.mylogo.model.runtime.predefined.ShowTurtleAction;
import ynn.mylogo.model.runtime.predefined.TurnLeftAction;
import ynn.mylogo.model.runtime.predefined.TurnRightAction;

public class RuntimeActionsExecutor implements RuntimeModelVisitor, PredefinedRuntimeActionVisitor
{
	
	private final TurtleCanvas _turtleCanvas;
	
	public RuntimeActionsExecutor(TurtleCanvas turtleCanvas)
	{
		_turtleCanvas = turtleCanvas;
	}

	@Override
	public void visit(VariableArgumentValue value)
	{
		System.err.println("Not implemented: visit(VariableArgumentValue)");
	}
	
	@Override
	public void visit(NumericValue value)
	{
		System.err.println("Not implemented: visit(NumericValue)");
	}
	
	@Override
	public void visit(ActionList value)
	{
		for (Action action : value.getValue())
		{
			action.accept(this);
		}
	}
	
	@Override
	public void visit(CompositeAction action)
	{
		for (Action subAction : action.getActions())
		{
			subAction.accept(this);
		}
	}
	
	@Override
	public void visit(Action action)
	{
		if (action instanceof PredefinedRuntimeActionVisitable)
		{
			((PredefinedRuntimeActionVisitable)action).accept(this);
		}
		else
		{
			System.err.println("Not implemented: visit(Action)");
		}
	}
	
	@Override
	public void visit(RepeatAction action)
	{
		for (int i = 0; i < action.getTimes(); i++)
		{
			for (Action a : action.getActions())
			{
				a.accept(this);
			}
		}
	}
	
	@Override
	public void visit(TurnLeftAction action)
	{
		_turtleCanvas.turnLeft(action.getAngle());
	}
	
	@Override
	public void visit(TurnRightAction action)
	{
		_turtleCanvas.turnRight(action.getAngle());
	}
	
	@Override
	public void visit(GoBackwardAction action)
	{
		_turtleCanvas.goBackward(action.getDistance());
	}
	
	@Override
	public void visit(GoForwardAction action)
	{
		_turtleCanvas.goForward(action.getDistance());
	}

	@Override
	public void visit(GoHomeAction action)
	{
		_turtleCanvas.goHome();
	}

	@Override
	public void visit(ClearAction action)
	{
		_turtleCanvas.clear();
	}

	@Override
	public void visit(PenUpAction action)
	{
		_turtleCanvas.penUp();
	}

	@Override
	public void visit(PenDownAction action)
	{
		_turtleCanvas.penDown();
	}

	@Override
	public void visit(ShowTurtleAction action)
	{
		_turtleCanvas.showTurtle();
	}

	@Override
	public void visit(HideTurtleAction action)
	{
		_turtleCanvas.hideTurtle();
	}

	@Override
	public void visit(SetColorAction action)
	{
		_turtleCanvas.setColor(action.getColor());
	}

}
