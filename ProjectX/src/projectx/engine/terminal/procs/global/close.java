package projectx.engine.terminal.procs.global;

import projectx.engine.Handler;
import projectx.engine.terminal.Proc;

public class close extends Proc{	
	@Override
	public String run(byte[] byte_args, Handler handler) {
		handler.getEngine().getTerminal().stop();
		return null;
	}
}
