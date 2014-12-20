package viewmodel;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import processing.core.PApplet;

public class WinLostLegend {
	Rectangle2D.Float winRect = new Rectangle2D.Float();
	Rectangle2D.Float lostRect = new Rectangle2D.Float();
	
	int middleX, middleY;
	int barWidth = 20, barHeight = 15;
	
	boolean leftHover = false;
	boolean rightHover = false;
	
	public WinLostLegend (int middleX, int middleY) {
		this.middleX = middleX;
		this.middleY = middleY;
		setRects();
	}
	
	public void setRects () {
		winRect.x = middleX - barWidth;
		winRect.y = middleY;
		winRect.width = barWidth;
		winRect.height = barHeight;
		
		lostRect.x = middleX;
		lostRect.y = middleY;
		lostRect.width = barWidth;
		lostRect.height = barHeight;	
	}
	
	public void draw (SeasonCanvas canvas) {
		canvas.pushStyle();
		
		canvas.noStroke();
		canvas.fill(252,146,114);
		if (!TimeView.displayWin) {
			canvas.fill(252,146,114,50);
		}
		canvas.rect(winRect.x, winRect.y, barWidth, barHeight);
		canvas.fill(173,221,142);
		if (!TimeView.displayLost) {
			canvas.fill(173,221,142,50);
		}
		canvas.rect(lostRect.x, lostRect.y, barWidth, barHeight);
		
		
		canvas.fill(80);
		canvas.textSize(15);
		canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
		canvas.text("Win", winRect.x - 2, middleY + barHeight / 2);
		canvas.textAlign(PApplet.LEFT, PApplet.CENTER);
		canvas.text("Lose", lostRect.x  + barWidth  + 2, middleY + barHeight / 2);
		
		canvas.stroke(180);
		canvas.strokeWeight(1);
		canvas.line(middleX, middleY - 2, middleX, middleY  + barHeight + 2);
		canvas.popStyle();
	}
	
	public void mouseHover (SeasonCanvas canvas) {
		if (winRect.contains(canvas.mouseX, canvas.mouseY)) {
			leftHover = true;
		} else {
			leftHover = false;
		}
		if (lostRect.contains(canvas.mouseX, canvas.mouseY)) {
			rightHover = true;
		} else {
			rightHover = false;
		}
	}
	public void mouseClicked (SeasonCanvas canvas) {
		if (leftHover == true) {
			TimeView.displayWin = !TimeView.displayWin;
		} else if (rightHover == true) {
			TimeView.displayLost = !TimeView.displayLost;
		}
	}
	
}
