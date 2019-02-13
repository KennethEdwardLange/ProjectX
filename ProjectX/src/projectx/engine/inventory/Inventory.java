package projectx.engine.inventory;

import projectx.engine.Handler;
import projectx.engine.managers.items.Item;
import projectx.game.enums.ItemType;

/**
 * This class is responsible for handling an entity's inventory.
 * 
 * @author Kenneth Lange
 *
 */
public class Inventory {

	private final int LENGTH = 3;

	private Handler handler;
	private boolean active = false;
	private Item[] inventoryItems;
	private int size;

	/**
	 * This constructor takes in a handler and creates a new arrayList for held
	 * items within the inventory.
	 * 
	 * @param handler
	 */
	public Inventory(Handler handler) {
		this.handler = handler;
		inventoryItems = new Item[LENGTH];
		for (int i= 0; i<LENGTH; i++) {
			inventoryItems[i]=new Item(handler, 0, 0, ItemType.Null);
		}
	}

	public Inventory(Handler handler, byte[] a) {
		this.handler = handler;
		inventoryItems = new Item[LENGTH];
		for (int i = 0; i < inventoryItems.length; i++) {
			 inventoryItems[i] = new Item(a[i]);
		}

	}

	

	// Inventory Methods
	/**
	 * This method adds an item to the inventory.
	 * 
	 * @param item
	 */
	public boolean addItem(Item item) {
		if (size == LENGTH)
			return false;
		for (int i=0; i<size; i++) {
			Item it = inventoryItems[i];
				if (it.getId() == item.getId()) {
					return false;
				}
			
		}
		inventoryItems[size] = item;
		size++;
		return true;
	}

	// Getters and Setters
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * Returns true if the player's inventory matches the indicated item
	 * @param id
	 * @return
	 */
	public boolean contains(int id) {
		for (int i=0; i<size; i++) {
			Item it = inventoryItems[i];
				if (it.getId() == id) {
					return true;
				}
		}
		return false;
	}

	/**
	 * To be filled in later, converts the current object to a byte array to be
	 * transferred over the socket.
	 * 
	 * @return
	 */
	public byte[] toByteArray() {
		byte[] b = new byte[LENGTH];
		for (int i = 0; i < b.length; i++) {
			b[i] = inventoryItems[i].getId();
		}
		return b;
	}

	public Item[] getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(Item[] inventoryItems) {
		this.inventoryItems = inventoryItems;
	}
	
	/**
	 * Removes the item with matching id from inventory
	 * @param id
	 * @return true if item has been removed
	 */
	public boolean remove(int id) {
		for (int i=0; i<size; i++) {
			Item it = inventoryItems[i];
				if (it.getId() == id) {
					if (i!=size-1) {
						inventoryItems[i]=inventoryItems[size-1];
						inventoryItems[size-1]=new Item(handler, 0, 0, ItemType.Null);
					}
					else {
						inventoryItems[i]=new Item(handler, 0, 0, ItemType.Null);
					}
					size--;
					return true;
				}
			
		}
		return false;
	}

}
