package viewmodel;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PApplet;
import datamodel.Efficiency;
import datamodel.PlayerGameStat;

public class PlayerBar {
	static final int[] colorPos = {222,45,38};
	static final int[] colorZero = {189,189,189};
	static final int[] colorNeg = {49,130,189};
	static final int[] colorWhite = {255,255,255};

	PlayerGameStat pgs;
	Rectangle2D.Float background = new Rectangle2D.Float();
	ArrayList<InsideBar> rectList = new ArrayList<InsideBar>();
	boolean hover = false;
	
	public PlayerBar (PlayerGameStat pgs, int x, int y, int width, int height) {
		this.pgs = pgs;
		background.x = x;
		background.y = y;
		background.width = width;
		background.height = height;
	}
	
	public void setInsideBar () {
		Efficiency tempEff;
		float lastX = background.x;
		float width;
		int[] color;
		int value;
		for (int i = 0; i < pgs.getEffList().size(); i++) {
			tempEff = pgs.getEffList().get(i);
			value = (int) tempEff.getValue();
			width = Math.round(tempEff.getRatio() * background.width);
			if (value < 0) {
				color = colorNeg;
			} else if (value == 0) {
				color = colorZero;
			} else {
				if (value == 999) {
					color = colorWhite;
				} else  {
					color = colorPos;
				}
			}
			InsideBar curBar = new InsideBar(value, color, lastX, background.y, width, background.height);
			this.rectList.add(curBar);
			lastX += width;
		}
	}
	public void draw (SingleGameCanvas canvas) {
		hover = false;
		for (int i = 0; i < rectList.size(); i++) {
			rectList.get(i).draw(canvas);
			if (rectList.get(i).isMouseHover(canvas)) {
				hover = true;
			}
		}
		canvas.pushStyle();
		canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
		canvas.fill(30);
		canvas.textSize(15);
		if (hover) {
			canvas.fill(255, 0, 0);
		}
		canvas.text(pgs.getName(), background.x - 10, background.y + background.height / 2 - 3);
		canvas.popStyle();
	}
	
	public class InsideBar {
		int[] color = new int[3];
		Rectangle2D.Float rect = new Rectangle2D.Float();
		int value;
		
		public InsideBar (int value, int[] color, float lastX, float y, float width, float height) {
			this.color[0] = color[0];
			this.color[1] = color[1];
			this.color[2] = color[2];
			rect.x = lastX;
			rect.y = y;
			rect.width = width;
			rect.height = height;
		}
		public void draw (SingleGameCanvas canvas) {
			canvas.pushStyle();
			canvas.stroke(250);
			if (isMouseHover(canvas)) {
				canvas.stroke(0);
			}
			canvas.fill(color[0], color[1], color[2], 230);
			canvas.rect(rect.x, rect.y, rect.width, rect.height);
			canvas.popStyle();
		}
		
		public boolean isMouseHover (SingleGameCanvas canvas) {
			if (value == 999) {
				return false;
			}
			if (this.rect.contains(canvas.mouseX, canvas.mouseY)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
/*	public class InsideCircle {
		int[] color = new int[3];
		Ellipse2D.Float circle = new Ellipse2D.Float();
		String type;
		
		public InsideCircle (int type, float x)
	}
	*/
}
