package projectx.engine.audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import javazoom.jl.converter.Converter;
/**
 * This class is responsible for playing the the selected audio.
 * @author Kenneth Lange 
 */
public class AudioPlayer {
	
	public static Clip clip;
	
	public AudioPlayer() {}
	
	/**
	 * This method tries to play the selected audio file denoted by the parameter sfx.
	 * If it cannot, it throws an exception.
	 * @param sfx
	 */
	public static synchronized void playSound(AudioClip sfx, double vol){
		Thread thread = new Thread(){
			public void run(){
				try{
					AudioInputStream stream = sfx.getAudioStream();
					clip = AudioSystem.getClip();
					clip.open(stream);
					setVol(vol, clip);
					clip.start();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}; thread.start();
	}
	/**
	 * This method tries to play the selected audio file denoted by the parameter sfx.
	 * If it cannot, it throws an exception.
	 * @param sfx
	 */
	public static synchronized void playLoop(AudioClip sfx, double vol){
		Thread thread = new Thread(){
			public void run(){
				try{
					AudioInputStream stream = sfx.getAudioStream();
					clip = AudioSystem.getClip();
					clip.open(stream);
					setVol(vol, clip);
					clip.loop(clip.LOOP_CONTINUOUSLY);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}; thread.start();
	}
	/**
	 * This method converts a .mp3 file into a .wav file using the jl converter library.  
	 * @param clip
	 * @return
	 */
	public static AudioClip convertToMP3(AudioClip clip){
		try{
			File file = clip.getFile();
			String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
			Converter c = new Converter();
			c.convert(file.getAbsolutePath(), file.getParent()+"/"+fileName+".wave");
			File newfile = new File(file.getParent()+file.getParent()+"/"+fileName+".wave");
			return new AudioClip(newfile.getAbsolutePath());
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method sets the volume of the selected audio being played by converting it to decibles and changing he gain respectfully.
	 * @param vol
	 * @param clip
	 */
	private static void setVol (double vol, Clip clip){
		FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN); //Gets access to the gain variable
		float dB = (float)(Math.log(vol)/Math.log(10)*20);//converts volume variable into decibels
		gain.setValue(dB);
	}
	
}
