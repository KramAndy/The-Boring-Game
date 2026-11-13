package Entities;
import static util.Constants.ANIMATION_SPEED;
import static util.Constants.GRAVITY;
import static util.Constants.EnemyConstants.*;
import static util.HelperMethods.*;

import java.awt.geom.Rectangle2D;

import static util.Constants.Directions.*;

import Main.Game;



public abstract class Enemy extends Entity{
	protected int enemyType;
	protected boolean firstUpdate = true;
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = Game.TILES_SIZE;
	protected boolean attackChecked;
	
	

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		this.walkSpeed = Game.SCALE * 0.35f;
	}
	
	protected void firstUpdateCheck(int[][] levelData) {
		if (!IsEntityOnFloor(hitBox, levelData))
			inAir = true;
		firstUpdate = false;
	}
	
	protected void updateInAir(int[][] levelData){	
		if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
			hitBox.y += airSpeed;
			airSpeed += GRAVITY;
		} else {
			inAir = false;
			hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
			tileY =(int)(hitBox.y / Game.TILES_SIZE);
		}
	}
	
	protected void move(int[][] levelData) {
		float xSpeed = 0;

		if (walkDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData))
			if (IsFloor(hitBox, xSpeed, levelData)) {
				hitBox.x += xSpeed;
				return;
			}

		changeWalkDir();
	}
	
	protected void turnTowardsPlayer(Player player) {
		if(player.hitBox.x > hitBox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}
	
	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int) (player.getHitBox().y / Game.TILES_SIZE);
		if (playerTileY == tileY)
			if (isPlayerInRange(player)) {
				if (IsSightClear(lvlData, hitBox, player.hitBox, tileY))
					return true;
			}

		return false;
	}
	protected boolean isPlayerInRange(Player player) {
		int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
		return absValue <= attackDistance * 5;
	}
	
	protected boolean isPlayerCloseForAttack(Player player) {
		int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
		return absValue <= attackDistance;
	}
	

	protected void newState(int state) {
		this.state = state;
		animationTick = 0;
		animationIndex = 0;
	}
	
	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if (attackBox.intersects(player.hitBox))
			player.changeLives(-GetEnemyDamage(enemyType));
		attackChecked = true;

	}
	
	protected void updateAnimationTick() {
		
	    animationTick++;
	    if (animationTick >= ANIMATION_SPEED) {
	        animationTick = 0;
	        animationIndex++;
	        
	        if (animationIndex >= GetSpriteAmount(enemyType, state)) {
	            animationIndex = 0;
	        		
	            switch(state) {
	            case ATTACK:
	            	state = IDLE;
	            		
	            	
	            }	
	        }
	    }  
	}
	
	
	protected void changeWalkDir() {
		if(walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	
	public int getEnemyState() {
		return state;
	}
	public void resetEnemy() {
		hitBox.x = x;
		hitBox.y = y;
		firstUpdate = true;
		newState(IDLE);
		airSpeed = 0;
		
	}

}
