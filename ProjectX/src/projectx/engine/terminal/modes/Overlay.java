//import java.util.ArrayList;
package projectx.engine.terminal.modes;
//
//import java.util.ArrayList;
//
//import org.lwjgl.input.Keyboard;
//import org.lwjgl.util.Color;
//
//import projectx.engine.glgfx.Graphics;
//import projectx.engine.terminal.InvalidCommandException;
//
public class Overlay {}
//	//Defaults for the sizes of the terminal.
//		int FONT_SIZE = 16;
//		int NUM_LINES = 5;
//		int TRANSPARENCY = 50;
//		
//		int WIDTH = 500;
//		
//		int X_BORDER = 50;
//		int Y_BORDER = 50;
//		
//		//Time until the presses key will start repeating
//		int REPEAT_DELAY 	= 500;
//		//When repeating the char will print every _ms
//		int REPEAT_FREQ 	= 75;
//		
//		//When inserting chars 
//		char INSERT_CHAR = '_';
//		char APPEND_CHAR = '\u2013';
//		
//		//char to determine what typing mode the t is inserting or replacing
//		char typing_mode;
//		
//		String VERTICAL_JUSTIFICATION 		= "BOTTOM";//TOP, CENTER, BOTTOM
//		String HORIZONATAL_JUSTIFICATION 	= "LEFT";//LEFT, CENTER, RIGHT
//		
//		//How long until the char stops repeating
//		long char_cooldown;
//		
//		
//		//Hoe fast the char repeats
//		long char_repeatduration;
//		char char_prev;
//		
//		//The current String typed into the command line.
//		String command_line;
//		//The current position of the caret
//		int caret;
//		
//		//What is being displayed on the terminal history
//		ArrayList<String> display_list;
//		//The past command list.
//		ArrayList<String> command_list;	
//
//		public Overlay() {
//			char_cooldown = System.currentTimeMillis();
//			char_prev = 0;
//			caret = 0;
//			command_line = "";
//		
//			display_list = new ArrayList<String>();
//			command_list = new ArrayList<String>();
//		}
//
//		public void update() {
//			checkInput();
//		if(char_prev != 0 && !handler.getInput().isKeyDown(char_prev)) //TODO Inputs not implemented yet
//			char_prev = 0;//TODO Inputs not implemented yet
//		}
//		
//		private void checkEnable() {
//			handler.getEngine().getKeyboard();
//			if(Keyboard.isKeyDown(Keyboard.KEY_GRAVE))
//				setEnabled(!isEnabled());
//		}
//		
//		/**
//		 * Add char to the command line at the location of the caret
//		 * @param c
//		 * @return boolean
//		 */
//		private boolean addChar(char c) {
//			if(getCommandLineLength() < caret) {return false;}
//			addCharCommandLine(c, caret);
//			char_prev = c;
//			caret++;
//			return true;
//		}
//		
//		/**
//		 * Removes the char at the location of the caret.
//		 * @return
//		 */
//		private boolean removeChar() {
//			if(getCommandLineLength() < caret || caret == 0) {return false;}
//			removeCharCommandLine(caret);
//			caret--;
//			return true;
//		}
//		
//		/**
//		 * Depending on the char that is typed, do different actions, ex. ENTER, ESC, 'a', ' '. 
//		 * @param c
//		 */
//		private void parseChar(char c) {
//			if(c == Input.KEY_BACKSPACE) {
//				pressedBackspace();
//			} else if(c == 9) {
//			} else if(c == 10) {
//				pressedEnter();
//			} else if(c == Input.KEY_UP) {addChar('^');}
//			else if(c == Input.KEY_DOWN) {addChar('V');}
//			else if(c == Input.KEY_LEFT) {addChar('<');}
//			else if(c == Input.KEY_RIGHT) {addChar('>');}
//			else if(c >= 32 && c <= 90) {
//				addChar(c);
//			} else if(c == Input.KEY_DELETE) {
//				pressedDelete();
//			} 
//		}
//		
//		public void render(Graphics g) {
//			if(!isEnabled) return;
//			renderBackground(g);
//				
//			renderCommandLine(g);
//		
//			renderHistory(g);
//		}
//		
//		/**
//		 * This method regulates when and what is typed to the terminal, if a char is pressed
//		 * down this method acts as a governor to prevent the char from being typed as fast as 
//		 * the game is updated. This is achieved by using the cooldown variables. 
//		 */
//		private void checkInput() { //TODO Inputs not implemented yet
//			if(!isEnabled) return;
//			
//			if(handler.getInput().getPressedKeys().size() == 0) {
//				resetCooldown();
//			}
//
//			if(handler.getInput().getPressedKeys().size() != 0) {
//				char pressedKey = (char)((int)(handler.getInput().getPressedKeys().get(handler.getInput().getPressedKeys().size() - 1)));
//				if((charOffCooldown() && charRepeatOffCooldown()) || pressedKey != char_prev) {
//					parseChar(pressedKey);
//					resetRepeatCooldown();
//					if(pressedKey != char_prev)
//						resetCooldown();
//					
//				}
//			}
//		}
//		
//		/**
//		 * When the user presses enter, reset the command line and execute the command.
//		 */
//		private void pressedEnter() {
//			if(getCommandLineLength() != 0) {
//				command_list.add(0,getCommandLine());
//				try {commandParser.run(getCommandLine().toLowerCase());} catch (InvalidCommandException e) {}
//				resetCommandLine();
//			}
//		}
//		
//		/**
//		 * When 'delete' is pressed, clear the command line.
//		 */
//		private void pressedDelete() {
//			resetCommandLine();
//		}
//		
//		/**
//		 * Delete the char at the location of the caret.
//		 */
//		private void pressedBackspace() {
//				removeChar();
//				char_prev = (char) Input.KEY_BACKSPACE;
//		}
//		
//		/**
//		 * return the command line as a string
//		 * @return
//		 */
//		private String 	getCommandLine() 		{return command_line;}
//		/**
//		 * return the length of the command line string
//		 * @return
//		 */
//		private int 	getCommandLineLength() 	{return command_line.length();}
//		/**
//		 * clear the command line and reset the caret location.
//		 */
//		private void 	resetCommandLine() 		{
//			command_line = "";
//			caret = 0;
//		}
//		
//		/**
//		 * Adds char c to the location given, if the location is out of bounds,
//		 * nothing will happen.
//		 * @param c
//		 * @param location
//		 */
//		private void 	addCharCommandLine(char c, int location) {
//			if(location < 0 || location > getCommandLineLength() + 1) return;
//			command_line = command_line.substring(0, location) + c + command_line.substring(location);
//		}
//		
//		/**
//		 * Removes char c at the location given, if the location is out of bounds,
//		 * nothing will happen.
//		 * @param c
//		 * @param location
//		 */
//		private void 	removeCharCommandLine(int location) {
//			if(location < 0 || location > getCommandLineLength() + 1) return;
//			command_line = command_line.substring(0, location - 1) + command_line.substring(location);
//		}
//		
//		
//		/**
//		 * Renders a transparent black background to show the bounds of the terminal
//		 * and a darker bar at the bottom to show the command line bounds. 
//		 * @param g
//		 */
//		private void renderBackground(Graphics g) {
//			int alpha = (int) (255 * TRANSPARENCY / 100);
//			int LINE_SPACE = FONT_SIZE / 2;
//			
//			int width = WIDTH;
//			int height = (NUM_LINES + 1) * FONT_SIZE;
//			int x = X_BORDER;
//			int y = handler.getHeight() - Y_BORDER - height;
//			
//			Color c = new Color(0, 0, 0, alpha);
//			g.setColor(c);
//			g.fillRect(x, y, width, height);
//			c = new Color(0, 0, 0, alpha);
//			g.setColor(c);
//			g.fillRect(x, handler.getHeight() - Y_BORDER - FONT_SIZE - (LINE_SPACE / 2), width, FONT_SIZE + LINE_SPACE / 2);
//			g.setColor(Color.WHITE);
//		}
//		
//		/**
//		 * Renders the command line text
//		 * @param g
//		 */
//		private void renderCommandLine(Graphics g) {
//			int LINE_SPACE = FONT_SIZE / 2;
//					
//			Color c = new Color(255,255,255);
//			g.setColor(c);
////			g.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
//			g.drawString(">" + getCommandLine() + (typing_mode == 0 ? APPEND_CHAR : INSERT_CHAR), 
//					X_BORDER + LINE_SPACE, handler.getHeight() - Y_BORDER - LINE_SPACE);
//			g.setColor(Color.WHITE);
//		}
//		
//		/**
//		 * Renders the past commands and output of the terminal above the command line
//		 * @param g
//		 */
//		private void renderHistory(Graphics g) {
//			int LINE_SPACE = FONT_SIZE / 2;
//			
//			for (int i = 0;i < display_list.size() && i < NUM_LINES; i++) {
//				g.drawString(display_list.get(i), X_BORDER + LINE_SPACE, handler.getHeight() - LINE_SPACE - Y_BORDER - (FONT_SIZE * (i + 1)));
//			}
//		}
//		
//		/**
//		 * Displays some numbers above the terminal in case you need to see some varaibles in real time.
//		 * @param g
//		 */
//		private void renderDebug(Graphics g) {
//			int height = (NUM_LINES + 1) * FONT_SIZE;
//			int x = X_BORDER;
//			int y = handler.getHeight() - Y_BORDER - height;
//			
//			Color c = new Color(255,255,255);
//			g.setColor(c);
////			g.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
//			g.drawString("", x, y);
//			g.setColor(Color.WHITE);
//		}
//		
//		/**
//		 * These functions manage the cooldowns of the char inputs. 
//		 * Getting and reseting the cooldowns is handled here. 
//		 * @return
//		 */
//		private boolean 	charOffCooldown() 		{	return System.currentTimeMillis() - char_cooldown > REPEAT_DELAY;	}
//		private int 		getCooldown() 			{	return (int) ((int)System.currentTimeMillis() - char_cooldown);		}
//		private void 		resetCooldown() 		{	char_cooldown = System.currentTimeMillis();							}
//		private boolean 	charRepeatOffCooldown() {	return System.currentTimeMillis() - char_repeatduration > REPEAT_FREQ;	}
//		private int 		getRepeatCooldown() 	{	return (int) ((int)System.currentTimeMillis() - char_repeatduration);	}
//		private void 		resetRepeatCooldown() 	{	char_repeatduration = System.currentTimeMillis();						}
//
//		/**
//		 * Changes the isEnabled variable to the parameter.
//		 * @param b
//		 */
//		public void setEnabled(boolean b) {
//			if(b) {
//				char_cooldown = System.currentTimeMillis();
//				char_prev = 0;
//				caret = 0;
//				command_line = "";
//			}
//			isEnabled = b;
//		}
//
//}
