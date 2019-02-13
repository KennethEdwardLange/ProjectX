package projectx.engine.state;

import org.lwjgl.util.Color;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.worlds.Map;
/**
 * This class acts as the state that allows the user to create and modify the maps in the game.
 * @author Mitchell Hoppe
 *
 */
public class MapBuilderState extends State{
	private Map map;
	public MapBuilderState(Handler handler, String path){
		super(handler);
		//At this point in time the decision for a new map or existing map has been made.	
		map = new Map(handler, path);
		handler.setWorld(map);
	}
	
	@Override
	public void update() {	
		map.update();
	}
	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		g.setColor(Color.WHITE);
		map.render(g);		
	}
}
