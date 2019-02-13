package projectx.engine.managers.locators;

import java.util.ArrayList;
import java.util.Iterator;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.game.enums.LocatorType;
/**
 * This class manages all entities
 * @author Kenneth Lange
 *
 */
public class LocatorManager {
	
	private Handler handler;
	private ArrayList<Locator> locators;
	private ArrayList<Locator> checkpoints;
	private ArrayList<Locator> stations;
	

	/**
	 * This method creates an ArrayList that holds all the data of how many locators exist
	 * @param handler
	 */
	public LocatorManager(Handler handler) {
		this.handler = handler;
		locators = new ArrayList<Locator>();
		checkpoints = new ArrayList<Locator>();
		stations = new ArrayList<Locator>();
	}
	/**
	 * This method updates the position of locators on a screen and sorts them from top to bottom. 
	 */
	public void update(){
		Iterator<Locator> it = locators.iterator();
		while(it.hasNext()) {
			Locator e = it.next();
			e.update();
			if(!e.isActive())
				it.remove();
		}
	}
	/**
	 * This method renders the updated positions of the locators.
	 * @param g
	 */
	public void render(Graphics g){
		for(Locator e : locators){
			e.render(g);
		}
	}
	
	public void render(Graphics g, double scale){
		for(Locator e : locators){
			e.render(g, scale);
		}
	}
	
	/**
	 * This method adds a locators to the locators ArrayList to be stored.
	 * @param e
	 */
	public void addLocators(Locator e){
		locators.add(e);
		if (e.getType().equals(LocatorType.Checkpoint)) {
			checkpoints.add(e);
		} else if (e.getType().equals(LocatorType.Station)) {
			stations.add(e);
		}
//		} else if (e.getType().equals(LocatorType.PrisonerSpawner)) {
//			
//		}
	}
	
	//Getters and Setters
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<Locator> getLocators() {
		return locators;
	}

	public void setLocators(ArrayList<Locator> locators) {
		this.locators = locators;
	}
	public ArrayList<Locator> getCheckpoints() {
		return checkpoints;
	}
	public ArrayList<Locator> getStations() {
		return stations;
	}
	
}
