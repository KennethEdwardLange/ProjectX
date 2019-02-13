package projectx.engine.controls.ai;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import projectx.engine.Handler;
import projectx.engine.controls.ai.utils.Node;
import projectx.engine.controls.ai.utils.Vector2i;
import projectx.engine.managers.entities.Entity;
import projectx.engine.managers.entities.creatures.Creature;
import projectx.engine.managers.entities.nonmoving.Nonmoving;
import projectx.engine.managers.entities.objects.Objects;
import projectx.engine.managers.locators.Locator;
import projectx.engine.tile.Tile;
import projectx.game.enums.CreatureType;

public class AI {
	private Handler handler;
	public Pathfinding pf;
	public List<Node> path = null;
	private Creature creature, seenEnemy = null;
	private Random rand = new Random();
	private int index = 0, x, y;
	private boolean seen = false, arrived;
	private long lastMoveTimer, arriveCooldown = 1000, arriveTimer = arriveCooldown; //Timers
	private ArrayList<Locator> checkpoints = null;
	//Pathfinding cooldown
	public long lastPathCheck;
	public long pathCheckCooldown= 800;
	public long pathCheck= pathCheckCooldown;
	public AI(Handler handler, Creature creature) {
		this.handler = handler;
		this.creature = creature;
		pf =new Pathfinding(handler);
		checkpoints = creature.getCheckpoints();
	}
	/**
	 * This is the method all guards should call when they are controlled by the AI.
	 * Depending on the guard, it will either travel between checkpoints, sit at a stationary location, or roam randomly around the map.
	 * If the guard sees a prisioner or player, it will attack the it until it runs out of a set tile range (5)
	 * in which case it will instead return to it's movement schedule. 
	 */
	public void guardUpdate(){
		//check if a enemy has been spotted
		if (checkSight()){
			seen = true;
		} if (seen) {
			creature.setSpeed(1.5);
			chaseEnemy(seenEnemy, 5);
			return; //if player has been spotted end method here
		}
		//if no enemy has been spotted
		creature.setSpeed(1);
		if (creature.getCreature().equals(CreatureType.PGuard) || creature.getCreature().equals(CreatureType.SGuard)){ //Patrol Guards and Stationary Guards
			if (index > checkpoints.size()-1){
				index = 0;
			}
			goToLocation (checkpoints.get(index));
		} else if(creature.getCreature().equals(CreatureType.RGuard)){ //Roaming Guards
			roamArea();
		} if (!checkCollision(x, y)) {
			goToLocation(x, y);
		}
	}
	/**
	 * This is the method that all prisoners call when they are controlled by the AI.
	 * All prisoners controlled by the AI will move around their spawn a total of 5
	 * blocks in any direction from initial spawn. If any of the prisoners see a guard
	 * they will attack that guard until killing that guard.
	 */
	public void prisonerUpdate(){
		//check if a enemy has been spotted
		if (checkSight()){
			seen = true;
		} if (seen) {
			creature.setSpeed(1.5);
			chaseEnemy(seenEnemy, 5);
			return; //if player has been spotted end method here
		}
		//if no enemy has been spotted
		creature.setSpeed(1);
		creature.setAttacking(false);
		roamArea();
		if (!checkCollision(x, y)) {
		goToLocation(x, y);
		}
	}
	//Helper Methods
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void roamArea() {
		if (creature.getxMove() == 0 && creature.getyMove() == 0){
			if (!arrived) {
				lastMoveTimer = System.currentTimeMillis();
				arrived = true;
			}
			arriveTimer = System.currentTimeMillis() - lastMoveTimer;
			if(arriveTimer < arriveCooldown) return;
			//Roaming Guard goes to new random location
			x = rand.nextInt(11) - 5 + creature.roamx;
			y = rand.nextInt(11) - 5 + creature.roamy;
			arrived = false;
		}
	}
	/**
	 * This method sets the creatures speed to 1.5 and chases down a given seen player
	 * @param seenEnemy
	 * @param maxDistance
	 */
	public void chaseEnemy(Creature seenEnemy, int maxDistance) {
		//Check if Player is further than 5 tiles away
		if (!seenEnemy.isActive()) {
			seen = false;
			seenEnemy = null;
			return;
		}
		float maxX = maxDistance*Tile.TILEWIDTH;
		float maxY = maxDistance*Tile.TILEHEIGHT;
		if ((seenEnemy.getXlocation() > (creature.getXlocation() + maxX)) || (seenEnemy.getXlocation() < (creature.getXlocation() - maxX)) ||
				(seenEnemy.getYlocation() > (creature.getYlocation() + maxY)) || (seenEnemy.getYlocation() < (creature.getYlocation() - maxY))) {
			creature.setxMove(0);
			creature.setyMove(0);
			seen = false;
			seenEnemy = null;
			return;
		}
		attack(seenEnemy);
	}
	/**
	 * This method sends the player to the given x and y tile location
	 * @param x
	 * @param y
	 */
	public void goToLocation(int x, int y) { //Roaming Guard or Event Driven Action
		if (!checkCollision(x, y)) {
		Vector2i start = new Vector2i((int)(creature.getXlocation()/Tile.TILEWIDTH), (int)(creature.getYlocation()/Tile.TILEHEIGHT));
		Vector2i destination = new Vector2i(x, y);
		path = pf.findPath(start, destination);
		followPath(path);
		arrived = true;
		}
	}
	/**
	 * This method sends the player to the given checkpoint tile location
	 * @param checkpoint
	 */
	public void goToLocation (Locator checkpoint) { //Patrolling Guard and Stationary Guard
		Vector2i start = new Vector2i((int)(creature.getXlocation()/Tile.TILEWIDTH), (int)(creature.getYlocation()/Tile.TILEHEIGHT));
		int destx = (int)(checkpoint.getX()/Tile.TILEWIDTH);
		int desty = (int)(checkpoint.getY()/Tile.TILEHEIGHT);
		if (!checkCollision(destx, desty)) {
		Vector2i destination = new Vector2i(destx, desty);
		path = pf.findPath(start, destination);
		followPath(path);
		}
		arrive();
	}
	/**
	 * This method decides if the player has arrived at a location and counts down for half a second to contiune on to the next location
	 */
	public void arrive() {
		if (creature.getxMove() == 0 && creature.getyMove() == 0){
			arriveTimer += System.currentTimeMillis() - lastMoveTimer;
			lastMoveTimer = System.currentTimeMillis();
			if(arriveTimer < arriveCooldown)
				return;
			index++;
			if (index > checkpoints.size()){
				index = 0;
			}
		}
	}
	/**
	 * This method is used by an attacking creature to chased down, face, and attack a target creature.
	 * @param target
	 */
	public void attack(Creature target) {
		//Check if attacker is within 1 tile
		int destx = (int) (target.getXlocation()/Tile.TILEWIDTH);
		int desty = (int) (target.getYlocation()/Tile.TILEHEIGHT);
		Vector2i start = new Vector2i((int) (creature.getXlocation() / Tile.TILEWIDTH), (int) (creature.getYlocation()/ Tile.TILEHEIGHT));
		//FIND SHORTEST DISTANCE
		Vector2i destination = null; 		
		double shortest = 100000; 										 //Set huge
		byte Direction = 2;												 //default Look DOWN
		double temp = getDistance(start, new Vector2i(destx+1, desty));  //Check first
		if (shortest > temp && !handler.getWorld().getTile(destx+1, desty).isSolid() && !checkCollision(destx+1, desty)) {
			shortest = temp;
			destination = new Vector2i(destx+1, desty);
			Direction = 1;												 //Look LEFT
		}
		temp = getDistance(start, new Vector2i(destx-1, desty)); 		 //Check second
		if (shortest > temp && !handler.getWorld().getTile(destx-1, desty).isSolid() && !checkCollision(destx-1, desty)) {
			shortest = temp;
			destination = new Vector2i(destx-1, desty);
			Direction = 3;												 //Look RIGHT
		}
		temp = getDistance(start, new Vector2i(destx, desty+1)); 		 //Check third
		if (shortest > temp && !handler.getWorld().getTile(destx, desty+1).isSolid() && !checkCollision(destx, desty+1)) {
			shortest = temp;
			destination = new Vector2i(destx, desty+1);
			Direction = 0;												 //Look UP
		}
		temp = getDistance(start, new Vector2i(destx, desty-1)); 		 //Check fourth
		if (shortest > temp && !handler.getWorld().getTile(destx, desty-1).isSolid() && !checkCollision(destx, desty-1)) {
			shortest = temp;
			destination = new Vector2i(destx, desty-1);
			Direction = 2;												 //Look DOWN
		}
		//SET PATH FOR SHORTEST DISTANCE
		List<Node> path = pf.findPath(start, destination);
		//FOLLOW SHORTEST DISTANCE PATH
		followPath(path);
		//IF ATTACKER HAS ARRIVED
		if (creature.getxMove() == 0 && creature.getyMove() == 0) {
			//face target -> 
			creature.setLastDirection(Direction);
			//attack
			creature.setAttacking(true);
		} else {
			creature.setAttacking(false);
		}
	}
	/**
	 * Checks if the creature sees a player or shell
	 * @return
	 */
	public boolean checkSight() {
		int x = (int) (creature.getXlocation()/Tile.TILEWIDTH);
		int y = (int) (creature.getYlocation()/Tile.TILEHEIGHT);
		//Facing Left
		if (creature.getLastDirection() == 1) {
			if (checkSeesPlayer(x-1, y+1)) {
				return true;
			}else if (!checkCollision(x-1, y+1)) {
				if (checkSeesPlayer(x-2, y+1)) {
					return true;
				}else if (checkSeesPlayer(x-2, y+2)) {
					return true;
				}
			}else if (checkSeesPlayer(x-1, y)) {
				return true;  
			}else if (!checkCollision(x-1, y)) {
				if (checkSeesPlayer(x-2, y)) {
					return true;  
				}
			}else if (checkSeesPlayer(x-1, y-1)) {
				return true;
			}else if (!checkCollision(x-1, y-1)) {
				if (checkSeesPlayer(x-2, y-1)) {
					return true;
				}else if (checkSeesPlayer(x-2, y-2)) {
					return true;
				}
			}
		//Facing Right
		}else if (creature.getLastDirection() == 3) {
			if (checkSeesPlayer(x+1, y+1)) {
				return true;
			}else if (!checkCollision(x+1, y+1)) {
				if (checkSeesPlayer(x+2, y+1)) {
					return true;
				}
				if (checkSeesPlayer(x+2, y+2)) {
					return true;
				}
			}else if (checkSeesPlayer(x+1, y)) {
				return true;
			}else if (!checkCollision(x+1, y)) {
				if (checkSeesPlayer(x+2, y)) {
					return true;
				}
			}else if (checkSeesPlayer(x+1, y-1)) {
				return true;
			}else if (!checkCollision(x+1, y-1)) {
				if (checkSeesPlayer(x+2, y-1)) {
					return true;
				}
				if (checkSeesPlayer(x+2, y-2)) {
					return true;
				}
			}
		//Facing Up
		}else if (creature.getLastDirection() == 0) {
			if (checkSeesPlayer(x-1, y-1)) {
				return true;
			}else if (!checkCollision(x-1, y-1)) {
				if (checkSeesPlayer(x-1, y-2)) {
					return true;
				}else if (checkSeesPlayer(x-2, y-2)) {
					return true;
				}
			}else if (checkSeesPlayer(x, y-1)) {
				return true;
			}else if (!checkCollision(x, y-1)) {
				if (checkSeesPlayer(x, y-2)) {
					return true;
				}
			}else if (checkSeesPlayer(x+1, y-1)) {
				return true;
			}else if (!checkCollision(x+1, y-1)) {
				if (checkSeesPlayer(x+1, y-2)) {
					return true;
				} else if (checkSeesPlayer(x+2, y-2)) {
					return true;
				}
			}
		//Facing Down
		}else if (creature.getLastDirection() == 2) {
			if (checkSeesPlayer(x-1, y+1)) {
				return true;
			}else if (!checkCollision(x-1, y+1)) {
				if (checkSeesPlayer(x-1, y+2)) {
					return true;
				}else if (checkSeesPlayer(x-2, y+2)) {
					return true;
				}
			}else if (checkSeesPlayer(x, y+1)) {
				return true;
			}else if (!checkCollision(x, y+1)) {
				if (checkSeesPlayer(x, y+2)) {
					return true;
				}
			}else if (checkSeesPlayer(x+1, y+1)) {
				return true;
			}else if (!checkCollision(x+1, y+1)) {
				if (checkSeesPlayer(x+1, y+2)) {
					return true;
				}else if (checkSeesPlayer(x+2, y+2)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * This method is tells the given creature how to follow the given path.
	 * @param creature
	 * @param path
	 */
	public void followPath(List<Node> path) {
		float xlocation = creature.getXlocation();
		float ylocation = creature.getYlocation();
		//Path check timer
		/*-------------------------------------------------------------*/
		pathCheck += System.currentTimeMillis() - lastPathCheck;
		lastPathCheck = System.currentTimeMillis();
		if(pathCheck >= pathCheckCooldown) {
		/*-------------------------------------------------------------*/
			creature.xMove = 0;
			creature.yMove = 0;
			creature.path = path;
			if (creature.path != null) {
				if (creature.path.size() > 0) {
					Vector2i vec = creature.path.get(creature.path.size() - 1).tile;
					if (xlocation / Tile.TILEWIDTH < vec.getX() + .5) creature.xMove = creature.speed;		//Right
					else if (xlocation / Tile.TILEWIDTH > vec.getX() + .5) creature.xMove = -creature.speed;	//Left		/
					if (ylocation / Tile.TILEHEIGHT < vec.getY() + .5) creature.yMove = creature.speed;		//Down
					else if (ylocation / Tile.TILEHEIGHT > vec.getY() + .5) creature.yMove = -creature.speed;	//Up		/
				}
				else{
					Point p =  moveToCenter(2, creature);
					if(p.getX() == 0 && p.getY() == 0) {
						creature.travelling = false;
					} else {				
					creature.xMove = creature.speed * p.getX();
					creature.yMove = creature.speed * p.getY();
					}
				}
			}
		}
	}
	/**
	 * This method gets the distance by squaring x and y and square rooting the sum of the two.
	 * @param tile
	 * @param goal
	 * @return
	 */
	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt((dx * dx) + (dy * dy));
	}
	/**
	 * This checks if the Entity is alive. 
	 * @param entity
	 * @return
	 */
	public boolean isAlive(Entity entity ) {
		boolean charIsAlive = false; 	
		while (entity.getHealth()>0) {
				if(entity.getHealth() >0) {
					charIsAlive = true; 
					} 
			}
		return charIsAlive; 
	}
	/**
	 * Finds the center of a tile and moves to the center when pathfinding ends
	 * @param r
	 * @return
	 */
	private Point moveToCenter(int r, Creature creature) {
		float xlocation = creature.getXlocation();
		float ylocation = creature.getYlocation();
		
		int tilex = (int) (xlocation / Tile.TILEWIDTH) * Tile.TILEWIDTH;
		int tiley = (int) (ylocation / Tile.TILEHEIGHT) * Tile.TILEHEIGHT;
		
		int tilecenterx = tilex + (Tile.TILEWIDTH / 2);
		int tilecentery = tiley + (Tile.TILEHEIGHT / 2);
		
		int playercenterx = (int) (xlocation);
		int playercentery = (int) (ylocation);
		
		if(Math.hypot(tilecenterx-playercenterx, tilecentery-playercentery) < r)
			return new Point(0,0);
		Point p = new Point();
		if(Math.abs(tilecenterx-playercenterx)<r)p.x = 0;
		else{p.x = (tilecenterx-playercenterx>0?1:-1);}
		if(Math.abs(tilecentery-playercentery)<r)p.y = 0;
		else{p.y = (tilecentery-playercentery>0?1:-1);}
			
		return p;
	}
	/**
	 * This method is checks if there is a nonmoving entity on the tile location given.
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean checkCollision(int x, int y) {
		Rectangle r = new Rectangle(); // Location Rectangle
		r.width = Tile.TILEWIDTH;
		r.height = Tile.TILEHEIGHT;
		r.x = x * Tile.TILEWIDTH;
		r.y = y * Tile.TILEHEIGHT;
		for(Nonmoving n : handler.getWorld().getEntityManager().getNonmoving()) {
			if(n.getCollisionBounds(0, 0).intersects(r)) {
				return true;
			}
		}
		for(Objects o : handler.getWorld().getEntityManager().getObjects()) {
			if(o.getCollisionBounds(0, 0).intersects(r)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * This method is checks if there is a creature on the tile location given.
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean checkSeesPlayer(int x, int y) {
		int enemy;
		if (creature.getCreature().team == 1) {
			enemy = 2;
		} else if (creature.getCreature().team == 2) {
			enemy = 1;
		} else {
			enemy = 0; //No enemy
		}
		Rectangle r = new Rectangle(); // Location Rectangle
		r.width = Tile.TILEWIDTH;
		r.height = Tile.TILEHEIGHT;
		r.x = x * Tile.TILEWIDTH;
		r.y = y * Tile.TILEHEIGHT;
		for(Creature c : handler.getWorld().getEntityManager().getCreatures()) {
			if (c.getCreature().team == enemy) {
				if(c.getCollisionBounds(0, 0).intersects(r)) {
					seenEnemy = c;
					return true;
				}
			}
		}
		return false;
	}
}
