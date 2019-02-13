package projectx.engine.controls.ai.utils;
/**
 * This method is responsible for making a vector with two parameters to an integer x and y.
 * @author Kenneth Lange
 *
 */
public class Vector2i { //Vector with two parameters to an integer
	
	private int x, y;
	/**
	 * This method creates a vector with the parameters at 0, 0
	 */
	public Vector2i() {
		set(0, 0);
	}
	/**
	 * This method creates a vector with the parameters based on an existing vector's x and y
	 * @param vector
	 */
	public Vector2i(Vector2i vector) {
		set(vector.x, vector.y);
	}
	/**
	 * This method creates a vector with the parameters at x, y
	 * @param x
	 * @param y
	 */
	public Vector2i(int x, int y) {
		set(x, y);
	}
	/**
	 * Adds to the vector based another vector
	 * @param vector
	 * @return
	 */
	public Vector2i add(Vector2i vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	/**
	 * Subtracts from the vector based another vector
	 * @param vector
	 * @return
	 */
	public Vector2i subtract(Vector2i vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}
	/**
	 * This method overrides the Object class's equals method.
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Vector2i)) return false;
		Vector2i vec = (Vector2i) object;
		if (vec.getX() == getX() && vec.getY() == getY()) return true;
		return false;
	}
	
	public String toString() {
		return this.toString();
	}
	
	//Getters and Setters
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}

	public Vector2i setY(int y) {
		this.y = y;
		return this;
	}
	
	
	
}
