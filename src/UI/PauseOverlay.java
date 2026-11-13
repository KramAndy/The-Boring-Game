package UI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import GameStates.GameState;
import GameStates.Playing;
import static util.Constants.UI.UrmButtons.*;

import Main.Game;
import util.LoadSave;

public class PauseOverlay{
	
	private Playing playing;
	private BufferedImage bgImg;
	private int bgX, bgY, bgWidth, bgHeight;
	private AudioOptions audioOptions;
	private UrmButtons menuB, replayB, unpauseB;
	
	public PauseOverlay(Playing playing){
		this.playing = playing;
		loadbackground();
		createUrmButtons();
		audioOptions = playing.getGame().getAudioOptions();
		
	}
	
	private void createUrmButtons() {
		int menuX = (int) (313 * Game.SCALE);
		int replayX = (int)(387 * Game.SCALE);
		int unpauseX = (int)(462 * Game.SCALE);
		int bY = (int)(325 * Game.SCALE);
		
		menuB = new UrmButtons(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB = new UrmButtons(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new UrmButtons(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
		
	}
	
	private void loadbackground() {
		bgImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgWidth = (int) (bgImg.getWidth() * Game.SCALE);
		bgHeight =  (int) (bgImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgWidth / 2;
		bgY = (int)(25 * Game.SCALE);
		
	}
	public void update() {
		menuB.update();
		replayB.update();
		unpauseB.update();
		audioOptions.update();
		
	}
	public void draw(Graphics g) {
		//pause BG
		g.drawImage(bgImg, bgX, bgY, bgWidth, bgHeight, null);
			
		//URM buttons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		audioOptions.draw(g);
	
		
	}
	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);
		}
		
	
	
	public void mousePressed(MouseEvent e) {
		 if (isIn(e, menuB)) {
			menuB.setMousePressed(true);
		}else if (isIn(e, unpauseB)) {
			unpauseB.setMousePressed(true);
		}else if (isIn(e, replayB)) {
			replayB.setMousePressed(true);
		}else 
			audioOptions.mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		
		if (isIn(e, menuB)) {
			if(menuB.isMousePressed()) {
				playing.resetAll();
				GameState.state = GameState.MENU;
				playing.unpauseGame();
				playing.setGamestate(GameState.MENU);
			}
		}
		else if (isIn(e, replayB)) {
			if(replayB.isMousePressed()) {
				playing.resetAll();
				playing.unpauseGame();
			}
				
		}
		else if (isIn(e, unpauseB)) {
			if(unpauseB.isMousePressed()) 
				playing.unpauseGame();
		}
		else
			audioOptions.mouseReleased(e);
		
		
		menuB.resetBooleans();
		unpauseB.resetBooleans();
		replayB.resetBooleans();
		
		
	}
	
	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		replayB.setMouseOver(false);
		
		if (isIn(e, menuB)) {
			menuB.setMouseOver(true);
		}else if (isIn(e, unpauseB)) {
			unpauseB.setMouseOver(true);
		}else if (isIn(e, replayB)) {
			replayB.setMouseOver(true);
		}else
			audioOptions.mouseMoved(e);
		
	}
	
	
	
	private Boolean isIn(MouseEvent e, PauseButtons b) {
		return b.getBounds().contains(e.getX(), e.getY());
		
	}
}
