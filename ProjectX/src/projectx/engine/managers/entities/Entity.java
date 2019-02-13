package projectx.engine.managers.entities;

import java.awt.Rectangle;

import projectx.engine.glgfx.Graphics;
import projectx.engine.Handler;
/**
 * This class is a template for all entities
 * @author Kenneth Lange
 *
 */
public abstract class Entity {
	//DEBUGMODE
	public static boolean DEBUGMODE = true; //Shows DEBUGMODE for entities
	
	public static final byte DEFAULT_HEALTH = 100;
	
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected byte health;
	protected boolean active = true;
	protected Rectangle bounds;
	
	public Entity(Handler handler, float x, float y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	public abstract void render(Graphics g, double scale);
	
	public abstract void destroy();
	
	public abstract void activate();

	public abstract void hurt(int amount);
	/**
	 * Checks the bounds of an entities and sees if it collides with another entity
	 * @param xOffset
	 * @param yOffset
	 * @return
	 */
	public boolean checkCollisions(float xOffset, float yOffset){
		for(Entity e : handler.getWorld().getEntityManager().getNonmoving()){
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		for(Entity e : handler.getWorld().getEntityManager().getObjects()){
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
	public byte getHealth() {
		return health;
	}
	public void setHealth(byte health) {
		this.health = health;
	}
	
	public String toString() {
		return "[" + x + ", " + y + "]"; 
	}
	
}