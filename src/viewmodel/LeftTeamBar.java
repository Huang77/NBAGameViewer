package viewmodel;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import processing.core.PApplet;

public class LeftTeamBar {
	Rectangle2D.Float rectBackground = new Rectangle2D.Float();
	Rectangle2D.Float rectFront = new Rectangle2D.Float();
	
	int strokeW = 1;
	int[] colorBackground = {222,235,247};
	int[] colorFront = {252,146,114};
	
	int teamIndex;
	
	public LeftTeamBar (int teamIndex) {
		this.teamIndex = teamIndex;
	}
	public void setBackgroundRectSize (int x, int y, int width, int height) {
		rectBackground.x = x;
		rectBackground.y = y;
		rectBackground.width = width;
		rectBackground.height = height;
	}
	public void setFrontRectSize (int x, int y, int width, int height) {
		rectFront.x = x;
		rectFront.y = y;
		rectFront.width = width;
		rectFront.height = height;
	}
	public void setBackgroundColor (int[] color) {
		colorBackground[0] = color[0];
		colorBackground[1] = color[1];
		colorBackground[2] = color[2];
	}
	public void setFrontColor (int[] color) {
		colorFront[0] = color[0];
		colorFront[1] = color[1];
		colorFront[2] = color[2];
	}
	
	public void draw (SeasonCanvas canvas) {
		canvas.pushStyle();
		canvas.strokeWeight(strokeW);
		canvas.fill(colorBackground[0], colorBackground[1], colorBackground[2]);
		canvas.rect(rectBackground.x, rectBackground.y, rectBackground.width, rectBackground.height);
		canvas.fill(colorFront[0], colorFront[1], colorFront[2]);
		canvas.rect(rectFront.x, rectFront.y, rectFront.width, rectFront.height);

		// draw team names
    	canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
    	canvas.fill(0);
        if (this.teamIndex == canvas.leftHoverTextIndex) {
        	canvas.fill(250,0,0);
        }
    	canvas.text(canvas.database.teams[teamIndex].name, rectBackground.x + rectBackground.width - 5, rectBackground.y + rectBackground.height / 2);
		canvas.popStyle();
	}
}
