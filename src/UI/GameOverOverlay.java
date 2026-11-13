package UI;


import static util.Constants.UI.UrmButtons.URM_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import GameStates.GameState;
import GameStates.Playing;
import Main.Game;
import util.LoadSave;

public class GameOverOverlay {
	
	private Playing playing;
	private BufferedImage img;
	private int imgX, imgY, imgW, imgH;
	private UrmButtons menu;
	
	public GameOverOverlay(Playing playing){
		this.playing = playing;
		createImg();
		createButtons();
		
	}
	public void update() {
		menu.update();
	}
	
	private void createButtons() {
		int menuX = (int)(475 * Game.SCALE);
		int y = (int)(225 * Game.SCALE);
		menu = new UrmButtons(menuX, y, URM_SIZE, URM_SIZE, 2);
		
	}

	private void createImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.GAME_OVER_BG);
		imgW = (int)(img.getWidth()*2.5 * Game.SCALE);
		imgH = (int)(img.getHeight()*2.5 * Game.SCALE);
		imgX = Game.GAME_WIDTH / 2 - imgW / 2;
		imgY = (int)(100 * Game.SCALE);
	}

	public void draw(Graphics g) {
		
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		
		g.drawImage(img, imgX, imgY, imgW, imgH, null);
		menu.draw(g);
	}
	
	public void keyPressed(KeyEvent e) {
		
	
	}
	private boolean isIn(UrmButtons b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		
		menu.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
		
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) 
			if (menu.isMousePressed()) {
					playing.resetAll();
					GameState.state = GameState.MENU;
					playing.setGamestate(GameState.MENU);
				}


		menu.resetBooleans();
		
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e))
			menu.setMousePressed(true);
	}
}
