package projectx.engine.managers.locators;

import java.awt.Rectangle;

import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.tile.Tile;
import projectx.game.enums.LocatorType;
/**
 * This class is a template for all locators
 * @author Kenneth Lange
 *
 */
public class Locator {
	//DEBUGMODE
	public static boolean DEBUGMODE = true; //Shows DEBUGMODE for locators
	
	private Handler handler;
	private float x, y;
	private int width, height;
	private boolean active = true;
	private Rectangle bounds;
	private LocatorType type;
	
	public Locator(Handler handler, float x, float y, LocatorType type) {
		this.handler = handler;
		this.type = type;
		this.x = x * Tile.TILEWIDTH;
		this.y = y * Tile.TILEHEIGHT;
		this.width = Tile.TILEWIDTH;
		this.height = Tile.TILEHEIGHT;
		bounds = new Rectangle(0, 0, width, height);
	}
	/**
	 * This method is responsible for updating the position of the object
	 */
	public void update() {}
	
	/**
	 * This method is responsible for rendering a locator's new location and collision box.
	 */
	public void render(Graphics g) {
		if(DEBUGMODE){
			g.drawImage(type.texture, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height);
			g.setColor(Color.GREEN);
			g.drawRect((int) (x + bounds.x -handler.getGameCamera().getxOffset()), (int) (y + bounds.y -handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
			g.setColor(Color.WHITE);
		}
	}
	
	/**
	 * This method is responsible for rendering a locator's new location and collision box based on a scaler.
	 */
	public void render(Graphics g, double scale) {
		int x = (int) ((this.x/Tile.TILEWIDTH)*(int)(Tile.TILEWIDTH*scale)-handler.getGameCamera().getxOffset());
		int y = (int) ((this.y/Tile.TILEWIDTH)*(int)(Tile.TILEWIDTH*scale)-handler.getGameCamera().getyOffset());
		if(DEBUGMODE) {
			g.drawImage(type.texture, x, y, (int)(bounds.width * scale), (int)(bounds.width * scale));
			g.setColor(Color.GREEN);
			g.drawRect(x, y, (int)(bounds.height * scale), (int)(bounds.height * scale));
			g.setColor(Color.WHITE);
		}	
	}
	/**
	 * Checks the bounds of locators and sees if it collides with another locator
	 * @param xOffset
	 * @param yOffset
	 * @return	
	 */
	public boolean checkLocatorsCollisions(float xOffset, float yOffset){
		for(Locator e : handler.getWorld().getLocatorManager().getLocators()){
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	//Getters and Setters
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public Texture getTexture(){
		return type.texture;
	}
	public LocatorType getType() {
		return type;
	}
	
}