package viewmodel;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PApplet;

public class WinLostCell {
	public Rectangle2D.Float rect = new Rectangle2D.Float();
	ArrayList<LittleGameBar> gameBarList = new ArrayList<LittleGameBar>();
	
	public WinLostCell (int x, int y, int width, int height) {
		rect.x = x;
		rect.y = y;
		rect.width = width;
		rect.height = height;
	}
	
	public void setGameBar () {
		
	}
	

	
	public void draw (PApplet canvas) {
		canvas.pushMatrix();
		
		
		canvas.popMatrix();
	}
	
	
	
}
