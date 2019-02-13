package projectx.engine.state;
import java.util.ArrayList;

import org.lwjgl.util.Color;

import projectx.engine.Handler;
import projectx.engine.audio.AudioPlayer;
import projectx.engine.glgfx.Graphics;
import projectx.engine.managers.entities.creatures.Creature;
import projectx.engine.worlds.World;
import projectx.game.audio.Audio;
import projectx.game.enums.CreatureType;

/**
 * This class acts as the game state of the game. It is responsible for loading in the world and updating and rendering it.
 * @author Kenneth Lange
 *
 */
public class GameState extends State{

	private World world;
	private AudioPlayer ap;
	
	public GameState(Handler handler){
		super(handler);
		world = new World(handler,"res/worlds/world1.txt");
		handler.setWorld(world);
		
		ap = new AudioPlayer();
		ap.playLoop(Audio.game_music,0.3);
	}
	
	public GameState(Handler handler, String path){
		super(handler);
		world = new World(handler, path);
		handler.setWorld(world);
		ap = new AudioPlayer();
		ap.playLoop(Audio.game_music,0.3);
	}
	
	@Override
	public void update() {
		world.update();
	
		if(handler.getEngine().getDataProtocol().getClient().isConnected() && world.getSelf() != null &&
				world.getSelf().getCreature() == CreatureType.Player) {
			sendCreatures();
			if(isHost) {}
			else if(!isHost) {
			}
		}
		for(Creature c : world.getEntityManager().getCreatures()){
			if (c.isWinner()) {
				ap.clip.stop();
				handler.getEngine().setMenuState(new MenuState(handler));
				State.setState(handler.getEngine().getMenuState());
			}
		}
	}
	
	private void sendCreatures() {
		Creature[] creatures = getCreatureArray();
		
		handler.getEngine().getDataProtocol().sendCreatureArray(creatures);
		
	}
	
	/**
	 * Gets the creature[] that the DP will send over the socket.
	 * @return
	 */
	private Creature[] getCreatureArray() {
		if(!isHost) return new Creature[] {world.getSelf()};	
		else {
			ArrayList<Creature> creatureList = new ArrayList<Creature>();
			for(Creature c : world.getEntityManager().getCreatures()) {
				if(c.getBirthNano() == world.getSelf().getBirthNano() || c.isNPC())
					if(c.getCreature() != CreatureType.PShell && c.getCreature() != CreatureType.GShell)
						creatureList.add(c);
			}
			return creatureList.toArray(new Creature[creatureList.size()]);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		g.setColor(Color.WHITE);
		world.render(g);
	}
	
	public boolean isHost() {
		return isHost;
	}
	
	public void setHost(boolean isHost) {
		this.isHost = isHost;
	}

}
