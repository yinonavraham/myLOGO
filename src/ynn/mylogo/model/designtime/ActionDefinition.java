package ynn.mylogo.model.designtime;

import java.util.List;

import ynn.mylogo.model.NameProvider;
import ynn.mylogo.model.runtime.Action;

public interface ActionDefinition extends NameProvider
{
	
	List<ArgumentDefinition> getArguments();
	
	Action createRuntimeAction();

	String getSignature();
	
}
