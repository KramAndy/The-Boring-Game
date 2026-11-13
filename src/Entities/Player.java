package Entities;

import static util.Constants.PlayerActions.*;
import static util.HelperMethods.*;
import static util.Constants.GRAVITY;
import util.Constants;
import util.LoadSave;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import Audio.AudioPlayer;
import GameStates.Playing;
import Main.Game;

public class Player extends Entity{

	private BufferedImage[][] animations;
	private boolean moving = false;
	private boolean left, right, jump;
	private int[][] levelData;
	private float xDrawOffSet = 6 * Game.SCALE;
	private float yDrawOffSet = 4 * Game.SCALE;
	private Playing playing;
	
	//jumping / gravity
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	
	//statusBar UI
	private int maxLives = 3;
	private int currentLives = maxLives;
	private BufferedImage livesImg, livesIcon;
	private int livesImgDefaultWidth =  40;
	private int livesImgDefaultHeight = 15;
	private int livesImgWidth = (int)(livesImgDefaultWidth * Game.SCALE);
	private int livesImgHeight = (int)(livesImgDefaultHeight * Game.SCALE);
	private int livesIconDefaultSize = 25;
	private int livesIconSize = (int)(livesIconDefaultSize * Game.SCALE);
	
	
	
	//flip img
	private int flipX = 0;
	private int flipW = 1;
	private int tileY = 0;
	
	private boolean isDead = false, isSpawn = true;
	
	
	
	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		this.state = IDLE;
		this.walkSpeed = Game.SCALE * 1.0f;
		loadAnimations();
		initHitBox( 20, 28);
		
	}
	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitBox.x = x;
		hitBox.y = y;
	}

	
		
	public void update() {

		
		    if (currentLives <= 0) {
		        currentLives = 0;
		        playing.setGameOver(true);
		        playing.getGame().getAudioPlayer().stopMusic();
		        playing.getGame().getAudioPlayer().playSFX(AudioPlayer.GAMEOVER);
		        return;
		    }

		    if (isDead) {
		        handleDeathState();
		        return;
		    }

		    if (isSpawn) {
		        handleSpawnState();
		        return;
		    }

		   
		    updatePos();

		    if (moving) {
		        checkObjectContact();
		        checkSpikeContact();
		        tileY = (int) (hitBox.y / Game.TILES_SIZE);
		    }

		    updateAnimationTick();
		    setAnimation();
		}
		
	
	private void handleDeathState() {
	    if (state != DEAD) {
	        transitionToState(DEAD);
	        playing.getGame().getAudioPlayer().playSFX(AudioPlayer.DEATH);
	        playing.setPlayerDying(true);
	    } else if (isAnimationComplete(DEAD)) {
	        if (currentLives <= 0) {
	            currentLives = 0;
	            playing.setGameOver(true);
	        } else {
	            resetPosition();
	        }
	    } else {
	        updateAnimationTick();
	    }
	}

	private void handleSpawnState() {
	    if (state != SPAWN) {
	        transitionToState(SPAWN);
	        playing.getGame().getAudioPlayer().playSFX(AudioPlayer.SPAWN);
	    } else if (isAnimationComplete(SPAWN)) {
	        playing.setPlayerDying(false);
	        isSpawn = false;
	    } else {
	        updateAnimationTick();
	    }
	}

	private void transitionToState(int newState) {
	    state = newState;
	    animationTick = 0;
	    animationIndex = 0;
	}

	private boolean isAnimationComplete(int state) {
	    return animationIndex == Constants.GetSpriteAmount(state) - 1 && animationTick >= 11;
	}


	private void checkSpikeContact() {
		playing.checkSpikeContact(this);
		
	}
	private void checkObjectContact() {
			playing.checkPotionContact(hitBox);
			
		}
	public void render(Graphics g, int levelOffset) {
		if (animationIndex >= 0 && animationIndex < animations[state].length) {
			g.drawImage(animations[state][animationIndex], 
					(int)(hitBox.x - xDrawOffSet )- levelOffset + flipX,
					(int)(hitBox.y - yDrawOffSet),
					width * flipW, height, null);
			} else {
			        animationIndex = 0;
			}
		//drawHitBox(g, levelOffset);
		drawUi(g);
	}
	
	

	private void drawUi(Graphics g) {
		
		g.drawImage(livesImg,(int) (10 * Game.SCALE), (int)(10 * Game.SCALE), livesImgWidth, livesImgHeight, null);
		
		int startX = (int)(livesImgWidth + (15 * Game.SCALE));
		int startY = (int)(10 * Game.SCALE);
		
		for (int i = 0; i < currentLives; i++) {
		g.drawImage(livesIcon, startX + i * (int) (livesIconSize + (int)(5 * Game.SCALE)), startY, livesIconSize, livesIconSize, null);
		}
	}




	private void updateAnimationTick() {
		
		
		animationTick++;
	    if (animationTick >= 12) {  
	        animationTick = 0;
	        animationIndex++;

	        
	        if (animationIndex >= Constants.GetSpriteAmount(state)) {
	            animationIndex = 0;

	            
	        }
	    }
	}
	private void setAnimation() {
	    int previousAction = state;

	
	    
	      if (inAir) {
	        if (airSpeed < 0) state = JUMP;
	        else state = FALL;
	    } else if (moving) {
	    	state = RUN;
	    } else {
	    	state = IDLE;
	    }

	   
	    if (previousAction != state) {
	       
	        animationIndex = 0;
	        animationTick = 0;
	    }
	  }
	
	public void changeLives(int value) {
		int previousLives = currentLives;
		currentLives += value;
		if(currentLives < previousLives) {
			isDead = true;
		}
		
		if (currentLives <= 0)
			currentLives = 0;
		else if (currentLives >= maxLives)
			currentLives = maxLives;
	}
	
	
	public void kill() {
		int previousLives = currentLives;
		currentLives --;
		if(currentLives < previousLives)
		isDead = true;
	}
	public void resetPosition() {
		resetDirBooleans();
		inAir = false;
		moving = false;
		airSpeed = 0f;
		state = IDLE;
		hitBox.x = x;
		hitBox.y = y;
		if (!IsEntityOnFloor(hitBox, levelData))
		inAir = true;
		isDead = false;
		isSpawn = true;
		
	    loadLevelData(levelData);
	}



	private void updatePos() {
		moving = false;
		
		if (jump)
			jump();
		

		if(!inAir)
			if((!left && !right) || right && left)
				return;
		
		float xSpeed = 0;
			
		if (left) {
			
			xSpeed -= walkSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right) {
			xSpeed += walkSpeed;
			flipX = 0;
			flipW = 1;
		}
		if (!inAir)
			if (!IsEntityOnFloor(hitBox, levelData))
				inAir = true;
		if (inAir) {
			if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
				hitBox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
			} else {
				hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
				if (airSpeed > 0){
					resetInAir();
					updateXPos(xSpeed);
				}
					
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
		moving = true;
	}
		
		
	private void jump() {
		if (inAir)
			return;
		playing.getGame().getAudioPlayer().playSFX(AudioPlayer.JUMP);
		inAir = true;
		airSpeed = jumpSpeed;
		
		
	}
	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
			
	}

	
	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
			hitBox.x += xSpeed;
		} else {			
			hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);
		}
		
	}
	

	private void loadAnimations() {
		animations = new BufferedImage[6][];
		 animations[Constants.PlayerActions.IDLE] = new BufferedImage[Constants.GetSpriteAmount(Constants.PlayerActions.IDLE)];
		    animations[Constants.PlayerActions.RUN] = new BufferedImage[Constants.GetSpriteAmount(Constants.PlayerActions.RUN)];
		    animations[Constants.PlayerActions.JUMP] = new BufferedImage[Constants.GetSpriteAmount(Constants.PlayerActions.JUMP)];
		    animations[Constants.PlayerActions.FALL] = new BufferedImage[Constants.GetSpriteAmount(Constants.PlayerActions.FALL)];
		    animations[Constants.PlayerActions.DEAD] = new BufferedImage[Constants.GetSpriteAmount(Constants.PlayerActions.DEAD)];
		    animations[Constants.PlayerActions.SPAWN] = new BufferedImage[Constants.GetSpriteAmount(Constants.PlayerActions.SPAWN)];
	
		    loadActionSprites(Constants.PlayerActions.IDLE, "PlayerSprites/01-Idle/Idle_");
		    loadActionSprites(Constants.PlayerActions.RUN, "PlayerSprites/02-Run/Run_");
		    loadActionSprites(Constants.PlayerActions.JUMP, "PlayerSprites/03-Jump/Jump_");
		    loadActionSprites(Constants.PlayerActions.FALL, "PlayerSprites/04-Fall/Fall_");
		    loadActionSprites(Constants.PlayerActions.DEAD, "PlayerSprites/05-Dead/Dead_");
		    loadActionSprites(Constants.PlayerActions.SPAWN, "PlayerSprites/06-Spawn/Spawn_");
		
		    livesImg = LoadSave.GetSpriteAtlas(LoadSave.LIVES_IMAGE);
			livesIcon = LoadSave.GetSpriteAtlas(LoadSave.LIVES_ICON);
	}
	
	

	private void loadActionSprites(int action, String basePath) {
		for(int i = 1; i <= animations[action].length; i++) {
			String imagePath = String.format(basePath + "%02d.png", i);
			animations[action][i - 1] = LoadSave.GetSpriteAtlas(imagePath);
		}
		
	}
	public void loadLevelData(int[][] levelData) {
		this.levelData = levelData;
	}
		
		

	public void resetDirBooleans() {
		left = false;
		
		right = false;
		
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}


	public float getHitBoxX() {
		return hitBox.x;
	}
	
	public int getTileY() {
		return tileY;
	}



	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		moving = false;
		isSpawn = true;
		state = IDLE;
		currentLives = maxLives;
		
		hitBox.x = x;
		hitBox.y = y;
		if (!IsEntityOnFloor(hitBox, levelData))
		inAir = true;
	}
	
	
	
}
