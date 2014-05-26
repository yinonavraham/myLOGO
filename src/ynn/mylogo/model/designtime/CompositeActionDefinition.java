package ynn.mylogo.model.designtime;

import java.util.List;

import ynn.mylogo.model.runtime.Action;

public interface CompositeActionDefinition extends ActionDefinition
{

	List<Action> getActions();
	
}
