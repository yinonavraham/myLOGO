package ynn.mylogo.ui;

public enum TurtleColors
{
	
	BLACK(0),
	BLUE(1),
	GREEN(2),
	CYAN(3),
	RED(4),
	MAGENTA(5),
	YELLOW(6),
	WHITE(7),
	BROWN(8),
	ORANGE(9);
	
	private final int _value;
	
	private TurtleColors(int value)
	{
		_value = value;
	}
	
	public int getValue()
	{
		return _value;
	}
	
	public static TurtleColors valueOf(int value)
	{
		value = value % values().length;
		for (TurtleColors color : values())
		{
			if (color.getValue() == value)
			{
				return color;
			}
		}
		throw new IllegalArgumentException("Color is not defined for value: " + value);
	}

}
