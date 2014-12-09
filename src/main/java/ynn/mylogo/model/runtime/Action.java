package ynn.mylogo.model.runtime;

import java.util.List;

import ynn.mylogo.model.NameProvider;
import ynn.mylogo.model.designtime.ActionDefinition;

public interface Action extends NameProvider, Cloneable, RuntimeModelVisitable
{
	
	List<ArgumentValue<?>> getValues();
	
	ActionDefinition getDefinition();

}
