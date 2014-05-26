package ynn.mylogo.model;

import static ynn.mylogo.model.ModelUtil.normalizeActionName;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ynn.mylogo.model.designtime.ActionDefinition;
import ynn.mylogo.model.designtime.ActionDefinitionsProvider;
import ynn.mylogo.model.designtime.predefined.impl.ClearActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.GoBackwardActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.GoForwardActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.GoHomeActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.HideTurtleActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.PenDownActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.PenUpActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.RepeatActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.SetColorActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.ShowTurtleActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.TurnLeftActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.TurnRightActionDefinitionImpl;

public class ActionsRegistry implements ActionDefinitionsProvider
{
	
	private final Map<String, ActionDefinition> _actionDefs;
	
	public ActionsRegistry()
	{
		_actionDefs = new HashMap<String, ActionDefinition>();
		initActions();
	}

	private void initActions()
	{
		registerAction(new GoForwardActionDefinitionImpl());
		registerAction(new GoBackwardActionDefinitionImpl());
		registerAction(new TurnRightActionDefinitionImpl());
		registerAction(new TurnLeftActionDefinitionImpl());
		registerAction(new RepeatActionDefinitionImpl());
		registerAction(new GoHomeActionDefinitionImpl());
		registerAction(new ClearActionDefinitionImpl());
		registerAction(new PenUpActionDefinitionImpl());
		registerAction(new PenDownActionDefinitionImpl());
		registerAction(new ShowTurtleActionDefinitionImpl());
		registerAction(new HideTurtleActionDefinitionImpl());
		registerAction(new SetColorActionDefinitionImpl());
	}
	
	public void registerAction(ActionDefinition action)
	{
		_actionDefs.put(normalizeActionName(action.getName()),action);
	}

	@Override
	public Collection<ActionDefinition> getActionDefinitions()
	{
		return _actionDefs.values();
	}

	@Override
	public ActionDefinition getActionDefinition(String actionName)
	{
		return _actionDefs.get(normalizeActionName(actionName));
	}

}
