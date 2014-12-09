package ynn.mylogo.model.runtime.impl;

import ynn.mylogo.model.runtime.NumericValue;
import ynn.mylogo.model.runtime.RuntimeModelVisitor;

public class NumericValueImpl extends AbstractRuntimeModelElement implements NumericValue
{
	
	private final Integer _value;
	
	public NumericValueImpl(Integer value)
	{
		_value = value;
	}

	@Override
	public Integer getValue()
	{
		return _value;
	}

	@Override
	public void accept(RuntimeModelVisitor visitor)
	{
		visitor.visit(this);
	}
	
	@Override
	public String toString()
	{
		return _value.toString();
	}

}
