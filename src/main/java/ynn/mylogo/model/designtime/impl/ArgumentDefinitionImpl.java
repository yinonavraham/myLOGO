package ynn.mylogo.model.designtime.impl;

import ynn.mylogo.common.Consts;
import ynn.mylogo.model.designtime.ArgumentDefinition;
import ynn.mylogo.model.runtime.ArgumentValue;
import ynn.mylogo.model.runtime.VariableArgumentValue;

public class ArgumentDefinitionImpl implements ArgumentDefinition
{
	
	private final String _name;
	private final Class<? extends ArgumentValue<?>> _type;
	
	public ArgumentDefinitionImpl(String name, Class<? extends ArgumentValue<?>> type)
	{
		_name = name.startsWith(Consts.ARGUMENT_NAME_PREFIX) ? name : Consts.ARGUMENT_NAME_PREFIX + name;
		_type = type;
	}

	@Override
	public String getName()
	{
		return _name;
	}

	@Override
	public Class<? extends ArgumentValue<?>> getValueType()
	{
		return _type;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isValueTypeSupported(Class<? extends ArgumentValue> type)
	{
		return VariableArgumentValue.class.isAssignableFrom(type) || _type.isAssignableFrom(type);
	}
	
	@Override
	public String toString()
	{
		return _name;
	}

}
