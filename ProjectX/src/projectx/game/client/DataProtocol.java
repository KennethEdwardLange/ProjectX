package projectx.game.client;

import java.util.LinkedHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import projectx.engine.Handler;
import projectx.engine.inventory.Inventory;
import projectx.engine.io.IO;
import projectx.engine.managers.entities.creatures.Creature;
import projectx.engine.utils.Utils;
import projectx.game.client.Client;
import projectx.game.enums.CreatureType;

public class DataProtocol {
	
	int CREATURE_BYTE_ARRAY_LENGTH = 31;
	
	String DEFAULT_IP = "10.24.226.239";
	int DEFAULT_PORT = 4444;
	
	private Client client;
	private Handler handler;
	
	public DataProtocol(Handler handler) {
		this.handler = handler;
		
		client = new Client();
	}
	
	/**
	 * Converts the given Creature array to a JSON object and sends the serialized version over the client
	 * @param creatures
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendCreatureArray(Creature[] creatures) {
		byte[] data = new byte[10000];//10000

		JSONArray creatureJSONArray = new JSONArray();
		for (int i = 0; i < creatures.length; i++) {
			JSONObject creatureJSON = new JSONObject();
			creatureJSON.put("health", creatures[i].getHealth());
			JSONArray inventory = new JSONArray();
			for(int j = 0; j < creatures[i].getInventory().toByteArray().length;j++)
				inventory.add(new LinkedHashMap().put("id", creatures[i].getInventory().toByteArray()[j]));
			creatureJSON.put("inventory", inventory);
			creatureJSON.put("attacking", creatures[i].getAttacking());
			creatureJSON.put("speed", creatures[i].getSpeed());
			creatureJSON.put("x", creatures[i].getX());
			creatureJSON.put("y", creatures[i].getY());
			creatureJSON.put("nano", creatures[i].getBirthNano());
			creatureJSON.put("win", creatures[i].isWinner());
			creatureJSON.put("dir", creatures[i].getLastDirection());
			creatureJSON.put("team", creatures[i].getCreature().team);
			creatureJSON.put("picking", creatures[i].isPickingUp());

			creatureJSONArray.add(creatureJSON);
		}
		
		JSONObject mainObj = new JSONObject();
		mainObj.put("creatures", creatureJSONArray);
		
		String JSONtoString = mainObj.toJSONString() + "\n";
		
		byte[] JSONbytes = JSONtoString.getBytes();
		
		Utils.addByteArrayToArray(data, JSONbytes, 0);
		if(client.isConnected())
			client.sendBytes(data);
	}
	
	/**
	 * Recieves the JSON object from the server and deposits it in to the entity manager
	 */
	public void update() {
		if(client.isConnected() && client.isDataAvailable()) {
			byte[] data = client.getData();
			
			String JSONstring = new String(data);
			
			try {

				JSONParser parser = new JSONParser(); 
				JSONstring = JSONstring.substring(0, JSONstring.indexOf("\n"));
				
				JSONObject json;
				json = (JSONObject) parser.parse(JSONstring);
				
				JSONArray JSONcreatures = (JSONArray) json.get("creatures");
				Creature[] creatureArray = new Creature[JSONcreatures.size()];
				for (int i = 0; i < JSONcreatures.size(); i++) {
					JSONObject c = (JSONObject)JSONcreatures.get(i);
					creatureArray[i] = new Creature(handler, 0, 0, ((byte)(long)c.get("team") == 1? CreatureType.PShell : CreatureType.GShell));
					creatureArray[i].setX((float)(double)c.get("x"));
					creatureArray[i].setY((float)(double)c.get("y"));
					creatureArray[i].setHealth((byte)(long)c.get("health"));
					
					JSONArray inventory = (JSONArray) c.get("inventory");
					byte[] inv_id = new byte[inventory.size()];
					for(int j = 0; j < inventory.size();j++)
						inv_id[j] = (byte)(int)(inventory.get(j) == null ? 0 : inventory.get(j) == null);
						
					creatureArray[i].setInventory(new Inventory(handler, inv_id));
					creatureArray[i].setAttacking((boolean)c.get("attacking"));
					creatureArray[i].setSpeed((double)c.get("speed"));
					creatureArray[i].setBirthNano((long)c.get("nano"));
					creatureArray[i].setWinner((boolean)c.get("win"));
					creatureArray[i].setLastDirection((byte)(long)c.get("dir"));
					creatureArray[i].setPickingUp((boolean)c.get("picking"));
					
				}
			
			handler.getWorld().getEntityManager().recieveCreatures(creatureArray);
			} catch (ParseException e) {
				IO.printStackTrace(e);
				IO.println(JSONstring);
			}
			
		}
		
	}

	/**
	 * Hosts a game at the given ip and port
	 * @param host
	 * @param port
	 * @return
	 */
	public boolean host(String host, int port) {
		client = new Client();
		if(client.connect(host, port))
		{
			client.run();
			handler.getEngine().getGameState().setHost(true);
			return true;
		}
		return false;
		//Sends world.txt to the server who then hangs on to it
		//Sends host player's creature number to server (location in arraylist) the id
	}
	
	/**
	 * Default host method
	 */
	public boolean host() {
		return host(DEFAULT_IP, DEFAULT_PORT);
	}
	
	/**
	 * Joins a game at the given ip and port
	 * @param host
	 * @param port
	 * @return
	 */
	public boolean join(String host, int port) {
		client = new Client();
		if(client.connect(host, port))
		{
			client.run();
			return true;
		}
		return false;
		//Receives world.txt and sets it in game
		//Sends connected player's creature number to server (location in arraylist) the id
		//Sends host player's creature number to server (location in arraylist)
	}
	
	/**
	 * Joins a game at the default ip and port
	 * @return
	 */
	public boolean join() {
		return join(DEFAULT_IP, DEFAULT_PORT);
	}
	
	/**
	 * Returns the client
	 * @return
	 */
	public Client getClient() {
		return client;
	}
	
	/**
	 * Closes the client
	 */
	public void end() {
		client.end();
	}
}
