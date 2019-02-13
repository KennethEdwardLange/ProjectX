package projectx.game.enums;

import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;

import projectx.game.textures.Assets;
import projectx.engine.tile.Tile;

public enum ObjectsType {
	//Changeable types
	Door0(1, 0, 1, Assets.door_closed),
	Door1(2, 0, 0, Assets.door_opened),
	CrackedWall(3, 0, 2, Assets.wall_cracked),
	BrokenWall(4, 0, 0, Assets.wall_broken),
	
	//Single types
	ServingArea(5, 0, 0, Assets.serving_area),
	WeightedBar(6, 0, 0, Assets.weighted_bar),
	Dumbbell(7, 0, 0, Assets.dumbbell),
	Well(8, 0, 0, Assets.well),
	
	//Multiple types
	Bed1(10, 0, 0, Assets.bed1),
	Bed2(11, 0, 0, Assets.bed2),
	Wall0(20, 0, 0, Assets.wall),
	Wall1(20, 1, 0, Assets.wall),
	Wall2(20, 2, 0, Assets.wall),
	Wall3(20, 3, 0, Assets.wall),
	Wall4(20, 4, 0, Assets.wall),
	ElectricFence0(21, 0, 0, Assets.electric_fence),
	ElectricFence1(21, 1, 0, Assets.electric_fence),
	ElectricFence2(21, 2, 0, Assets.electric_fence),
	ElectricFence3(21, 3, 0, Assets.electric_fence),
	ElectricFence4(21, 4, 0, Assets.electric_fence);
	
	public int id, type, activator;
	public float xoffset, yoffset, width, height;
	public Texture texture;
	public Rectangle bounds = new Rectangle();

	// Single-type
	ObjectsType(int id, int type, int activator, Texture texture) {
		// Identifiers
		this.id = id;
		this.type = type;
		this.activator = activator;
		// Textures
		this.texture = texture;

		Setup(type);
	}

	// Multi-type
	ObjectsType(int id, int type, int activator, Texture[] texture) {
		// Identifiers
		this.id = id;
		this.type = type;
		this.activator = activator;
		// Textures
		this.texture = texture[type];

		Setup(type);
	}

	public void Setup(int type) {

		/*----------------------------------------------------------------------------------*/
		/*Door/Cracked Wall Specific*/
		/*----------------------------------------------------------------------------------*/
		if (id == 1 || id==3) {
			/*Tile centering*/
			this.xoffset = 0.0f*Tile.TILEWIDTH;
			this.yoffset = -.8f*Tile.TILEHEIGHT+1;
			this.width = Tile.TILEWIDTH;
			this.height = 2.4f*Tile.TILEHEIGHT;
			/*Bounds*/
			bounds.x = 1;
			bounds.y = (int) (Tile.TILEHEIGHT*1.1f);
			bounds.width = Tile.TILEWIDTH - 2;
			bounds.height = (int) (Tile.TILEHEIGHT*.7f);
		} else if (id == 2|| id==4) {
			/*Tile centering*/
			this.xoffset = 0.0f*Tile.TILEWIDTH;
			this.yoffset = -.8f*Tile.TILEHEIGHT+1;
			this.width = Tile.TILEWIDTH;
			this.height = 2.4f*Tile.TILEHEIGHT;
			bounds.x = 1;
			bounds.y = (int) (Tile.TILEHEIGHT*.8f + 1);
			bounds.width = 0;
			bounds.height = 0;
		}
		/*----------------------------------------------------------------------------------*/
		/*Serving Area Specific*/
		/*----------------------------------------------------------------------------------*/
		else if (id == 5) {
			/*Tile centering*/
			this.xoffset = 0;
			this.yoffset = -Tile.TILEHEIGHT;
			this.width = 7*Tile.TILEWIDTH;
			this.height = Tile.TILEHEIGHT*1.8f;
			/*Bounds*/
			bounds.x = 0;
			bounds.y = (int) (.8*Tile.TILEHEIGHT);
			bounds.width = (int)(7*Tile.TILEWIDTH);
			bounds.height = (int) (.7f*Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		/*Weighted Bard Specific*/
		/*----------------------------------------------------------------------------------*/
		else if (id == 6) {
			/*Tile centering*/
			this.xoffset = Tile.TILEWIDTH*.18f;
			this.yoffset = 0;
			this.width = .7f*Tile.TILEWIDTH;
			this.height = 2.5f*Tile.TILEHEIGHT;
			/*Bounds*/
			bounds.x = 0;
			bounds.y = 0;
			bounds.width = (int)(.7*Tile.TILEWIDTH);
			bounds.height = (int) (2f*Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		/*Dumbbel Specific*/
		/*----------------------------------------------------------------------------------*/
		else if (id == 7) {
			/*Tile centering*/
			this.xoffset = .25f*Tile.TILEWIDTH;
			this.yoffset = .25f*Tile.TILEHEIGHT;
			this.width = .75f*Tile.TILEWIDTH;
			this.height = .75f*Tile.TILEHEIGHT;
			/*Bounds*/
			bounds.x = (int)(.3f*Tile.TILEWIDTH);
			bounds.y = 0;
			bounds.width = (int)(.25f*Tile.TILEWIDTH);
			bounds.height = (int) (.65f*Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		/*Bed Specific*/
		/*----------------------------------------------------------------------------------*/
		else if (id == 10 || id == 11) {
			/*Tile centering*/
			this.xoffset = 0.0f*Tile.TILEWIDTH;
			this.yoffset = (-.25f)*Tile.TILEHEIGHT;
			this.width = 2*Tile.TILEWIDTH;
			this.height = Tile.TILEHEIGHT*1.1f;
			/*Bounds*/
			bounds.x = 3;
			bounds.y = (int) (Tile.TILEHEIGHT*.2f);
			bounds.width = (int)(1.75f*Tile.TILEWIDTH);
			bounds.height = (int) (Tile.TILEHEIGHT*.7f);
		}
		/*----------------------------------------------------------------------------------*/
		/*Wall Specific*/
		/*----------------------------------------------------------------------------------*/
		/* Types:
		0 = Top/Bottom, 1 = Top Left, 2 = Top Right, 3 = Left, 4 = Right
		*/
		else if (id == 20 || id == 21){
			/*Tile centering*/
			this.xoffset = 0.0f*Tile.TILEWIDTH;
			this.yoffset = -.8f*Tile.TILEHEIGHT+1;
			this.width = Tile.TILEWIDTH;
			this.height = 2.4f*Tile.TILEHEIGHT-1;
			/*Bounds*/			
			if(type < 0 || type > 5) {
				this.type = 0;
			} else {
				this.type = type;
			}
			if(type < 3) {
				bounds.x = 1;
				bounds.y = (int) (Tile.TILEHEIGHT*1.1f);
				bounds.width = Tile.TILEWIDTH - 2;
				bounds.height = (int) (Tile.TILEHEIGHT*.7f);
			} else if (type == 3) {
				bounds.x = 1;
				bounds.y = (int) (Tile.TILEHEIGHT*.8f + 1);
				bounds.width = (int) (Tile.TILEWIDTH*.5f - 2);
				bounds.height = (int) (Tile.TILEHEIGHT - 2);
			} else if (type == 4) {
				bounds.x = (int) (Tile.TILEWIDTH*.5f + 1);
				bounds.y = (int) (Tile.TILEHEIGHT*.8f + 1);
				bounds.width = (int) (Tile.TILEWIDTH*.5f - 2);
				bounds.height = (int) (Tile.TILEHEIGHT - 2);
			}
		}
		/*----------------------------------------------------------------------------------*/
		/* Well Specific */
		/*----------------------------------------------------------------------------------*/
		else if (id == 8) {
			/* Tile centering */
			this.xoffset = -1.5f * Tile.TILEWIDTH;
			this.yoffset = -2*Tile.TILEHEIGHT;
			this.width = 4 * Tile.TILEWIDTH;
			this.height = 3 * Tile.TILEHEIGHT;
			/* Bounds */
			bounds.x = (int) ((-.5f + 128f / 64f) * Tile.TILEWIDTH);
			bounds.y = (int) ((-.13f + 160f / 64f) * Tile.TILEHEIGHT);
			bounds.width = (int) ((64f / 64f) * Tile.TILEWIDTH);
			bounds.height = (int) ((32f / 64f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
	}

}
