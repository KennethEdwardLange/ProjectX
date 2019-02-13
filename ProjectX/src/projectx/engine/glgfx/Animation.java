package projectx.engine.glgfx;


import org.newdawn.slick.opengl.Texture;
/**
 * This class is responsible for animating a selected texture.
 * @author Kenneth Lange
 *
 */
public class Animation {

	private int speed, index;
	private long lastTime, timer;
	private Texture[] frames;
	/**
	 * This constructor takes in the animation speed and the frames that need to be animated
	 * @param speed
	 * @param frames
	 */
	public Animation(int speed, Texture[] frames) {
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	/**
	 * This method updates the current frame being animated
	 */
	public void update(){
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > speed){
			index++;
			timer = 0;
			if(index >= frames.length){
				index = 0;
			}
		}
	}
	//Getters and Setters
	public Texture getCurrentFrame(){
		return frames[index];
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

}
