package ynn.mylogo.model.designtime;

import java.util.Collection;

public interface ActionDefinitionsProvider
{
	
	Collection<ActionDefinition> getActionDefinitions();
	
	ActionDefinition getActionDefinition(String actionName);

}
