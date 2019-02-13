package projectx.engine.display;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import projectx.engine.glgfx.Graphics;
import projectx.engine.io.IO;
/**
 * 
 * @author Energy
 *
 * This class starts an OpenGL based display to process images in a window
 */
public class EngineDisplay {
	
	private static final int FPS = 60;
	
	//Test Code:
	private Graphics g = new Graphics();
	/**
	 * This method creates a display with a set width and height and gives it a title
	 * @param title
	 * @param width
	 * @param height
	 */
	public EngineDisplay(String title, int width, int height) {
		Display.setTitle(title);
		
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			IO.printStackTrace(e);
		}
		//Everything under her allows for proper rendering of textures.
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	
}
