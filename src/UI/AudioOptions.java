package UI;

import static util.Constants.UI.PauseButton.SOUND_SIZE;
import static util.Constants.UI.VolumeButton.*;

import java.awt.Graphics;
import java.awt.event.MouseEvent;


import Main.Game;


public class AudioOptions {

	private VolumeButtons  volumeButtons;
	private SoundButton musicButton, sfxButton;
	private Game game;
	
	public AudioOptions(Game game) {
		this.game = game;
		createSoundButtons();
		createVolumeButton();
	}
	
	private void createVolumeButton() {
		int vX = (int)(309 * Game.SCALE);
		int vY = (int)(278 * Game.SCALE);
		volumeButtons = new VolumeButtons(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
		
	}
	private void createSoundButtons() {
		int soundX = (int)(450*Game.SCALE);
		int musicY = (int)(140*Game.SCALE);
		int sfxY = (int)(186*Game.SCALE);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
		
	}
	public void update() {
		musicButton.update();
		sfxButton.update();
		
		volumeButtons.update();
		
	}
	public void draw(Graphics g) {
		musicButton.draw(g);
		sfxButton.draw(g);
		volumeButtons.draw(g);
	}public void mousePressed(MouseEvent e) {
		if (isIn(e, musicButton)) {
			musicButton.setMousePressed(true);
		}else if (isIn(e, sfxButton)) {
			sfxButton.setMousePressed(true);
		}else if (isIn(e, volumeButtons)) {
			volumeButtons.setMousePressed(true);
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {
		if (isIn(e, musicButton)) {
			if(musicButton.isMousePressed()) {
				musicButton.setMuted(!musicButton.isMuted()); 
				game.getAudioPlayer().toggleMusicMute();
			}
		}
		else if (isIn(e, sfxButton)) {
			if(sfxButton.isMousePressed()) {
				sfxButton.setMuted(!sfxButton.isMuted());
				game.getAudioPlayer().toggleSFXMute();
			}
		}
	
		
		
		musicButton.resetBooleans();
		sfxButton.resetBooleans();
		volumeButtons.resetBooleans();
		
	}
	
	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		volumeButtons.setMouseOver(false);
		
		if (isIn(e, musicButton)) {
			musicButton.setMouseOver(true);
		}else if (isIn(e, sfxButton)) {
			sfxButton.setMouseOver(true);
		}else if (isIn(e, volumeButtons)) {
			volumeButtons.setMouseOver(true);
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if(volumeButtons.isMousePressed()) {
			float valueBefore = volumeButtons.getFloatValue();
			volumeButtons.changeX(e.getX());
			float valueAfter = volumeButtons.getFloatValue();
			if(valueBefore != valueAfter)
				game.getAudioPlayer().setVolume(valueAfter);
		}
		
	}
	
	private Boolean isIn(MouseEvent e, PauseButtons b) {
		return b.getBounds().contains(e.getX(), e.getY());
		
	}
	
	
}
