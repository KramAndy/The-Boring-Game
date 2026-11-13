package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Audio.AudioPlayer;
import Entities.Player;
import GameStates.Playing;
import Main.Game;
import levels.Level;
import util.LoadSave;
import static util.Constants.ObjectConstants.*;
import static util.HelperMethods.CannonCanSeePlayer;
import static util.HelperMethods.IsProjectileHittingLevel;
import static util.Constants.Projectiles.*;


public class ObjectManager {
	
	
	private Playing playing;
	private BufferedImage[][] potionImgs;
	private BufferedImage spikeImg;
	private BufferedImage[] cannonImg;
	private BufferedImage cannonBallImg;
	private ArrayList<Potion> potions;
	private ArrayList<Spike> spikes;
	private ArrayList<Cannon> cannons;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	
	
	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadimgs();
	
	}
	public void checkSpikeContact(Player p) {
		for(Spike s : spikes)
			if(s.getHitBox().intersects(p.getHitBox())) {
					p.kill();
			}
				
				
	}
	public void checkObjectContact(Rectangle2D.Float hitBox) {
		for(Potion p : potions)
			if(p.isActive()) {
				if(hitBox.intersects(p.getHitBox())) {
					playing.getGame().getAudioPlayer().playSFX(AudioPlayer.GOT_HEAL);
					p.setActive(false);
					applyEffect(p);
				}
			}
	}
	public void applyEffect(Potion p) {
		if(p.getObjType() == POTION)
			playing.getPlayer().changeLives(POTION);
		
	}
	public void loadObjects(Level newLevel) {
			potions = new ArrayList<>(newLevel.getPotion());	
			spikes = newLevel.getSpike();
			cannons = newLevel.getCannon();
			projectiles.clear();
		}

	private void loadimgs() {
		BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_IMAGE);
		potionImgs = new BufferedImage[2][7];
		
		for(int j = 0; j < potionImgs.length; j++) {
			for(int i = 0; i < potionImgs[j].length; i++)
				potionImgs[j][i] = potionSprite.getSubimage(12*i, 16*j, 12, 16);
		}
		spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);
		
		cannonImg = new BufferedImage[7];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CANNON_ATLAS);
	
		
		for(int i = 0; i < cannonImg.length; i++) 
			cannonImg[i] = temp.getSubimage(i * 40, 0, 40, 26);
		
		cannonBallImg = LoadSave.GetSpriteAtlas(LoadSave.CANNON_BALL);
		
		
		
	}
	
	public void update(int[][] levelData, Player player) {
		for(Potion p : potions) 
			if(p.isActive()) 
				p.update();
		
			
			updateCannons(levelData, player);
			updateProjectiles(levelData, player);
		
	}
	
	private void updateProjectiles(int[][] levelData, Player player) {
		for(Projectile p : projectiles) 
			if(p.isActive()) {
				p.updatePosition();
			if(p.getHitBox().intersects(player.getHitBox())) {
				player.changeLives(-1);
				p.setActive(false);
			}
			else if(IsProjectileHittingLevel( p, levelData)) 
				p.setActive(false);
			}
		

	}
	private boolean isInRange(Cannon c, Player player) {
		int absValue = (int) Math.abs(player.getHitBox().x - c.getHitBox().x);
		return absValue <= Game.TILES_SIZE * 7;
		
	}
	private boolean isPlayerInFront(Cannon c, Player player) {
		if(c.getObjType() == CANNON_LEFT) {
			if(c.getHitBox().x > player.getHitBoxX())
				return true;
			
		}else if(c.getHitBox().x < player.getHitBoxX())
				return true;
			return false;
	}
	
	
	
	private void updateCannons(int[][] levelData, Player player) {
		for(Cannon c : cannons) {
			
			if(!c.doAnimation)
				if(c.getTileY() == player.getTileY()) 
					if(isInRange(c, player))
						if(isPlayerInFront(c, player))
							if(CannonCanSeePlayer(levelData, player.getHitBox(),c.getHitBox(), c.getTileY())) 
								c.setAnimation(true);			
			
			c.update();
			
			if(c.getAnimationIndex() == 4 && c.getAnimationTick() == 0) 
				shootCannon(c);
			
		}
	}
	
	
	
	private void shootCannon(Cannon c) {
		int dir = 1;
		if(c.objType == CANNON_LEFT)
			dir = -1;
		
		projectiles.add(new Projectile((int)(c.getHitBox().x), (int)(c.getHitBox().y), dir));
		
	}
	public void draw(Graphics g, int xLevelOffset) {
		drawPotions(g,xLevelOffset);
		drawTraps(g, xLevelOffset);
		drawCannons(g, xLevelOffset);
		drawProjectiles(g, xLevelOffset);
		
	}
	

	private void drawProjectiles(Graphics g, int xLevelOffset) {
		for(Projectile p : projectiles)
			if(p.isActive())
			g.drawImage(cannonBallImg, (int) (p.getHitBox().x - xLevelOffset),
					(int) (p.getHitBox().y),
					CANNONBALL_SIZE, CANNONBALL_SIZE, null);
	}
	private void drawCannons(Graphics g, int xLevelOffset) {
		for(Cannon c : cannons) {
			int x = (int)(c.getHitBox().x - xLevelOffset);
			int width = CANNON_WIDTH;
			if (c.objType == CANNON_RIGHT) {
				x += width;
				width *= -1;
			}
			g.drawImage(cannonImg[c.getAnimationIndex()], x, (int) (c.getHitBox().y), width, CANNON_HEIGHT, null);
		}
	}
	private void drawTraps(Graphics g, int xLevelOffset) {
		for(Spike s : spikes)
			g.drawImage(spikeImg, (int) (s.getHitBox().x - xLevelOffset),
					(int) (s.getHitBox().y - s.getyDrawOffset()),
					SPIKE_WIDTH, SPIKE_HEIGHT, null);

		
	}
	private void drawPotions(Graphics g, int xLevelOffset) {
		
		for(Potion p : potions) {
			int type = 1;
			if(p.isActive()) {
					g.drawImage(potionImgs[type][p.getAnimationIndex()], 
							(int)(p.getHitBox().x - p.getxDrawOffset() - xLevelOffset), 
							(int)(p.getHitBox().y - p.getyDrawOffset()), 
							POTION_WIDTH, 
							POTION_HEIGHT, null);
			}
				
		}
	}
	public void resetAllObjects() {
		loadObjects(playing.getLevelManager().getCurrentLevel());
		for(Potion p : potions)
			p.reset();
		for(Cannon c : cannons)
			c.reset();
	}

	
	
	
}
