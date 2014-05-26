package ynn.mylogo.parser;

final class ParserUtil {
	
	private ParserUtil() {}
	
	public static boolean keywordEquals(String keyword, String text) {
		if (keyword == null || keyword.trim().isEmpty()) throw new IllegalArgumentException("Keyword must not be null nor empty!");
		if (text == null) return false;
		return keyword.equalsIgnoreCase(text);
	}
	
	public static boolean argumentNameEquals(String argName, String text) {
		return keywordEquals(argName, text);
	}

}
