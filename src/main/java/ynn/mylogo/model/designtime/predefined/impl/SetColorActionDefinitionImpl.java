package ynn.mylogo.model.designtime.predefined.impl;

import ynn.mylogo.model.designtime.ArgumentDefinition;
import ynn.mylogo.model.designtime.impl.ActionDefinitionImpl;
import ynn.mylogo.model.designtime.impl.ArgumentDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.NumericValue;
import ynn.mylogo.model.runtime.predefined.impl.SetColorActionImpl;

public class SetColorActionDefinitionImpl extends ActionDefinitionImpl
{

	public SetColorActionDefinitionImpl()
	{
		super("SETC");
		ArgumentDefinition argColor = new ArgumentDefinitionImpl("C", NumericValue.class);
		getArguments().add(argColor);
	}
	
	@Override
	public Action createRuntimeAction()
	{
		return new SetColorActionImpl(this);
	}

}
