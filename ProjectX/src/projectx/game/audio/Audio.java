package projectx.game.audio;

import projectx.engine.audio.AudioClip;
import projectx.engine.io.IO;

public class Audio {

	//Music
	public static AudioClip menu_music, game_music;
	//Effects
	public static AudioClip walking, attack;
	
	public static void init() {
		try {
			game_music= new AudioClip("res/sounds/Eyes_of_Glory.wave");
			menu_music= new AudioClip("res/sounds/Da_Funky_Rapsta.wave");//Too much
		}
		catch (Exception e) {
			e.printStackTrace();
			IO.printlnErr("Failed to load sounds");
		}
	}
}
