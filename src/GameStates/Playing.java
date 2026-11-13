package GameStates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import Audio.AudioPlayer;
import Entities.EnemyManager;
import Entities.Player;
import Main.Game;
import UI.GameOverOverlay;
import UI.LevelCompletedOverlay;
import UI.PauseOverlay;
import UI.StoryOverlay;
import levels.LevelManager;
import objects.ObjectManager;
import util.LoadSave;
import static util.Constants.Environment.*;

public class Playing extends State implements StatesMethods{
	
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private ObjectManager objectManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private StoryOverlay storyOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;


	private boolean paused = false;
	
	private int xLevelOffset;
	private int leftBorder = (int) (0.3 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.7 * Game.GAME_WIDTH);
	private int maxLevelOffset;
	
	private boolean gameOver;
	private boolean lvlCompleted;
	private BufferedImage bg_img, bigCloudImg, smallCloud;
	private int[] smallCloudPos;
	private Random rand = new Random();
	private boolean playerDying = false;
	
	public Playing(Game game) {
		super(game);
		initClasses();
		
		bg_img = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG);
		bigCloudImg = LoadSave.GetSpriteAtlas(LoadSave.LARGE_CLOUD);
		smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUD);
		smallCloudPos = new int[8];
		for (int i = 0; i< smallCloudPos.length; i++)
			smallCloudPos[i] = (int)(80 * Game.SCALE + rand.nextInt((int)(130* Game.SCALE)));
		
		calcLevelOffset();
		loadStartLevel();
	}
	

	public void loadNextLevel() {
		resetforNextLevel();
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().GetLevelSpawn());
	}
	
	private void loadStartLevel() {
		enemyManager.loadEnemies(levelManager.getCurrentLevel());
		objectManager.loadObjects(levelManager.getCurrentLevel());
	}


	private void calcLevelOffset() {
		maxLevelOffset = levelManager.getCurrentLevel().getLevelOffset();
		
	}


	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		objectManager = new ObjectManager(this);
		player = new Player((int)(100*Game.SCALE), (int)(291*Game.SCALE),(int) (31*Game.SCALE), (int) (33*Game.SCALE), this);
		player.setSpawn(levelManager.getCurrentLevel().GetLevelSpawn());
		player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		storyOverlay = new StoryOverlay(this);
		levelCompletedOverlay = new LevelCompletedOverlay(this);
	
	}
	


	@Override
	public void update() {
		if(storyOverlay.displayStory) {
			storyOverlay.update();
		}else {
		if (paused) {
			pauseOverlay.update();
		}else if(lvlCompleted) {
			levelCompletedOverlay.update();
		}else if(gameOver){
			gameOverOverlay.update();
		}else if(playerDying){
			player.update();
		}else {
		
			levelManager.update();
			objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			checkCloseToBorder();
			checkLevelCompletion();
			}
		}
	}
	
	private int getCurrentMapSize() {
	 
	    return levelManager.getCurrentLevel().getLevelData()[0].length * Game.TILES_SIZE;
	}
		
	
	private void checkLevelCompletion() {
	    
	    int playerX = (int)(player.getHitBox().x);
	    
	    
	    if (playerX >= getCurrentMapSize() - 100) {
	        setLevelCompleted(true);
	        
	    }
	}
	


	private void checkCloseToBorder() {
		int playerX = (int)(player.getHitBox().x);
		int diff = playerX - xLevelOffset;
		
		if (diff > rightBorder)
			xLevelOffset += diff - rightBorder;
		else if (diff < leftBorder)
			xLevelOffset += diff - leftBorder;
		
		if (xLevelOffset > maxLevelOffset)
			xLevelOffset = maxLevelOffset;
		else if (xLevelOffset < 0)
			xLevelOffset = 0;
		
		
	}


	@Override
	public void draw(Graphics g) {
		g.drawImage(bg_img, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		drawClouds(g);
		
		levelManager.draw(g, xLevelOffset);
		player.render(g, xLevelOffset);
		enemyManager.draw(g, xLevelOffset);
		objectManager.draw(g, xLevelOffset);
		
		if(paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}else if(gameOver) {
			gameOverOverlay.draw(g);
		}else if(lvlCompleted){
			levelCompletedOverlay.draw(g);
		}else if (storyOverlay.getDisplayStory()) {
            storyOverlay.draw(g);
        }
			
	}


	private void drawClouds(Graphics g) {
		
		for (int i = 0; i < 3; i++)
		g.drawImage(bigCloudImg, i * BIG_CLOUD_WIDTH - (int)(xLevelOffset * 0.4), (int)(204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		
		for(int i = 0; i < smallCloudPos.length; i++)
		g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int)(xLevelOffset * 0.8), smallCloudPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
	}
	
	public void checkSpikeContact(Player p) {
		objectManager.checkSpikeContact(p); 	
	}
	public void checkPotionContact(Rectangle2D.Float hitBox) {
		objectManager.checkObjectContact(hitBox);
	}
	
	
	public void resetAll() {
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
		objectManager.resetAllObjects();
		
	}
	public void resetforNextLevel() {
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		player.resetPosition();
		enemyManager.resetAllEnemies();
		objectManager.resetAllObjects();
		
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
		
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameOver) {
			if(paused) 
				pauseOverlay.mousePressed(e);
			else if(lvlCompleted) 
				levelCompletedOverlay.mousePressed(e);
		}else{
			gameOverOverlay.mousePressed(e);
		}
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver) {
			if(paused) 
				pauseOverlay.mouseMoved(e);
			else if(lvlCompleted) 
				levelCompletedOverlay.mouseMoved(e);
		} else {
			gameOverOverlay.mouseMoved(e);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!gameOver) {
			if(paused) 
				pauseOverlay.mouseReleased(e);
			else if(lvlCompleted) 
				levelCompletedOverlay.mouseReleased(e);
		}else {
			gameOverOverlay.mouseReleased(e);
		}
	}
	public void mouseDragged(MouseEvent e) {
		if (paused)
			pauseOverlay.mouseDragged(e);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		 if (storyOverlay.getDisplayStory()) {
	            storyOverlay.keyPressed(e);
	        } else if(gameOver) {
		gameOverOverlay.keyPressed(e);
	        }else {
		switch (e.getKeyCode()) {
		
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
			
		case KeyEvent.VK_LEFT:
			player.setLeft(true);
			break;

		case KeyEvent.VK_D:
			player.setRight(true);
	            break;
		case KeyEvent.VK_RIGHT:
			player.setRight(true);
	            break;
	            
		case KeyEvent.VK_SPACE:
			player.setJump(true);
            	break;
		case KeyEvent.VK_UP:
			player.setJump(true);
            	break;
		case KeyEvent.VK_ESCAPE:
			paused = !paused;
			break;
			}
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if(!gameOver)
		switch (e.getKeyCode()) {
		
		case KeyEvent.VK_A:
			player.setLeft(false);
				break;
		case KeyEvent.VK_LEFT:
			player.setLeft(false);
				break;

		case KeyEvent.VK_D:
			player.setRight(false);
	            break;
		case KeyEvent.VK_RIGHT:
			player.setRight(false);
	            break;
		case KeyEvent.VK_SPACE:
			player.setJump(false);
				break;
		case KeyEvent.VK_UP:
			player.setJump(false);
				break;
		}	
	}
	
	
	public void unpauseGame(){
		paused = false;
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	public void setMaxLevelOffset(int levelOffset) {
		this.maxLevelOffset = levelOffset;
	}
	public void setLevelCompleted(boolean levelCompleted) {
		this.lvlCompleted = levelCompleted;
		if(levelCompleted)
			game.getAudioPlayer().stopMusic();
			game.getAudioPlayer().playSFX(AudioPlayer.LEVEL_COMPLETE);
	}
	public Player getPlayer() {
		return player;
	}
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public LevelManager getLevelManager() {
		return levelManager;
	}


	public void setPlayerDying(boolean playerDying) {
		this.playerDying = playerDying;
		
	}
	public void getPlayerReset() {
		 player.resetPosition();
	}

	 public StoryOverlay getStoryOverlay() {
	        return storyOverlay;
	    }


	

	


	
}
