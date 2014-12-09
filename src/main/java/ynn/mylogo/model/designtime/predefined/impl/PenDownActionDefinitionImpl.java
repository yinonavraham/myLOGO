package ynn.mylogo.model.designtime.predefined.impl;

import ynn.mylogo.model.designtime.impl.ActionDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.predefined.impl.PenDownActionImpl;

public class PenDownActionDefinitionImpl extends ActionDefinitionImpl
{

	public PenDownActionDefinitionImpl()
	{
		super("PD");
	}
	
	@Override
	public Action createRuntimeAction()
	{
		return new PenDownActionImpl(this);
	}

}
