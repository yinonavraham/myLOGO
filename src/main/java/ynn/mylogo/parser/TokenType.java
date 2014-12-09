package ynn.mylogo.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum TokenType {
	
	// General
	NAME					("[A-Za-z_]+[A-Za-z_0-9]*",		1),
	PARAMETER				(":" + NAME.getPattern(),	2),
	VALUE_INTEGER			("\\d+",					3),
	SQUARE_BRACKET_OPEN		("\\[",						4),
	SQUARE_BRACKET_CLOSE	("\\]",						5),
	WHITESPACE				("[\\s+]", 					6),
	UNKNOWN					("", 						7);

	private static final TokenType[] ORDERED_VALUES = initOrderedValues();
	
	private final String pattern;
	private final int index;
	
	private TokenType(String pattern, int index) {
		this.pattern = pattern;
		this.index = index;
	}
	
	private static TokenType[] initOrderedValues() {
		List<TokenType> values = new ArrayList<TokenType>(Arrays.asList(values()));
		Collections.sort(values, new Comparator<TokenType>() {
			@Override
			public int compare(TokenType o1, TokenType o2) {
				return new Integer(o1.index).compareTo(o2.index);
			}
		});
		return values.toArray(new TokenType[values.size()]);
	}

	public String getPattern() {
		return pattern;
	}
	
	public int getIndex() {
		return index;
	}
	
	public static TokenType[] orderedValues() {
		return ORDERED_VALUES;
	}

}
