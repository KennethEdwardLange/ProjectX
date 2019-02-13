package projectx.engine.terminal.TerminalExceptions;

import projectx.engine.terminal.Terminal;

public class ProcConflictException extends Exception {
	public ProcConflictException(String proc1, String proc2, Terminal terminal) {
		super(errorMessage(proc1, proc2));
		terminal.print(errorMessage(proc1, proc2));
	}
	
	private static String errorMessage(String proc1, String proc2) {
		String s = "";
		s += 	"ProcConflictException: multiple procs with the same name" + 
				proc2 + "WILL be loaded" +
				proc1 + "WILL NOT be loaded" + 
				"resolve this conflict to get access to " + proc1;
		return s;
	}
}
