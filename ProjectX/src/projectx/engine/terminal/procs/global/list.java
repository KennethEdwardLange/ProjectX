package projectx.engine.terminal.procs.global;

import projectx.engine.Handler;
import projectx.engine.terminal.Proc;
import projectx.engine.utils.Utils;

public class list extends Proc{
	@Override
	public String run(byte[] byte_args, Handler handler) {
		String[] args = Utils.byteArrayToStringArray(byte_args, " ");
		switch(args[0]) {
			case "--p":
				return handler.getEngine().getTerminal().getCommandParser().mapToString().replaceAll(", ", ",\n");
			case "--e":
				return handler.getWorld().getEntityManager().getCreatures().toString().replaceAll("], ", "],\n");
			case "--c":
				return handler.getWorld().getEntityManager().getEntities().toString().replaceAll("], ", "],\n");
			default:
			return "invalid arguments, see \'man list\' for list options";
		}
	}
}
