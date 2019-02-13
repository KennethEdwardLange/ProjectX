package projectx.engine.terminal.procs.world;

import projectx.engine.Handler;
import projectx.engine.terminal.Proc;


public class spawn extends Proc {
	@Override
	public String run(byte[] byte_args, Handler handler) {
//		String[] args = Utils.byteArrayToStringArray(byte_args, " ");
//		if (handler.getWorld() != null) {
//			switch (args[0]) {
//			case "shell":
//				handler.getWorld().getEntityManager()
//						.addEntity(new Creature(handler, Integer.parseInt(args), Integer.parseInt(y), CreatureType.Shell));
//			break;
//			case "rock":
//				handler.getWorld().getEntityManager().addEntity(
//						new Nonmoving(handler, Integer.parseInt(x), Integer.parseInt(y), NonmovingType.Rock));
//			break;
//			default:
//				return "invalid args";
//			}
//		}
		return "";
	}
}
