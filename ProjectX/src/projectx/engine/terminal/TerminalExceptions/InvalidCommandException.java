package projectx.engine.terminal.TerminalExceptions;

import projectx.engine.terminal.Terminal;

public class InvalidCommandException extends Exception{
	public InvalidCommandException(String errorMessage, Terminal terminal) {
//		super(errorMessage);
		terminal.print(errorMessage);
	}
}
