package projectx.engine.input;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import projectx.engine.debug.Debug;
/**
 * This class uses the inputs of the keyboard and implements them into the game for it to use.
 * @author Kenneth Lange
 *
 */
public class Input{
	
	public static boolean DEBUGMODE = false; //Shows DEBUGMODE for inputs

	private ArrayList<Integer> PressedKeys = new ArrayList<Integer>();
	private ArrayList<Integer> DownKeys = new ArrayList<Integer>();
	
	public void update() {
		if (Keyboard.next()) {
			if (Keyboard.getEventKeyState())
				keyPressed(Keyboard.getEventKey());
			else
				keyReleased(Keyboard.getEventKey());
		}
	}
	
	/**
	 * This method checks whether a key is being pressed or not.
	 * If it is, it then adds that key to an ArrayList based on it's ID.
	 */
	public void keyPressed(int key) {
		if(DownKeys.indexOf(key) == -1){
			PressedKeys.add(key);
			DownKeys.add(key);
			if(DEBUGMODE)
				Debug.Log("ADDED KEY " + Keyboard.getKeyName(key)); //Used for debugging
		}
	}
	/**
	 * This method checks whether a key has been pressed release or not after being pressed.
	 * If it is, it then removes that key from the ArrayList based on it's ID.
	 */
	public void keyReleased(int key) {
		if(PressedKeys.indexOf(key) != -1)
			PressedKeys.remove(PressedKeys.indexOf(key));
		if(DownKeys.indexOf(key) != -1)
			DownKeys.remove(DownKeys.indexOf(key));
		if(DEBUGMODE)
			Debug.Log("REMOVED KEY " + Keyboard.getKeyName(key)); //Used for debugging
	}
	
	public ArrayList<Integer> getPressedKeys() {
		return PressedKeys;
	}
	//GAME ACCESS
	/**
	 * This method checks whether a key is being pressed or not.
	 * If it is, it then returns true, otherwise it returns false.
	 * @param key
	 * @return
	 */
	public boolean isKeyPressed(int key){
		if(PressedKeys.indexOf(key) != -1){
			PressedKeys.remove(PressedKeys.indexOf(key));
			return true;
		}
		return false;
	}
	/**
	 * This method checks whether a key is being held down or not.
	 * If it is, it then returns true, otherwise it returns false.
	 * @param key
	 * @return
	 */
	public boolean isKeyDown(int key){
		if(DownKeys.indexOf(key) != -1){
			return true;
		}
		return false;
	}
}
