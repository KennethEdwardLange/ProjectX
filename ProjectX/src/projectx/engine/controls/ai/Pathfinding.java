package projectx.engine.controls.ai;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import projectx.engine.Handler;
import projectx.engine.controls.ai.utils.Node;
import projectx.engine.controls.ai.utils.Vector2i;
import projectx.engine.io.IO;
import projectx.engine.managers.entities.Entity;
import projectx.engine.tile.Tile;
/**
 * This class is responsible for an automated path finding operation for creatures and players to use
 * @author Kenneth Lange
 *
 */
public class Pathfinding {
	private Handler handler;
	private boolean DEBUGMODE = false;
	private Comparator<Node> nodeSorter = new Comparator<Node>() { //Sorts nodes in list
		@Override
		public int compare(Node n0, Node n1) {
			if(n1.fCost < n0.fCost) return 1;
			else if(n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	/**
	 * This constructor takes in the parameter handler to access the game data
	 * @param handler
	 */
	public Pathfinding(Handler handler) {
		this.handler = handler;
	}
	/**
	 * This method is an Astar algorithm that finds the shortest path with the least amount of cost.
	 * @param start
	 * @param goal
	 * @return
	 */
	public List<Node> findPath(Vector2i start, Vector2i goal){
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while (openList.size() > 0) { //While there is still a tile to check in the open list
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if(current.tile.equals(goal)) { //If the path finds the goal, it returns the path array list.
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				if (DEBUGMODE)
					IO.println(path.toString());
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			//Used for diagonal checks
			boolean up = false;
			boolean left = false;
			boolean right = false;
			boolean down = false;
			//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			//1,3,5,7
			//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			for (int i = 1; i < 8; i+=2) { //Checks adjacent tiles and adds the possible tiles to the open list.
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = handler.getWorld().getTile(x + xi, y + yi); // Gets and sets the tile at the location in the world to at.
				if (at == null) continue; //If off of the it ignores this adjacent or diagonal tile for a possible path.
				if (at.isSolid() || checkCollision(x + xi, y + yi)){
					if (i == 1){
						up = true;
						continue; //If tile is solid, it ignores this tile for a possible path.
					} else if (i == 3){
						left = true;
						continue; //If tile is solid, it ignores this tile for a possible path.
					}else if (i == 5){
						right = true;
						continue; //If tile is solid, it ignores this tile for a possible path.
					}else if (i == 7){
						down = true;
						continue; //If tile is solid, it ignores this tile for a possible path.
					}
				}
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + getDistance(current.tile, a);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= node.gCost) continue;
				if (!vecInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
			//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			//0,2,4,6,8
			//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			for (int i = 0; i < 9; i+=2) { //Checks diagonal tiles and adds the possible tiles to the open list.
				if (i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = handler.getWorld().getTile(x + xi, y + yi); // Gets and sets the tile at the location in the world to at.
				if (at == null || checkCollision(x + xi, y + yi) || at.isSolid()) continue; //If off of the it ignores this adjacent or diagonal tile for a possible path.
				//special check for diagonals
				if (up && (i == 0 || i == 2)){
					continue; //If adjacent solid, it ignores this tile for a possible path.
				} else if (left && (i == 0 || i == 6)){
					continue; //If adjacent solid, it ignores this tile for a possible path.
				}else if (right && (i == 2 || i == 8)){
					continue; //If adjacent solid, it ignores this tile for a possible path.
				}else if (down && (i == 6 || i == 8)){
					continue; //If adjacent solid, it ignores this tile for a possible path.
				}
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + getDistance(current.tile, a);
//				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95); //Makes path finding prefer diagonal to non-diagonal aka looks more human
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= node.gCost) continue;
				if (!vecInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
			//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		}
		closedList.clear();
		return null; //If the path is never found, the path returns null.
	}
	/**
	 * This method determines whether the vector is in the array list or not and returns respectfully.
	 * @param list
	 * @param vector
	 * @return
	 */
	private boolean vecInList(List<Node> list, Vector2i vector) {
		for(Node n : list) {
			if(n.tile.equals(vector)) return true;
		}
		return false;
	}
	/**
	 * This method gets the distance by squaring x and y and square rooting the sum of the two.
	 * @param tile
	 * @param goal
	 * @return
	 */
	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt((dx * dx) + (dy * dy));
	}
	/**
	 * This method is checks if there is a entity at the location given.
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean checkCollision(int x, int y) {
		Rectangle r = new Rectangle(); // Location Rectangle
		r.width = Tile.TILEWIDTH;
		r.height = Tile.TILEHEIGHT;
		r.x = x * Tile.TILEWIDTH;
		r.y = y * Tile.TILEHEIGHT;
		
		for(Entity e : handler.getWorld().getEntityManager().getNonmoving()) {
			if(e.getCollisionBounds(0, 0).intersects(r)) {
				return true;
			}
		}
		for(Entity e : handler.getWorld().getEntityManager().getObjects()) {
			if(e.getCollisionBounds(0, 0).intersects(r)) {
				return true;
			}
		}
		return false;
	}
}
