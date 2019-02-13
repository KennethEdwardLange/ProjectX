package projectx.engine.glgfx;


import java.awt.Font;

import org.lwjgl.util.ReadableColor;
/**
 * This class is responsible for drawing text onto the screen.
 * @author Kenneth Lange
 *
 */
public class Text {
	/**
	 * This method draws the text to the screen and chooses whether to center it of not and which font to use.
	 * @param g
	 * @param text
	 * @param xPos
	 * @param yPos
	 * @param center
	 * @param c
	 * @param font
	 */
	public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, ReadableColor c, Font font) {
		g.setColor(c);
		g.setFont(font);
		int x = xPos;
		int y = yPos;
		if(center) {
			//TODO FIx this method
//			FontMetrics fm = g.getFontMetrics(font);
//			x = xPos - fm.stringWidth(text) / 2;
//			y = (yPos - fm.getHeight() / 2) + fm.getAscent();
			x = 0;
			y =0;
		}
		g.drawString(text, x, y);
	}

}
