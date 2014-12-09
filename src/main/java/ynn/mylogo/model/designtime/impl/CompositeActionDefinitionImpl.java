package ynn.mylogo.model.designtime.impl;

import java.util.ArrayList;
import java.util.List;

import ynn.mylogo.common.Utils;
import ynn.mylogo.model.designtime.CompositeActionDefinition;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.impl.ActionImpl;
import ynn.mylogo.model.runtime.impl.CompositeActionImpl;

public class CompositeActionDefinitionImpl extends ActionDefinitionImpl implements
	CompositeActionDefinition
{
	
	private final List<Action> _actions;

	public CompositeActionDefinitionImpl(String name)
	{
		super(name);
		_actions = new ArrayList<Action>();
	}

	@Override
	public List<Action> getActions()
	{
		return _actions;
	}
	
	@Override
	public Action createRuntimeAction()
	{
		CompositeActionImpl action = new CompositeActionImpl(this);
		for (Action subAction : getActions())
		{
			ActionImpl clone = ((ActionImpl)subAction).clone();
			action.addAction(clone);
		}
		return action;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "{ " + Utils.toDelimitedList(_actions, " ") + " }"; 
	}

}
