package ynn.mylogo.model.runtime.impl;


public abstract class AbstractRuntimeModelElement implements Cloneable
{
	
	public AbstractRuntimeModelElement clone()
	{
		try
		{
			return (AbstractRuntimeModelElement) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException("Unexpected error: " + e.getMessage(),e);
		}
	}
	
}
