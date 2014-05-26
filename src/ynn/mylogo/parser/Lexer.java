package ynn.mylogo.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	
	private static final String LINE_SEP = System.getProperty("line.separator");
    private static final char NL = '\n';
	
	private	final String text;
	private final Matcher matcher;
    private int currentPos = 0;
    private int currentRow = 1;
    private int currentCol = 1;
    private Token nextToken;
    private Token currentToken;
	
	public Lexer(String text) {
		this.text = text;
		String regex = buildRegex();
		Pattern pattern = Pattern.compile(regex);
		this.matcher = pattern.matcher(text);
		this.nextToken = nextToken();
		this.currentToken = null;
	}

	private String buildRegex() {
		StringBuilder sb = new StringBuilder();
		for (TokenType tokenType : TokenType.orderedValues()) {
			if (tokenType.getPattern().isEmpty()) continue;
			sb.append(String.format("|(%s)", tokenType.getPattern()));
		}
		return sb.substring(1); // Remove the first '|'
	}
	
	public String getText() {
		return this.text;
	}
	
	
	private Token nextToken() {
		if (this.matcher.find(currentPos)) {
			if (this.matcher.start() == currentPos) {
				for (TokenType tokenType : TokenType.orderedValues()) {
					String txt = this.matcher.group(tokenType.getIndex());
					if (txt != null) {
//	                    int lexemeLength = this.matcher.end() - this.matcher.start();
	                    int lexemeLength = txt.length();
						int nlIndex = txt.lastIndexOf(NL);
	                    Token token = new Token(txt, tokenType, currentPos, this.currentRow, this.currentCol);
	                    this.currentPos += lexemeLength;
	                    if (nlIndex != -1) {
	                        this.currentRow += countNewLineSeparators(txt, nlIndex);
	                        this.currentCol = lexemeLength - (nlIndex + getNewLineSepLengthAt(nlIndex, txt)) + 1;
	                    } else {
	                        this.currentCol += lexemeLength;
	                    }
						
						return token;
					}
				}
			} else {
				String txt = this.text.substring(currentPos, this.matcher.start());
                TokenType tokenType = TokenType.UNKNOWN;
                int lexemeLength = txt.length();
				int nlIndex = txt.lastIndexOf(NL);
				Token token = new Token(txt, tokenType, currentPos, this.currentRow, this.currentCol);
                this.currentPos += lexemeLength;
                if (nlIndex != -1) {
                    this.currentRow += countNewLineSeparators(txt, nlIndex);
                    this.currentCol = lexemeLength - (nlIndex + getNewLineSepLengthAt(nlIndex, txt)) + 1;
                } else {
                    this.currentCol += lexemeLength;
                }
				
				return token;
			}
		}
		return null;
	}
	
	private int getNewLineSepLengthAt(int nlIndex, String txt) {
        if (LINE_SEP.length() == 1)
            return 1;
        int sepLen = LINE_SEP.length();
        int nlIndexInSep = LINE_SEP.indexOf(NL);
        int leftLen = sepLen - (nlIndexInSep + 1);
        if (leftLen != 0 && nlIndex + leftLen - 1 < txt.length() && LINE_SEP.substring(nlIndexInSep).equals(txt.substring(nlIndex, nlIndex + leftLen))) {
            return leftLen + 1;
        } else {
            return 1;
        }
    }
 
    /**
     * Count number of line separators
     * @param txt
     * @param nlIndex
     * @return
     */
    private static int countNewLineSeparators(String txt, int nlIndex) {
        int count = 0;
        for (int i = 0; i <= nlIndex; i++) {
            if (NL == txt.charAt(i))
                count++;
        }
        return count;
    }
    
    public Token next() {
    	this.currentToken = this.nextToken;
    	this.nextToken = nextToken();
    	return this.currentToken;
    }
    
    public Token current() {
    	return this.currentToken;
    }

	public boolean hasNext() {
		return this.nextToken != null;
	}
	
	
	public static void main(String[] args) {
		Lexer lexer = new Lexer("fd 90");
		while (lexer.hasNext())
			System.out.println(lexer.next());
	}
	
}
