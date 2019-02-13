package projectx.engine.managers.entities.nonmoving;

import org.lwjgl.util.Color;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.managers.entities.Entity;
import projectx.engine.tile.Tile;
import projectx.game.enums.NonmovingType;

/**
 * This class is a template for all static entities
 * @author Kenneth Lange
 *
 */
public class Nonmoving extends Entity{

	private NonmovingType nonmovingType;
	
	public Nonmoving(Handler handler, int x, int y, NonmovingType nonmovingType) {
		super(handler, x*Tile.TILEWIDTH+nonmovingType.xoffset, y*Tile.TILEHEIGHT+nonmovingType.yoffset, (int)nonmovingType.width, (int)nonmovingType.height);
		this.nonmovingType = nonmovingType;
		health = DEFAULT_HEALTH;
		bounds = nonmovingType.bounds;
	}
	/**
	 * This method is responsible for updating the object
	 */
	@Override
	public void update() {
		
	}
	@Override
	public void destroy() {
		handler.getWorld().getEntityManager().getEntities().remove(this);
	}
	/**
	 * This method is used for when a nonmoving is activated
	 */
	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This method receives the amount of damage done to an entity and removes the health accordingly
	 * @param amount
	 */
	public void hurt (int amount) {
		health -= amount;
		if(health <= 0) {
			health = 0; //I will be using this later when entity is knocked out
			active = false;
//			destroy();
		}
	}
	
	/**
	 * This method is responsible for rendering an object's new location and collision box.
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(nonmovingType.texture, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height);
		
		if(DEBUGMODE){
			g.setColor(Color.WHITE);
			g.drawRect((int) (x + bounds.x -handler.getGameCamera().getxOffset()), (int) (y + bounds.y -handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		}
		
	}
	@Override
	public void render(Graphics g, double scale) {

		int x = (int) ((this.x/Tile.TILEWIDTH)*(int)(Tile.TILEWIDTH*scale)-handler.getGameCamera().getxOffset());
		int y = (int) ((this.y/Tile.TILEWIDTH)*(int)(Tile.TILEWIDTH*scale)-handler.getGameCamera().getyOffset());
		int width = (int)(this.width*scale);
		int height = (int)(this.height*scale);
		
		g.drawImage(nonmovingType.texture, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height);
		
		if(DEBUGMODE){
			g.setColor(Color.WHITE);
			g.drawRect((int) (this.x*scale + bounds.x*scale -handler.getGameCamera().getxOffset()), (int) (this.y*scale + bounds.y*scale -handler.getGameCamera().getyOffset()), (int)(bounds.width*scale), (int)(bounds.height*scale));
			g.drawRect(x, y, width, height);
		}
	}
	
	@Override
	public String toString() {
		return "[" + nonmovingType.toString() + ", " + x + ", " + y + "]"; 
	}
}