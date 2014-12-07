package viewmodel;

import processing.core.PApplet;

public class LittleGameBar {
	int leftTopX, leftTopY;
	int width;
	int height;
	int[] color = new int[3];
	int strokeW = 1;;
	
	public LittleGameBar (int x, int y, int width, int height, int color[]) {
		leftTopX = x;
		leftTopY = y;
		this.width = width;
		this.height = height;
		this.setColor(color);
	}
	
	public void setColor (int[] color) {
		this.color[0] = color[0];
		this.color[1] = color[1];
		this.color[2] = color[2];
	}
	
	public void draw (PApplet canvas) {
		canvas.pushStyle();

		canvas.stroke(150, 150, 150);
		canvas.strokeWeight(strokeW);
		//canvas.noStroke();
		canvas.fill(color[0], color[1], color[2]);
		canvas.rect(leftTopX, leftTopY, width, height);
		canvas.popStyle();
	}
}
