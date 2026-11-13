package Entities;
import static util.Constants.Directions.*;

import static util.Constants.EnemyConstants.*;

import java.awt.geom.Rectangle2D;
import Main.Game;


public class Pirate extends Enemy{
	
	
	
	@SuppressWarnings("unused")
	private int attackBoxOffsetX;
	@SuppressWarnings("unused")
	private boolean left, right;

	public Pirate(float x, float y) {
		super(x, y, PIRATE_WIDTH, PIRATE_HEIGHT, PIRATE);
		initHitBox( 20, 27);
		initAttackBox();
		
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(25 * Game.SCALE), (int)(20 * Game.SCALE));
		
	}

	public void update(int[][] levelData, Player player) {
		updateAttackBox();
		updateMove(levelData, player);
		updateAnimationTick();
		
		
		
	}
	
	private void updateAttackBox() {
		if(state == ATTACK) {
		if (walkDir == RIGHT) {
			attackBox.x = hitBox.x + hitBox.width  + (int)(Game.SCALE * 5);
		}else if(walkDir == LEFT) {
			attackBox.x = hitBox.x - hitBox.width - (int)(Game.SCALE * 5);
		}
			attackBox.y = hitBox.y + (int)(Game.SCALE * 10);
		}else {
			attackBox.x = -100;
			attackBox.y = -100;
		}
		
		
	}

	private void updateMove(int[][] lvlData, Player player) {
	    if (firstUpdate)
	        firstUpdateCheck(lvlData);

	    if (inAir)
	        updateInAir(lvlData);
	    else {
	        switch (state) {
	            case IDLE:
	                newState(RUNNING);
	                break;
	            case RUNNING:
	                if (canSeePlayer(lvlData, player)) {
	                    turnTowardsPlayer(player);
	                    if (isPlayerCloseForAttack(player))
	                        newState(ATTACK);
	                }
	                move(lvlData);
	                break;
	            case ATTACK:
	                if (animationIndex == 0)
	                    attackChecked = false;
	                if (animationIndex == 1 && !attackChecked) {
	                    checkPlayerHit(attackBox, player);
	                    attackChecked = true;
	                }
	                break;
	        }
	    }
	}

	

	public int flipX() {
		if (walkDir == LEFT)
			return width;
		else
			return 0;
	}
	public int flipW() {
		if(walkDir == LEFT)
			return -1;
		else
			return 1;
	}

	

	

	

}
