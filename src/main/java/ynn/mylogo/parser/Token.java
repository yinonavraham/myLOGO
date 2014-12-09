package ynn.mylogo.parser;

public class Token {
	
	private final String value;
	private final int start;
	private final int row;
	private final int col;
	private final TokenType type;
	
	public Token(String value, TokenType type, int start, int row, int col) {
		this.value = value;
		this.type = type;
		this.start = start;
		this.row = row;
		this.col = col;
	}
	
	public String getValue() {
		return value;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return col;
	}
	
	@Override
	public String toString() {
		return String.format("'%s':%s [row=%d, col=%s]", value, type, row, col);
	}

}
