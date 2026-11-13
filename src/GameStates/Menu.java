package GameStates;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.Game;
import UI.MenuButton;
import util.LoadSave;

public class Menu extends State implements StatesMethods{
	
	private MenuButton [] buttons = new MenuButton[3];
	private BufferedImage menuBGImg, menuBackgroundImg;
	private int menuX, menuY, menuWidth, menuHeight, margin;

	public Menu(Game game) {
		super(game);
		loadButtons();
		loadmenuBG();
	}

	private void loadmenuBG() {
		menuBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_MENU_IMG);
		menuBGImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int)(menuBGImg.getWidth() * Game.SCALE);
		menuHeight = (int)(menuBGImg.getHeight() * Game.SCALE);
		margin = (int)(25 * Game.SCALE);
		menuX = Game.GAME_WIDTH - menuWidth - margin;
		menuY = (int)(45*Game.SCALE);
	}

	private void loadButtons() {
		buttons[0] = new MenuButton(Game.GAME_WIDTH - menuWidth -(int)(165 * Game.SCALE), (int)(150*Game.SCALE), 0, GameState.PLAYING);
		buttons[1] = new MenuButton(Game.GAME_WIDTH - menuWidth -(int)(165 * Game.SCALE), (int)(220*Game.SCALE), 1, GameState.OPTIONS);
		buttons[2] = new MenuButton(Game.GAME_WIDTH - menuWidth -(int)(165 * Game.SCALE), (int)(290*Game.SCALE), 2, GameState.QUIT);
		
	}

	@Override
	public void update() {
		for(MenuButton mb : buttons)
			mb.update();
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(menuBackgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(menuBGImg, menuX, menuY, menuWidth, menuHeight, null);
		
		for(MenuButton mb : buttons)
			mb.draw(g);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if (isIn(e,mb)) {
				mb.setMousePressed(true);
				break;
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if (isIn(e,mb)) {
				if(mb.isMousePressed())
					mb.applyGameState();
				if(mb.getState() == GameState.PLAYING)
					game.getAudioPlayer().setLevelMusic(game.getPlaying().getLevelManager().getLevelIndex());
				break;
			}
		}
		resetButtons();
		
	}

	private void resetButtons() {
		for(MenuButton mb : buttons) {
			mb.resetBooleans();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons) {
			mb.setMouseOver(false);
		}
		for(MenuButton mb : buttons) {
			if(isIn(e,mb)) {
				mb.setMouseOver(true);
				break;
			}
		
		}
		
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			GameState.state = GameState.PLAYING;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
