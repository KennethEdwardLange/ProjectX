package projectx.engine.worlds;

import java.awt.Point;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Color;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.io.IO;
import projectx.engine.state.MapBuilderMenuState;
import projectx.engine.state.State;
import projectx.engine.tile.Tile;
import projectx.engine.utils.Utils;
/**
 * 
 * @author Kenneth Lange and Mitchell Hoppe
 *
 */
public class Map extends World{
	
	private final String[] tileName = {"Air", "Shallow Water", "Sand", "Dirt", "Grass", "Stone", "Deep Water", "Cement"};
	private final String[] modeName = {"tiles", "locators", "nonmoving", "objects"};
	
	private  boolean REN_POINTER 		= true;
	private boolean REN_SCALE 		= true;
	private String path;
	private double scale = 1.00;
	private int camera_speed = 5;
	private int tile_mode = 5;
	private int tiletype_mode = 0;

	public Map(Handler handler, String path){
		this.handler = handler;
		this.path = path;
		loadWorld(path);
		getClass();
	}
	/**
	 * This method loads in the world from a file path.
	 * @param path
	 */
	@Override
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
	}
	/**
	 * This method changes the current tile that is to be placed by the user
	 * @return
	 */
	public String changeCurTile(){
		if(tile_mode < 0)
			tile_mode = 1;
		return tileName[tile_mode];		
	}
	
	public void update(){
		getInput();
	}
	
	public void render(Graphics g){
		//Black background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		g.setColor(Color.WHITE);
		/*Render optimization*/
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH / scale);																		// Renders only the
		int xEnd = (int) Math.min(width * scale, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH * (scale < 1 ? 1 / scale : scale) + 1);		// tiles that can
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT / scale);																		// be seen by the
		int yEnd = (int) Math.min(height * scale, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT * (scale < 1 ? 1 / scale : scale) + 1);	// player's camera
		/*-------------------*/
		for(int y = yStart; y < (int)(yEnd / scale); y++){
			for(int x = xStart; x < (int)(xEnd / scale); x++){
				getTile(x, y).render(g, (int) (x*(int)(Tile.TILEWIDTH * scale) - handler.getGameCamera().getxOffset()), (int) (y*(int)(Tile.TILEHEIGHT * scale) - handler.getGameCamera().getyOffset()), scale);
			}
		}
		int tile_x = (int) getPointerTile().getX();
		int tile_y = (int) getPointerTile().getY();
		g.setColor(Color.WHITE);
		if(REN_SCALE) {
			g.drawString("scale> " + scale, 20, 30);
		}
		if(REN_POINTER) {
			g.drawString("pointer> "+tile_x+" , "+tile_y, 20, 60);
		}
		g.drawString("Current Tile = " + changeCurTile(), 5, 12 );
		g.drawString("Current Mode = " + modeName[tiletype_mode], 5, 80);
	}
	/**
	 * This method gets the input from the user
	 */
	private void getInput() {
		//Keyboard
		handler.getKeyboard();
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			handler.getGameCamera().move(0,-camera_speed, scale);
		else if(Keyboard.isKeyDown(Keyboard.KEY_S) && !handler.getKeyboard().isKeyDown(Keyboard.KEY_LCONTROL))
			handler.getGameCamera().move(0,camera_speed, scale);
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
			handler.getGameCamera().move(-camera_speed,0, scale);
		else if(Keyboard.isKeyDown(Keyboard.KEY_D))
			handler.getGameCamera().move(camera_speed,0, scale);
		if(Keyboard.isKeyDown(Keyboard.KEY_F1)) {
			scale = 0.97;
			scale = Math.round(scale * 100.0) / 100.0;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_F2)) {
			scale = 1.00;
			scale = Math.round(scale * 100.0) / 100.0;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_F3)) {
			scale = 1.03;
			scale = Math.round(scale * 100.0) / 100.0;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_F4)) {
			scale = 1.12;
			scale = Math.round(scale * 100.0) / 100.0;
		}if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			handler.getEngine().setMapBuilderMenuState(new MapBuilderMenuState(handler));
			State.setState(handler.getEngine().getMapBuilderMenuState());
		//Increment through arraylist
		}if(handler.getKeyboard().isKeyPressed(Keyboard.KEY_UP)) {
			tile_mode++;
			if(tile_mode > 7)
				tile_mode = 1;
		}else if(handler.getKeyboard().isKeyPressed(Keyboard.KEY_DOWN)) {
			tile_mode--;
			if(tile_mode < 1)
				tile_mode = 7;
		//Save
		}if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			Utils.saveWorld(path, super.tilelocation, super.spawnX, super.spawnY);
			IO.println("World saved to " + path);
		}
		//Scale
		if(handler.getKeyboard().isKeyPressed(Keyboard.KEY_F12) && scale < 1.25) {
			scale += 0.01;
			scale = Math.round(scale * 100.0) / 100.0;
		}else if(handler.getKeyboard().isKeyPressed(Keyboard.KEY_F11) && scale > 0.75) {
			scale -= 0.01;
			scale = Math.round(scale * 100.0) / 100.0;
		}
		//Mouse
		handler.getMouse();
		if(Mouse.isButtonDown(0)) {		
			int tile_x = (int) getPointerTile().getX();
			int tile_y = (int) getPointerTile().getY();
			if(tile_x >= 0 && tile_x < tilelocation.length && tile_y >= 0 && tile_y < tilelocation[0].length)
				tilelocation[tile_x][tile_y] = tile_mode;
		}
	}
	//Getters and Setters
	private Point getPointerTile() {
		handler.getMouse();
		int x = (int) (Mouse.getX() + handler.getGameCamera().getxOffset());
		handler.getMouse();
		int y = (int) (handler.getHeight() - Mouse.getY() + handler.getGameCamera().getyOffset());			
		int tile_x = (int)(x / (Tile.TILEWIDTH * scale));
		int tile_y = (int)(y / (Tile.TILEHEIGHT * scale));
		return new Point((int)tile_x, (int)tile_y);
	}
	public int get_tile_mode() {
		if (tile_mode > 6){
			tile_mode = 1;
		}
		if(tile_mode < 1){
			tile_mode = 6;
		}
		return tile_mode;
	}
	public void set_tile_mode(int tile_mode) {
		if(!(tile_mode > 6 && tile_mode < 1))
			this.tile_mode = tile_mode;	
	}
	public void set_tiletype_mode(int tiletype_mode){
		if(!(tiletype_mode > 3 && tiletype_mode < 0)){
			this.tiletype_mode = tiletype_mode;
		}
	}
	
	
}