package projectx.engine.display;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;
/**
 * Display is the window class
 * @author Energy
 *
 */
public class Display {
	
	private Canvas canvas; //Draw graphics to canvas element
	private JFrame frame; //Frame around the canvas
	
	private String title;
	private int width, height;
	
	public Display(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;
		
		createDisplay();
	}
	/**
	 * This method creates the display window;
	 */
	private void createDisplay(){
		frame = new JFrame(title); //Sets the window's name
		frame.setSize(width, height); //Sets the window's size: width and height in pixels
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Properly exits window
		frame.setResizable(false); //User cannot resize the window
		frame.setLocationRelativeTo(null); //Sets location of initial screen to center of screen
		frame.setVisible(true);//Makes window visible
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height)); //Sets preferred size
		canvas.setMaximumSize(new Dimension(width, height)); //Sets maximum size
		canvas.setMinimumSize(new Dimension(width, height)); //Sets minimum size
		canvas.setFocusable(false); //Allows application to focus itself instead of part that is being drawn on
		
		frame.add(canvas); //Adds canvas to window
		frame.pack(); //Resizes window to fully see canvas
	}

	//Getters
	public Canvas getCanvas(){
		return canvas;
	}

	public JFrame getFrame(){
		return frame;
	}
}
