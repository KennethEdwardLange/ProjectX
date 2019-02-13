package projectx.engine;

import org.lwjgl.input.Mouse;

import projectx.engine.glgfx.GameCamera;
import projectx.engine.input.Input;
import projectx.engine.worlds.World;
/**
 * This class is a handler for the game for ease of access throughout the entire game.
 * It consists of getters and setters.
 * @author Kenneth Lange
 *
 */
public class Handler {
	
	private EngineGL engine;
	private World world;
	
	public Handler(EngineGL engine) {
		this.engine = engine;
	}
	
	public GameCamera getGameCamera(){
		return engine.getGameCamera();
	}
	
	public Input getKeyboard(){
		return engine.getKeyboard();
	}
	
	public Mouse getMouse(){ //Y is upside-down lol
		return engine.getMouse();
	}
	
	public int getWidth(){
		return engine.getWidth();
	}
	
	public int getHeight(){
		return engine.getHeight();
	}

	public EngineGL getEngine() {
		return engine;
	}

	public void setEngine(EngineGL engine) {
		this.engine = engine;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
