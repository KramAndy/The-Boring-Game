package inputs;

import java.awt.event.*;

import GameStates.GameState;
import Main.GamePanel;



public class KeyBoardInputs implements KeyListener{

	private GamePanel gamePanel;
	
	public KeyBoardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(GameState.state){
		case MENU:
			gamePanel.getGame().getMenu().keyPressed(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyPressed(e);
			break;
		case OPTIONS:
			gamePanel.getGame().getGameOptions().keyPressed(e);
			break;
		default:
			break;
		}
		
	}
		
	

	@Override
	public void keyReleased(KeyEvent e) {
		switch(GameState.state){
		case MENU:
			gamePanel.getGame().getMenu().keyReleased(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyReleased(e);
			break;
		
		default:
			break;
		}
		
		
		
	}

}
