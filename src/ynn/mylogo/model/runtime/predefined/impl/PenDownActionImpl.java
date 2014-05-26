package ynn.mylogo.model.runtime.predefined.impl;

import ynn.mylogo.model.designtime.ActionDefinition;
import ynn.mylogo.model.runtime.RuntimeModelVisitor;
import ynn.mylogo.model.runtime.impl.ActionImpl;
import ynn.mylogo.model.runtime.predefined.PenDownAction;
import ynn.mylogo.model.runtime.predefined.PredefinedRuntimeActionVisitor;

public class PenDownActionImpl extends ActionImpl implements PenDownAction
{

	public PenDownActionImpl(ActionDefinition definition)
	{
		super(definition);
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
