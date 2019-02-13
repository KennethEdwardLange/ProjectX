package projectx.engine.terminal.modes;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.terminal.Terminal;

public abstract class Mode {
	//A reference to the superclass Terminal so the class knows where to give inputs to.
	Terminal terminal;
	Handler handler;

	public Mode(Terminal terminal, Handler handler) {
		this.terminal = terminal;
		this.handler = handler;
	}
	
	public abstract void init();
	
	/**
	 * This method should start whatever processes that the class needs to run.
	 */
	public abstract void run();
	
	public abstract void pause();
	
	/**
	 * If the Class is using any loops or other automatic processes, this method should stop them
	 */
	public abstract void stop();
	
	/**
	 * Returns the given command to the terminal to then parse
	 */
	public abstract void returnCommand();
	
	/**
	 * If the terminal needs to update every cycle, update.
	 */
	public abstract void update();
	
	/**
	 * If the terminal needs to render every cycle, update
	 */
	public abstract void render(Graphics g);
	
	/**
	 * Prints the following string to the terminal
	 * @param s
	 */
	public abstract void print(String s);
	
	/**
	 * If this mode is available for use
	 * @return
	 */
	public abstract boolean validate();
	
	/**
	 * Returns a String representation of this Mode
	 */
	public abstract String toString();
}
