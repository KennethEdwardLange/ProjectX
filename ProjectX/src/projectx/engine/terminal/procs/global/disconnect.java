package projectx.engine.terminal.procs.global;

import projectx.engine.Handler;
import projectx.engine.terminal.Proc;

public class disconnect extends Proc{
	@Override
	public String run(byte[] byte_args, Handler handler) {
		handler.getEngine().getDataProtocol().end();
		return handler.getEngine().getDataProtocol().getClient().printSocket();
	}
}
