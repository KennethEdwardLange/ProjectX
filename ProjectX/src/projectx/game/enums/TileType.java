package projectx.game.enums;

import org.newdawn.slick.opengl.Texture;

import projectx.game.textures.Assets;

public enum TileType {
	
	Air(0, true, null, false, false),
	ShallowWater(1, false, Assets.shallowwater, true, false),
	Sand(2, false, Assets.sand, false, false),
	Dirt(3, false, Assets.dirt, false, false),
	Grass(4, false, Assets.grass, false, false),
	Stone(5, false, Assets.stonepath, false, false),
	DeepWater(6, false, Assets.deepwater, true, false),
	Cement(7, false, Assets.cement, true, false),
	Win(100, false, null, true, true);
	
	public int id;
	public boolean solid;
	public Texture texture;
	public boolean water;
	public boolean win;
	
	TileType(int id, boolean solid, Texture texture, boolean water, boolean win){
		this.id = id;
		this.solid = solid;
		this.texture = texture;
		this.water = water;
		this.win = win;
	}

}
