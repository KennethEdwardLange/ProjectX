package projectx.engine.debug;

import projectx.engine.io.IO;
import projectx.engine.managers.entities.Entity;
import projectx.engine.managers.locators.Locator;
import projectx.engine.tile.Tile;

/**
 * This class is created for debugging purposes.
 * @author Kenneth Lange
 *
 */
public class Debug {
	/**
	 * This method displays a message in the log based on the parameter msg.
	 * @param msg
	 */
	public boolean DEBUGMODE ; 
		
	public Debug(boolean DEBUGMODE) {
		this.DEBUGMODE =DEBUGMODE ; 
	}
	public boolean isDEBUGMODE() {
		return DEBUGMODE;
	}
	public void setDEBUGMODE(boolean dEBUGMODE) {
		DEBUGMODE = dEBUGMODE;
	}
	public static void Log(String msg){
		IO.println(msg);
	}
	/**
	 * This method displays an error message in the log based on the parameter msg.
	 * @param msg
	 */
	public static void  LogError(String msg){
		System.err.println(msg);
	}
	/**
	 * This method sets the Debug Mode on/off for convenience of seeing hit boxes and other developer specific details.
	 */
	public static void setDEBUGMODE() {
		Entity.DEBUGMODE = !Entity.DEBUGMODE;
		Tile.DEBUGMODE = !Tile.DEBUGMODE;
		Locator.DEBUGMODE = !Locator.DEBUGMODE;
	}
}