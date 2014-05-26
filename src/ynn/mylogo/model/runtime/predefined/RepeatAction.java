package ynn.mylogo.model.runtime.predefined;

import java.util.List;

import ynn.mylogo.model.runtime.Action;

public interface RepeatAction extends Action, PredefinedRuntimeActionVisitable
{
	
	int getTimes();
	
	List<Action> getActions();

}
