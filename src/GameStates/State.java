package GameStates;

import java.awt.event.MouseEvent;

import Audio.AudioPlayer;
import Main.Game;
import UI.MenuButton;

public class State {
	
	protected Game game;
	
	public State(Game game) {
		this.game = game;
	}
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(),e.getY());
	}
	
	public Game getGame() {
		return game;
	}
	public void setGamestate(GameState state) {
		switch(state) {
		case MENU -> game.getAudioPlayer().playMusic(AudioPlayer.MENU);
		case PLAYING -> game.getAudioPlayer().setLevelMusic(game.getPlaying().getLevelManager().getLevelIndex());
		case OPTIONS -> game.getAudioPlayer().playMusic(AudioPlayer.MENU);
		default -> throw new IllegalArgumentException("Unexpected value: " + state);
		}
		GameState.state = state;
	}
}
