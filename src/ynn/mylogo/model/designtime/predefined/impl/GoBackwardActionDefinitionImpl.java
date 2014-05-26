package ynn.mylogo.model.designtime.predefined.impl;

import ynn.mylogo.model.designtime.ArgumentDefinition;
import ynn.mylogo.model.designtime.impl.ActionDefinitionImpl;
import ynn.mylogo.model.designtime.impl.ArgumentDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.NumericValue;
import ynn.mylogo.model.runtime.predefined.impl.GoBackwardActionImpl;

public class GoBackwardActionDefinitionImpl extends ActionDefinitionImpl
{

	public GoBackwardActionDefinitionImpl()
	{
		super("BK");
		ArgumentDefinition argDistance = new ArgumentDefinitionImpl("D", NumericValue.class);
		getArguments().add(argDistance);
	}
	
	@Override
	public Action createRuntimeAction()
	{
		return new GoBackwardActionImpl(this);
	}

}
