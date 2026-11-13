package util;

import static util.Constants.EnemyConstants.PIRATE;
import static util.Constants.ObjectConstants.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Entities.Pirate;
import Main.Game;
import objects.Cannon;
import objects.Potion;
import objects.Projectile;
import objects.Spike;

public class HelperMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
	    if (!IsSolid(x, y, levelData))
	        if (!IsSolid(x + width, y + height, levelData))
	            if (!IsSolid(x + width, y, levelData))
	                if (!IsSolid(x, y + height, levelData))
	                    return true;
	    return false;
	}


	private static boolean IsSolid(float x, float y, int[][] levelData) {
	    int maxWidth = levelData[0].length * Game.TILES_SIZE;
	    if (x < 0 || x >= maxWidth)
	        return true;
	    if (y < 0 || y >= Game.GAME_HEIGHT)
	        return true;

	    float xIndex = x / Game.TILES_SIZE;
	    float yIndex = y / Game.TILES_SIZE;
	    
	    return IsTileSolid((int)(xIndex),(int) (yIndex), levelData);
	}
	public static boolean IsTileSolid(int xTile, int yTile, int[][] levelData) {
		int value = levelData[yTile][xTile];

		if (value >= 48 || value < 0 || value != 11)
			return true;
		return false;
	}
	public static boolean IsProjectileHittingLevel(Projectile p, int[][] levelData) {
		return IsSolid(p.getHitBox().x + p.getHitBox().width / 2, p.getHitBox().y + p.getHitBox().height / 2, levelData);
	
	}
	
	public static boolean CannonCanSeePlayer(int[][] lvlData, Rectangle2D.Float firsthitBox, Rectangle2D.Float secondhitBox, int yTile) {
		int firstXTile = (int) (firsthitBox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondhitBox.x / Game.TILES_SIZE);

		if (firstXTile > secondXTile)
			return isAllTilesClear(secondXTile, firstXTile, yTile, lvlData);
		else
			return isAllTilesClear(firstXTile, secondXTile, yTile, lvlData);
	}
	public static boolean isAllTilesClear(int xStart, int xEnd, int y, int[][] levelData) {
		for(int i = 0; i < xEnd - xStart; i++) 
			 if(IsTileSolid(xStart + i, y, levelData))
				 return false;
			 
			 return true;
	}
	public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] levelData) { 
		if (isAllTilesClear(xStart, xEnd, y,  levelData))
		for(int i = 0; i < xEnd - xStart; i++) {
			 if(!IsTileSolid(xStart + i, y + 1, levelData))
				 return false;
		}
		 return true;
		 	
	}


	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
		int currentTile = (int) (hitBox.x / Game.TILES_SIZE);
		if (xSpeed > 0) {
			// Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int) (Game.TILES_SIZE - hitBox.width);
			return tileXPos + xOffset - 1;
		} else
			// Left
			return currentTile * Game.TILES_SIZE;
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
		int currentTile = (int) (hitBox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int) (Game.TILES_SIZE - hitBox.height);
			return tileYPos + yOffset - 1;
		} else
			// Jumping
			return currentTile * Game.TILES_SIZE;

	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][] levelData) {
		if (!IsSolid(hitBox.x, hitBox.y + hitBox.height + 1, levelData))
			if (!IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, levelData))
				return false;

		return true;

	}
	public static boolean IsFloor(Rectangle2D.Float hitBox, float xSpeed, int[][] levelData) {
		if (xSpeed > 0)
			return IsSolid(hitBox.x + xSpeed + hitBox.width, hitBox.y + hitBox.height +1, levelData);
		else
			return IsSolid(hitBox.x + xSpeed, hitBox.y + hitBox.height +1, levelData);
	}
	
	
	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firsthitBox, Rectangle2D.Float secondhitBox, int yTile) {
		int firstXTile = (int) (firsthitBox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondhitBox.x / Game.TILES_SIZE);

		if (firstXTile > secondXTile)
			return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);

	}
	public static int [][] GetLevelData(BufferedImage img){	
		int [][] levelData = new int [img.getHeight()][img.getWidth()]; 
		for (int j = 0; j<img.getHeight(); j++) {
			for(int i = 0; i<img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if(value >= 48) {
					value = 0;
				}
				levelData[j][i] = color.getRed();
			}
		}
		return levelData;
	}
	public static ArrayList<Pirate> GetPirates(BufferedImage img){
		ArrayList<Pirate> list = new ArrayList<>();
		for (int j = 0; j<img.getHeight(); j++) 
			for(int i = 0; i<img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if(value == PIRATE) 
					list.add(new Pirate( i * Game.TILES_SIZE, j*Game.TILES_SIZE));
			}
		return list;
		
			
	}
	public static Point GetPlayerSpawn(BufferedImage img) {
		
		for (int j = 0; j<img.getHeight(); j++) 
			for(int i = 0; i<img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if(value == 100) 
					return new Point( i * Game.TILES_SIZE, j * Game.TILES_SIZE);
				
			}
		return new Point( 1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
	}
	public static ArrayList<Potion> GetPotions(BufferedImage img){
		ArrayList<Potion> list = new ArrayList<>();
		for (int j = 0; j<img.getHeight(); j++) 
			for(int i = 0; i<img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if(value == POTION) 
					list.add(new Potion( i * Game.TILES_SIZE, j*Game.TILES_SIZE, value));
			}
		return list;
		
			
	}


	public static ArrayList<Spike> GetSpike(BufferedImage img) {
		ArrayList<Spike> list = new ArrayList<>();
		for (int j = 0; j<img.getHeight(); j++) 
			for(int i = 0; i<img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if(value == SPIKE) 
					list.add(new Spike( i * Game.TILES_SIZE, j*Game.TILES_SIZE, SPIKE));
			}
		return list;
		
	}
	public static ArrayList<Cannon> GetCannon(BufferedImage img) {
		ArrayList<Cannon> list = new ArrayList<>();
		for (int j = 0; j<img.getHeight(); j++) 
			for(int i = 0; i<img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if(value == CANNON_LEFT ||value == CANNON_RIGHT) 
					list.add(new Cannon( i * Game.TILES_SIZE, j*Game.TILES_SIZE, value));
			}
		return list;
		
	}
	
	

}