package projectx.engine.glgfx;

import java.awt.image.BufferedImage;
/**
 * This Class is responsible for splitting up sprite sheets into multiple images.
 * @author Energy
 *
 */
public class SpriteSheet {
	
	private BufferedImage sheet;
	/**
	 * This constructor receives the image that needs to be modified.
	 * @param sheet
	 */
	public SpriteSheet(BufferedImage sheet){
		this.sheet = sheet;
	}
	/**
	 * This crops a sprite sheet to get the subimage within the picture.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public BufferedImage crop(int x, int y, int width, int height){
		return sheet.getSubimage(x*width, y*height, width, height);
	}
}
