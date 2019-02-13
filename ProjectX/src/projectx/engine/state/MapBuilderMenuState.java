package projectx.engine.state;

import java.io.File;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.lwjgl.util.Color;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.ui.UIImageButton;
import projectx.engine.utils.Utils;
import projectx.game.textures.Assets;

/**
 * This class acts as the state that allows the user to choose if they want to
 * create a new map or modify an existing file.
 * 
 * @author Merin Mundt
 *
 */

public class MapBuilderMenuState extends State {
	private UIImageButton loadButton, newButton, backButton;

	public MapBuilderMenuState(Handler handler) {
		super(handler);

		loadButton = new UIImageButton(handler, handler.getWidth() / 2 - 128/* height of button / 2 */,
				handler.getHeight() / 2 - 128 - 64/* width of button / 2 */, 256, 128, Assets.load_button);
		newButton = new UIImageButton(handler, handler.getWidth() / 2 - 128/* height of button / 2 */,
				handler.getHeight() / 2 - 000 - 64/* width of button / 2 */, 256, 128, Assets.new_button);
		backButton = new UIImageButton(handler, handler.getWidth() / 2 - 128/* height of button / 2 */,
				handler.getHeight() / 2 + 128 - 64/* width of button / 2 */, 256, 128, Assets.back_button);
	}

	@Override
	public void update() {
		loadButton.update();
		newButton.update();
		backButton.update();
		if (loadButton.isActivated()) {
			File f = Utils.pickFile();
			if(f != null) {
				String path = f.getPath();
				State.setState(new MapBuilderState(handler, path));
			}
		}
		if (newButton.isActivated()) {
			String path = newFile();
			if(path != null)
				State.setState(new MapBuilderState(handler, path));
		}
		if (backButton.isActivated()) {
			State.setState(new MenuState(handler));
		}
	}

	private String newFile() {
		
		String name, path;
		int width, height;
		
		JTextField name_field = new JTextField(5);
		JTextField width_field = new JTextField(5);
		JTextField height_field = new JTextField(5);
		
		width_field.setText("40");
		height_field.setText("40");
		
		JPanel fullPanel = new JPanel();
		JPanel namePanel = new JPanel();
		JPanel dimPanel = new JPanel();
		
		fullPanel.setLayout(new BoxLayout(fullPanel, BoxLayout.Y_AXIS));
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		dimPanel.setLayout(new BoxLayout(dimPanel, BoxLayout.X_AXIS));
		namePanel.add(new JLabel("Name:"));
		namePanel.add(name_field);

		dimPanel.add(new JLabel("Width:"));
		dimPanel.add(width_field);
		dimPanel.add(Box.createHorizontalStrut(5)); // a spacer
		dimPanel.add(new JLabel("Height:"));
		dimPanel.add(height_field);

		fullPanel.add(namePanel);
		fullPanel.add(Box.createVerticalStrut(15)); // a spacer
		fullPanel.add(dimPanel);
		int result = JOptionPane.showConfirmDialog(null, fullPanel, 
				"New File", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			name = name_field.getText();
			width = Integer.parseInt(width_field.getText());
			height = Integer.parseInt(height_field.getText());
			
			path = "res/worlds/" + name + ".txt";
			
			int[][] map_ids = new int[width][height];

			for (int[] row: map_ids)
			    Arrays.fill(row, 3);
		
			Utils.saveWorld(path, map_ids, 0, 0);
			return path;
		}
		return null;
}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		g.setColor(Color.WHITE);
		loadButton.render(g);
		newButton.render(g);
		backButton.render(g);
	}
}
