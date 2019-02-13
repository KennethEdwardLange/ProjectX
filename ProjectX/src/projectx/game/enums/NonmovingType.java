package projectx.game.enums;

import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;

import projectx.game.textures.Assets;
import projectx.engine.tile.Tile;

public enum NonmovingType {
	//Single Types
	Rock(1, 0, Assets.rock),
	Tree(2, 0, Assets.tree),
	Table(3, 0, Assets.table),
	Toilet(4, 0, Assets.toilet),
	Chair1(5, 0, Assets.chair1),
	Chair2(5, 1, Assets.chair2),
	Chair3(5, 3, Assets.chair3),
	//Plants
	Bush(6, 0, Assets.bush),
	Sunflower(7, 0, Assets.sunflower),
	Pumpkin(8, 0, Assets.pumpkin),
	Watermelon(9, 0, Assets.watermelon),
	Corn(10, 0, Assets.corn);
	
	//Multiple types
	
	public int id, type; // id = identifier, type: type of that identity 
	public float xoffset, yoffset, width, height;
	public Texture texture;
	public Rectangle bounds = new Rectangle();
	
	//Single-type
	NonmovingType(int id, int type, Texture texture){
		//Identifiers
		this.id = id;
		this.type = type;
		//Textures
		this.texture = texture;
		
		Setup(type);
	}
	//Multi-type
	NonmovingType(int id, int type, Texture[] texture){
		//Identifiers
		this.id = id;
		this.type = type;
		//Textures
		this.texture = texture[type];
		
		Setup(type);
	}
	
	void Setup(int type){
		
		/*----------------------------------------------------------------------------------*/
		/*Rock Specific*/
		/*----------------------------------------------------------------------------------*/
		if (id == 1){
			/*Tile centering*/
			this.xoffset = 0.0f*Tile.TILEWIDTH;
			this.yoffset = -2f/64f*Tile.TILEHEIGHT;
			this.width = Tile.TILEWIDTH;
			this.height = Tile.TILEHEIGHT;
			/*Bounds*/
			bounds.x = (int)((4f / 64f) * Tile.TILEWIDTH);
			bounds.y = (int)((15f / 64f) * Tile.TILEHEIGHT);
			bounds.width = (int)((56f / 64f) * Tile.TILEWIDTH) ;
			bounds.height = (int)((36f / 64f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		/*Tree Specific*/
		/*----------------------------------------------------------------------------------*/
		else if (id == 2){
			/*Tile centering*/
			this.xoffset = (-2 + 9f/64f) * Tile.TILEWIDTH;
			this.yoffset = (-3 - 32f/64f) * Tile.TILEHEIGHT;
			this.width = 5*Tile.TILEWIDTH;
			this.height = 5*Tile.TILEHEIGHT;
			/*Bounds*/
			bounds.x = (int) ((128f / 64f) * Tile.TILEWIDTH);
			bounds.y = (int) ((240f/64f) * Tile.TILEHEIGHT);
			bounds.width = (int)((48f /64f) * Tile.TILEWIDTH);
			bounds.height = (int) ((48f/64f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		/*Table Specific*/
		/*----------------------------------------------------------------------------------*/
		else if (id == 3){
			/*Tile centering*/
			this.xoffset = (9f/64f - .2f) * Tile.TILEWIDTH;
			this.yoffset = (-40f/64f) * Tile.TILEHEIGHT;
			this.width = 1.f*Tile.TILEWIDTH;
			this.height = 2.10f*Tile.TILEHEIGHT;
			/*Bounds*/
			bounds.x = (int) (Tile.TILEHEIGHT/5.3f);
			bounds.y = (int) (.75*Tile.TILEHEIGHT);
			bounds.width = (int)((48f /64f) * Tile.TILEWIDTH);
			bounds.height = (int) ((48f/64f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		/*Toilet Specific*/
		/*----------------------------------------------------------------------------------*/
		else if (id == 4){
			/*Tile centering*/
			this.xoffset = (13f/64f - .2f) * Tile.TILEWIDTH;
			this.yoffset = (-70f/64f) * Tile.TILEHEIGHT;
			this.width = 1.f*Tile.TILEWIDTH;
			this.height = 2.10f*Tile.TILEHEIGHT;
			/*Bounds*/
			bounds.x = (int) (Tile.TILEHEIGHT/5.65f);
			bounds.y = (int) (1.3*Tile.TILEHEIGHT);
			bounds.width = (int)((48f /64f) * Tile.TILEWIDTH);
			bounds.height = (int) ((48f/64f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		/*Chair Specific*/
		/*----------------------------------------------------------------------------------*/
		else if (id == 5){
			/*Tile centering*/
			this.xoffset = (13f/64f - .2f) * Tile.TILEWIDTH;
			this.yoffset = (-70f/64f) * Tile.TILEHEIGHT;
			this.width = Tile.TILEWIDTH;
			this.height = 2*Tile.TILEHEIGHT;
			/*Bounds*/
			bounds.x = (int) (Tile.TILEHEIGHT/5.65f);
			bounds.y = (int) (1.3*Tile.TILEHEIGHT);
			bounds.width = (int)((48f /64f) * Tile.TILEWIDTH);
			bounds.height = (int) ((48f/64f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		/*Bush Specific*/
		/*----------------------------------------------------------------------------------*/
		else if (id == 6){
			/*Tile centering*/
			this.xoffset = (13f/64f - .6f) * Tile.TILEWIDTH;
			this.yoffset = (-70f/64f) * Tile.TILEHEIGHT;
			this.width = 2*Tile.TILEWIDTH;
			this.height = 2*Tile.TILEHEIGHT;
			/*Bounds*/
			bounds.x = (int) (.55f*Tile.TILEHEIGHT);
			bounds.y = (int) (1.5*Tile.TILEHEIGHT);
			bounds.width = (int)((48f /64f) * Tile.TILEWIDTH);
			bounds.height = (int) ((40f/64f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		/* Sunflower Specific */
		/*----------------------------------------------------------------------------------*/
		else if (id == 7) {
			/* Tile centering */
			this.xoffset = (-.1f)*Tile.TILEWIDTH;
			this.yoffset = (-.2f - 32f / 64f) * Tile.TILEHEIGHT ;
			this.width = 1.5f * Tile.TILEWIDTH;
			this.height = 1.5f * Tile.TILEHEIGHT;
			/* Bounds */
			bounds.x = (int) ((32f / 64f ) * Tile.TILEWIDTH);
			bounds.y = (int) ((32f / 64f + .6f) * Tile.TILEHEIGHT);
			bounds.width = (int) ((32f / 64f) * Tile.TILEWIDTH);
			bounds.height = (int) ((32f / 64f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		/* Pumpkin Specific */
		/*----------------------------------------------------------------------------------*/
		else if (id == 8) {
			/* Tile centering */
			this.xoffset = (-.85f)*Tile.TILEWIDTH;
			this.yoffset = (-144f / 64f +.4f) * Tile.TILEHEIGHT ;
			this.width = 2.75f * Tile.TILEWIDTH;
			this.height = 2.75f * Tile.TILEHEIGHT;
			/* Bounds */
			bounds.x = (int) ((64f / 64f -.1f) * Tile.TILEWIDTH);
			bounds.y = (int) ((140f / 64f-.3f) * Tile.TILEHEIGHT);
			bounds.width = (int) ((64f / 64f) * Tile.TILEWIDTH);
			bounds.height = (int) ((64f / 64f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		/* Watermelon Specific */
		/*----------------------------------------------------------------------------------*/
		else if (id == 9) {
			/* Tile centering */
			this.xoffset = (-.85f)*Tile.TILEWIDTH;
			this.yoffset = (-144f / 64f +.4f) * Tile.TILEHEIGHT ;
			this.width = 2.75f * Tile.TILEWIDTH;
			this.height = 2.75f * Tile.TILEHEIGHT;
			/* Bounds */
			bounds.x = (int) ((64f / 64f -.1f) * Tile.TILEWIDTH);
			bounds.y = (int) ((140f / 64f-.3f) * Tile.TILEHEIGHT);
			bounds.width = (int) ((64f / 64f) * Tile.TILEWIDTH);
			bounds.height = (int) ((64f / 64f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		/* Corn Specific */
		/*----------------------------------------------------------------------------------*/
		else if (id == 10) {
			/* Tile centering */
			this.xoffset = (-.45f)*Tile.TILEWIDTH;
			this.yoffset = (-80f / 64f ) * Tile.TILEHEIGHT ;
			this.width = 2f * Tile.TILEWIDTH;
			this.height = 2f * Tile.TILEHEIGHT;
			/* Bounds */
			bounds.x = (int) ((64f / 64f -.3f) * Tile.TILEWIDTH);
			bounds.y = (int) ((80f / 64f +.1f) * Tile.TILEHEIGHT);
			bounds.width = (int) ((32f / 64f) * Tile.TILEWIDTH);
			bounds.height = (int) ((64f / 64f - .2f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/	
	}

}
