package projectx.engine.worlds;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.managers.entities.EntityManager;
import projectx.engine.managers.entities.creatures.Creature;
import projectx.engine.managers.entities.nonmoving.Nonmoving;
import projectx.engine.managers.entities.objects.Objects;
import projectx.engine.managers.items.Item;
import projectx.engine.managers.items.ItemManager;
import projectx.engine.managers.locators.Locator;
import projectx.engine.managers.locators.LocatorManager;
import projectx.engine.tile.Tile;
import projectx.engine.utils.Utils;
import projectx.game.enums.CreatureType;
import projectx.game.enums.ItemType;
import projectx.game.enums.LocatorType;
import projectx.game.enums.NonmovingType;
import projectx.game.enums.ObjectsType;

/**
 * This class hold all the data needed to load in a world and render it to the game screen.
 * @author Kenneth Lange
 *
 */
public class World {

	protected Handler handler;
	protected int width, height; //Size of map
	protected int spawnX, spawnY;
	protected int[][] tilelocation;
	protected int [][] nonmovinglocation, locatorlocation;
	protected Creature self;
	private Generator generator;
	//Locators
	protected LocatorManager locatorManager;
	//Entities
	protected EntityManager entityManager;
	//Item
	protected ItemManager itemManager;

	World(){}
	/**
	 * This constructor sets the handler and loads in the world properly.
	 * @param handler
	 * @param path
	 */
	public World(Handler handler, String path){
		this.handler = handler;
		generator = new Generator(handler, this);
		//Managers
		entityManager = new EntityManager(handler);
		locatorManager = new LocatorManager(handler);
		itemManager = new ItemManager(handler);//
		
		//Entities
		this.self = new Creature(handler, 15, 48, CreatureType.Player);
		entityManager.addEntity(self);
		//keys
				itemManager.addItem(new Item(handler, 13, 51, ItemType.Key));
				itemManager.addItem(new Item(handler, 24, 40, ItemType.Key));
				itemManager.addItem(new Item(handler, 49, 53, ItemType.Key));
				itemManager.addItem(new Item(handler, 50, 8, ItemType.Key));
				//weapons
				itemManager.addItem(new Item(handler, 30, 59, ItemType.Crowbar));
				itemManager.addItem(new Item(handler, 41, 59, ItemType.Crowbar));
				itemManager.addItem(new Item(handler, 31, 7, ItemType.Crowbar));
				//armor
				itemManager.addItem(new Item(handler, 66, 51, ItemType.Helmet));
				itemManager.addItem(new Item(handler, 43, 7, ItemType.Helmet));
		
		loadWorld(path);
		getClass();

			
	}
	
	/**
	 * This method updates all items and entities in the world.
	 */
	public void update(){
		itemManager.update();
		locatorManager.update();
		entityManager.update();
	}
	/**
	 * This method is responsible for rendering everything in the world.
	 * @param g
	 */
	public void render(Graphics g){
//		g.setColor(.5,.5,.5,1);//This could be based on time
		
		/*Render optimization*/
		int xStart =  (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);									// Renders only the
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);		// tiles that can
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);									// be seen by the
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);	// player's camera
		/*-------------------*/		
		for(int y = yStart; y < yEnd; y++){
			for(int x = xStart; x < xEnd; x++){
				getTile(x, y).render(g, (int) (x*Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), (int) (y*Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		//Entities
		itemManager.render(g);
		locatorManager.render(g);
		entityManager.render(g);
	}
	/**
	 * This method loads in the world from a file path.
	 * @param path
	 */
	protected void loadWorld(String path){
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]) * Tile.TILEWIDTH  - (Tile.TILEWIDTH/2) + 1; //Sets the spawn to size of tiles.
		spawnY = Utils.parseInt(tokens[3]) * Tile.TILEHEIGHT - (Tile.TILEWIDTH/2) - 22;//and centers the player on the tile
		
		//Tile locations taken from the text file
		tilelocation = new int[width][height];
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				tilelocation[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
		//Nonmoving entities' locations taken from text file
		nonmovinglocation = new int[width][height];
		for(int y = height; y < height*2; y++){
			for(int x = 0; x < width; x++){
				int temp = Utils.parseInt(tokens[(x + y * width) + 4]);
				String tempString = temp + "";
				switch(tempString) {
				//SINGLE TYPES
				case  "1": 
					entityManager.addEntity(new Nonmoving(handler, x, y-height, NonmovingType.Rock));
					break; 
				case "2": 
					entityManager.addEntity(new Nonmoving(handler, x, y-height, NonmovingType.Tree));
					break;
				case "3": 
					entityManager.addEntity(new Nonmoving(handler, x, y-height, NonmovingType.Table));
					break;
				case "4": 
					entityManager.addEntity(new Nonmoving(handler, x, y-height, NonmovingType.Toilet));
					break;
				case "5": 
					entityManager.addEntity(new Nonmoving(handler, x, y-height, NonmovingType.Bush));
					break;
				case "6": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.ServingArea));
					break;
				case "7": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.WeightedBar));
					break;
				case "8": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.Dumbbell));
					break;
				case "9": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.Well));
					break;
				//MULTIPLE TYPES]
				case "10": 
					entityManager.addEntity( new Objects(handler,x,y-height, ObjectsType.Door0));
					break;
				case "11": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.Door1));
					break;
				case "12": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.CrackedWall));
					break;
				case "13": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.BrokenWall));
					break;
				case "14": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.Bed1));
					break;
				case "15": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.Bed2));
					break;
				case "16": 
					entityManager.addEntity(new Nonmoving(handler, x, y-height, NonmovingType.Chair1));
					break;
				case "17": 
					entityManager.addEntity(new Nonmoving(handler, x, y-height, NonmovingType.Chair2));
					break;
				case "18": 
					entityManager.addEntity(new Nonmoving(handler, x, y-height, NonmovingType.Chair3));
					break;
				case "20": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.Wall0));
					break; 
				case "21": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.Wall1));
					break; 
				case "22": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.Wall2));
					break;
				case "23": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.Wall3));
					break;
				case "24": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.Wall4));
					break;
				case "25": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.ElectricFence0));
					break; 
				case "26": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.ElectricFence1));
					break; 
				case "27": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.ElectricFence2));
					break;
				case "28": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.ElectricFence3));
					break;
				case "29": 
					entityManager.addEntity(new Objects(handler, x, y-height, ObjectsType.ElectricFence4));
					break;
				case "30": 
					entityManager.addEntity(new Nonmoving(handler, x, y-height, NonmovingType.Sunflower));
					break;
				case "31": 
					entityManager.addEntity(new Nonmoving(handler, x, y-height, NonmovingType.Pumpkin));
					break;
				case "32": 
					entityManager.addEntity(new Nonmoving(handler, x, y-height, NonmovingType.Watermelon));
					break;
				case "33": 
					entityManager.addEntity(new Nonmoving(handler, x, y-height, NonmovingType.Corn));
					break;
				default : 
					
				
				}
			}
		}
		//Locators' locations taken from text file
		locatorlocation = new int[width][height];
		for(int y = height*2; y < height*3; y++){
			for(int x = 0; x < width; x++){
				int temp  = Utils.parseInt(tokens[(x + y * width) + 4]);
					
				String tempString = temp + "";
				switch(tempString) {
				case  "0": 
					break; //Do nothing for efficiency 
				//SINGLE TYPES
				case  "1": 
					locatorManager.addLocators(new Locator(handler, x, y-height*2, LocatorType.Checkpoint));
					break;
				case  "2": 
					locatorManager.addLocators(new Locator(handler, x, y-height*2, LocatorType.Station));
					break;
				case  "3": 
					locatorManager.addLocators(new Locator(handler, x, y-height*2, LocatorType.PrisonerSpawner));
					break; 
				case "4": 
					locatorManager.addLocators(new Locator(handler, x, y-height*2, LocatorType.GaurdSpawner));
					break;
				case "5": 
					locatorManager.addLocators(new Locator(handler, x, y-height*2, LocatorType.ItemSpawner));
					break;
				//MULTIPLE TYPES
				case "21": 
					locatorManager.addLocators(new Locator(handler, x, y-height*2, LocatorType.PrisonerSpawner));
					break; 
				case "30": 
					generator.generateRandomItem(x, y - height*2);
					break;
				case "31": 
					itemManager.addItem(new Item(handler, x, y-height*2, ItemType.Key));
					break; 
				case "32": 
					itemManager.addItem(new Item(handler, x, y-height*2, ItemType.Crowbar));
					break;
				case "33":
					itemManager.addItem(new Item(handler, x, y-height*2, ItemType.Helmet));
					break; 
				default :
					break; //Do nothing if number does not exist
				}
			}
		}
		
	}
	//Getters and Setters
	public Tile getTile(int x, int y){
		if( x < 0 || y < 0 || x >= width || y >= height){
			return Tile.airTile; //If any tile is not set, it is defaulted to a air tile.
		}
		
		Tile t = Tile.tiles[tilelocation[x][y]];
		if(t == null)
			return Tile.winTile; //If tile doesn't exist replace with win tile.
		return t;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}
	public ItemManager getItemManager() {
		return itemManager;
	}
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	public LocatorManager getLocatorManager() {
		return locatorManager;
	}
	public void setLocatorManager(LocatorManager locatorManager) {
		this.locatorManager = locatorManager;
	}
	
	public Creature getSelf() {
		return self;
	}
	public Generator getGenerator() {
		return generator;
	}
}

