package ynn.mylogo.model.runtime;

public interface ArgumentValue<T> extends Cloneable, RuntimeModelVisitable
{
	
	T getValue();
	
}
