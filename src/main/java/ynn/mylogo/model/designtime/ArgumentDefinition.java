package ynn.mylogo.model.designtime;

import ynn.mylogo.model.NameProvider;
import ynn.mylogo.model.runtime.ArgumentValue;

public interface ArgumentDefinition extends NameProvider
{
	
	Class<? extends ArgumentValue<?>> getValueType();
	
	@SuppressWarnings("rawtypes")
	boolean isValueTypeSupported(Class<? extends ArgumentValue> class1);
	
}
