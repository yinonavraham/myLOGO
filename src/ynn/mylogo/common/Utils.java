package ynn.mylogo.common;

import java.util.Collection;

public class Utils
{

	public static <T> String toDelimitedList(Collection<T> values, String delimiter)
	{
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (T value : values)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				sb.append(delimiter);
			}
			sb.append(value);
		}
		return sb.toString();
	}
	
}
