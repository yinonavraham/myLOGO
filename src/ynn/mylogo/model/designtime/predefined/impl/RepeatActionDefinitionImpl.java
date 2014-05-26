package ynn.mylogo.model.designtime.predefined.impl;

import ynn.mylogo.model.designtime.ArgumentDefinition;
import ynn.mylogo.model.designtime.impl.ActionDefinitionImpl;
import ynn.mylogo.model.designtime.impl.ArgumentDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.ActionList;
import ynn.mylogo.model.runtime.NumericValue;
import ynn.mylogo.model.runtime.predefined.impl.RepeatActionImpl;

public class RepeatActionDefinitionImpl extends ActionDefinitionImpl
{

	public RepeatActionDefinitionImpl()
	{
		super("REPEAT");
		ArgumentDefinition argTimes = new ArgumentDefinitionImpl("T", NumericValue.class);
		getArguments().add(argTimes);
		ArgumentDefinition argActions = new ArgumentDefinitionImpl("A", ActionList.class);
		getArguments().add(argActions);
	}
	
	@Override
	public Action createRuntimeAction()
	{
		return new RepeatActionImpl(this);
	}

}
