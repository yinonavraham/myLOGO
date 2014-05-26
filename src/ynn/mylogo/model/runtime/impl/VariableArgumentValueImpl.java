package ynn.mylogo.model.runtime.impl;

import ynn.mylogo.model.runtime.RuntimeModelVisitor;
import ynn.mylogo.model.runtime.VariableArgumentValue;

public class VariableArgumentValueImpl extends AbstractRuntimeModelElement implements VariableArgumentValue
{
	
	private final String _argumentName;
	
	public VariableArgumentValueImpl(String variableName)
	{
		_argumentName = variableName;
	}

	@Override
	public String getValue()
	{
		return _argumentName;
	}

	@Override
	public void accept(RuntimeModelVisitor visitor)
	{
		visitor.visit(this);
	}
	
	@Override
	public String toString()
	{
		return _argumentName.toString();
	}

}
