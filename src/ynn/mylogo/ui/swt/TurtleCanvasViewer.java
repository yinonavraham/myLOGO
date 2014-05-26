package ynn.mylogo.ui.swt;

import org.eclipse.swt.widgets.Composite;

public class TurtleCanvasViewer
{

	private final TurtleCanvas _turtle;
	private Object _input = null;
	
	public TurtleCanvasViewer(Composite parent, int style)
	{
		_turtle = new TurtleCanvas(parent, style);
	}
	
	public TurtleCanvasViewer(TurtleCanvas canvas)
	{
		_turtle = canvas;
	}
	
	public void setInput(Object input)
	{
		_input = input;
	}
	
	public Object getInput()
	{
		return _input;
	}
	
}
