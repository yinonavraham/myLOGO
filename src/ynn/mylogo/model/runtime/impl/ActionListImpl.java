package ynn.mylogo.model.runtime.impl;

import java.util.ArrayList;
import java.util.List;

import ynn.mylogo.common.Utils;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.ActionList;
import ynn.mylogo.model.runtime.RuntimeModelVisitor;

public class ActionListImpl extends AbstractRuntimeModelElement implements ActionList
{
	
	private List<Action> _actions;
	
	public ActionListImpl()
	{
		_actions = new ArrayList<Action>();
	}
	
	public ActionListImpl(List<Action> actions)
	{
		_actions = new ArrayList<Action>(actions);
	}

	@Override
	public List<Action> getValue()
	{
		return _actions;
	}

	@Override
	public void accept(RuntimeModelVisitor visitor)
	{
		visitor.visit(this);
	}
	
	@Override
	public String toString()
	{
		return "[" + Utils.toDelimitedList(_actions, " ") + "]";
	}
	
	@Override
	public ActionListImpl clone()
	{
		AbstractRuntimeModelElement clone = super.clone();
		ActionListImpl actionList = (ActionListImpl) clone;
		actionList._actions = new ArrayList<Action>();
		for (Action action : _actions)
		{
			ActionImpl actionClone = ((ActionImpl)action).clone();
			actionList._actions.add(actionClone);
		}
		return actionList;
	}

}
