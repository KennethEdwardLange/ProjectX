package projectx.engine.terminal.procs.global;

import projectx.engine.Handler;
import projectx.engine.terminal.Proc;
import projectx.engine.utils.Utils;

public class host extends Proc{
	@Override
	public String run(byte[] byte_args, Handler handler) {
		String[] args = Utils.byteArrayToStringArray(byte_args, " ");
		if(args.length == 0) {
			if(!handler.getEngine().getDataProtocol().host())
				 return "Connection Unsuccessful";
			else
				return "Connection Successful\n" + handler.getEngine().getDataProtocol().getClient().socketToString();
		} else if(args.length == 2) {
			if(!handler.getEngine().getDataProtocol().join(args[0], Integer.parseInt(args[1])))
				return "Connection Unsuccessful";
			else
				return "Connection Successful\n" + handler.getEngine().getDataProtocol().getClient().socketToString();
		}
		return "Invalid arguments";
	}
}
