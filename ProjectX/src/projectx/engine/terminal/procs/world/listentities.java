package projectx.engine.terminal.procs.world;

import projectx.engine.Handler;
import projectx.engine.terminal.Proc;
import projectx.engine.utils.Utils;

public class listentities extends Proc{
	@Override
	public String run(byte[] byte_args, Handler handler) {
		String[] args = Utils.byteArrayToStringArray(byte_args, " ");
		try {
			return handler.getWorld().getEntityManager().toString();
		}catch(Exception e) {return "[]";}
	}
}
