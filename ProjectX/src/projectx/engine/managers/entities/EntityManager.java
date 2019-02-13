package projectx.engine.managers.entities;

import java.util.ArrayList;
import java.util.Iterator;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.managers.entities.creatures.Creature;
import projectx.engine.managers.entities.nonmoving.Nonmoving;
import projectx.engine.managers.entities.objects.Objects;
import projectx.engine.utils.MergeSort;
import projectx.game.enums.CreatureType;

/**
 * This class manages all entities
 * @author Kenneth Lange
 *
 */
public class EntityManager {
	
	private Handler handler;
	private ArrayList<Entity> entities;
	private ArrayList<Creature> creatures;
	private ArrayList<Nonmoving> nonmoving;
	private ArrayList<Objects> objects;
	private boolean additionalEntity = false;
	private MergeSort mergeSort;
//	private Comparator<Entity> renderSorter = new Comparator<Entity>() {
//		@Override
//		public int compare(Entity a, Entity b) {
//			if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
//				return -1;
//			return 1;
//		}
//	};
	/**
	 * This method creates an ArrayList that holds all the data of how many entities exist
	 * @param handler
	 * @param player
	 */
	public EntityManager(Handler handler) {
		this.handler = handler;
		entities = new ArrayList<Entity>();
		creatures = new ArrayList<Creature>();
		nonmoving = new ArrayList<Nonmoving>();
		objects = new ArrayList<Objects>();
		mergeSort = new MergeSort();
	}
	/**
	 * This method updates the position of entities on a screen and sorts them from top to bottom. 
	 */
	public void update() {
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
		if (additionalEntity) {
			it = entities.iterator();
			additionalEntity = false;
		}
		Entity e = it.next();
		if(!e.isActive()) {
			it.remove();
		} else {
			e.update();
		}
	}
	mergeSort.mergeSort(entities);
//	entities.sort(renderSorter);
}
	/**
	 * This method renders the updated positions of the entities.
	 * @param g
	 */
	public void render(Graphics g){
		for(Entity e : entities){
			if(e.getClass().getName().toString().equals("projectx.engine.managers.entities.creatures.Creature"))
				e.render(g);
			else
				e.render(g);
		}
		for(Creature e : creatures){
			if (e.getCreature() == CreatureType.Player)
				e.postRender(g);
		}
	}
	public void render(Graphics g, double scale){
		for(Entity e : entities){
			e.render(g, scale);
		}
	}
	/**
	 * This method adds an entity to the entities ArrayList and the non-moving ArrayList to be stored.
	 * @param e
	 */
	public void addEntity(Nonmoving e){
		additionalEntity = true;
		entities.add(e);
		nonmoving.add(e);
	}
	/**
	 * This method adds an entity to the entities ArrayList and the creatures ArrayList to be stored.
	 * @param e
	 */
	public void addEntity(Creature e){
		additionalEntity = true;
		entities.add(e);
		creatures.add(e);
	}
	/**
	 * This method adds an entity to the entities ArrayList and the objects ArrayList to be stored.
	 * @param e
	 */
	public void addEntity(Objects o){
		additionalEntity = true;
		entities.add(o);
		objects.add(o);
	}
	
	public void updateCreature(int index, Creature c) {
		//Misc
		creatures.get(index).setHealth(c.getHealth());
		creatures.get(index).setInventory(c.getInventory());
		//Attacking
		creatures.get(index).setAttacking(c.getAttacking());
		//Locations and Directions
		creatures.get(index).setSpeed(c.getSpeed());
		creatures.get(index).setX(c.getX());
		creatures.get(index).setY(c.getY());
		creatures.get(index).setLastDirection(c.getLastDirection());
	}
	
	public void recieveCreatures(Creature[] creatures) {
		for (int i = 0; i < creatures.length; i++) {
			boolean found = false;
			for(int j = 0; j < this.creatures.size();j++) {
				if(creatures[i].equalsBirthNano(this.creatures.get(j))) {
					updateCreature(j, creatures[i]);
					found = true;
					j = this.creatures.size();
				}
			}
			if(!found)
			{
				addEntity(creatures[i]);
//				Convert.creatureToString(creatures[i]);
			}
		}
	}
	
	
	//Getters and Setters
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	public ArrayList<Creature> getCreatures() {
		return creatures;
	}
	public void setCreatures(ArrayList<Creature> creatures) {
		this.creatures = creatures;
	}
	public ArrayList<Nonmoving> getNonmoving() {
		return nonmoving;
	}
	public void setNonmoving(ArrayList<Nonmoving> nonmoving) {
		this.nonmoving = nonmoving;
	}
	
	public String toString() {
		String s = "";
		for(Entity e : entities)
			s += e.toString() + "\n";
		return s;
	}
	public ArrayList<Objects> getObjects() {
		return objects;
	}
	public void setObjects(ArrayList<Objects> objects) {
		this.objects = objects;
	}
}
