package util;

public class ConsoleOutput {
	private static String WARNING_PREFIX = "[**] ";
	private static String ERROR_PREFIX = "[!!] ";
	
	public static void printWarning(String message) {
		StringBuilder builder = new StringBuilder();
		builder.append(WARNING_PREFIX).append(message);
		System.out.println(builder.toString());
	}
	
	public static void printError(String message) {
		StringBuilder builder = new StringBuilder();
		builder.append(ERROR_PREFIX).append(message);
		System.out.println(builder.toString());
	}
}
