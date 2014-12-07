package viewmodel;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.ArrayList;

public class DiffHorizonGraph {
	static final int[] colorPos = {255,255,255, 239,243,255, 189,215,231, 107,174,214, 49,130,189, 8,81,156};
	static final int[] colorNeg = {255,255,255, 254,229,217, 252,174,145, 251,106,74, 222,45,38, 165,15,21};
	
	
	int gameIndex;
	
	Rectangle2D.Float backgroundRect = new Rectangle2D.Float();
	
	ArrayList<InsideRect> insideRectList = new ArrayList<InsideRect>();
	
	
	public DiffHorizonGraph (int gameIndex) {
		this.gameIndex = gameIndex;
	}
	
	public void setBackgroundRect (int x, int y, int width, int height) {
		backgroundRect.x = x;
		backgroundRect.y = y;
		backgroundRect.width = width;
		backgroundRect.height = height;
	}
	
	public void addInsideRectList (int scoreDiff, int x, int width) {
		boolean scorePositive = scoreDiff >= 0 ? true : false;
		scoreDiff = scorePositive ? scoreDiff : -scoreDiff;
		
		int colorLevel = scoreDiff / 5 + 1;
		int remaining = 5 - scoreDiff % 5;
		
		float heightUp = backgroundRect.height * remaining / 5;
		InsideRect upRect = new InsideRect(x, (int)backgroundRect.y, width, (int)heightUp);
		InsideRect downRect = new InsideRect(x, (int) (backgroundRect.y + heightUp), width, (int) (backgroundRect.height - heightUp));
		if (scorePositive) {
			upRect.setColor(colorPos[3 * (colorLevel - 1)], colorPos[3 * (colorLevel - 1) + 1], colorPos[3 * (colorLevel - 1) + 2]);
			downRect.setColor(colorPos[3 * colorLevel], colorPos[3 * colorLevel + 1], colorPos[3 * colorLevel + 2]);
		} else {
			upRect.setColor(colorNeg[3 * (colorLevel - 1)], colorNeg[3 * (colorLevel - 1) + 1], colorNeg[3 * (colorLevel - 1) + 2]);
			downRect.setColor(colorNeg[3 * colorLevel], colorNeg[3 * colorLevel + 1], colorNeg[3 * colorLevel + 2]);
		}
		insideRectList.add(upRect);
		insideRectList.add(downRect);
	}

	public void draw (SeasonCanvas canvas) {
		for (int i = 0; i < insideRectList.size(); i++) {
			insideRectList.get(i).draw(canvas);
		}
		canvas.pushStyle();
		canvas.stroke(0);
		canvas.noFill();
		canvas.rect(backgroundRect.x, backgroundRect.y, backgroundRect.width, backgroundRect.height);
		canvas.popStyle();
	}
	
	public class InsideRect {
		Rectangle2D.Float rect = new Rectangle.Float();
		int[] color = new int[3];
		
		public InsideRect (int x, int y, int width, int height) {
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
			
			canvas.fill(this.color[0], this.color[1], this.color[2]);
			canvas.noStroke();
			canvas.rect(rect.x, rect.y, rect.width, rect.height);
			canvas.popStyle();
		}
	}
}
