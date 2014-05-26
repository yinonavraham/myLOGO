package ynn.mylogo.model.designtime.impl;

import java.util.ArrayList;
import java.util.List;

import ynn.mylogo.common.Utils;
import ynn.mylogo.model.designtime.ActionDefinition;
import ynn.mylogo.model.designtime.ArgumentDefinition;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.impl.ActionImpl;

public class ActionDefinitionImpl implements ActionDefinition
{
	
	private final String _name;
	private final List<ArgumentDefinition> _args;
	
	public ActionDefinitionImpl(String name)
	{
		_name = name;
		_args = new ArrayList<ArgumentDefinition>();
	}

	@Override
	public String getName()
	{
		return _name;
	}

	@Override
	public List<ArgumentDefinition> getArguments()
	{
		return _args;
	}

	@Override
	public String getSignature() 
	{
		StringBuilder args = new StringBuilder();
		for (ArgumentDefinition arg : _args)
		{
			args.append(" ").append(arg.getName());
		}
		return String.format("%s%s", _name, args);
	}
	
	@Override
	public String toString()
	{
		return _name + " " + Utils.toDelimitedList(_args, " ");
	}

	@Override
	public Action createRuntimeAction()
	{
		return new ActionImpl(this);
	}

}
