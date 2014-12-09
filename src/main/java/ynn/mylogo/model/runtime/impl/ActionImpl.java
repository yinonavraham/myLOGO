package ynn.mylogo.model.runtime.impl;

import java.util.ArrayList;
import java.util.List;

import ynn.mylogo.common.Utils;
import ynn.mylogo.model.designtime.ActionDefinition;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.ArgumentValue;
import ynn.mylogo.model.runtime.RuntimeModelVisitor;

public class ActionImpl extends AbstractRuntimeModelElement implements Action
{
	
	private List<ArgumentValue<?>> _values;
	private final ActionDefinition _definition;
	
	public ActionImpl(ActionDefinition definition)
	{
		_values = new ArrayList<ArgumentValue<?>>();
		_definition = definition;
	}

	@Override
	public String getName()
	{
		return _definition.getName();
	}

	@Override
	public List<ArgumentValue<?>> getValues()
	{
		return _values;
	}

	@Override
	public void accept(RuntimeModelVisitor visitor)
	{
		visitor.visit(this);
	}
	
	@Override
	public String toString()
	{
		return getName() + " " + Utils.toDelimitedList(_values, " ");
	}

	@Override
	public ActionDefinition getDefinition()
	{
		return _definition;
	}
	
	@Override
	public ActionImpl clone()
	{
		ActionImpl action = (ActionImpl)super.clone();
		action._values = new ArrayList<ArgumentValue<?>>();
		for (ArgumentValue<?> value : _values)
		{
			AbstractRuntimeModelElement clone = ((AbstractRuntimeModelElement)value).clone();
			action._values.add((ArgumentValue<?>)clone);
		}
		return action;
	}

}
