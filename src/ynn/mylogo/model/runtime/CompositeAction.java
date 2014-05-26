package ynn.mylogo.model.runtime;

import java.util.List;

public interface CompositeAction extends Action
{
	
	List<Action> getActions();

}
