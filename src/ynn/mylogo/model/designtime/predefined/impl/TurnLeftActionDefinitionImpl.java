package ynn.mylogo.model.designtime.predefined.impl;

import ynn.mylogo.model.designtime.ArgumentDefinition;
import ynn.mylogo.model.designtime.impl.ActionDefinitionImpl;
import ynn.mylogo.model.designtime.impl.ArgumentDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.NumericValue;
import ynn.mylogo.model.runtime.predefined.impl.TurnLeftActionImpl;

public class TurnLeftActionDefinitionImpl extends ActionDefinitionImpl
{

	public TurnLeftActionDefinitionImpl()
	{
		super("LT");
		ArgumentDefinition argAngle = new ArgumentDefinitionImpl("A", NumericValue.class);
		getArguments().add(argAngle);
	}
	
	@Override
	public Action createRuntimeAction()
	{
		return new TurnLeftActionImpl(this);
	}

}
