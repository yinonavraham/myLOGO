package ynn.mylogo.ui.swt;

import org.eclipse.swt.widgets.Display;

public class Main
{

	/**
	 * @param args the arguments for the main method
	 */
	public static void main(String[] args)
	{
		Display display = new Display();
		MainWindow shell = new MainWindow(display);
		shell.open();
		display.dispose();
	}

}
