package ynn.mylogo.model.designtime.predefined.impl;

import ynn.mylogo.model.designtime.impl.ActionDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.predefined.impl.HideTurtleActionImpl;

public class HideTurtleActionDefinitionImpl extends ActionDefinitionImpl
{

	public HideTurtleActionDefinitionImpl()
	{
		super("HT");
	}
	
	@Override
	public Action createRuntimeAction()
	{
		return new HideTurtleActionImpl(this);
	}

}
