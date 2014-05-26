package ynn.mylogo.ui.swt;

import org.eclipse.swt.widgets.Display;

public class Main
{

	/**
	 * TODO: Doc. 
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		Display display = new Display();
		MainWindow shell = new MainWindow(display);
		shell.open();
		display.dispose();
	}

}
