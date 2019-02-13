package projectx.engine.tile;

import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;

import projectx.engine.glgfx.Graphics;
import projectx.game.enums.TileType;

/**
 * This class is responsible for taking in all the different types of tiles and creating a template for any additional tiles that may be created.
 * @author Kenneth Lange
 *
 */
public class Tile {
	//DEBUG MODE
	public static boolean DEBUGMODE = true; //Change to true for tile grid
	
	//All TILES PREMADE
	public static Tile[] tiles = new Tile[256]; //Holds one type of every single tile in the game (Increase as needed)	
	public static Tile airTile = new Tile(TileType.Air);
	public static Tile shallowWaterTile = new Tile(TileType.ShallowWater);
	public static Tile sandTile = new Tile(TileType.Sand);
	public static Tile dirtTile = new Tile(TileType.Dirt);
	public static Tile grassTile = new Tile(TileType.Grass);
	public static Tile stoneTile = new Tile(TileType.Stone);
	public static Tile deepWaterTile = new Tile(TileType.DeepWater);
	public static Tile cementTile = new Tile(TileType.Cement);
	public static Tile winTile = new Tile(TileType.Win);
	
	
	//CLASS STUFF
	public static final int TILEWIDTH = 32, TILEHEIGHT = 32;
	
	protected Texture texture;
	private TileType type;
	
	public Tile(TileType type) {
		this.type = type;
		this.texture = type.texture;
		tiles[type.id] = this;
	}
	/**
	 * This method is responsible for updating rendered tiles.
	 */
	public void update(){
		//Something that may be added here is animated tiles.
	}
	/**
	 * This method is responsible for rendering updated tiles.
	 * @param g
	 * @param x
	 * @param y
	 */
	public void render(Graphics g, int x, int y){
		if (texture != null)
			g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT);
		if(DEBUGMODE) {
			g.setColor(Color.BLACK);
			g.drawRect(x, y, TILEWIDTH, TILEHEIGHT);
			g.setColor(Color.WHITE);
		}
	}
	
	/**
	 * This method is responsible for rendering updated tiles to the scale provided
	 * @param g
	 * @param x
	 * @param y
	 */
	public void render(Graphics g, int x, int y, double scale){
		if (texture!=null)
		g.drawImage(texture, x, y, (int)(TILEWIDTH * scale), (int)(TILEHEIGHT * scale));
		if(DEBUGMODE) {
			g.setColor(new Color(0,0,0,127));
			g.drawRect(x, y, (int)(TILEWIDTH * scale), (int)(TILEHEIGHT * scale));
			g.setColor(Color.WHITE);
		}
	}
	/**
	 * This method decides whether the tile is solid.
	 * It is by default set to false.
	 * @return
	 */
	public boolean isSolid(){
		return type.solid;
	}
	/**
	 * This method gets the ID of a specific tile.
	 * @return
	 */
	public int getId(){
		return type.id;
	}
	/**
	 * This method determines if a specific tile is a winning tile.
	 * @return
	 */
	public boolean isWin(){
		return type.win;
	}
	/**
	 * This method determines if a specific tile is a water tile.
	 * @return
	 */
	public boolean isWater(){
		return type.water;
	}
}
