package projectx.engine.terminal.TerminalExceptions;

import projectx.engine.terminal.Terminal;

public class ProcsNotFoundException extends Exception {
	public ProcsNotFoundException(String errorMessage, Terminal terminal) {
		super(errorMessage);
		terminal.print(errorMessage);
	}

}
