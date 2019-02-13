package projectx.engine.state;

import projectx.engine.glgfx.Graphics;
import projectx.engine.Handler;
/**
 * This is an abstract state template class. All states can use these methods and have to have abstract methods in them. 
 * @author Kenneth Lange
 *
 */
public abstract class State {
	
	protected boolean isHost = false;
	private static State currentState = null;
	
	//STATE MANAGER
	public static void setState(State state){
		currentState = state;
	}
	
	public static State getState(){
		return currentState;
	}
	
	//CLASS
	protected Handler handler;
	
	public State(Handler handler){
		this.handler = handler;
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);

	public boolean isHost() {
		return isHost;
	}

	public void setHost(boolean isHost) {
		this.isHost = isHost;
	}
	
}
