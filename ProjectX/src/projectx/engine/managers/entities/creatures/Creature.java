package projectx.engine.managers.entities.creatures;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;

import projectx.engine.Handler;
import projectx.engine.controls.ai.AI;
import projectx.engine.controls.ai.utils.Node;
import projectx.engine.controls.player.Controls;
import projectx.engine.debug.Debug;
import projectx.engine.glgfx.Animation;
import projectx.engine.glgfx.Graphics;
import projectx.engine.inventory.Inventory;
import projectx.engine.managers.entities.Entity;
import projectx.engine.managers.items.Item;
import projectx.engine.managers.locators.Locator;
import projectx.engine.tile.Tile;
import projectx.game.enums.CreatureType;
import projectx.game.textures.Assets;

/**
 * This class is a template for all creatures and creature used methods.
 * 
 * @author Kenneth Lange
 *
 */
public class Creature extends Entity {

	public static final double DEFAULT_SPEED = 2;
	public static final int DEFAULT_CREATURE_WIDTH = 64;
	public static final int DEFAULT_CREATURE_HEIGHT = 64;

	public long birthNano = System.nanoTime();

	public double speed;
	public double xMove;
	public double yMove;

	// CreatureType
	private CreatureType creature;
	// Attack
	private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown, at, lat, respawnCooldown; // Timers
	private boolean attacking, pickingUp;
	private boolean dead;
	// Coordinates
	private float xlocation, ylocation;
	public int roamx, roamy;
	// Animations
	private Animation animUp, animDown, animLeft, animRight;
	private byte lastDirection = 2; // Set default start direction to be down
	private byte frame = 0;
	// Inventory
	private Inventory inventory;
	// Pathfinding
	public List<Node> path = null;
	public boolean travelling;
	private ArrayList<Locator> checkpoints;
	// Winning conditions
	private boolean winner = false;
	// Controls
	private Controls controls = null;
	private AI ai = null;
	private static int xmouse, ymouse; // Control
	// Respawn
	private final float spawnx, spawny;

	public Creature(Handler handler, float x, float y, CreatureType creature) {
		super(handler, (x - 1.5f) * Tile.TILEWIDTH + 33, (y - 1.5f) * Tile.TILEHEIGHT + 11,
				Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		speed = creature.speed;
		health = DEFAULT_HEALTH;
		respawnCooldown = 0;
		
		xMove = 0;
		yMove = 0;
		// Spawn location
		spawnx = x;
		spawny = y;

		// Must be set to the exact pixel x and y beginning and the width and height of
		// the character
		// ie set it to be around the body of character only
		bounds.x = creature.bounds.x;
		bounds.y = creature.bounds.y;
		bounds.width = creature.bounds.width;
		bounds.height = creature.bounds.height;

		// Animations
		animUp = creature.animUp;
		animDown = creature.animDown;
		animLeft = creature.animLeft;
		animRight = creature.animRight;
		checkpoints = new ArrayList<Locator>();
		inventory = new Inventory(handler);
		this.creature = creature;
		if (creature.player_controlled) {
			controls = new Controls(handler, this);
		} else {
			ai = new AI(handler, this);
			roamx = (int) x;
			roamy = (int) y;
		}
	}

	/**
	 * This is the update method, it updates everything in regards to each creature.
	 */
	@Override
	public void update() {

		// Coordinates
		xlocation = (x + bounds.x + bounds.width / 2);
		ylocation = (y + bounds.y + bounds.height / 2);
		// Respawn timer
		if (dead && System.currentTimeMillis()<respawnCooldown) {
			return;
		}
		dead=false;
		// Controls
		if (creature.player_controlled) {
			handler.getGameCamera().centerOnEntity(this);
			controls.update();
		} else if (creature.equals(CreatureType.PShell) || creature.equals(CreatureType.GShell)) {
//			update via DataParser, received by server
		} else {
			if (getCreature().equals(CreatureType.PGuard) || getCreature().equals(CreatureType.SGuard)
					|| getCreature().equals(CreatureType.RGuard)) { // if creature is a guard
				ai.guardUpdate();
			} else if (getCreature().equals(CreatureType.Prisoner)) { // if creature is a prisoner
				ai.prisonerUpdate();
			}
		}
		if (!creature.equals(CreatureType.Spectator)) {
			// Movement
			move();
			// Animations
			animUp.update();
			animDown.update();
			animLeft.update();
			animRight.update();
			// Attacks
			checkAttacks();
		} else {
			moveX();
			moveY();
		}
		// DEBUGMODE
		if (handler.getKeyboard().isKeyPressed(Keyboard.KEY_F3)) // Controls
			Debug.setDEBUGMODE();
	}

	/**
	 * This method is used for when a player is killed.
	 */
	public void destroy() {
		respawnCooldown = System.currentTimeMillis() + 5000;
		health = 100;
		attacking = false;
		setX((spawnx - 1.5f) * Tile.TILEWIDTH + 33);
		setY((spawny - 1.5f) * Tile.TILEHEIGHT + 11);
		inventory = new Inventory(handler);
		dead = true;
	}

	/**
	 * This method is used for when a creature is activated
	 */
	public void activate() {
		// Not used
	}

	/**
	 * This method receives the amount of damage done to an entity and removes the
	 * health accordingly
	 * 
	 * @param amount
	 */
	public void hurt(int amount) {
		if (inventory.contains(3)) {
			inventory.remove(3);
		} else {
			health -= amount;
			if (health <= 0 && active) {
				health = 0; // I will be using this later when entity is knocked out
				destroy();
			}
		}
	}

	/**
	 * This method is responsible for rendering the newly updated creature.
	 */
	@Override
	public void render(Graphics g) {
		if (creature.equals(CreatureType.Spectator) || dead)
			return;
		g.drawImage(getCurentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height); // render player
		if (inventory.isActive()) {
			postRender(g);
		}
		// DEBUGMODE
		/*-------------------------------------------*/
		if (DEBUGMODE) {
			DEBUGMODE_render(g);
			// player specific DEBUGMODE
			if (creature == CreatureType.Player) {
				g.setColor(Color.WHITE);
				String c = "X: " + (int) (xlocation / Tile.TILEWIDTH) + " Y: " + (int) (ylocation / Tile.TILEHEIGHT)
						+ "   Actual: X: " + xlocation + " Y: " + ylocation;
				g.drawString(c, 5, 12);
				xmouse = (int) ((handler.getGameCamera().getxOffset() + Mouse.getX()) / Tile.TILEWIDTH);
				ymouse = (int) ((handler.getGameCamera().getyOffset() + handler.getHeight() - Mouse.getY())
						/ Tile.TILEHEIGHT);
				String mc = "X: " + xmouse + " Y: " + ymouse;
				g.drawString(mc, 5, 24);
				String hp = "Health: " + health;
				g.drawString(hp, 5, 36);
				String nonmovings = "Nonmovings: " + (handler.getWorld().getEntityManager().getNonmoving().size());
				g.drawString(nonmovings, 5, 48);
				String creatures = "Creatures: " + (handler.getWorld().getEntityManager().getCreatures().size());
				g.drawString(creatures, 5, 60);
				String objects = "Objects: " + (handler.getWorld().getEntityManager().getObjects().size());
				g.drawString(objects, 5, 72);
				String items = "Items: " + (handler.getWorld().getItemManager().getItems().size());
				g.drawString(items, 5, 84);
			}
		}
		/*-------------------------------------------*/
	}

	public void render(Graphics g, double scale) {
		int x = (int) (this.x * scale - handler.getGameCamera().getxOffset());
		int y = (int) (this.y * scale - handler.getGameCamera().getyOffset());
		int width = (int) (this.width * scale);
		int height = (int) (this.height * scale);

		g.drawImage(getCurentAnimationFrame(), x, y, width, height);
		if (DEBUGMODE) {
			// Player collision box
			g.setColor(Color.WHITE);
			g.drawRect((int) (this.x * scale + bounds.x * scale - handler.getGameCamera().getxOffset()),
					(int) (this.y * scale + bounds.y * scale - handler.getGameCamera().getyOffset()),
					(int) (bounds.width * scale), (int) (bounds.height * scale));
			g.drawRect(x, y, width, height);
		}
	}

	/**
	 * This method is used to render the creature's inventory on top of everything
	 * else.
	 * 
	 * @param g
	 */
	public void postRender(Graphics g) {
		String hps = "Health: ";
		g.drawString(hps, 48, 650);

		if (health > 90) {
			g.drawImage(Assets.heart, 32 * 5, 670, 32, 32);
			g.drawImage(Assets.heart, 32 * 4, 670, 32, 32);
			g.drawImage(Assets.heart, 32 * 3, 670, 32, 32);
			g.drawImage(Assets.heart, 32 * 2, 670, 32, 32);
			g.drawImage(Assets.heart, 32, 670, 32, 32);
		} else if (health > 80) {
			g.drawImage(Assets.halfheart, 32 * 5, 670, 32, 32);
			g.drawImage(Assets.heart, 32 * 4, 670, 32, 32);
			g.drawImage(Assets.heart, 32 * 3, 670, 32, 32);
			g.drawImage(Assets.heart, 32 * 2, 670, 32, 32);
			g.drawImage(Assets.heart, 32, 670, 32, 32);
		} else if (health > 70) {
			g.drawImage(Assets.heart, 32 * 4, 670, 32, 32);
			g.drawImage(Assets.heart, 32 * 3, 670, 32, 32);
			g.drawImage(Assets.heart, 32 * 2, 670, 32, 32);
			g.drawImage(Assets.heart, 32, 670, 32, 32);
		} else if (health > 60) {
			g.drawImage(Assets.halfheart, 32 * 4, 670, 32, 32);
			g.drawImage(Assets.heart, 32 * 3, 670, 32, 32);
			g.drawImage(Assets.heart, 32 * 2, 670, 32, 32);
			g.drawImage(Assets.heart, 32, 670, 32, 32);
		} else if (health > 50) {
			g.drawImage(Assets.heart, 32 * 3, 670, 32, 32);
			g.drawImage(Assets.heart, 32 * 2, 670, 32, 32);
			g.drawImage(Assets.heart, 32, 670, 32, 32);
		} else if (health > 40) {
			g.drawImage(Assets.halfheart, 32 * 3, 670, 32, 32);
			g.drawImage(Assets.heart, 32 * 2, 670, 32, 32);
			g.drawImage(Assets.heart, 32, 670, 32, 32);
		} else if (health > 30) {
			g.drawImage(Assets.heart, 32 * 2, 670, 32, 32);
			g.drawImage(Assets.heart, 32, 670, 32, 32);
		} else if (health > 20) {
			g.drawImage(Assets.halfheart, 32 * 2, 670, 32, 32);
			g.drawImage(Assets.heart, 32, 670, 32, 32);
		} else if (health > 10) {
			g.drawImage(Assets.heart, 32, 670, 32, 32);
		} else if (health > 0) {
			g.drawImage(Assets.halfheart, 32, 670, 32, 32);
		}

		String items = "Inventory: ";
		g.drawString(items, 370, 650);
		for (int i = 0; i < handler.getWorld().getEntityManager().getCreatures().get(0).getInventory()
				.getInventoryItems().length; i++) {
			Item item = handler.getWorld().getEntityManager().getCreatures().get(0).getInventory()
					.getInventoryItems()[i];
			if (item.getId() != 0) {
				g.drawImage(item.getTexture(), 350 + (i * 32), 670, 32, 32);
			}
		}
	}

	// Getters and Setters
	/**
	 * This method gets the current animation image which allows for a animated
	 * creature character.
	 * 
	 * @return
	 */
	private Texture getCurentAnimationFrame() {
		if (xMove < 0) { // Moving Left
			lastDirection = 1;
			frame = (byte)animLeft.getIndex();
			return animLeft.getCurrentFrame();
		} else if (xMove > 0) { // Moving Right
			lastDirection = 3;
			frame = (byte)animRight.getIndex();
			return animRight.getCurrentFrame();
		} else if (yMove < 0) { // Moving Up
			lastDirection = 0;
			frame = (byte)animUp.getIndex();
			return animUp.getCurrentFrame();
		} else if (yMove > 0) { // Moving Down
			lastDirection = 2;
			frame = (byte)animDown.getIndex();
			return animDown.getCurrentFrame();
		} else
			return creature.notMoving[lastDirection];
	}

	/**
	 * This method uses the methods moveX() and moveY() to move a creature around a
	 * screen. It also checks for collision with other entities.
	 */
	public void move() {
		if (!checkCollisions((float) xMove, 0f))
			moveX();
		if (!checkCollisions(0f, (float) yMove))
			moveY();
	}

	/**
	 * This method allow for a creature to move in the X-Axis.
	 */
	public void moveX() {
		if (xMove < 0) { // Moving Left
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
			if (isOnWinTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& isOnWinTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				setWinner(true);
				x += xMove;
			} else if (collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
			} else {
				x += xMove;
			}
		} else if (xMove > 0) { // Moving Right
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			if (isOnWinTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& isOnWinTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				setWinner(true);
				x += xMove;
			} else if (collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			} else {
				x += xMove;
			}
		}
	}

	/**
	 * This method allow for a creature to move in the Y-Axis.
	 */
	public void moveY() {
		if (yMove < 0) { // Moving Up
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
			if (isOnWinTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& isOnWinTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				setWinner(true);
				y += yMove;
			} else if (collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			} else {
				y += yMove;
			}
		} else if (yMove > 0) { // Moving Down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
			if (isOnWinTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& isOnWinTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				setWinner(true);
				y += yMove;
			} else if (collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
			} else {
				y += yMove;
			}
		}
	}

	/**
	 * This helper method checks if creature entity is colliding with a tile.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}

	/**
	 * This helper method checks if creature entity is standing on winning tile.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean isOnWinTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isWin();
	}

	/**
	 * This method is responsible for checking whether an entity is attacking or not
	 * and whether it hits something or not. It also has the ability change the
	 * attack based on what the player is holding. Additional item features are
	 * found in the objects entities methods
	 */
	protected void checkAttacks() {
		// Cooldown timing code
		// ----------------------------------------------------------
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if (attackTimer < attackCooldown)
			return;
		// ----------------------------------------------------------

		Rectangle cb = getCollisionBounds(0, 0);// Collision Bounds of Player
		Rectangle ar = new Rectangle(); // Attack Rectangle
		int arSize = 23; // Size of Attack Rectangle
		ar.width = arSize;
		ar.height = arSize;

		if (attacking) {
			if (lastDirection == 1) { // Attack Left
				ar.x = cb.x - arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			} else if (lastDirection == 3) { // Attack Right
				ar.x = cb.x + arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			} else if (lastDirection == 0) { // Attack Up
				ar.x = cb.x;
				ar.y = cb.y - arSize;
			} else if (lastDirection == 2) { // Attack Down
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y + cb.height;
			}
		} else
			return;

		attackTimer = 0;

		for (Entity e : handler.getWorld().getEntityManager().getCreatures()) {
			if (e.equals(this))
				continue;
			if (e.getCollisionBounds(0, 0).intersects(ar)) {
				if (inventory.contains(2)) {
					e.hurt(100);
					inventory.remove(2);
				} else {
					e.hurt(5); // Entity receives damage value of 1
				}
				return;
			}
		}
		for (Entity e : handler.getWorld().getEntityManager().getNonmoving()) {
			if (e.getCollisionBounds(0, 0).intersects(ar)) {
				e.hurt(1); // Entity receives damage value of 1
				return;
			}
		}
		for (Entity e : handler.getWorld().getEntityManager().getObjects()) {
			if (e.getCollisionBounds(0, 0).intersects(ar)) {
				e.activate();
				return;
			}
		}
	}

	/**
	 * This method is responsible for rendering DEBUGMODE related rendering
	 * 
	 * @param g
	 */
	public void DEBUGMODE_render(Graphics g) {
		g.setColor(Color.WHITE);
		// Draw path
		if (path != null) { // Drawing a path
			if (path.size() > 0) {
				for (int i = 0; i < path.size() - 1; i++) {
					g.drawLine(
							(int) (path.get(i).tile.getX() * Tile.TILEWIDTH + Tile.TILEWIDTH / 2
									- handler.getGameCamera().getxOffset()),
							(int) (path.get(i).tile.getY() * Tile.TILEHEIGHT + Tile.TILEHEIGHT / 2
									- handler.getGameCamera().getyOffset()),
							(int) (path.get(i + 1).tile.getX() * Tile.TILEWIDTH + Tile.TILEWIDTH / 2
									- handler.getGameCamera().getxOffset()),
							(int) (path.get(i + 1).tile.getY() * Tile.TILEHEIGHT + Tile.TILEHEIGHT / 2
									- handler.getGameCamera().getyOffset()));
				}
				g.drawLine(
						(int) (path.get(path.size() - 1).tile.getX() * Tile.TILEWIDTH + Tile.TILEWIDTH / 2
								- handler.getGameCamera().getxOffset()),
						(int) (path.get(path.size() - 1).tile.getY() * Tile.TILEWIDTH + Tile.TILEWIDTH / 2
								- handler.getGameCamera().getyOffset()),
						(int) (xlocation - handler.getGameCamera().getxOffset()),
						(int) (ylocation - handler.getGameCamera().getyOffset()));
			}
		}
		// NPC collision box
		g.drawRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);

		// Attack box
		at += System.currentTimeMillis() - lat;
		lat = System.currentTimeMillis();
		if (at < attackCooldown)
			return;

		Rectangle cb = getCollisionBounds(0, 0);// Collision Bounds of Player
		Rectangle ar = new Rectangle(); // Attack Rectangle
		int arSize = 23; // Size of Attack Rectangle
		ar.width = arSize;
		ar.height = arSize;
		g.setColor(Color.GREEN);
		if (attacking) {
			if (lastDirection == 1) { // Attack Left
				ar.x = cb.x - arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			} else if (lastDirection == 3) { // Attack Right
				ar.x = cb.x + cb.width;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			} else if (lastDirection == 0) { // Attack Up
				ar.x = cb.x;
				ar.y = cb.y - arSize;
			} else if (lastDirection == 2) { // Attack Down
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y + cb.height;
			} else
				return;
			at = 0;
			for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
				if (e.equals(this))
					continue;
				if (e.getCollisionBounds(0, 0).intersects(ar)) {
					g.setColor(Color.RED);
				}
			}
			g.fillRect((int) (ar.x - handler.getGameCamera().getxOffset()),
					(int) (ar.y - handler.getGameCamera().getyOffset()), arSize, arSize);
		}
		g.setColor(Color.WHITE);
	}

	// GETTERS AND SETTERS

	public double getxMove() {
		return xMove;
	}

	public void setxMove(double xMove) {
		this.xMove = xMove;
	}

	public double getyMove() {
		return yMove;
	}

	public void setyMove(double yMove) {
		this.yMove = yMove;
	}

	public double getSpeed() {
		return speed;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public float getXlocation() {
		return xlocation;
	}

	public void setXlocation(float xlocation) {
		this.xlocation = xlocation;
	}

	public float getYlocation() {
		return ylocation;
	}

	public int getXmouse() {
		return xmouse;
	}

	public static void setXmouse(int x) {
		xmouse = x;
	}

	public int getYmouse() {
		return ymouse;
	}

	public static void setYmouse(int y) {
		ymouse = y;
	}

	public void setYlocation(float ylocation) {
		this.ylocation = ylocation;
	}

	public CreatureType getCreature() {
		return creature;
	}

	public void setCreature(CreatureType creature) {
		this.creature = creature;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean getAttacking() {
		return attacking;
	}

	public void setPickingUp(boolean pickingUp) {
		this.pickingUp = pickingUp;
	}

	public boolean isPickingUp() {
		return pickingUp;
	}

	public void setLastDirection(byte lastDirection) {
		this.lastDirection = lastDirection;
	}

	public byte getLastDirection() {
		return lastDirection;
	}

	public void setPath(List<Node> path) {
		this.path = path;
	}

	public List<Node> getPath() {
		return path;
	}

	public void setTravelling(boolean travelling) {
		this.travelling = travelling;
	}

	public boolean getTravelling() {
		return travelling;
	}

	public long getBirthNano() {
		return birthNano;
	}

	public void setBirthNano(long birthNano) {
		this.birthNano = birthNano;
	}

	/**
	 * Is this creature an NPC
	 * 
	 * @return true if creature is an NPC
	 */
	public boolean isNPC() {
		return (!(this.getCreature().isServer_controlled() || this.getCreature().isPlayer_controlled()));
	}

	/**
	 * Is this creature server controlled
	 * 
	 * @return true if server controlled
	 */
	public boolean isServerControlled() {
		return this.getCreature().isServer_controlled();
	}

	/**
	 * Is this creature player controlled
	 * 
	 * @return true if player controlled
	 */
	public boolean isPlayerControlled() {
		return this.getCreature().isPlayer_controlled();
	}

	/**
	 * Sets a creature to being server controlled
	 */
	public void setServerControlled() {
		this.getCreature().setPlayer_controlled(false);
		this.getCreature().setServer_controlled(true);
	}

	/**
	 * Sets a creature to being the player character
	 */
	public void setPlayerControlled() {
		this.getCreature().setPlayer_controlled(true);
		this.getCreature().setServer_controlled(true);
	}

	/**
	 * Sets a creature as an NPC
	 */
	public void setNPC() {
		this.getCreature().setPlayer_controlled(false);
		this.getCreature().setServer_controlled(false);
	}

	public boolean equalsBirthNano(Creature creature) {
		return getBirthNano() == creature.getBirthNano();
	}

	public void addCheckpoint(Locator checkpoint) {
		checkpoints.add(checkpoint);
	}

	public void setCheckpoints(ArrayList<Locator> checkpoints) {
		this.checkpoints = checkpoints;
	}

	public ArrayList<Locator> getCheckpoints() {
		return checkpoints;
	}

	public byte getFrame() {
		return frame;
	}

	public void setFrame(byte frame) {
		this.frame = frame;
	}

	@Override
	public String toString() {
		return "[" + creature.toString() + ", " + x + ", " + y + "]";
	}

}