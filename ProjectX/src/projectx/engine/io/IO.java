package projectx.engine.io;

import java.io.PrintWriter;
import java.io.StringWriter;

public class IO {
	
	/**
	 * Print the given input to console
	 */
	public static void println(String s) {
		String callerClassName = new Exception().getStackTrace()[1].getClassName();
		System.out.print("[" + callerClassName + "]" + s + "\n");
	}
	
	/**
	 * Print the given input to console
	 */
	public static void println(int i) {
		String callerClassName = new Exception().getStackTrace()[1].getClassName();
		System.out.print("[" + callerClassName + "]" + i + "\n");
	}
	
	/**
	 * Print the given input to console
	 */
	public static void println(Object o) {
		String callerClassName = new Exception().getStackTrace()[1].getClassName();
		System.out.print("[" + callerClassName + "]" + o.toString() + "\n");
	}
	
	/**
	 * Print the given input to console
	 */
	public static void printStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		
		String callerClassName = new Exception().getStackTrace()[1].getClassName();
		System.err.print("[" + callerClassName + "] " + exceptionAsString);
	}
	
	/**
	 * Print the given input to console
	 */
	public static void printErr(String s) {
		String callerClassName = new Exception().getStackTrace()[1].getClassName();
		System.err.print("[" + callerClassName + "]" + s);
	}
	
	/**
	 * Print the given input to console
	 */
	public static void printlnErr(String s) {
		String callerClassName = new Exception().getStackTrace()[1].getClassName();
		System.err.print("[" + callerClassName + "]" + s + "\n");
	}
}


