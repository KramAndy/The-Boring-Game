package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import static util.Constants.ANIMATION_SPEED;
import Main.Game;

import static util.Constants.ObjectConstants.*;

public class GameObjects {
	
	protected int x, y, objType;
	protected Rectangle2D.Float hitBox;
	protected boolean active = true;
	protected int animationTick, animationIndex, animationSpeed = 15;
	protected int xDrawOffset, yDrawOffset;
	protected boolean doAnimation;
	
	public GameObjects(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}
	protected void updateAnimationTick() {
	    animationTick++;
	    if (animationTick >= ANIMATION_SPEED) {
	        animationTick = 0;
	        animationIndex++;
	        if (animationIndex >= GetSpriteAmount(objType)) {
	        	animationIndex = 0;
	         if(objType == CANNON_LEFT ||objType == CANNON_RIGHT) {
	        	doAnimation = false;
	        }
	            
	   }      
	    }  
	}
	public void drawHitBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.PINK);
		g.drawRect((int) hitBox.x - xLvlOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
	}

	
	public void reset() {
		animationTick = 0;
		animationIndex = 0;
		active = true;
		
		if(objType == CANNON_LEFT ||objType == CANNON_RIGHT)
			doAnimation = false;
		else 
			doAnimation = true;
		
		
		
	}
	protected void initHitBox( int width, int height) {
		hitBox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
	}
	public int getObjType() {
		return objType;
	}
	
	public Rectangle2D.Float getHitBox() {
		return hitBox;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setAnimation(boolean doAnimation) {
		this.doAnimation = doAnimation;
	}
	
	public int getxDrawOffset() {
		return xDrawOffset;
	}
	
	public int getyDrawOffset() {
		return yDrawOffset;
	}
	
	public int getAnimationIndex() {
		return animationIndex;
	}
	public int getAnimationTick() {
		return animationTick;
	}
		
}
