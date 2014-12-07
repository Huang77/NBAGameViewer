package viewmodel;

import java.awt.geom.Ellipse2D;

public class ShootCircle {
	Ellipse2D.Float circle = new Ellipse2D.Float();
	int point;
	boolean made;
	static final int[] radius = {6, 10, 14};
	static final int[] color = {49,163,84, 222,45,38};
	
	public ShootCircle (int point, boolean made) {
		this.point = point;
		this.circle.width = radius[point - 1];
		this.made = made;
	}
	public void setPosition (int x, int y) {
		if (circle == null) {
			circle = new Ellipse2D.Float();
		}
		circle.x = x;
		circle.y = y;
	}
	
	public void draw (SeasonCanvas canvas) {
		canvas.pushStyle();
		canvas.noStroke();
		if (made) {
			canvas.fill(color[3], color[4], color[5]);
		} else {
			canvas.fill(color[0], color[1], color[2]);
		}
		canvas.ellipse(circle.x, circle.y, circle.width, circle.width);
		canvas.popStyle();
	}
	
	
}
