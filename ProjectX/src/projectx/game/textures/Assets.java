package projectx.game.textures;

import java.awt.Font;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import projectx.engine.glgfx.ImageLoader;
import projectx.engine.glgfx.SpriteSheet;
import projectx.engine.io.IO;
/**
 * This class is responsible for loading in the resources for the game.
 * @author Kenneth Lange
 *
 */
public class Assets {
	
	//Widths and Heights in pixels
	private static final int tileWidth = 128, tileHeight = 128;
	private static final int playerWidth = 64, playerHeight = 64;
	private static final int prisonerWidth = 64, prisonerHeight = 64;
	private static final int guardWidth = 64, guardHeight = 64;
	private static final int buttonWidth = 256, buttonHeight = 128;
	private static final int itemWidth = 128, itemHeight = 128;
	private static final int smallItemWidth = 64, smallItemHeight = 64;
	
	//Fonts
	public static Font font28;	
	
	/*Textures*/
	//Tiles
	public static Texture grass, dirt, sand, stonepath, shallowwater, deepwater, cement;
	//Static Entities
	public static Texture rock, tree, well;
	public static Texture[] cellar_wall, wall, electric_fence;
	//Plants
	public static Texture sunflower, corn, pumpkin, watermelon, sprout;
	//Items
	public static Texture stick, key;
	//Weapons
	public static Texture crowbar;
	//Armor
	public static Texture helmet;
	//Character Models
	public static Texture[] player_up, player_down, player_left, player_right, player_notmoving;
	public static Texture[] prisoner_up, prisoner_down, prisoner_left, prisoner_right, prisoner_notmoving;
	public static Texture[] guard_up, guard_down, guard_left, guard_right, guard_notmoving;
	//Buttons
	public static Texture[] start_button, exit_button, map_builder_button, load_button, new_button, back_button, multiplayer_button, host_button, join_button;
	//Static Objects
	public static Texture guardspawner, prisonerspawner, checkpoint, door_opened, door_closed, wall_cracked, wall_broken, table, toilet, bed1, bed2, bush, chair1, chair2, chair3, dumbbell, weighted_bar, serving_area;
	//Screens
	public static Texture inventoryScreen;
	//Misc
	public static Texture heart,halfheart;
	public static Texture null_image, big_i;
	public static Texture[] food;
	
	/**
	 * Loads in all resources once for the game
	 */
	public static void init(){
//		font28 = FontLoader.loadFont("res/fonts/slkscr.ttf", 28);
		
		//Characters
		SpriteSheet playerImages = new SpriteSheet(ImageLoader.loadImage("/textures/Character.png"));
		SpriteSheet guardImages = new SpriteSheet(ImageLoader.loadImage("/textures/Guard.png"));
		SpriteSheet prisonerImages = new SpriteSheet(ImageLoader.loadImage("/textures/Prisoner.png"));
		//Tiles
		SpriteSheet tileTextures = new SpriteSheet(ImageLoader.loadImage("/textures/Tiles.png"));
		//Buttons
		SpriteSheet menuButtons = new SpriteSheet(ImageLoader.loadImage("/textures/MenuButtons.png"));
		//Screens
		SpriteSheet InventoryScreen = new SpriteSheet(ImageLoader.loadImage("/textures/InventoryScreen.png"));
		//Items
		SpriteSheet itemTextures = new SpriteSheet(ImageLoader.loadImage("/textures/Items.png"));
		//Objects
		SpriteSheet Well = new SpriteSheet(ImageLoader.loadImage("/textures/Well.png"));
		SpriteSheet Table = new SpriteSheet(ImageLoader.loadImage("/textures/Table.png"));
		SpriteSheet ObjectIcons = new SpriteSheet(ImageLoader.loadImage("/textures/ObjectIcons.png"));
		SpriteSheet CellarWall = new SpriteSheet(ImageLoader.loadImage("/textures/CellarWall.png"));
		SpriteSheet LargeObjects = new SpriteSheet(ImageLoader.loadImage("/textures/LargeObjects.png"));
		SpriteSheet SmallObjects = new SpriteSheet(ImageLoader.loadImage("/textures/SmallObjects.png"));
		SpriteSheet Wall = new SpriteSheet(ImageLoader.loadImage("/textures/Wall.png"));
		SpriteSheet ElectricFence = new SpriteSheet(ImageLoader.loadImage("/textures/ElectricFence.png"));
		SpriteSheet Door = new SpriteSheet(ImageLoader.loadImage("/textures/door.png"));
		SpriteSheet Toilet = new SpriteSheet(ImageLoader.loadImage("/textures/Toilet.png"));
		SpriteSheet CrackedWall = new SpriteSheet(ImageLoader.loadImage("/textures/WallCracked.png"));
		SpriteSheet Bed = new SpriteSheet(ImageLoader.loadImage("/textures/Beds.png"));
		SpriteSheet Bush = new SpriteSheet(ImageLoader.loadImage("/textures/Bush.png"));
		SpriteSheet Chairs = new SpriteSheet(ImageLoader.loadImage("/textures/Chairs.png"));
		SpriteSheet Dumbbell = new SpriteSheet(ImageLoader.loadImage("/textures/Dumbbell.png"));
		SpriteSheet WeightedBar = new SpriteSheet(ImageLoader.loadImage("/textures/WeightedBar.png"));
		SpriteSheet ServingArea = new SpriteSheet(ImageLoader.loadImage("/textures/ServingArea.png"));
		//Plants
		SpriteSheet Sunflower = new SpriteSheet(ImageLoader.loadImage("/textures/Sunflower.png"));
		SpriteSheet Watermelon = new SpriteSheet(ImageLoader.loadImage("/textures/Watermelon.png"));
		SpriteSheet Pumpkin = new SpriteSheet(ImageLoader.loadImage("/textures/Pumpkin.png"));
		SpriteSheet Corn = new SpriteSheet(ImageLoader.loadImage("/textures/SweetCornFull.png"));
		SpriteSheet Sprout = new SpriteSheet(ImageLoader.loadImage("/textures/Sprout.png"));
		//Misc
		SpriteSheet Heart = new SpriteSheet(ImageLoader.loadImage("/textures/Heart.png"));
		SpriteSheet HalfHeart = new SpriteSheet(ImageLoader.loadImage("/textures/HalfHeart.png"));
		SpriteSheet FoodImages = new SpriteSheet(ImageLoader.loadImage("/textures/FoodImages.png"));
		SpriteSheet BigI = new SpriteSheet(ImageLoader.loadImage("/textures/BigI.png"));
		
		
		try {
		//Menu Buttons
		start_button = new Texture[2];
		exit_button = new Texture[2];
		map_builder_button = new Texture[2];
		map_builder_button = new Texture[2];
		load_button = new Texture[2];
		new_button = new Texture[2];
		back_button = new Texture[2];
		multiplayer_button = new Texture[2];
		host_button = new Texture[2];
		join_button = new Texture[2];
		start_button[0] 		= BufferedImageUtil.getTexture("",menuButtons.crop(0, 0, buttonWidth, buttonHeight));
		start_button[1] 		= BufferedImageUtil.getTexture("",menuButtons.crop(0, 1, buttonWidth, buttonHeight));
		exit_button[0] 			= BufferedImageUtil.getTexture("",menuButtons.crop(0, 2, buttonWidth, buttonHeight));
		exit_button[1] 			= BufferedImageUtil.getTexture("",menuButtons.crop(0, 3, buttonWidth, buttonHeight));
		map_builder_button[0]	= BufferedImageUtil.getTexture("",menuButtons.crop(0, 4, buttonWidth, buttonHeight));
		map_builder_button[1] 	= BufferedImageUtil.getTexture("",menuButtons.crop(0, 5, buttonWidth, buttonHeight));
		load_button[0] 			= BufferedImageUtil.getTexture("",menuButtons.crop(0, 6, buttonWidth, buttonHeight));
		load_button[1] 			= BufferedImageUtil.getTexture("",menuButtons.crop(0, 7, buttonWidth, buttonHeight));
		new_button[0] 			= BufferedImageUtil.getTexture("",menuButtons.crop(0, 8, buttonWidth, buttonHeight));
		new_button[1] 			= BufferedImageUtil.getTexture("",menuButtons.crop(0, 9, buttonWidth, buttonHeight));
		back_button[0] 			= BufferedImageUtil.getTexture("",menuButtons.crop(0, 10, buttonWidth, buttonHeight));
		back_button[1] 			= BufferedImageUtil.getTexture("",menuButtons.crop(0, 11, buttonWidth, buttonHeight));
		multiplayer_button[0]	= BufferedImageUtil.getTexture("",menuButtons.crop(0, 12, buttonWidth, buttonHeight));
		multiplayer_button[1]	= BufferedImageUtil.getTexture("",menuButtons.crop(0, 13, buttonWidth, buttonHeight));
		join_button[0]			= BufferedImageUtil.getTexture("",menuButtons.crop(0, 14, buttonWidth, buttonHeight));
		join_button[1]			= BufferedImageUtil.getTexture("",menuButtons.crop(0, 15, buttonWidth, buttonHeight));
		host_button[0]			= BufferedImageUtil.getTexture("",menuButtons.crop(0, 16, buttonWidth, buttonHeight));
		host_button[1]			= BufferedImageUtil.getTexture("",menuButtons.crop(0, 17, buttonWidth, buttonHeight));
		
		//Tiles
		grass = BufferedImageUtil.getTexture("",tileTextures.crop(0, 0, tileWidth, tileHeight));
		dirt = BufferedImageUtil.getTexture("",tileTextures.crop(1, 0, tileWidth, tileHeight));
		sand = BufferedImageUtil.getTexture("",tileTextures.crop(2, 0, tileWidth, tileHeight));
		stonepath = BufferedImageUtil.getTexture("",tileTextures.crop(3, 0, tileWidth, tileHeight));
		shallowwater = BufferedImageUtil.getTexture("",tileTextures.crop(4, 0, tileWidth, tileHeight));
		deepwater = BufferedImageUtil.getTexture("",tileTextures.crop(5, 0, tileWidth, tileHeight));
		cement = BufferedImageUtil.getTexture("",tileTextures.crop(0, 1, tileWidth, tileHeight));
		
		/*Characters*/
		//Player
		player_up = new Texture[8];
		player_right = new Texture[8];
		player_left = new Texture[8];
		player_down = new Texture[8];
		player_notmoving = new Texture[4];
		for (int i = 0; i < 8; i++){
			player_up[i] =  BufferedImageUtil.getTexture("",playerImages.crop(i + 1, 0, playerWidth, playerHeight));
		}
		for (int i = 0; i < 8; i++){
			player_right[i] =  BufferedImageUtil.getTexture("",playerImages.crop(i + 1, 3, playerWidth, playerHeight));
		}
		for (int i = 0; i < 8; i++){
			player_left[i] =  BufferedImageUtil.getTexture("",playerImages.crop(i + 1, 1, playerWidth, playerHeight));
		}
		for (int i = 0; i < 8; i++){
			player_down[i] =  BufferedImageUtil.getTexture("",playerImages.crop(i + 1, 2, playerWidth, playerHeight));
		}
		for (int i = 0; i < 4; i++){
			player_notmoving[i] =  BufferedImageUtil.getTexture("",playerImages.crop(0, i, playerWidth, playerHeight));
		}
		//Player
		prisoner_up = new Texture[8];
		prisoner_right = new Texture[8];
		prisoner_left = new Texture[8];
		prisoner_down = new Texture[8];
		prisoner_notmoving = new Texture[4];
		for (int i = 0; i < 8; i++){
			prisoner_up[i] =  BufferedImageUtil.getTexture("",prisonerImages.crop(i + 1, 0, prisonerWidth, prisonerHeight));
		}
		for (int i = 0; i < 8; i++){
			prisoner_right[i] =  BufferedImageUtil.getTexture("",prisonerImages.crop(i + 1, 3, prisonerWidth, prisonerHeight));
		}
		for (int i = 0; i < 8; i++){
			prisoner_left[i] =  BufferedImageUtil.getTexture("",prisonerImages.crop(i + 1, 1, prisonerWidth, prisonerHeight));
		}
		for (int i = 0; i < 8; i++){
			prisoner_down[i] =  BufferedImageUtil.getTexture("",prisonerImages.crop(i + 1, 2, prisonerWidth, prisonerHeight));
		}
		for (int i = 0; i < 4; i++){
			prisoner_notmoving[i] =  BufferedImageUtil.getTexture("",prisonerImages.crop(0, i, prisonerWidth, prisonerHeight));
		}
		//Guard
		guard_up = new Texture[8];
		guard_right = new Texture[8];
		guard_left = new Texture[8];
		guard_down = new Texture[8];
		guard_notmoving = new Texture[4];
		for (int i = 0; i < 8; i++){
			guard_up[i] = BufferedImageUtil.getTexture("",guardImages.crop(i + 1, 0, guardWidth, guardHeight));
		}
		for (int i = 0; i < 8; i++){
			guard_right[i] = BufferedImageUtil.getTexture("",guardImages.crop(i + 1, 3, guardWidth, guardHeight));
		}
		for (int i = 0; i < 8; i++){
			guard_left[i] = BufferedImageUtil.getTexture("",guardImages.crop(i + 1, 1, guardWidth, guardHeight));
		}
		for (int i = 0; i < 8; i++){
			guard_down[i] = BufferedImageUtil.getTexture("",guardImages.crop(i + 1, 2, guardWidth, guardHeight));
		}
		for (int i = 0; i < 4; i++){
			guard_notmoving[i] = BufferedImageUtil.getTexture("",guardImages.crop(0, i, guardWidth, guardHeight));
		}
		
		/*Misc.*/
		food = new Texture[20];
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 5; j++){
				food[i] = BufferedImageUtil.getTexture("",FoodImages.crop(i, j, 125, 100));
			}
		}
		heart = BufferedImageUtil.getTexture("", Heart.crop(0, 0, 32, 32));
		halfheart = BufferedImageUtil.getTexture("", HalfHeart.crop(0,0,32,32));
		/*Static Entities*/
		//Solo types
		rock = BufferedImageUtil.getTexture("",itemTextures.crop(0, 1, itemWidth, itemHeight));
		tree = BufferedImageUtil.getTexture("",LargeObjects.crop(0, 0, 128, 128));
		well = BufferedImageUtil.getTexture("", Well.crop(0, 0, 128, 128));
		table = BufferedImageUtil.getTexture("",Table.crop(0, 0, 256, 384));
		toilet = BufferedImageUtil.getTexture("",Toilet.crop(0, 0, 32, 64));
		sunflower = BufferedImageUtil.getTexture("", Sunflower.crop(0, 0, 64, 64));
		watermelon = BufferedImageUtil.getTexture("", Watermelon.crop(0, 0, 64, 64));
		pumpkin = BufferedImageUtil.getTexture("", Pumpkin.crop(0, 0, 64, 64));
		sprout = BufferedImageUtil.getTexture("", Sprout.crop(0, 0, 128, 128));
		corn = BufferedImageUtil.getTexture("", Corn.crop(0, 0, 64, 64));
		//Multiple types
		cellar_wall = new Texture[12];
		for (int i = 0; i < 12; i++){
			cellar_wall[i] = BufferedImageUtil.getTexture("",CellarWall.crop(i, 0, tileWidth, tileHeight));
		}
		wall = new Texture[5];
		for (int i = 0; i < 5; i++){
			wall[i] = BufferedImageUtil.getTexture("",Wall.crop(i, 0, 64, 192));
		}
		electric_fence = new Texture[5];
		for (int i = 0; i < 5; i++){
			electric_fence[i] = BufferedImageUtil.getTexture("",ElectricFence.crop(i, 0, 64, 192));
		}
		
		//Inventory
		inventoryScreen = BufferedImageUtil.getTexture("",InventoryScreen.crop(0, 0, 512, 384));
		
		//Items
		stick = BufferedImageUtil.getTexture("",itemTextures.crop(0, 0, itemWidth, itemHeight));
		key = BufferedImageUtil.getTexture("",SmallObjects.crop(0, 1, smallItemWidth, smallItemHeight));
		
		//Weapons
		crowbar = BufferedImageUtil.getTexture("",SmallObjects.crop(0, 2, smallItemWidth, smallItemHeight));
		
		//Armor
		helmet = BufferedImageUtil.getTexture("",SmallObjects.crop(0, 3, smallItemWidth, smallItemHeight));
		
		//misc
		big_i = BufferedImageUtil.getTexture("", BigI.crop(0, 0, smallItemWidth, smallItemHeight));
		
		/*rock item uses static entity rock's picture*/
		
		//Objects (Spawners, Checkpoints, Etc)
		prisonerspawner = BufferedImageUtil.getTexture("",ObjectIcons.crop(0, 0, tileWidth, tileHeight));
		guardspawner = BufferedImageUtil.getTexture("",ObjectIcons.crop(1, 0, tileWidth, tileHeight));
		checkpoint = BufferedImageUtil.getTexture("",ObjectIcons.crop(2, 0, tileWidth, tileHeight));
		door_closed = BufferedImageUtil.getTexture("",Door.crop(0, 0, 64, 192));
		door_opened = BufferedImageUtil.getTexture("",Door.crop(1, 0, 64, 192));
		wall_cracked = BufferedImageUtil.getTexture("",CrackedWall.crop(0, 0, 64, 192));
		wall_broken = BufferedImageUtil.getTexture("",CrackedWall.crop(1, 0, 64, 192));
		bed1 = BufferedImageUtil.getTexture("",Bed.crop(2, 0, 64, 32));
		bed2 = BufferedImageUtil.getTexture("",Bed.crop(2, 1, 64, 32));
		bush = BufferedImageUtil.getTexture("",Bush.crop(0, 0, 128, 128));
		chair1 = BufferedImageUtil.getTexture("",Chairs.crop(0, 0, 32, 64));
		chair2 = BufferedImageUtil.getTexture("",Chairs.crop(1, 0, 32, 64));
		chair3 = BufferedImageUtil.getTexture("",Chairs.crop(2, 0, 32, 64));
		dumbbell = BufferedImageUtil.getTexture("",Dumbbell.crop(0, 0, 64, 64));
		weighted_bar = BufferedImageUtil.getTexture("",WeightedBar.crop(0, 0, 128, 400));
		serving_area = BufferedImageUtil.getTexture("",ServingArea.crop(0, 0, 256, 64));
		} catch (Exception e) {
			e.printStackTrace();
			IO.printlnErr("Failed to load assets");
		}
		
		
	}
}