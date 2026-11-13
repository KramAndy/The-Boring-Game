package UI;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static util.Constants.UI.UrmButtons.*;

import GameStates.GameState;
import GameStates.State;
import GameStates.StatesMethods;
import Main.Game;
import util.LoadSave;

public class GameOptions  extends State implements StatesMethods{
	
	private AudioOptions audioOptions;
	private BufferedImage backgroundImg, optionsBackgroundImg;
	private int bgX, bgY, bgW, bgH;
	private UrmButtons menuB;
	
	public GameOptions(Game game) {
		super(game);
		loadImgs();
		loadButtons();
		audioOptions = game.getAudioOptions();
	}

	private void loadButtons() {
		int menuX = (int) (387 * Game.SCALE);
		int menuY = (int) (325 * Game.SCALE);
		menuB = new UrmButtons(menuX, menuY, URM_SIZE, URM_SIZE, 2);
	}

	private void loadImgs() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_MENU_IMG);
		optionsBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_BG);
		bgW = (int)(optionsBackgroundImg.getWidth() * Game.SCALE);
		bgH = (int)(optionsBackgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int)(30 * Game.SCALE);
	}

	@Override
	public void update() {
		menuB.update();
		audioOptions.update();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);
		
		menuB.draw(g);
		audioOptions.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(isIn(e, menuB)) {
			menuB.setMousePressed(true);
		}else {
			audioOptions.mousePressed(e);
		}
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(isIn(e, menuB)) {
			if(menuB.isMousePressed())
			GameState.state = GameState.MENU;
		}else 
			audioOptions.mouseReleased(e);
		
		menuB.resetBooleans();
			
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		if(isIn(e, menuB))
			menuB.setMouseOver(true);
		else
			audioOptions.mouseMoved(e);
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			GameState.state = GameState.MENU;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	
		
	}
	private Boolean isIn(MouseEvent e, PauseButtons b) {
		return b.getBounds().contains(e.getX(), e.getY());
		
	}

}
