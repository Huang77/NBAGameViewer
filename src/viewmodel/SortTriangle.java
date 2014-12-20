package viewmodel;

import java.awt.geom.Rectangle2D;

public class SortTriangle {
	static final float sideWidth = 6;
	static final int[] color = {220,220,220};
	
	float xLeft, yLeft;
	float xRight, yRight;
	float xMiddle, yMiddle;
	Rectangle2D.Float backgroundRect = new Rectangle2D.Float();
	
	boolean hover = false;
	boolean oppo;
	String sortAttrString;
	
	public SortTriangle (String sortAttrString, boolean oppo, float centerX, float centerY) {
		this.oppo = oppo;
		this.sortAttrString = sortAttrString;
		xLeft = centerX - sideWidth / 2;
		yLeft = centerY - sideWidth / 2;
		xRight = centerX + sideWidth / 2;
		yRight = centerY - sideWidth / 2;
		xMiddle = centerX;
		yMiddle = centerY + sideWidth / 2 - 1;
		backgroundRect.x = xLeft;
		backgroundRect.y = yLeft;
		backgroundRect.width = sideWidth;
		backgroundRect.height = sideWidth;
	}
	
	public void draw (SeasonCanvas canvas) {
		canvas.pushStyle();
		canvas.fill(color[0], color[1], color[2]);
		canvas.stroke(180);
		if (hover) {
			canvas.stroke(50);
		}
		canvas.strokeWeight(1);
		canvas.triangle(xLeft, yLeft, xRight, yRight, xMiddle, yMiddle);
		canvas.popStyle();
	}
	
	public void mouseHover (SeasonCanvas canvas) {
		if (backgroundRect.contains(canvas.mouseX, canvas.mouseY)) {
			hover = true;
		} else {
			hover = false;
		}
	}
}
