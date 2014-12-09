package ynn.mylogo.ui;

public interface TurtleActions
{

	void turnRight(int angle);

	void turnLeft(int angle);

	void goForward(int distance);

	void goBackward(int distance);
	
	void penDown();
	
	void penUp();
	
	void setColor(int color);
	
	void showTurtle();
	
	void hideTurtle();
	
	void goHome();
	
	void clear();
	
}
