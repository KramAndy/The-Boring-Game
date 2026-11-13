package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import Main.Game;



public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitBox;
	protected Rectangle2D.Float attackBox;
	protected int animationTick, animationIndex;
	protected int state;
	protected float airSpeed;
	protected boolean inAir;
	protected float walkSpeed;
	
	public Entity(float x, float y,  int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		
		
	}protected void drawAttacKBox(Graphics g, int xLevelOffset) {
		g.setColor(Color.red);
		g.drawRect((int)attackBox.x - xLevelOffset, (int)attackBox.y,(int) attackBox.width,(int) attackBox.height);
		
	}
	public void drawHitBox(Graphics g, int xLevelOffset) {
		// for debugging hitbox
		g.setColor(Color.red);
		g.drawRect((int)hitBox.x - xLevelOffset, (int)hitBox.y,(int) hitBox.width,(int) hitBox.height);
	}
	protected void initHitBox( int width, int height) {
		hitBox = new Rectangle2D.Float(x, y, (int)(width * Game.SCALE), (int)(height * Game.SCALE));
	}
	
	public Rectangle2D.Float getHitBox() {
		return hitBox;
	}
	public int getAnimationIndex() {
		return animationIndex;
	}

	public int getState() {
		return state;
	}

}
