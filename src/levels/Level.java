package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Entities.Pirate;
import Main.Game;
import objects.Cannon;

import objects.Potion;
import objects.Spike;
import util.HelperMethods;

import static util.HelperMethods.GetLevelData;
import static util.HelperMethods.GetPirates;
import static util.HelperMethods.GetPlayerSpawn;
public class Level {
	
	private BufferedImage img;
	private int[][] levelData;
	private ArrayList<Pirate> pirates;
	private ArrayList<Potion> potions;
	private ArrayList<Spike> spikes;
	private ArrayList<Cannon> cannons;
	private int leveltilesWide;
	private int maxTilesOffset;
	private int maxLevelOffset;
	private Point playerSpawn;
	
	
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		createPotions();
		createSpikes();
		createCannons();
		calcLevelOffsets();
		calcPlayerSpawn();
		
	}
	
	
	private void createCannons() {
		cannons = HelperMethods.GetCannon(img);
		
	}


	private void createSpikes() {
		spikes = HelperMethods.GetSpike(img);
		
	}

	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
		
		
	}


	private void calcLevelOffsets() {
		leveltilesWide = img.getWidth();
		maxTilesOffset = leveltilesWide - Game.TILES_IN_WIDTH;
		maxLevelOffset = Game.TILES_SIZE * maxTilesOffset;
	}
	

	private void createEnemies() {
		pirates = GetPirates(img);
		
	}
	private void createPotions() {
		 potions = HelperMethods.GetPotions(img);
	}

	private void createLevelData() {
		levelData = GetLevelData(img);
		
	}

	public int getSpriteIndex(int x, int y) {
		return levelData[y][x];
	}
	
	public int [][] getLevelData(){
		return levelData;
	}
	public int getLevelOffset() {
		return maxLevelOffset;
	}
	
	public ArrayList<Potion> getPotion(){
		return potions;
	}
	public ArrayList<Pirate> getPirate(){
		return pirates;
	}
	public Point GetLevelSpawn() {
		return playerSpawn;
	}
	public ArrayList<Spike> getSpike(){
		return spikes;
	}
	public ArrayList<Cannon> getCannon(){
		return cannons;
	}
	
}
