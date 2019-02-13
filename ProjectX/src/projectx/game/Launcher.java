package projectx.game;

import projectx.engine.EngineGL;

/**
 * This class launches the game
 * 
 * @author Kenneth Lange
 *
 */
public class Launcher {
	/**
	 * This method sets the window name, canvas and window size, then starts the
	 * game.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		EngineGL engine = new EngineGL("projectX", 1280); // Creates window size and title
		engine.run(); // Starts game
	}
}