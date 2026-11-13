package Audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	
	public static int MENU = 0;
	public static int LEVEL_1 = 1;
	public static int LEVEL_2 = 2;
	
	public static int DEATH = 0;
	public static int JUMP = 1;
	public static int GAMEOVER = 2;
	public static int GOT_HEAL = 3;
	public static int LEVEL_COMPLETE = 4;
	public static int SPAWN = 5;
	
	private Clip[] bgm, sfx;
	private int currentBGMId, sfxID;
	private float volume = 0.8f;
	private boolean bgmMute, sfxMute;
	
	public AudioPlayer() {
		loadMusic();
		loadSfx();
		playMusic(MENU);
	}
	
	
	private void loadMusic() {
		String[] names = {"menu","level1","level2"};
		bgm = new Clip[names.length];
		for(int i = 0; i < bgm.length; i++)
			bgm[i] = getClip(names[i]);
		
	} 
	private void loadSfx() {
		String[] names = {"death","jump","game_over","pick_up_item","level_clear", "spawn"};
		sfx = new Clip[names.length];
		for(int i = 0; i < sfx.length; i++)
			sfx[i] = getClip(names[i]);
		
		updateSFXVolume();
	}
	private Clip getClip(String name) {
		URL url = getClass().getResource("/audios/" + name + ".wav");
		AudioInputStream audio;
		
		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

			e.printStackTrace();
		}

		return null;

	}
	public void setVolume(float volume) {
		this.volume = volume;
		updateSFXVolume();
		updateMusicVolume();
	}
	public void stopMusic() {
		if(bgm[currentBGMId].isActive())
			bgm[currentBGMId].stop();
	}
	public void stopSFX() {
		if(sfx[sfxID].isActive())
			sfx[sfxID].stop();
	}
	public void setLevelMusic(int levelIndex) {
		if(levelIndex % 2 == 0)
			playMusic(LEVEL_1);
		else
			playMusic(LEVEL_2);
	}
	public void levelCompleted() {
		stopMusic();
		playSFX(LEVEL_COMPLETE);
	}
	
	public void playSFX(int effect) {
		sfx[effect].setMicrosecondPosition(0);
		sfx[effect].start();
		
		sfxID = effect;
		
	}
	public void playMusic(int music) {
		stopMusic();

		currentBGMId = music;
		updateMusicVolume();
		bgm[currentBGMId].setMicrosecondPosition(0);
		bgm[currentBGMId].loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void toggleMusicMute() {
		this.bgmMute = !bgmMute;
		for(Clip c: bgm) {
			BooleanControl boolControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			boolControl.setValue(bgmMute);
		}
		if(!sfxMute)
			playSFX(JUMP);
	}
	

	public void toggleSFXMute() {
		this.sfxMute = !sfxMute;
		for(Clip c: sfx) {
			BooleanControl boolControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			boolControl.setValue(sfxMute);
		}
		if(!sfxMute)
			playSFX(JUMP);
	}
	
	
	private void updateMusicVolume() {
		
		FloatControl gainControl = (FloatControl) bgm[currentBGMId].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
	}
	private void updateSFXVolume() {
		for(Clip c : sfx) {
		FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
		}
	}
	
}
