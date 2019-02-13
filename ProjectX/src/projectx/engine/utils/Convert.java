package projectx.engine.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import projectx.engine.Handler;
import projectx.engine.inventory.Inventory;
import projectx.engine.io.IO;
import projectx.engine.managers.entities.creatures.Creature;
import projectx.engine.tile.Tile;
import projectx.game.enums.CreatureType;

public class Convert {

	public final static int INT_LENGTH = 4;
	public final static int FLOAT_LENGTH = 4;
	public final static int BOOLEAN_LENGTH = 1;
	public final static int BYTE_LENGTH = 1;
	public final static int LONG_LENGTH = 8;
	public final static int DOUBLE_LENGTH = 8;
	
	public final static int CREATURE_BYTE_ARRAY_LENGTH = 31;

	public static float xtileToPixel(float tile) {
		return (tile - 1.5f) * Tile.TILEWIDTH + 33;
	}

	public static float xpixelToTile(float pixel) {
		return (((pixel - 33) / Tile.TILEWIDTH) + 1.5f);
	}

	public static float ytileToPixel(float tile) {
		return (tile - 1.5f) * Tile.TILEWIDTH + 33;
	}

	public static float ypixelToTile(float pixel) {
		return (((pixel - 33) / Tile.TILEWIDTH) + 1.5f);
	}

	public static byte booleanToByte(boolean b) {
		return (byte) (b ? 1 : 0);
	}

	public static boolean byteToBoolean(byte b) {
		return (b == 1 ? true : false);
	}

	public static byte[] doubleToByteArray(double value) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putDouble(value);
		return bytes;
	}

	public static double byteArrayToDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}

	public static byte[] longToByteArray(long value) {
		return ByteBuffer.allocate(8).putLong(value).array();
	}

	public static long byteArrayToLong(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getLong();
	}

	/**
	 * Casts to in for conversion
	 * @param value
	 * @return
	 */
	public static byte[] floatToByteArray(float value) {
		return ByteBuffer.allocate(4).putFloat(Math.round(value)).array();
	}

	
	/**
	 * Casts to int for conversion
	 * @param bytes
	 * @return
	 */
	public static float byteArrayToFloat(byte[] bytes) {
		return (float)ByteBuffer.wrap(bytes).getInt();
	}

	public static int byteArrayToInt(byte[] bytes) {
		return (int) (ByteBuffer.wrap(bytes).getInt());
	}

	public static byte[] intToByteArray(int value) {
		return ByteBuffer.allocate(4).putInt(value).array();
	}
	
	public static String byteArrayToString(byte[] bytes) {
		return new String(bytes);
	}
	
	public static byte[] stringToByteArray(String s) {
		return s.getBytes();
	}
	
	public static Byte objectToByte(Object o) {
		return (Byte)(o);
	}
	
	public static Object byteToObject(Byte b) {
		return (Object)(b);
	}
	
	public static byte byteToPrim(Byte b) {
		return b.byteValue();
	}
	
	public static Byte bytePrimtoByte(byte b) {
		return new Byte(b);
	}

	public static byte[] objectArrayToBytePrimArray(Object[] obj) throws IOException {
		byte[] bytes = new byte[obj.length];
		
		for (int i = 0; i < bytes.length; i++) 
			bytes[i] = byteToPrim(objectToByte(obj[i]));
			
		return bytes;
	}
	
	public static Object[] bytePrimArrayToObjectArray(byte[] bytes) throws IOException {
		Object[] obj = new Object[bytes.length];
		
		for (int i = 0; i < bytes.length; i++) 
			obj[i] = byteToObject(bytePrimtoByte(bytes[i]));
			
		return obj;
	}
	
	/**
	 * Mitchell will make this better
	 * @param c
	 * @return
	 */
	public static byte[] creatureToByteArray(Creature c) {

		byte[] data = new byte[CREATURE_BYTE_ARRAY_LENGTH];
		
		int index = 0;
		
		data[index] = c.getHealth();														index += Convert.BYTE_LENGTH;
		Utils.addByteArrayToArray(data, c.getInventory().toByteArray(), index);				index += Convert.BYTE_LENGTH * 3;
		data[index] = Convert.booleanToByte(c.getAttacking());								index += Convert.BOOLEAN_LENGTH;
		Utils.addByteArrayToArray(data, Convert.doubleToByteArray(c.getSpeed()), index);	index += Convert.DOUBLE_LENGTH;
		
		Utils.addByteArrayToArray(data, Convert.floatToByteArray((c.getX())),index);		index += Convert.FLOAT_LENGTH;
		Utils.addByteArrayToArray(data, Convert.floatToByteArray((c.getY())),index); 		index += Convert.FLOAT_LENGTH;
		
		Utils.addByteArrayToArray(data, Convert.longToByteArray(c.getBirthNano()), index);	index += Convert.LONG_LENGTH;
		data[index] = Convert.booleanToByte(c.isWinner());									index += Convert.BOOLEAN_LENGTH;
		data[index] = c.getLastDirection();													index += Convert.BYTE_LENGTH;
		
		return data;

	}
	
	public static Creature byteArrayToCreature(byte[] a, Handler handler) {		
		Creature c = new Creature(null,
				Convert.xpixelToTile(Convert.byteArrayToFloat(Arrays.copyOfRange(a, 13, 13 + Convert.FLOAT_LENGTH))),
				Convert.xpixelToTile(Convert.byteArrayToFloat(Arrays.copyOfRange(a, 17, 17 + Convert.FLOAT_LENGTH))),
				CreatureType.PShell);
		
		c.setHealth(a[0]);
		c.setInventory(byteArrayToInventory(Arrays.copyOfRange(a, 1, 1 + (Convert.BYTE_LENGTH * 3))));
		c.setAttacking(Convert.byteToBoolean(a[4]));
		c.setSpeed(Convert.byteArrayToDouble(Arrays.copyOfRange(a, 5, 5 + Convert.DOUBLE_LENGTH)));

		c.setBirthNano(Convert.byteArrayToLong(Arrays.copyOfRange(a, 21, 21 + Convert.LONG_LENGTH)));
		c.setWinner(Convert.byteToBoolean(a[29]));
		c.setLastDirection(a[30]);
		
		return c;
	}
	
	public static Inventory byteArrayToInventory(byte[] a) {
		return new Inventory(null, a);
	}

	/**
	 * To Be finished later
	 * @return
	 */
	public static Creature[] byteArrayToCreatureArray(byte[] b, Handler handler) {
		int num = (int)b[0];
		Creature[] creatureArray = new Creature[num];
		for (int i = 0; i < creatureArray.length; i++) {
			creatureArray[i] = byteArrayToCreature(Arrays.copyOfRange(b,
					(CREATURE_BYTE_ARRAY_LENGTH * i + 1), (CREATURE_BYTE_ARRAY_LENGTH * i + 1 + CREATURE_BYTE_ARRAY_LENGTH)), handler);
		}
		return creatureArray;
	}
	
	public static byte[] creatureArrayToByteArray(Creature[] creatures) {
		byte[] b = new byte[creatures.length * CREATURE_BYTE_ARRAY_LENGTH + 1];
		b[0] = (byte) creatures.length;
		for (int i = 0; i < creatures.length; i++) {
			Utils.addByteArrayToArray(b, creatureToByteArray(creatures[i]), (CREATURE_BYTE_ARRAY_LENGTH * i + 1));
		}
		return b;
	}
	
	public static void creatureToString(Creature c) {
		IO.println(Arrays.toString(creatureToByteArray(c)));
		
		IO.println(c.getHealth());
		IO.println(Arrays.toString(c.getInventory().toByteArray()));
		IO.println(c.getSpeed());
		IO.println(c.getX());
		IO.println(c.getY());
		IO.println(c.getBirthNano());
		IO.println(c.isWinner());
		IO.println(c.getLastDirection());
	}

	public static void main(String[] args) throws IOException {
		IO.println("Testing conversion functions...");
		float pixel = 0.4f;
		float tile_pixel = xtileToPixel(xpixelToTile(pixel));
		IO.println("\t" + (pixel == tile_pixel ? "✓" : "✖") + "   " + pixel + " = " + tile_pixel);
		
		pixel = 2.0f;
		tile_pixel = ytileToPixel(xpixelToTile(pixel));
		IO.println("\t" + (pixel == tile_pixel ? "✓" : "✖") + "   " + pixel + " = " + tile_pixel);
		
		pixel = -15.0f;
		tile_pixel = xtileToPixel(xpixelToTile(pixel));
		IO.println("\t" + (pixel == tile_pixel ? "✓" : "✖") + "   " + pixel + " = " + tile_pixel);
		
		pixel = -15.0f;
		tile_pixel = ytileToPixel(xpixelToTile(pixel));
		IO.println("\t" + (pixel == tile_pixel ? "✓" : "✖") + "   " + pixel + " = " + tile_pixel);
		
		pixel = 2.0f;
		tile_pixel = xpixelToTile(xtileToPixel(pixel));
		IO.println("\t" + (pixel == tile_pixel ? "✓" : "✖") + "   " + pixel + " = " + tile_pixel);
		
		pixel = 2.0f;
		tile_pixel = ypixelToTile(ytileToPixel(pixel));
		IO.println("\t" + (pixel == tile_pixel ? "✓" : "✖") + "   " + pixel + " = " + tile_pixel);
		
		pixel = -15.0f;
		tile_pixel = xpixelToTile(xtileToPixel(pixel));
		IO.println("\t" + (pixel == tile_pixel ? "✓" : "✖") + "   " + pixel + " = " + tile_pixel);
		
		pixel = -15.0f;
		tile_pixel = ypixelToTile(ytileToPixel(pixel));
		IO.println("\t" + (pixel == tile_pixel ? "✓" : "✖") + "   " + pixel + " = " + tile_pixel);

		boolean bol = true;
		boolean byte_bol = byteToBoolean(booleanToByte(bol));
		IO.println("\t" + (bol == byte_bol ? "✓" : "✖") + "   " + bol + " = " + byte_bol);

		double d = 2.5;
		double byte_d = byteArrayToDouble(doubleToByteArray(d));
		IO.println("\t" + (d == byte_d ? "✓" : "✖") + "   " + d + " = " + byte_d);

		long l = 12345678910L;
		long byte_l = byteArrayToLong(longToByteArray(l));
		IO.println("\t" + (l == byte_l ? "✓" : "✖") + "   " + l + " = " + byte_l);

		float f = 0.5f;
		float byte_f = byteArrayToFloat(floatToByteArray(f));
		IO.println("\t" + (f == byte_f ? "✓" : "✖") + "   " + f + " = " + byte_f);
		
		f = 2.0f;
		byte_f = byteArrayToFloat(floatToByteArray(f));
		IO.println("\t" + (f == byte_f ? "✓" : "✖") + "   " + f + " = " + byte_f);
		
		f = 2.0f;
		byte_f = byteArrayToFloat(floatToByteArray(f));
		IO.println("\t" + (f == byte_f ? "✓" : "✖") + "   " + f + " = " + byte_f);

		int i = 123456;
		int byte_i = byteArrayToInt(intToByteArray(i));
		IO.println("\t" + (i == byte_i ? "✓" : "✖") + "   " + i + " = " + byte_i);

		byte[] ba = new byte[] { 12, 67, 89, 43, 2, 90, 6, 21, 1, 5, 98, 7, 4, 31, 12, 4, 8, -0 };
		byte[] object_ba = objectArrayToBytePrimArray(bytePrimArrayToObjectArray(ba));
		IO.println("\t" + (Arrays.equals(object_ba, ba) ? "✓" : "✖") + "   " + Arrays.toString(ba) + " = ");
		IO.println("   " + Arrays.toString(object_ba));
		
		ba = new byte[] {2,56,9,-9,65,54,23,12,56,67,90,89,56,23,12,122,43,76,0,76};
		object_ba = objectArrayToBytePrimArray(bytePrimArrayToObjectArray(ba));
		IO.println("\t" + (Arrays.equals(object_ba, ba) ? "✓" : "✖") + "   " + Arrays.toString(ba) + " = ");
		IO.println("   " + Arrays.toString(object_ba));
	}
	
}