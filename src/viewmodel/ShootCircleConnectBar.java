package viewmodel;

import java.awt.geom.Rectangle2D;

public class ShootCircleConnectBar {
	Rectangle2D.Float rect = new Rectangle2D.Float();
	int[] color = new int[3];
	
	
	public ShootCircleConnectBar (int x, int y, int width, int height) {
		rect.x = x;
		rect.y = y;
		rect.width = width;
		rect.height = height;
	}
	public void setColor (int[] color) {
		this.color[0] = color[0];
		this.color[1] = color[1];
		this.color[2] = color[2];
	}
	public void setColor (int r, int g, int b) {
		this.color[0] = r;
		this.color[1] = g;
		this.color[2] = b;
	}
	public void draw (SeasonCanvas canvas) {
		canvas.pushStyle();
		canvas.noStroke();
		canvas.fill(color[0], color[1], color[2]);
		canvas.rect(rect.x, rect.y, rect.width, rect.height);
		canvas.popStyle();
	}
	

}
