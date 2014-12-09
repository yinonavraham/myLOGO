package ynn.mylogo.model.runtime.predefined.impl;

import ynn.mylogo.model.designtime.ActionDefinition;
import ynn.mylogo.model.runtime.RuntimeModelVisitor;
import ynn.mylogo.model.runtime.impl.ActionImpl;
import ynn.mylogo.model.runtime.predefined.PredefinedRuntimeActionVisitor;
import ynn.mylogo.model.runtime.predefined.SetColorAction;

public class SetColorActionImpl extends ActionImpl implements SetColorAction
{

	public SetColorActionImpl(ActionDefinition definition)
	{
		super(definition);
	}

	@Override
	public int getColor()
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
