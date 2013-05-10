package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
	
	private static String logPath = "C:\\Users\\David Wu\\Pentago\\pentago\\logs\\";
	private static String logFile = "log.txt";
	
	private static String logName = logPath.concat(logFile);
	
	public static void logMessage(String message) {
		File logDirectory = new File(logPath);
 
		if (!logDirectory.exists()) {
			throw new RuntimeException("Log directory does not exist: " + logPath);
		}
		
		File log = new File(logName);
		try {
			if (!log.exists()) {
				log.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(logName, true));
			writer.write(message);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void clearLog() {
		File log = new File(logName);
		if (log.exists()) {
			log.delete();
		}
	}
}
