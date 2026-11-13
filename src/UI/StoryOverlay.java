package UI;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import GameStates.Playing;
import Main.Game;
import util.LoadSave;

public class StoryOverlay {
	@SuppressWarnings("unused")
	private Playing playing;
	private BufferedImage img;
	public boolean displayStory = true;
	public StoryOverlay(Playing playing) {
		this.playing = playing;
		img = LoadSave.GetSpriteAtlas(LoadSave.STORY);
	}

	
	public void update() {
	
		
	}

	
	public void draw(Graphics g) {
		g.drawImage(img, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
	
	}
	

	
	public void keyPressed(KeyEvent e) {
	if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER )
		displayStory = false;
	}

	public boolean getDisplayStory() {
		return displayStory;
	}

}
