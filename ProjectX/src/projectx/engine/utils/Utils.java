package projectx.engine.utils;

import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import projectx.engine.io.IO;
/**
 * This method is a utilities class which loads in the world file which is converted into an actual world.
 * @author Kenneth Lange
 *
 */
public class Utils {
	/**
	 * This method tries to load in a file path for a world to be built based on. If it fails the game closes. 
	 * @param path
	 * @return
	 */
	public static String loadFileAsString(String path){
		StringBuilder builder = new StringBuilder();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null)
				builder.append(line + "\n");
			
			br.close();
		}catch(IOException e){
			IO.printStackTrace(e);
		}
		
		return builder.toString();
	}
	
	@SuppressWarnings("resource")
	public static boolean verifyFile(String path) {
		try {
			new FileReader(path);
		} catch (FileNotFoundException e) {return false;}
		return true;
	}
	
	public static String[] loadFileAsArray(String path) {
		return loadFileAsString(path).split("\n");
	}
	
	/**
	 * Saves the world as a text file given the tile IDs
	 * @param path
	 * @param locations
	 * @param spawn_x
	 * @param spawn_y
	 */
	public static void saveWorld(String path, int[][] locations, int spawn_x, int spawn_y){
		try{
			FileWriter fw = new FileWriter(new File(path));
			BufferedWriter bw = new BufferedWriter(fw);

			bw.append(locations.length + "\t" + locations[0].length + "\n");
			bw.append(((spawn_x - 1) / 64) + "\t" + ((spawn_y + 22) / 64) + "\n");
			for (int i = 0; i < locations.length; i++) {
				for (int j = 0; j < locations[i].length; j++) {
					bw.append(locations[j][i] + "\t");
				}
				bw.append("\n");
			}
			
			bw.close();
			fw.close();
		}catch(IOException e){
			IO.printStackTrace(e);
		}
	}
	
	public static File pickFile() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		fc.showOpenDialog(null);//returns int
		File file = fc.getSelectedFile();
		return file;
	}
	
	/**
	 * This method is responsible for grabbing an integer from the loaded file path
	 * @param number
	 * @return
	 */
	public static int parseInt(String number){
		try{
			return Integer.parseInt(number);
		}catch(NumberFormatException e){
			IO.printStackTrace(e);
			return 0;
		}
	}
	
	public static double parseDouble(String number) {
		try {
			return Double.parseDouble(number); 
		}catch(NumberFormatException e) {
			IO.printStackTrace(e);
		return 0.0; 
		}	
		
	}
	
	public static void addByteArrayToArray(byte[] master, byte[] addition, int index) {
		if(index + addition.length > master.length) return;
		for (int i = 0; i < addition.length; i++) {
			master[i + index] = addition[i];
		}
	}
	
	public static void addByteArrayToList(ArrayList<Byte> list, byte[] array) {
		for(int i = 0;i < array.length;i++) {
			list.add(array[i]);
		}
	} 
	
	public static String stringArrayToString(String[] arr, String sep) {
		return String.join(sep, arr);
	}
	
	public static String[] stringToStringArray(String string, String sep) {
		return string.split(sep);
	}
	
	public static String[] byteArrayToStringArray(byte[] b , String split) {
		if(b.length == 0)
			return new String[0];
		return Convert.byteArrayToString(b).split(split);
	}
	
	public static byte[] stringArrayToByteArray(String[] s, String split) {
		return Convert.stringToByteArray(String.join(split, s));
	}
	
	public boolean isFontValid(String s) {
		GraphicsEnvironment g = null;
		g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fonts = g.getAvailableFontFamilyNames();
		for (int i = 0; i < fonts.length; i++) 
           	if(fonts[i].equals(s))
           		return true;
        return false;
	}

}
