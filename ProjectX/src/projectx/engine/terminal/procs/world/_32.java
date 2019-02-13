package projectx.engine.terminal.procs.world;

import projectx.engine.Handler;
import projectx.engine.terminal.Proc;
import projectx.engine.utils.Utils;
import projectx.game.client.Client;

/**
 * This class sends the creature data over the serversocket
 * @author Mitch
 *
 */
public class _32 extends Proc {
	
	@Override
	public String run(byte[] byte_args, Handler handler) {
		
		Client client = handler.getEngine().getDataProtocol().getClient();
		
		byte[] data = new byte[10000];//10000
		data[0] = 32;
		
		Utils.addByteArrayToArray(data, byte_args, 1);
		if(client.isConnected())	
			client.sendBytes(data);
		return null;
	}
}
