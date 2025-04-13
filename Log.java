package parcelSystem;
import java.io.*;


public class Log {
	private static Log instance;
	private StringBuilder logData;
	
	private Log() {
		logData = new StringBuilder();
	}
	
	public static Log getInstance() {
		if(instance == null) {
			instance = new Log();
		}
		return instance;
	}
	
	public void logEvent(String event) {
		logData.append(event).append("\n");
	}
	
	public void writeLogToFile(String filePath) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
			writer.write(logData.toString());
		} catch(IOException e) {
			System.err.println("Error writing log to file: " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return logData.toString();
	}

}
