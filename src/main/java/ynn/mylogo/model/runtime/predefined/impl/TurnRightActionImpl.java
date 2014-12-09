package ynn.mylogo.model.runtime.predefined.impl;

import ynn.mylogo.model.designtime.ActionDefinition;
import ynn.mylogo.model.runtime.RuntimeModelVisitor;
import ynn.mylogo.model.runtime.impl.ActionImpl;
import ynn.mylogo.model.runtime.predefined.PredefinedRuntimeActionVisitor;
import ynn.mylogo.model.runtime.predefined.TurnRightAction;

public class TurnRightActionImpl extends ActionImpl implements TurnRightAction
{

	public TurnRightActionImpl(ActionDefinition definition)
	{
		super(definition);
	}

	@Override
	public int getAngle()
	{
		return (Integer)getValues().get(0).getValue();
	}
	
	@Override
	public void accept(RuntimeModelVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public void accept(PredefinedRuntimeActionVisitor visitor)
	{
		visitor.visit(this);
	}

}
