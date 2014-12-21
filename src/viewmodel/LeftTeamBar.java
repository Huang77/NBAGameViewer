package viewmodel;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import processing.core.PApplet;

public class LeftTeamBar {
	Rectangle2D.Float rectBackground = new Rectangle2D.Float();
	Rectangle2D.Float rectFront = new Rectangle2D.Float();
	
	int strokeW = 1;
	int[] colorBackground = {173,221,142};
	int[] colorFront = {252,146,114};
	
	int teamIndex;
	boolean hover = false;
	
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
	
	public void setNewPosition (int newX, int newY) {
		int transX = newX - (int) rectBackground.x;
		int transY = newY - (int) rectBackground.y;
		rectBackground.x = newX;
		rectBackground.y = newY;
		rectFront.x += transX;
		rectFront.y += transY;
	}
	
	public void drawWinRatioText (SeasonCanvas canvas) {
		canvas.pushStyle();

		// draw team names
    	canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
    	canvas.fill(0);
        int[] winRatio = canvas.database.teamsMap.get(teamIndex).getOverall();
        String text = winRatio[0] + "-" + winRatio[1];
        canvas.textSize(12);
        canvas.textAlign(PApplet.LEFT, PApplet.CENTER);
    	canvas.text(text, rectBackground.x + 1, rectBackground.y + rectBackground.height / 2);
		canvas.popStyle();
	}
	
	public void draw (SeasonCanvas canvas) {
		canvas.pushStyle();
		canvas.stroke(180, 180, 180);
		canvas.strokeWeight(strokeW);
		canvas.fill(colorBackground[0], colorBackground[1], colorBackground[2]);
		canvas.rect(rectBackground.x, rectBackground.y, rectBackground.width, rectBackground.height);
		canvas.fill(colorFront[0], colorFront[1], colorFront[2]);
		canvas.rect(rectFront.x, rectFront.y, rectFront.width, rectFront.height);

		// draw team names
    	canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
    	canvas.fill(0);
        if (this.teamIndex == canvas.oppoView.leftHoverTextIndex) {
        	canvas.fill(250,0,0);
        }
    	canvas.text(canvas.database.teamsMap.get(teamIndex).name, rectBackground.x + rectBackground.width - 5, rectBackground.y + rectBackground.height / 2);
    	drawWinRatioText(canvas);
		canvas.popStyle();
	}
	
	public void draw (SeasonCanvas canvas, boolean hover) {
		canvas.pushStyle();
		//canvas.stroke(180, 180, 180);
	    //canvas.strokeWeight(strokeW);
		canvas.noStroke();
		canvas.textSize(13);
		if (teamIndex == TimeView.hoverTeamIndex) {
			canvas.stroke(100);
			canvas.strokeWeight(1.5f);
			canvas.textSize(15);
		}
		
		canvas.fill(colorBackground[0], colorBackground[1], colorBackground[2], 220);
		canvas.rect(rectBackground.x, rectBackground.y, rectBackground.width, rectBackground.height);
		//canvas.stroke(180, 180, 180);
		//canvas.strokeWeight(strokeW);
		canvas.noStroke();
		canvas.fill(colorFront[0], colorFront[1], colorFront[2], 220);
		canvas.rect(rectFront.x + 1, rectFront.y + 1, rectFront.width - 1, rectFront.height - 1);

		// draw team names
    	canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
    	canvas.fill(0);
    	canvas.text(canvas.database.teamsMap.get(teamIndex).name, rectBackground.x + rectBackground.width - 5, rectBackground.y + rectBackground.height / 2);
    	drawWinRatioText(canvas);
    	canvas.popStyle();
	}
	public void mouseHover (SeasonCanvas canvas) {
		if (rectBackground.contains(canvas.mouseX, canvas.mouseY)) {
			hover = true;
			TimeView.hoverTeamIndex = teamIndex;
		} else {
			hover = false;
		}
	}
}
