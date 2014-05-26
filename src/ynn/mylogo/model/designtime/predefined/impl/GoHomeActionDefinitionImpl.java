package ynn.mylogo.model.designtime.predefined.impl;

import ynn.mylogo.model.designtime.impl.ActionDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.predefined.impl.GoHomeActionImpl;

public class GoHomeActionDefinitionImpl extends ActionDefinitionImpl
{

	public GoHomeActionDefinitionImpl()
	{
		super("HOME");
	}
	
	@Override
	public Action createRuntimeAction()
	{
		return new GoHomeActionImpl(this);
	}

}
