package projectx.engine.terminal;

import org.lwjgl.input.Keyboard;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.terminal.TerminalExceptions.InvalidCommandException;
import projectx.engine.terminal.modes.Mode;
import projectx.engine.terminal.modes.StdIO;
import projectx.engine.terminal.modes.Window;

public class Terminal{
	
	String[] TERMINAL_MODES = new String[] {"std", "win"};
	Mode[] MODE_OBJECTS;
	
	//if the terminal is currently open, this is true
	private boolean isEnabled;
	
	//Objects for referencing the Handler and CommandParser
	public Handler handler;
	CommandParser commandParser;

	public Mode mode;
	
	/**
	 * Constructor for the Terminal class
	 * instantiates all the necessary objects.
	 * @param handler
	 */
	public Terminal(Handler handler) {
		this.handler = handler;
		
		MODE_OBJECTS = new Mode[] {new StdIO(this, handler), new Window(this, handler)};
		mode = MODE_OBJECTS[1];
		
		if(mode != null ) {
			mode.init();
		}
		
		commandParser = new CommandParser(handler, this);
		
		isEnabled 		= false;
	}
	
	
	/**
	 * Update method that is called from the Engine, will check the input
	 * and reset the previous char variable.
	 */
	public void update() {
		
		if (handler.getKeyboard().isKeyPressed(Keyboard.KEY_GRAVE)) { // Controls
			isEnabled = !isEnabled;
			if(!isEnabled)
				mode.pause();
			else 
				mode.run();
		}
		
		if (mode != null)
			mode.update();
	}


	/**
	 * Will print the input String s to the terminal
	 * @param s
	 */
	public void print(String s) {
		if (mode != null)
			mode.print(s);
	}
	
	/**
	 * Master function for rendering the terminal
	 * Splits the rendering into two different areas
	 * @param g
	 */
	public void render(Graphics g) {
		if (mode != null)
			mode.render(g);
	}
	
	/**
	 * Runs the command in args
	 * @param command
	 */
	public void runCommand(String command) {
		try {
			commandParser.run(command.toLowerCase());
		} catch (InvalidCommandException e) {}
	}
	
	/**
	 * Runs a command with the given byte[] args
	 * @param name
	 * @param args
	 */
	public void runArgs(String name, byte[] args) {
		try {
			commandParser.run(name.toLowerCase(), args);
		} catch (InvalidCommandException e) {}
	}
	
	/**
	 * Stops the terminal from running
	 */
	public void stop() {
		if (mode != null)
			mode.stop();
	}

	/**
	 * Returns if the terminal is currently open
	 * @return
	 */
	public boolean isEnabled() {
		return isEnabled;
	}
	
	/**
	 * Sets whether or not the terminal is active
	 * @param b
	 */
	public void setEnabled(boolean b) {
		isEnabled = b;
	}
	
	/**
	 * Returns the command parser
	 */
	public CommandParser getCommandParser() {
		return commandParser;
	}
	
}