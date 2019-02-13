package projectx.engine.managers.items;


import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.managers.entities.creatures.Creature;
import projectx.engine.tile.Tile;
import projectx.game.enums.ItemType;

public class Item {

	public static boolean DEBUGMODE = true;
	private float x, y;
	private byte id;
	private ItemType itemType;
	private Handler handler;
	private boolean pickedUp;
	private Texture texture;

	public Item(Handler handler, float x, float y, ItemType itemType) {
		this.itemType = itemType;
		this.id = itemType.id;
		this.x = x*Tile.TILEWIDTH;
		this.y = y*Tile.TILEHEIGHT;
		this.handler = handler;
		this.texture = itemType.texture;
		pickedUp = false;
	}
	
	public Item(byte b) {
		this.id = b;
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

	/**
	 * This method sets the location of an image.
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public byte getId() {
		return (byte) id;
	}

	public boolean isWeapon() {
		return itemType.type == 2;
	}

	public boolean isUtility() {
		return itemType.type == 1;
	}

	public boolean isArmor() {
		return itemType.type == 3;
	}

	public void render(Graphics g) {
		if (itemType.texture == null) return;
		g.drawImage(itemType.texture, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), 32, 32);
	}

	/**
	 * The method updates the items to be rendered in the world. It is also used for
	 * a player to pick up an item and removes it from the world.
	 */
	public boolean update() {
		// Checks if player is on top of the item and if they are pressing SPACE picks
		// the item up.
		if (handler.getKeyboard().isKeyDown(Keyboard.KEY_SPACE)) {
			for (Creature e : handler.getWorld().getEntityManager().getCreatures()) {
				if (e.getCollisionBounds(0f, 0f).intersects(x, y, 32, 32)) {
					pickedUp = true;
					return e.getInventory().addItem(this);
				}
			}
		}
		return false;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setHandler(Handler handler2) {
		handler = handler2;
	}

	public Texture getTexture() {
		return texture;
	}
}
