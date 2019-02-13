package projectx.engine.terminal.procs.world;



import projectx.engine.Handler;
import projectx.engine.managers.entities.creatures.Creature;
import projectx.engine.terminal.Proc;
import projectx.engine.utils.Convert;

public class _96 extends Proc{
	
	Handler handler;
	
	@Override
	public String run(byte[] byte_args, Handler handler){
		
		this.handler = handler;
		
		Creature[] creatureArray = Convert.byteArrayToCreatureArray(byte_args, handler);
		handler.getWorld().getEntityManager().recieveCreatures(creatureArray);

//		return Arrays.toString(Arrays.copyOf(byte_args, 31*byte_args[1] + 1));
		return null;
		
	}
}
