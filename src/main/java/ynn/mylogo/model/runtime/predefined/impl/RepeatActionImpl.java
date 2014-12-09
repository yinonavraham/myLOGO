package ynn.mylogo.model.runtime.predefined.impl;

import java.util.List;

import ynn.mylogo.model.designtime.ActionDefinition;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.RuntimeModelVisitor;
import ynn.mylogo.model.runtime.impl.ActionImpl;
import ynn.mylogo.model.runtime.predefined.PredefinedRuntimeActionVisitor;
import ynn.mylogo.model.runtime.predefined.RepeatAction;

public class RepeatActionImpl extends ActionImpl implements RepeatAction
{

	public RepeatActionImpl(ActionDefinition definition)
	{
		super(definition);
	}

	@Override
	public int getTimes()
	{
		return (Integer)getValues().get(0).getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Action> getActions()
	{
		return (List<Action>)(getValues().get(1).getValue());
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
