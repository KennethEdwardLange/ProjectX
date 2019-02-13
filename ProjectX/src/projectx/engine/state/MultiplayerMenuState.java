package projectx.engine.state;

import org.lwjgl.util.Color;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.ui.UIImageButton;
import projectx.game.textures.Assets;

public class MultiplayerMenuState extends State {

	private UIImageButton hostButton, joinButton, backButton;

	public MultiplayerMenuState(Handler handler) {
		super(handler);
		//Buttons are 2 pixels Apart
		
		hostButton 		= new UIImageButton(handler, handler.getWidth()/2 - 128/*width of button / 2 */, handler.getHeight()/2 - 130 - 64/*height of button / 2 */, 256, 128, Assets.host_button);
		joinButton 		= new UIImageButton(handler, handler.getWidth()/2 - 128/*width of button / 2 */, handler.getHeight()/2 - 000 - 64/*height of button / 2 */, 256, 128, Assets.join_button);
		backButton 		= new UIImageButton(handler, handler.getWidth()/2 - 128/*width of button / 2 */, handler.getHeight()/2 + 130 - 64/*height of button / 2 */, 256, 128, Assets.back_button);
	}

	@Override
	public void update() {
		hostButton.update();
		joinButton.update();
		backButton.update();
		if (hostButton.isActivated()) {
			handler.getEngine().setGameState(new GameState(handler));
			handler.getEngine().getTerminal().runCommand("host");
			handler.getWorld().getGenerator().generateRoamingGuard(58, 12);
			handler.getWorld().getGenerator().generateRoamingGuard(58, 27);
			handler.getWorld().getGenerator().generateRoamingGuard(36, 28);
			handler.getWorld().getGenerator().generateRoamingGuard(40, 40);
			State.setState(handler.getEngine().getGameState());
			
		} else if (joinButton.isActivated()) {
			handler.getEngine().setGameState(new GameState(handler));
			handler.getEngine().getTerminal().runCommand("join");
			State.setState(handler.getEngine().getGameState());
			
		} else if (backButton.isActivated()) {
			handler.getEngine().setMenuState(new MenuState(handler));
			State.setState(handler.getEngine().getMenuState());
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		g.setColor(Color.WHITE);
		hostButton.render(g);
		joinButton.render(g);
		backButton.render(g);
	}

}
