package projectx.engine.terminal.procs.global;

import projectx.engine.Handler;
import projectx.engine.terminal.*;
import projectx.engine.utils.Utils;

public class man extends Proc{
	@Override
	public String run(byte[] byte_args, Handler handler) {
		String[] args = Utils.byteArrayToStringArray(byte_args, " ");
		CommandParser cp = handler.getEngine().getTerminal().getCommandParser();
		String file 	= cp.MANWD + cp.getDirectory(args[0]) + ".man";
		if(Utils.verifyFile(file))
			return Utils.loadFileAsString(file);
		else 
			return "man file \"" + args[0] + ".man\" not found";
	}
}
