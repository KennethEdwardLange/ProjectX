package projectx.engine.controls.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import projectx.engine.Handler;
import projectx.engine.controls.ai.AI;
import projectx.engine.controls.ai.utils.Vector2i;
import projectx.engine.managers.entities.creatures.Creature;
import projectx.engine.tile.Tile;

public class Controls {
	
	private Handler handler;
	private Creature creature;
	private AI ai;
	Vector2i start, destination;
	
	private int gox, goy;

	public Controls(Handler handler, Creature creatrue) {
		this.handler = handler;
		this.creature = creatrue;
		this.ai = new AI(handler, creature);
	}
	/**
	 * This method updates the controls class for a specific playable creature
	 */
	public void update() {
		getPlayerInput();
	}
	/**
	 * This method gets the player inputs for both keyboard and mouse
	 */
	public void getPlayerInput() {
		getMouseControls();
		getKeyboardControls();
	}
	/**
	 * This method takes care of all mouse related controls for a creature
	 */
	public void getMouseControls() {
		handler.getMouse();
		//Movement
		if (Mouse.isButtonDown(0)) {
			gox = (int) ((handler.getGameCamera().getxOffset() + Mouse.getX()) / Tile.TILEWIDTH);							//Works
			goy = (int) ((handler.getGameCamera().getyOffset() + handler.getHeight() - Mouse.getY()) / Tile.TILEHEIGHT);	//Works
			creature.setTravelling(true);
			destination = new Vector2i(gox, goy);
		} else if (creature.getTravelling()) {
			start = new Vector2i((int) (creature.getXlocation()/ Tile.TILEWIDTH), (int) (creature.getYlocation() / Tile.TILEHEIGHT));
			creature.setPath(ai.pf.findPath(start, destination));
			if(creature.getPath() == null) {
				creature.setTravelling(false);
				creature.setxMove(0);
				creature.setyMove(0);
			}
			ai.followPath(creature.getPath());
		} else if (creature.getXlocation()/ Tile.TILEWIDTH == gox + .5 && creature.getYlocation()/ Tile.TILEHEIGHT == goy + .5) {
			creature.setTravelling(false);
			creature.setxMove(0);
			creature.setyMove(0);
		}
		
		if(!creature.getTravelling()) {
			creature.setxMove(0);
			creature.setyMove(0);
			if (creature.getPath() != null)
				creature.setPath(null);
		}
	}
	/**
	 * This method takes care of all keyboard related controls for a creature
	 */
	public void getKeyboardControls() {
		//Prevents movement while in the inventory
//		if(handler.getKeyboard().isKeyPressed(Keyboard.KEY_E)) //TODO decide if I need this
//			creature.getInventory().setActive(!creature.getInventory().isActive());
//		if(creature.getInventory().isActive())
//			return;
		handler.getKeyboard();
		//Attack
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			creature.setAttacking(true);
		} else {
			creature.setAttacking(false);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			creature.setPickingUp(true);
		} else {
			creature.setPickingUp(false);
		}
		//Movement
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			creature.setyMove(-(creature.speed));
			creature.setTravelling(false);
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			creature.setyMove(creature.speed);
			creature.setTravelling(false);
		}if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			creature.setxMove(-(creature.speed));
			creature.setTravelling(false);
		}else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			creature.setxMove(creature.speed);
			creature.setTravelling(false);
		}
	}
	
}
