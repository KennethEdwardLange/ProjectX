package projectx.engine.terminal.procs.global;

import projectx.engine.Handler;
import projectx.engine.terminal.Proc;
import projectx.engine.utils.Utils;

public class exit extends Proc{

	@Override
	public String run(byte[] byte_args, Handler handler) {
		String[] args = Utils.byteArrayToStringArray(byte_args, " ");
		if(handler == null)
			isNull(args, "error: handler is null");
		if(handler.getEngine() == null)
			isNull(args, "error: engine is null");

		handler.getEngine().exit();
		return "";
	}
	
	private String isNull(String[] args, String message) {
		if(args[0].equals("!")) {
			System.exit(0);
			return null;
		}
		else 
			return message;
	}
}
