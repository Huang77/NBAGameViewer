package viewmodel;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.ArrayList;

import datamodel_new.Database;
import datamodel_new.Event;
import datamodel_new.MadeScoreEvent;
import processing.core.PApplet;

public class DiffHorizonGraph {
	static final int[] colorPos = {255,255,255, 239,243,255, 189,215,231, 107,174,214, 49,130,189, 8,81,156};
	static final int[] colorNeg = {255,255,255, 254,229,217, 252,174,145, 251,106,74, 222,45,38, 165,15,21};
	
	boolean lineMode = true;
	
	int gameIndex;
	String leftTeamName, rightTeamName;
	Database database;
	int maxQuarter;
	
	
	Rectangle2D.Float backgroundRect = new Rectangle2D.Float();
	
	ArrayList<InsideRect> insideRectList = new ArrayList<InsideRect>();
	
	
	public DiffHorizonGraph (int gameIndex, Database database, int maxQuarter) {
		this.gameIndex = gameIndex;
		this.database = database;
		leftTeamName = database.teamsMap.get(database.gameMap.get(gameIndex).leftTeamIndex).name;
		rightTeamName = database.teamsMap.get(database.gameMap.get(gameIndex).rightTeamIndex).name;
		this.maxQuarter = maxQuarter;
	}
	
	public void setBackgroundRect (int x, int y, int width, int height) {
		backgroundRect.x = x;
		backgroundRect.y = y;
		backgroundRect.width = width;
		backgroundRect.height = height;
	}
	public void setup (int x, int y, int width, int height) {
		setBackgroundRect(x, y, width, height);
		ArrayList<Event> eventList = database.gameStatDataList.get(gameIndex).getEventList();
		int i = 0;
		int startX = 0, endX;
		int scoreDiff = 0;
		int numOfEvent = eventList.size();
		for (; i < numOfEvent - 1; i++) {
			if (eventList.get(i) instanceof MadeScoreEvent) {
				endX = SeasonCanvas.translateTimeIndexToXPos(eventList.get(i).getTimeIndex(), this.maxQuarter, x, x + width);
				addInsideRectList_LineMode(scoreDiff, startX, endX - startX);
				startX = endX;
				scoreDiff = eventList.get(i).getScoreDiff();
			}
		}
		endX = x + width;
		addInsideRectList_LineMode(scoreDiff, startX, endX - startX);
	}
	
	public void addInsideRectList_HorizonMode (int scoreDiff, int x, int width) {
		lineMode = false;
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
	
	public void addInsideRectList_LineMode (int scoreDiff, int x, int width) {
		lineMode = true;
		int baseLine = (int) (backgroundRect.y + backgroundRect.height / 2);
		float barHeight = backgroundRect.height / 2;
		boolean scorePositive = scoreDiff >= 0 ? true : false;
		scoreDiff = scorePositive ? scoreDiff : -scoreDiff;
		
		//float rectHeight = scoreDiff >= 30? barHeight : barHeight * scoreDiff / 30.0f;
		float rectHeight = PApplet.map(scoreDiff, 0, database.gameMap.get(gameIndex).getMaxScoreDiff() + 3, 0, barHeight);
		float rectY = (int) (scorePositive ? baseLine - rectHeight : baseLine);
		InsideRect rect = new InsideRect(x, rectY, width, rectHeight);
		if (scorePositive) {
			rect.setColor(colorPos[3 * 5], colorPos[3 * 5 + 1], colorPos[3 * 5 + 2]);
		} else {
			rect.setColor(colorNeg[3 * 5], colorNeg[3 * 5 + 1], colorNeg[3 * 5 + 2]);
		}
		insideRectList.add(rect);
		
	}

	public void draw (SingleGameCanvas canvas) {
		for (int i = 0; i < insideRectList.size(); i++) {
			insideRectList.get(i).draw(canvas);
		}
		canvas.pushStyle();
		canvas.strokeWeight(1);
		canvas.stroke(0);
		canvas.noFill();
		//canvas.rect(backgroundRect.x, backgroundRect.y, backgroundRect.width, backgroundRect.height);
		if (lineMode) {
			canvas.line(backgroundRect.x, backgroundRect.y + backgroundRect.height / 2, backgroundRect.x + backgroundRect.width, backgroundRect.y + backgroundRect.height / 2);
		}
		int tempX;
		canvas.strokeWeight(3);
		canvas.stroke(240);
		for (int i = 1; i <= maxQuarter; i++) {
			if (i <= 4) {
				tempX = SeasonCanvas.translateTimeIndexToXPos(i * 720, maxQuarter, (int)backgroundRect.x, (int)(backgroundRect.x + backgroundRect.width));
			} else {
				tempX = SeasonCanvas.translateTimeIndexToXPos(2880 + (i - 4) * 300, maxQuarter, (int)backgroundRect.x, (int)(backgroundRect.x + backgroundRect.width));
			}
			canvas.line(tempX, backgroundRect.y, tempX, backgroundRect.y + backgroundRect.height);
			//canvas.line(tempX, canvas.leftTopY, tempX, canvas.leftTopY + canvas.height);
		}
		
		canvas.popStyle();
	}
	
	public class InsideRect {
		Rectangle2D.Float rect = new Rectangle.Float();
		int[] color = new int[3];
		
		public InsideRect (float x, float y, float width, float height) {
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
		
		public void draw (SingleGameCanvas canvas) {

			canvas.pushStyle();
			canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
			canvas.fill(80);
			canvas.textSize(20);
			canvas.text(leftTeamName, backgroundRect.x - 10, backgroundRect.y + backgroundRect.height / 2 - 20);
			canvas.text(rightTeamName, backgroundRect.x - 10, backgroundRect.y + backgroundRect.height / 2 + 10);
			
			canvas.fill(this.color[0], this.color[1], this.color[2]);
			canvas.noStroke();
			canvas.rect(rect.x, rect.y, rect.width, rect.height);
			canvas.popStyle();
		}
		
	}
}
