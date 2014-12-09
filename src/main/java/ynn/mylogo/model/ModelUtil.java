package ynn.mylogo.model;

public class ModelUtil {
	
	public static String normalizeActionName(String name) {
		return name == null ? null : name.toUpperCase();
	}
	
	public static String normalizeVariableName(String name) {
		return name == null ? null : name.toUpperCase();
	}

}
