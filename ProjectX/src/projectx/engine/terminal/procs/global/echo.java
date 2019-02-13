package projectx.engine.terminal.procs.global;

import projectx.engine.Handler;
import projectx.engine.terminal.Proc;
import projectx.engine.utils.Utils;

public class echo extends Proc{

	@Override
	public String run(byte[] byte_args, Handler handler) {
		String[] args = Utils.byteArrayToStringArray(byte_args, " ");
		return Utils.stringArrayToString(args, " ");
	}
}
