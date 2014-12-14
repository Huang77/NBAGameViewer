package viewmodel;

import java.awt.geom.Ellipse2D;

import datamodel_new.Event;

public class ShootCircle {
	Event event;
	Ellipse2D.Float circle = new Ellipse2D.Float();
	int point;
	boolean made;
	static final int[] radius = {6, 10, 14};
	static final int[] color = {49,163,84, 222,45,38};
	
	boolean selected = false;
	
	
	public ShootCircle (int point, boolean made) {
		this.point = point;
		this.circle.width = radius[point - 1];
		this.made = made;
	}
	public void setEvent (Event event) {
		this.event = event;
	}
	public void setPosition (int x, int y) {
		if (circle == null) {
			circle = new Ellipse2D.Float();
		}
		circle.x = x;
		circle.y = y;
	}
	
	public void draw (SingleGameCanvas canvas) {
		canvas.pushStyle();
		canvas.noStroke();
		if (isMouseHover(canvas)) {
			canvas.stroke(100);
			canvas.strokeWeight(2);
		} 
		if (made) {
			canvas.fill(color[3], color[4], color[5]);
		} else {
			canvas.fill(color[0], color[1], color[2]);
		}
		canvas.ellipse(circle.x, circle.y, circle.width, circle.width);
		canvas.popStyle();
	}
	
	public boolean isMouseHover (SingleGameCanvas canvas) {
		if (circle.contains(canvas.mouseX, canvas.mouseY)) {
			System.out.println("in");
			return true;
		} else {
			return false;
		}
	}
}
