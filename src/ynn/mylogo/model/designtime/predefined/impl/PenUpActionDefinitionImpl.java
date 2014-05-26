package ynn.mylogo.model.designtime.predefined.impl;

import ynn.mylogo.model.designtime.impl.ActionDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.predefined.impl.PenUpActionImpl;

public class PenUpActionDefinitionImpl extends ActionDefinitionImpl
{

	public PenUpActionDefinitionImpl()
	{
		super("PU");
	}
	
	@Override
	public Action createRuntimeAction()
	{
		return new PenUpActionImpl(this);
	}

}
