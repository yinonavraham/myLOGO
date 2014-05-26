package ynn.mylogo.model.designtime.predefined.impl;

import ynn.mylogo.model.designtime.impl.ActionDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.predefined.impl.ClearActionImpl;

public class ClearActionDefinitionImpl extends ActionDefinitionImpl
{

	public ClearActionDefinitionImpl()
	{
		super("CLEAR");
	}
	
	@Override
	public Action createRuntimeAction()
	{
		return new ClearActionImpl(this);
	}

}
