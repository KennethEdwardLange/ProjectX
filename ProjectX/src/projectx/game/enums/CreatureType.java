package projectx.game.enums;

import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;

import projectx.engine.glgfx.Animation;
import projectx.game.textures.Assets;

public enum CreatureType {
	//Players
	Player(1, 1, true, false, new Animation(150, Assets.prisoner_up), new Animation(150, Assets.prisoner_down), new Animation(150, Assets.prisoner_left), new Animation(150, Assets.prisoner_right), Assets.prisoner_notmoving),
	Spectator(9, 0, true, false, null, null, null, null, null),
	//Shells
	PShell(0, 1, false, true, new Animation(150, Assets.prisoner_up), new Animation(150, Assets.prisoner_down), new Animation(150, Assets.prisoner_left), new Animation(150, Assets.prisoner_right), Assets.prisoner_notmoving),
	GShell(0, 2, false, true, new Animation(150, Assets.guard_up), new Animation(150, Assets.guard_down), new Animation(150, Assets.guard_left), new Animation(150, Assets.guard_right), Assets.guard_notmoving),
	//NPCs
	Prisoner(1, 1, false, false, new Animation(150, Assets.prisoner_up), new Animation(150, Assets.prisoner_down), new Animation(150, Assets.prisoner_left), new Animation(150, Assets.prisoner_right), Assets.prisoner_notmoving),
	PGuard(1, 2, false, false, new Animation(150, Assets.guard_up), new Animation(150, Assets.guard_down), new Animation(150, Assets.guard_left), new Animation(150, Assets.guard_right), Assets.guard_notmoving),
	SGuard(1, 2, false, false, new Animation(150, Assets.guard_up), new Animation(150, Assets.guard_down), new Animation(150, Assets.guard_left), new Animation(150, Assets.guard_right), Assets.guard_notmoving),
	RGuard(1, 2, false, false, new Animation(150, Assets.guard_up), new Animation(150, Assets.guard_down), new Animation(150, Assets.guard_left), new Animation(150, Assets.guard_right), Assets.guard_notmoving);
	
	
	public double speed;
	public int team;
	public boolean player_controlled, server_controlled;
	public Animation animUp, animDown, animLeft, animRight;
	public Texture[] notMoving;
	public Rectangle bounds;
	public double[] ATHLETCS = {1, 2, 128/57, 128/54, 128/51, 128/48, 128/45, 128/42, 128/38, 128/35, 8};

	
	CreatureType(int speed, int team, boolean player_controlled, boolean server_controlled,
		Animation animUp, Animation animDown, Animation animLeft, Animation animRight, Texture[] notMoving){
		this.team = team;
		this.speed = ATHLETCS[speed];
		this.player_controlled = player_controlled;
		this.server_controlled = server_controlled;
		
		//Animations
		this.animUp = animUp;
		this.animDown = animDown;
		this.animLeft = animLeft;
		this.animRight = animRight;
		this.notMoving = notMoving;
		
		//Bounds
		bounds = new Rectangle(20, 43, 23, 17); //default character bounds, TODO change
	}

	public boolean isPlayer_controlled() {
		return player_controlled;
	}

	public void setPlayer_controlled(boolean player_controlled) {
		this.player_controlled = player_controlled;
	}

	public boolean isServer_controlled() {
		return server_controlled;
	}

	public void setServer_controlled(boolean server_controlled) {
		this.server_controlled = server_controlled;
	}
	
	
}
