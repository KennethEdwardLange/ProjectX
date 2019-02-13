package projectx.engine.state;

import org.lwjgl.util.Color;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.ui.UIImageButton;
import projectx.game.textures.Assets;

/**
 * This class acts as the menu state of the game. It is responsible for loading
 * in button images and making them go to to somewhere when clicked.
 * 
 * @author Kenneth Lange
 *
 */
public class MenuState extends State {

	private UIImageButton startButton, mapBuilderButton, exitButton, multiplayerButton;

	public MenuState(Handler handler) {
		super(handler);
		// Buttons are 2 pixels Apart
		startButton = new UIImageButton(handler, handler.getWidth() / 2 - 128/* width of button / 2 */,
				handler.getHeight() / 2 - 130 - 128, 256, 128, Assets.start_button);
		multiplayerButton = new UIImageButton(handler, handler.getWidth() / 2 - 128/* width of button / 2 */,
				handler.getHeight() / 2 - 000 - 128, 256, 128, Assets.multiplayer_button);
		mapBuilderButton = new UIImageButton(handler, handler.getWidth() / 2 - 128/* width of button / 2 */,
				handler.getHeight() / 2 + 130 - 128, 256, 128, Assets.map_builder_button);
		exitButton = new UIImageButton(handler, handler.getWidth() / 2 - 128/* width of button / 2 */,
				handler.getHeight() / 2 + 260 - 128, 256, 128, Assets.exit_button);
	}

	@Override
	public void update() {
		startButton.update();
		multiplayerButton.update();
		mapBuilderButton.update();
		exitButton.update();
		if (startButton.isActivated()) {
			handler.getEngine().setGameState(new GameState(handler));
			State.setState(handler.getEngine().getGameState());
		} else if (multiplayerButton.isActivated()) {
			handler.getEngine().setMultiplayerMenuState(new MultiplayerMenuState(handler));
			State.setState(handler.getEngine().getMultiplayerMenuState());
		} else if (mapBuilderButton.isActivated()) {
			handler.getEngine().setMapBuilderMenuState(new MapBuilderMenuState(handler));
			State.setState(handler.getEngine().getMapBuilderMenuState());
		} else if (exitButton.isActivated()) {
			handler.getEngine().exit();
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		g.setColor(Color.WHITE);
		startButton.render(g);
		multiplayerButton.render(g);
		mapBuilderButton.render(g);
		exitButton.render(g);
	}

}