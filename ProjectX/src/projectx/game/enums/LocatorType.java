package projectx.game.enums;

import org.newdawn.slick.opengl.Texture;
import projectx.game.textures.Assets;

public enum LocatorType {
	
	Checkpoint(1,0,Assets.checkpoint),
	Station(1,1,Assets.checkpoint), //TODO Change in the future
	PrisonerSpawner(2,0,Assets.prisonerspawner),
	GaurdSpawner(2,1,Assets.guardspawner),
	ItemSpawner(3, 1, Assets.big_i);
	
	public Texture texture;
	public int id, type;

	LocatorType(int id, int type, Texture texture) {
		this.id = id; 
		this.type = type; 
		this.texture = texture;
	}

}
