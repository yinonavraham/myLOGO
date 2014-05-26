package ynn.mylogo.model.designtime.predefined.impl;

import ynn.mylogo.model.designtime.impl.ActionDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.predefined.impl.ShowTurtleActionImpl;

public class ShowTurtleActionDefinitionImpl extends ActionDefinitionImpl
{

	public ShowTurtleActionDefinitionImpl()
	{
		super("ST");
	}
	
	@Override
	public Action createRuntimeAction()
	{
		return new ShowTurtleActionImpl(this);
	}

}
