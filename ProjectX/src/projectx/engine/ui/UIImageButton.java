package projectx.engine.ui;

import org.newdawn.slick.opengl.Texture;

import projectx.engine.glgfx.Graphics;
import projectx.engine.Handler;
/**
 * This class is responsible for creating a UIImage button
 * @author Kenneth Lange
 *
 */
public class UIImageButton extends UIObject{
	
	private Texture[] images;
	private Handler handler;
	private boolean step1, step2, activated;
	/**
	 * This constructor takes in the handler, location, width and height and the image texture array of a button to be added.
	 * @param handler
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param images
	 */
	public UIImageButton(Handler handler, float x, float y, int width, int height, Texture[] images) {
		super(handler, x, y, width, height);
		this.handler = handler;
		this.images = images;
	}
	
	/**
	 * This method updates a button for when it is hovered over and clicked.
	 */
	@Override
	public void update() {
		if(bounds.contains(handler.getMouse().getX(), handler.getEngine().getHeight() - handler.getMouse().getY()))
			hovering = true;
		else 
			hovering = false;
		//Clicking button without comming off of the button will activate it once		
		if(!step1 && hovering && !handler.getMouse().isButtonDown(0)) {
			step1 = true;
		} else if(step1 && hovering && handler.getMouse().isButtonDown(0)) {
			step2 = true;
		} else if(step1 && step2 && hovering && !handler.getMouse().isButtonDown(0)) {
			activated = true;
		} else if (!hovering) {
			step1 = false;
			step2 = false;
		} else {
			activated = false;
		}
	}
	/**
	 * This method renders the updated button.
	 */
	@Override
	public void render(Graphics g) {
		if(hovering /*&& handler.getMouse().isMouseInside()*/) //TODO
			g.drawImage(images[1],(int) x, (int) y, width, height);
		else
			g.drawImage(images[0],(int) x, (int) y, width, height);
	}
	public void hardReset(){
		hovering = false;
		step1=false;
		activated=false;
	}
	//Getters
	public boolean isActivated() {
		return activated;
	}
}