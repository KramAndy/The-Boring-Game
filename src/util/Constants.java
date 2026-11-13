package util;

import Main.Game;

public class Constants {
	public static final int ANIMATION_SPEED = 25;
	public static final float GRAVITY = 0.04f * Game.SCALE;
	
	public static class Projectiles{
		public static final int CANNONBALL_DEFAULT_SIZE = 15;
		public static final int CANNONBALL_SIZE = (int)(CANNONBALL_DEFAULT_SIZE * Game.SCALE);
		public static final float SPEED = 0.75f * Game.SCALE;
	}
	
	public static class ObjectConstants{
		public static final int POTION = 1;
		public static final int POTION_WIDTH_DEFAULT = 12;
		public static final int POTION_HEIGHT_DEFAULT = 16;
		public static final int POTION_WIDTH = (int)(POTION_WIDTH_DEFAULT * Game.SCALE);
		public static final int POTION_HEIGHT = (int)(POTION_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int CANNON_LEFT = 3;
		public static final int CANNON_RIGHT = 4;
		public static final int CANNON_WIDTH_DEFAULT = 40;
		public static final int CANNON_HEIGHT_DEFAULT = 26;
		public static final int CANNON_WIDTH = (int)(CANNON_WIDTH_DEFAULT * Game.SCALE);
		public static final int CANNON_HEIGHT = (int)(CANNON_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int SPIKE = 2;
		public static final int SPIKE_WIDTH_DEFAULT = 32;
		public static final int SPIKE_HEIGHT_DEFAULT = 32;
		public static final int SPIKE_WIDTH = (int)(SPIKE_WIDTH_DEFAULT * Game.SCALE);
		public static final int SPIKE_HEIGHT = (int)(SPIKE_HEIGHT_DEFAULT * Game.SCALE);
		
		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
			case POTION:
				return 7;
			case CANNON_LEFT, CANNON_RIGHT:
				return 7;
			default:
				return 1;
			}
		}
	}
	public static class EnemyConstants{
		public static final int PIRATE = 0;
		
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 4;

		
		public static final int PIRATE_DEFAULT_WIDTH = 64;
		public static final int PIRATE_DEFAULT_HEIGHT = 40;
		
		public static final int PIRATE_WIDTH = (int) (PIRATE_DEFAULT_WIDTH * Game.SCALE);
		public static final int PIRATE_HEIGHT = (int) (PIRATE_DEFAULT_HEIGHT * Game.SCALE);
		
		public static final int PIRATE_DRAW_OFFSET_X = (int)(21 * Game.SCALE);
		public static final int PIRATE_DRAW_OFFSET_Y = (int)(3 * Game.SCALE);
		
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			switch(enemy_type) {
			case PIRATE:
				switch(enemy_state) {
				case IDLE:
					return 5;
					
				case RUNNING:
					return 6;
					
				case ATTACK:
					return 3;

				}
			}
			return 0;
		}
		public static int GetEnemyDamage(int enemy_type) {
			switch (enemy_type) {
			case PIRATE:
				return 1;
			default:
				return 0;
			}
		}
		
	}
	
	public static class Environment{	
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

		
		public static final int BIG_CLOUD_WIDTH = (int)(BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int)(BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_WIDTH = (int)(SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_HEIGHT = (int)(SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
	}
	
	public static class UI{
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);
		}
		public static class PauseButton{
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);
		}
		public static class UrmButtons{
			public static final int URM_SIZE_DEFAULT = 56;
			public static final int URM_SIZE = (int)(URM_SIZE_DEFAULT * Game.SCALE);
		}
		public static class VolumeButton{
			public static final int VOLUME_WIDTH_DEFAULT = 28;
			public static final int VOLUME_HEIGHT_DEFAULT = 44;
			public static final int SLIDER_WIDTH_DEFAULT = 215;
			public static final int VOLUME_WIDTH = (int)(VOLUME_WIDTH_DEFAULT * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int)(VOLUME_HEIGHT_DEFAULT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int)(SLIDER_WIDTH_DEFAULT * Game.SCALE);
		}
		
		
	}
	
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class PlayerActions{
		public static final int IDLE = 0;
		public static final int RUN = 1;
		public static final int JUMP = 2;
		public static final int FALL = 3;
		public static final int DEAD = 4;
		public static final int SPAWN = 5;
		
	}
	
	public static int GetSpriteAmount(int action) {
		switch (action) {
		case PlayerActions.IDLE: return 11;
		case PlayerActions.RUN: return 12;
		case PlayerActions.JUMP: return 1;
		case PlayerActions.FALL: return 1;
		case PlayerActions.DEAD: return 7;
		case PlayerActions.SPAWN: return 7;
		default:
			return 0;
		
		}
	}

}
