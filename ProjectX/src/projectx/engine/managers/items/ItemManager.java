package projectx.engine.managers.items;

import java.util.ArrayList;
import java.util.Iterator;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
/**
 * This class manages all items in the game.
 * @author Kenneth Lange
 *
 */
public class ItemManager {

	private Handler handler;
	private ArrayList<Item> items;
	/**
	 * This constructor takes in a handler and creates a new array list of items.
	 * @param handler
	 */
	public ItemManager(Handler handler) {
		this.handler = handler;
		items = new ArrayList<Item>();
	}
	/**
	 * This method updates the items in the game.
	 */
	public void update() {
		Iterator<Item> it = items.iterator();
		while(it.hasNext()) {
			Item i = it.next();
			if(i.update())
				it.remove();
		}
	}
	/**
	 * This method renders the items in the game.
	 * @param g
	 */
	public void render(Graphics g) {
		for(Item i : items)
			i.render(g);
	}
	
	public void render(Graphics g, double scale) {
		for(Item i : items)
			i.render(g);
	}
	
	/**
	 * Thie method adds an item to the game.
	 * @param i
	 */
	public void addItem(Item i) {
		i.setHandler(handler);
		items.add(i);
	}

	//Getters and Setters
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
}
