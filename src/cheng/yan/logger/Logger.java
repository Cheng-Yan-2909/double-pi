package cheng.yan.logger;

public class Logger {

	private Logger() {
		
	}
	
	private final static StringBuilder sb = new StringBuilder();
	
	public static void logln(String s) {
		log(s + "\n");
	}
	
	public static void log(String s) {
		sb.append(s);
	}
	
	public static void write() {
		System.out.println(sb);
		sb.delete(0, sb.length()-1);
	}
}
