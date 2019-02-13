package projectx.engine;

import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import projectx.engine.display.EngineDisplay;
import projectx.engine.glgfx.GameCamera;
import projectx.engine.glgfx.Graphics;
import projectx.engine.input.Input;
import projectx.engine.state.MenuState;
import projectx.engine.state.State;
import projectx.engine.terminal.Terminal;
import projectx.game.audio.Audio;
import projectx.game.client.DataProtocol;
import projectx.game.textures.Assets;

/**
 * Holds all of the base code for the game.
 * @author Kenneth Lange
 *
 */
public class EngineGL implements Runnable{ //Must implement Runnable in order for it to use a thread
	@SuppressWarnings("unused")
	private EngineDisplay display;
	private int width, height;
	public String title;
	
	private static final int FPS = 60;
	private Graphics g;
	
	//States
	public State gameState;
	public State menuState;
	public State multiplayerMenuState;
	public State mapBuilderState;
	public State mapBuilderMenuState;

	//Input
	private Input keyboard;
	private Mouse mouse;
	
	//Terminal
	private Terminal terminal;
	
	//Camera
	private GameCamera gameCamera;
	
	//Handler
	public Handler handler;
	
	//Game Client
	public DataProtocol data_protocol;

	public EngineGL(String title, int width){
		this.width = width;
		height = width / 16 * 9;
		this.title = title;
		// Graphics and Inputs
		g = new Graphics();
	}
	/**
	 * This method initializes the graphics for the game
	 */
	private void init(){
		display = new EngineDisplay(title, width, height);//creates new display
		
		//Pictures
		Assets.init();
		Audio.init();
		
		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0, 0);
		terminal = new Terminal(handler);
		data_protocol = new DataProtocol(handler);
		
		//Initialize States
		menuState = new MenuState(handler);
		State.setState(menuState);//Sets the initial state of the window upon starting the game
		
		//Inputs
		keyboard = new Input();
		
	    g.setFont(new Font("Times New Roman", Font.BOLD, 15)); //Can't update this every update because it lags the game out
	}
	/**
	 * This method acts as an updater keeping track of objects' and window's new locations
	 */
	private void update() {//updates positions etc.	
		keyboard.update();
		if(State.getState() != null)
			State.getState().update();
		terminal.update();
		data_protocol.update();
	}
	/**
	 * This method takes the updated locations and draws them to the screen.
	 */
	private void render(){//draws updated positions to the screen
		if(State.getState() != null)
			State.getState().render(g);
		terminal.render(g);
	}
	/**
	 * This method runs the engine and sets a base for how often it can update and render.
	 */
	public void run() {
		init();
		while (!Display.isCloseRequested()) {
			
			update();
			render();

			Display.update(); //updates
			Display.sync(FPS); //sets fps in sync mode
		}
		terminal.stop();
		Display.destroy();
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public GameCamera getGameCamera() {
		return gameCamera;
	}
	public DataProtocol getDataProtocol() {
		return data_protocol;
	}
	public State getState() {
		return State.getState();
	}
	public State getGameState() {
		return gameState;
	}
	public void setGameState(State gameState) {
		this.gameState = gameState;
	}
	public State getMenuState() {
		return menuState;
	}
	public void setMenuState(State menuState) {
		this.menuState = menuState;
	}
	public State getMultiplayerMenuState() {
		return multiplayerMenuState;
	}
	public void setMultiplayerMenuState(State multiplayerMenuState) {
		this.multiplayerMenuState = multiplayerMenuState;
	}
	public State getMapBuilderState() {
		return mapBuilderState;
	}
	public void setMapBuilderState(State mapBuilderState) {
		this.mapBuilderState = mapBuilderState;
	}
	public State getMapBuilderMenuState() {
		return mapBuilderMenuState;
	}
	public void setMapBuilderMenuState(State mapBuilderMenuState) {
		this.mapBuilderMenuState = mapBuilderMenuState;
	}
	/**
	 * Closes the nessisary processes 
	 */
	public void exit() {
		data_protocol.end();
		terminal.stop();
		System.exit(1);
	}
	public Input getKeyboard() {
		return keyboard;
	}
	public Mouse getMouse() {
		return mouse;
	}
	
	public Terminal getTerminal() {
		return terminal;
	}
}