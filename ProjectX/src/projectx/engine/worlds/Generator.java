package projectx.engine.worlds;

import java.util.ArrayList;
import java.util.Random;

import projectx.engine.Handler;
import projectx.engine.managers.entities.creatures.Creature;
import projectx.engine.managers.items.Item;
import projectx.engine.managers.locators.Locator;
import projectx.engine.tile.Tile;
import projectx.game.enums.CreatureType;
import projectx.game.enums.ItemType;

public class Generator {
	
	private Handler handler;
	private World world;
	private Random rand;

	public Generator(Handler handler, World world) {
		this.handler = handler;
		this.world = world;
		rand = new Random();
	}
	/**
	 * This method takes in a number and creates a random guard that number of times who acts according to its type 
	 * @param amount
	 */
	protected void generateRandomGaurds(int amount) {
		
		for(int i=0; i<amount; i++) {
			int t = rand.nextInt(22);
			CreatureType type;
			Creature creature;
			if (t<9) {
				type = CreatureType.RGuard;
				int roamx = rand.nextInt(40) + 20, roamy = rand.nextInt(40) + 20;
				creature = new Creature(handler, roamx, roamy, type);
			} else if (t < 13) {
				type = CreatureType.SGuard;
				int index = rand.nextInt(world.locatorManager.getStations().size()-1);
				Locator station = world.locatorManager.getStations().get(index);
				creature = new Creature(handler, station.getX()/Tile.TILEWIDTH, station.getY()/Tile.TILEHEIGHT, type);
				creature.addCheckpoint(station);
			} else {
				type = CreatureType.PGuard;
				int index = rand.nextInt(world.locatorManager.getStations().size()-1);
				Locator checkpoint = world.locatorManager.getCheckpoints().get(index);
				creature = new Creature(handler, checkpoint.getX()/Tile.TILEWIDTH, checkpoint.getY()/Tile.TILEHEIGHT, type);
				creature.addCheckpoint(checkpoint);
				for(int j = 0; j < rand.nextInt(world.locatorManager.getCheckpoints().size()/2)+1;j++){
					creature.addCheckpoint(world.locatorManager.getCheckpoints().get(rand.nextInt(world.locatorManager.getCheckpoints().size()-1)));
				}
			}
			world.entityManager.addEntity(creature);
		}
	}
	/**
	 * This method takes in a list of checkpoints and creates a patrol guard who then walks back and forth between the checkpoints
	 * @param checkpoints
	 */
	public void generatePatrolGuard(ArrayList<Locator> checkpoints) {
		CreatureType type = CreatureType.PGuard;
		Creature creature = new Creature(handler, checkpoints.get(0).getX()/Tile.TILEWIDTH, checkpoints.get(0).getY()/Tile.TILEHEIGHT, type);
		for (int i = 0; i < checkpoints.size() - 1; i++) {
			creature.addCheckpoint(checkpoints.get(i));
		}
		world.entityManager.addEntity(creature);
	}
	/**
	 * This method takes in a station and creates a stationary guard who then stays at that station looking for prisoners
	 * @param x
	 * @param y
	 */
	public void generateStationaryGuard(Locator station) {
		CreatureType type = CreatureType.SGuard;
		int index = rand.nextInt(world.locatorManager.getStations().size()-1);
		station = world.locatorManager.getStations().get(index);
		Creature creature = new Creature(handler, station.getX()/Tile.TILEWIDTH, station.getY()/Tile.TILEHEIGHT, type);
		creature.addCheckpoint(station);
		world.entityManager.addEntity(creature);
	}
	/**
	 * This method takes in a location and creates a roaming guard there who then roams the area
	 * @param x
	 * @param y
	 */
	public void generateRoamingGuard(int x, int y) {
		CreatureType type = CreatureType.RGuard;
		Creature creature = new Creature(handler, x, y, type);
		world.entityManager.addEntity(creature);
	}
	/**
	 * This method takes in a location and creates a roaming prisoner there who then roams the area
	 * @param x
	 * @param y
	 */
	public void generateRoamingPrisoners(int x, int y) {
		CreatureType type = CreatureType.Prisoner;
		Creature creature = new Creature(handler, x, y, type);
		world.entityManager.addEntity(creature);
	}
	/**
	 * This method takes in a location and creates a random item there.
	 * @param x
	 * @param y
	 */
	public void generateRandomItem(int x, int y) {
		int n = rand.nextInt(10)+1;
		if (n<=6)
		world.itemManager.addItem(new Item(handler, x, y, ItemType.Key));
		else if(n<=8)
		world.itemManager.addItem(new Item(handler, x, y, ItemType.Helmet));
		else if(n<=10)
		world.itemManager.addItem(new Item(handler, x, y, ItemType.Crowbar));
	}
}
