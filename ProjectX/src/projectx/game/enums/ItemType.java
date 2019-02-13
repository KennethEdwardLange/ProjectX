package projectx.game.enums;


import org.newdawn.slick.opengl.Texture;

import projectx.game.textures.Assets;

public enum ItemType {

	Null((byte)0, (byte)0, null),
	Key((byte)1, (byte)1, Assets.key),
	Crowbar((byte)2, (byte)2, Assets.crowbar),
	Helmet((byte)3, (byte)3, Assets.helmet);
	
	
	public byte id;
	public byte type;
	public Texture texture;
	
	
	
	ItemType(byte id, byte type, Texture texture){
		this.id = id;
		this.type = type;
		this.texture = texture;
	}
	
}

