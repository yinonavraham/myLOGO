package ynn.mylogo.model.runtime.impl;

import static ynn.mylogo.model.ModelUtil.normalizeVariableName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ynn.mylogo.model.designtime.ActionDefinition;
import ynn.mylogo.model.designtime.ArgumentDefinition;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.ActionList;
import ynn.mylogo.model.runtime.ArgumentValue;
import ynn.mylogo.model.runtime.CompositeAction;
import ynn.mylogo.model.runtime.NumericValue;
import ynn.mylogo.model.runtime.RuntimeModelVisitor;
import ynn.mylogo.model.runtime.VariableArgumentValue;

public class CompositeActionImpl extends ActionImpl implements CompositeAction
{
	
	private List<Action> _actions;
	
	public CompositeActionImpl(ActionDefinition definistion)
	{
		super(definistion);
		_actions = new ArrayList<Action>();
	}

	@Override
	public List<Action> getActions()
	{
		Map<String,ArgumentValue<?>> values = new HashMap<String, ArgumentValue<?>>();
		for (int index = 0; index < getDefinition().getArguments().size(); index++)
		{
			ArgumentDefinition arg = getDefinition().getArguments().get(index);
			values.put(normalizeVariableName(arg.getName()), getValues().get(index));
		}
		VariableSwitcher switcher = new VariableSwitcher(values);
		for (Action action : _actions)
		{
			action.accept(switcher);
		}
		return _actions;
	}
	
	@Override
	public void accept(RuntimeModelVisitor visitor)
	{
		visitor.visit(this);
	}
	
	public void addAction(Action action)
	{
		_actions.add(action);
	}
	
	@Override
	public CompositeActionImpl clone()
	{
		AbstractRuntimeModelElement clone = super.clone();
		CompositeActionImpl compAction = (CompositeActionImpl) clone;
		compAction._actions = new ArrayList<Action>();
		for (Action action : _actions)
		{
			ActionImpl actionClone = ((ActionImpl)action).clone();
			compAction._actions.add(actionClone);
		}
		return compAction;
	}
	
	private class VariableSwitcher implements RuntimeModelVisitor
	{
		
		final Map<String,ArgumentValue<?>> values;
		Action parentAction = null;
		int argIndex;
		
		VariableSwitcher(Map<String,ArgumentValue<?>> values)
		{
			this.values = values;
		}

		@Override
		public void visit(Action action)
		{
			Action parent = parentAction;
			parentAction = action;
			for (int i = 0; i < action.getValues().size(); i++)
			{
				argIndex = i;
				ArgumentValue<?> arg = action.getValues().get(i);
				arg.accept(this);
			}
			parentAction = parent;
		}

		@Override
		public void visit(CompositeAction action)
		{
			visit((Action)action);
		}

		@Override
		public void visit(ActionList value)
		{
			for (Action action : value.getValue())
			{
				((ActionImpl)action).accept(this);
			}
		}

		@Override
		public void visit(NumericValue value)
		{
		}

		@Override
		public void visit(VariableArgumentValue value)
		{
			ArgumentValue<?> arg = this.values.get(normalizeVariableName(value.getValue()));
			parentAction.getValues().set(argIndex,arg);
		}
		
	}

}
