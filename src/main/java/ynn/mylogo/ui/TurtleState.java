package ynn.mylogo.ui;

public class TurtleState implements TurtleActions, Cloneable
{
	
	private static final int INITIAL_ANGLE = 270;
	private static final double HOME_X = 0;
	private static final double HOME_Y = 0;
	private static final int INITIAL_COLOR = 0;
	
	private double _x = HOME_X;
	private double _y = HOME_Y;
	private int _angle = INITIAL_ANGLE;
	private boolean _penDown = true;
	private boolean _turtleShown = true;
	private int _color = INITIAL_COLOR;
	
	private int normalizeAngle(int angle)
	{
        if (Math.abs(angle) > 360)
        {
        	angle = angle - 360 * (angle % 360);
        }
        if (angle < 0) 
    	{
        	angle += 360;
    	}
        return angle;
	}

	@Override
	public void turnRight(int angle)
	{
		_angle = normalizeAngle(_angle + angle);
	}

	@Override
	public void turnLeft(int angle)
	{
		_angle = normalizeAngle(_angle - angle);
	}
	
	public int getAngle()
	{
		return _angle;
	}

	@Override
	public void goForward(int distance)
	{
		_x = _x + Math.cos(Math.toRadians(_angle)) * distance;
		_y = _y + Math.sin(Math.toRadians(_angle)) * distance;
	}

	@Override
	public void goBackward(int distance)
	{
		_x = _x - Math.cos(Math.toRadians(_angle)) * distance;
		_y = _y - Math.sin(Math.toRadians(_angle)) * distance;
	}
	
	public double getX()
	{
		return _x;
	}
	
	public double getY()
	{
		return _y;
	}

	@Override
	public void penDown()
	{
		_penDown = true;
	}

	@Override
	public void penUp()
	{
		_penDown = false;
	}
	
	public boolean isPenDown()
	{
		return _penDown;
	}

	@Override
	public void setColor(int color)
	{
		_color = color;
	}
	
	public int getColor()
	{
		return _color;
	}

	@Override
	public void showTurtle()
	{
		_turtleShown = true;
	}

	@Override
	public void hideTurtle()
	{
		_turtleShown = false;
	}
	
	public boolean isTurtleShown()
	{
		return _turtleShown;
	}
	
	@Override
	public void goHome()
	{
		_x = HOME_X;
		_y = HOME_Y;
		_angle = INITIAL_ANGLE;
	}

	@Override
	public void clear()
	{
		goHome();
		penDown();
		showTurtle();
		setColor(INITIAL_COLOR);
	}
	
	@Override
	public String toString()
	{
		String shown = _turtleShown ? "shown" : "hidden";
		String pen = _penDown ? "pen-down" : "pen-up";
		return "(" + _x + "," + _y + ")|" + _angle + "deg|" + shown + "|" + pen;
	}
	
	@Override
	public TurtleState clone()
	{
		try
		{
			return (TurtleState)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException("Unexpected exception: " + e.getMessage(), e);
		}
	}
	
}
