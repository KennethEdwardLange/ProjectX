package projectx.engine.glgfx;

import projectx.engine.Handler;
import projectx.engine.managers.entities.Entity;
import projectx.engine.tile.Tile;
/**
 * This class acts as the game camera. In other words what the player sees.
 * @author Kenneth Lange
 *
 */
public class GameCamera {

	private float xOffset, yOffset;
	private Handler handler;
	/**
	 * This constructor takes in float x offset and y offset and the handler to set to the class's defaults.
	 * @param handler
	 * @param xOffset
	 * @param yOffset
	 */
	public GameCamera(Handler handler, float xOffset, float yOffset){
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	/**
	 * This method checks if there are blank spaces like the edges of the map.
	 * If a blank space is made, the offset don't move to exposed them.
	 */
	public void checkBlankSpace(double scale){
		if(xOffset < 0){
			xOffset = 0;
		} else if(xOffset > handler.getWorld().getWidth() * (float) (Tile.TILEWIDTH * scale) - handler.getWidth()){
			xOffset = handler.getWorld().getWidth() * (float) (Tile.TILEWIDTH * scale) - handler.getWidth();
		}
		if(yOffset < 0){
			yOffset = 0;
		}else if(yOffset > handler.getWorld().getHeight() * (float) (Tile.TILEWIDTH * scale) - handler.getHeight()){
			yOffset = handler.getWorld().getHeight() * (float) (Tile.TILEWIDTH * scale) - handler.getHeight();
		}
	}
	/**
	 * This method centers the game camera on a particular entity.
	 * @param e
	 */
	public void centerOnEntity(Entity e){
		xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
		checkBlankSpace(1.0);
	}
	/**
	 * This method moves to the selected location based on the parameters xAmt and yAmt
	 * @param xAmt
	 * @param yAmt
	 */
	public void move(float xAmt, float yAmt, double scale){
		xOffset += xAmt * scale;
		yOffset += yAmt * scale;
		checkBlankSpace(scale);
	}
	//Getters and Setters
	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
	
}
