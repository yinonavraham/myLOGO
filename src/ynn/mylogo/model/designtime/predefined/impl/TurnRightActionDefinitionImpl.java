package ynn.mylogo.model.designtime.predefined.impl;

import ynn.mylogo.model.designtime.ArgumentDefinition;
import ynn.mylogo.model.designtime.impl.ActionDefinitionImpl;
import ynn.mylogo.model.designtime.impl.ArgumentDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.NumericValue;
import ynn.mylogo.model.runtime.predefined.impl.TurnRightActionImpl;

public class TurnRightActionDefinitionImpl extends ActionDefinitionImpl
{

	public TurnRightActionDefinitionImpl()
	{
		super("RT");
		ArgumentDefinition argAngle = new ArgumentDefinitionImpl("A", NumericValue.class);
		getArguments().add(argAngle);
	}
	
	@Override
	public Action createRuntimeAction()
	{
		return new TurnRightActionImpl(this);
	}

}
