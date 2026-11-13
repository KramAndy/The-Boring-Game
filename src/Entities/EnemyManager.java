package Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import GameStates.Playing;
import levels.Level;
import util.LoadSave;
import static util.Constants.EnemyConstants.*;


public class EnemyManager {

	@SuppressWarnings("unused")
	private Playing playing;
	private BufferedImage[][] pirateArr;
	private ArrayList<Pirate> pirates = new ArrayList <>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}
	public void loadEnemies(Level level) {
		pirates = level.getPirate();
	}
	public void update(int[][] levelData, Player player) {
		for(Pirate p : pirates)
			p.update(levelData, player);
	}
	public void draw(Graphics g, int xLevelOffset) {
		drawPirate(g, xLevelOffset);
		
		
	}

	private void drawPirate(Graphics g, int xLevelOffset) {
		for(Pirate p : pirates) {
		g.drawImage(pirateArr[p.getEnemyState()][p.getAnimationIndex()],
				(int)(p.getHitBox().x - xLevelOffset - PIRATE_DRAW_OFFSET_X + p.flipX()), 
				(int)(p.getHitBox().y - PIRATE_DRAW_OFFSET_Y),
				PIRATE_WIDTH * p.flipW(), PIRATE_HEIGHT, null);
		
//		p.drawHitBox(g, xLevelOffset);
//		p.drawAttacKBox(g, xLevelOffset);
		}
	}
	private void loadEnemyImgs() {
		pirateArr = new BufferedImage[7][8];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.PIRATE_ATLAS);
		for (int j = 0; j < pirateArr.length; j++)
			for (int i = 0; i < pirateArr[j].length; i++)
				pirateArr[j][i] = temp.getSubimage(i * PIRATE_DEFAULT_WIDTH, j * PIRATE_DEFAULT_HEIGHT, PIRATE_DEFAULT_WIDTH, PIRATE_DEFAULT_HEIGHT);
	}
	public void resetAllEnemies() {
		for(Pirate p : pirates)
			p.resetEnemy();
		
	}
	
}
