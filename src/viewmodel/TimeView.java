package viewmodel;


import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;
import teamsort.TeamSortType;
import datamodel.*;

public class TimeView {
	static int filterDiffScore = 0;
	static int filterStreakNum = 0;
	static int hoverTeamIndex = -1;
	static boolean displayWin = true;
	static boolean displayLost = true;
	//static int displayAttrNum = 5;
	
	int leftTopX, leftTopY;
	int circleMarginX = 0, circleMarginY = 0;
	int width, height;
	
	int attrBarStartX = 800;
	int attrGap = 155;
	
	Database database;
	int[] verticalOrder;  // vertical order of the teams 
	int teamGap = 28;
	
	HashMap<Integer, WinLostLine> teamCircleMap = new HashMap<Integer, WinLostLine>();
		
	HashMap<String, HashMap<Integer, AttributeRect>> attrMap = new HashMap<String, HashMap<Integer, AttributeRect>>();
	ArrayList<SortTriangle[]> triangleList = new ArrayList<SortTriangle[]>();
	public ArrayList<String> selectedAttr = new ArrayList<String>();
	
	SortTriangle winTri, lostTri;
	WinLostLegend winLostLegend;
	
	Handle diffHandle;
	Handle streakHandle;
	
	public TimeView (Database database) {
		this.database = database;
		verticalOrder = new int[Database.teamNum];
		selectedAttr.add("Points");
		selectedAttr.add("Rebound");
		selectedAttr.add("Assist");
		selectedAttr.add("Turnover");
		selectedAttr.add("Block");
	}
	
	public void setPosition (int x, int y, int width, int height) {
		this.leftTopX = x;
		this.leftTopY = y;
		this.width = width;
		this.height = height;
	}
	
	public void setAllTeamWinLostLine () {
		for (int teamIndex = 0; teamIndex < Database.teamNum; teamIndex++) {
			WinLostLine line = new WinLostLine(teamIndex, database);
			line.setPosition(leftTopX, leftTopY + this.verticalOrder[teamIndex] * (teamGap));
			teamCircleMap.put(teamIndex, line);
		}
	}
	
	public void setAttrbuteRect () {
		for (int i = 0; i < Database.attrNames.length; i++) {
			HashMap<Integer, AttributeRect> tempAttrMap = new HashMap<Integer, AttributeRect>();
			for (int teamIndex = 0; teamIndex < Database.teamNum; teamIndex++) {
				AttributeRect rect = new AttributeRect(database, teamIndex, Database.attrNames[i], database.teamsMap.get(teamIndex).getAttrValueByType(Database.attrNames[i]));
				tempAttrMap.put(teamIndex, rect);
			}
			attrMap.put(Database.attrNames[i], tempAttrMap);
		}
	}
	
	public void setHandles () {
		int startX = 250, startY = 30;
		int maxWidth = 150;
		int size = 15;
		int startL = 0;
		
		int gap = 130;
		int streakStartX = startX + maxWidth + gap;
		
		diffHandle = new Handle(startX, startY, startL, size, maxWidth);
		diffHandle.setMinMaxValue(database.minScoreDiff, database.maxScoreDiff);
		diffHandle.setName("Score:");
		streakHandle = new Handle(streakStartX, startY, startL, size, maxWidth);
		streakHandle.setMinMaxValue(1, 26);
		streakHandle.setName("Streak:");
	}
	
	public void setup () {

		
		this.verticalOrder = TeamSortType.getSortResult("Overall", true);
		setAllTeamWinLostLine();
		setAttrbuteRect();
		resetAttrDisplay();
		setWinLostLegendAndTriangle();
		setHandles();
	}
	
	public void resetAttrDisplay () {
		for (int i = 0; i < selectedAttr.size(); i++) {
			HashMap<Integer, AttributeRect> tempAttrMap = attrMap.get(selectedAttr.get(i));
			for (int teamIndex = 0; teamIndex < Database.teamNum; teamIndex++) {
				tempAttrMap.get(teamIndex).setPosition(leftTopX + attrBarStartX + (i + 1) * attrGap, leftTopY + this.verticalOrder[teamIndex] * (teamGap) + 13);
			}
		}
		setTriangle();
	}
	
	public void setTriangle () {
		triangleList.clear();
		System.gc();
		for (int i = 0; i < selectedAttr.size(); i++) {
			SortTriangle[] tempTriangle = new SortTriangle[2];
			tempTriangle[0] = new SortTriangle(selectedAttr.get(i), false, leftTopX + attrBarStartX + (i + 1) * attrGap - 15, leftTopY + 4);
			tempTriangle[1] = new SortTriangle(selectedAttr.get(i), true, leftTopX + attrBarStartX + (i + 1) * attrGap + 15, leftTopY + 4);
			triangleList.add(tempTriangle);
		}
	}
	
	public void setWinLostLegendAndTriangle () {
		int middleX = 80;
		int middleY = 50;
		int barWidth = 20;
		winTri = new SortTriangle("WinRatio", false, middleX - barWidth / 2, middleY - 2);
		lostTri = new SortTriangle("LostRatio", false, middleX + barWidth / 2, middleY - 2);
		
		winLostLegend = new WinLostLegend(middleX, middleY - 25);
	}
	
	public void drawTriangle (SeasonCanvas canvas) {
		for (int i = 0; i < triangleList.size(); i++) {
			SortTriangle[] tempTriangle = triangleList.get(i);
			tempTriangle[0].draw(canvas);
			tempTriangle[1].draw(canvas);
		}
		winTri.draw(canvas);
		lostTri.draw(canvas);
	}
	
	
	public void drawAttrText (SeasonCanvas canvas) {
		canvas.pushStyle();
		canvas.textAlign(PApplet.CENTER, PApplet.BOTTOM);
		canvas.fill(80);
		canvas.textSize(15);
		
		for (int i = 0; i < selectedAttr.size(); i++) {
			canvas.text(selectedAttr.get(i), attrMap.get(selectedAttr.get(i)).get(0).middleX, leftTopY);
		}
		
		canvas.popStyle();
	}
	
	public void drawAttrLegend (SeasonCanvas canvas) {
		int middleX = this.width - 120;
		int middleY = 5;
		int barWidth = 20, barHeight = 15;
		canvas.pushStyle();

		canvas.noStroke();
		canvas.fill(239,138,98,150);
		canvas.rect(middleX - barWidth, middleY, barWidth, barHeight);
		canvas.fill(103,169,207,150);
		canvas.rect(middleX, middleY, barWidth, barHeight);
		
		
		canvas.fill(80);
		canvas.textSize(15);
		canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
		canvas.text("Team", middleX - barWidth - 2, middleY + barHeight / 2);
		canvas.textAlign(PApplet.LEFT, PApplet.CENTER);
		canvas.text("Opponent", middleX  + barWidth  + 2, middleY + barHeight / 2);
		
		canvas.stroke(180);
		canvas.strokeWeight(1);
		canvas.line(middleX, middleY - 2, middleX, middleY  + barHeight + 2);
		
		canvas.popStyle();
	}
	
	public void drawWinLostLegend (SeasonCanvas canvas) {
		winLostLegend.draw(canvas);
	}
	

	
/*	public static int convertTimeToIndex (String date) {
		String[] array = date.split(" ");
		int index = Integer.parseInt(array[2]);
		if (array[1].equals("Oct")) {
			index = index - 29;
		} else if (array[1].equals("Nov")) {
			index = 2 + index;
		} else if (array[1].equals("Dec")) {
			index = 2 + 30 + index;
		} else if (array[1].equals("Jan")) {
			index = 2 + 30 + 31 + index;
		} else if (array[1].equals("Feb")) {
			index = 2 + 30 + 31 + 31 + index;
		} else if (array[1].equals("Mar")) {
			index = 2 + 30 + 31 + 31 + 28 + index;
		} else if (array[1].equals("Apr")) {
			index = 2 + 30 + 31 + 31 + 28 + 31 + index;
		} else {
			System.out.println("Data Error: " + date);
		}
		return index;
	}*/
	
	public void draw (SeasonCanvas canvas) {
		canvas.smooth();
		diffHandle.update(canvas);
		diffHandle.display(canvas);
		filterDiffScore = diffHandle.getValue();
		
		streakHandle.update(canvas);
		streakHandle.display(canvas);
		filterStreakNum = streakHandle.getValue();
		
		canvas.pushStyle();
		
		
		
		for (int i = 0; i < Database.teamNum; i++) {
			teamCircleMap.get(i).draw(canvas);
			
			attrMap.get(selectedAttr.get(0)).get(i).draw(canvas);
			
			canvas.pushStyle();
			canvas.stroke(210);
			canvas.strokeWeight(1);
			canvas.line(teamCircleMap.get(i).endX + 2, teamCircleMap.get(i).endY, attrMap.get(selectedAttr.get(0)).get(i).getLeftX(canvas), 
					attrMap.get(selectedAttr.get(0)).get(i).getMiddleY());
			canvas.popStyle();
			
			int ii = 1;
			for (; ii < selectedAttr.size(); ii++) {
				canvas.pushStyle();
				canvas.stroke(210);
				canvas.strokeWeight(1);
				canvas.line(attrMap.get(selectedAttr.get(ii - 1)).get(i).getRightX(canvas), attrMap.get(selectedAttr.get(ii - 1)).get(i).getMiddleY(), 
						attrMap.get(selectedAttr.get(ii)).get(i).getLeftX(canvas), attrMap.get(selectedAttr.get(ii)).get(i).getMiddleY());
				canvas.popStyle();
				attrMap.get(selectedAttr.get(ii)).get(i).draw(canvas);
				
			}
		}
		
		// draw hover line
		if (hoverTeamIndex >= 0) {
			canvas.pushStyle();
			canvas.stroke(150);
			canvas.strokeWeight(2f);
			canvas.line(teamCircleMap.get(hoverTeamIndex).endX + 2, teamCircleMap.get(hoverTeamIndex).endY, attrMap.get(selectedAttr.get(0)).get(hoverTeamIndex).getLeftX(canvas), 
					attrMap.get(selectedAttr.get(0)).get(hoverTeamIndex).getMiddleY());
			canvas.popStyle();
			
			int ii = 1;
			for (; ii < selectedAttr.size(); ii++) {
				canvas.pushStyle();
				canvas.stroke(150);
				canvas.strokeWeight(2f);
				canvas.line(attrMap.get(selectedAttr.get(ii - 1)).get(hoverTeamIndex).getRightX(canvas), attrMap.get(selectedAttr.get(ii - 1)).get(hoverTeamIndex).getMiddleY(), 
						attrMap.get(selectedAttr.get(ii)).get(hoverTeamIndex).getLeftX(canvas), attrMap.get(selectedAttr.get(ii)).get(hoverTeamIndex).getMiddleY());
				canvas.popStyle();
			}
		}
		// end draw hover line
		
		drawAttrText(canvas);
		drawAttrLegend(canvas);
		drawTriangle(canvas);
		drawWinLostLegend(canvas);
		canvas.popStyle();
	}
	
	public void mouseMoved (SeasonCanvas canvas) {
		TimeView.hoverTeamIndex = -1;
		for (int i = 0; i < triangleList.size(); i++) {
			SortTriangle[] tempTriangle = triangleList.get(i);
			tempTriangle[0].mouseHover(canvas);
			tempTriangle[1].mouseHover(canvas);
		}
		mouseMovedOnAttributeRect(canvas);
		winTri.mouseHover(canvas);
		lostTri.mouseHover(canvas);
		winLostLegend.mouseHover(canvas);
	}
	
	public void mouseMovedOnAttributeRect (SeasonCanvas canvas) {
		for (int i = 0; i < Database.teamNum; i++) {
			teamCircleMap.get(i).mouseHover(canvas);
			for (int ii = 0; ii < selectedAttr.size(); ii++) {
				attrMap.get(selectedAttr.get(ii)).get(i).mouseHover(canvas);
			}
		}
	}
	
	public void mouseReleased (SeasonCanvas canvas) {
		diffHandle.releaseEvent();
		streakHandle.releaseEvent();
	}
	
	public void mouseClicked (SeasonCanvas canvas) {
		triangleClicked(canvas);
		winLostLegend.mouseClicked(canvas);
	}
	
	public void triangleClicked (SeasonCanvas canvas) {
		// hover on attribute triangle
		boolean oppo;
		for (int i = 0; i < triangleList.size(); i++) {
			SortTriangle[] tempTriangle = triangleList.get(i);
			if (tempTriangle[0].hover == true || tempTriangle[1].hover == true) {
				oppo = tempTriangle[0].hover == true ? false : true;
				this.verticalOrder = TeamSortType.getSortResult(tempTriangle[0].sortAttrString, oppo);
				HashMap<Integer, AttributeRect> toSortAttrRect = attrMap.get(tempTriangle[0].sortAttrString);
				for (int teamIndex = 0; teamIndex < Database.teamNum; teamIndex++) {
					toSortAttrRect.get(teamIndex).setYPosition(leftTopY + this.verticalOrder[teamIndex] * (teamGap) + 13);
				}
			} 
		}
		
		// hover on win lost triangle
		if (winTri.hover == true) {
			this.verticalOrder = TeamSortType.getSortResult(winTri.sortAttrString, false);
			for (int teamIndex = 0; teamIndex < Database.teamNum; teamIndex++) {
				teamCircleMap.get(teamIndex).setYPosition(leftTopY + this.verticalOrder[teamIndex] * (teamGap));
			}
		} else if (lostTri.hover == true) {
			this.verticalOrder = TeamSortType.getSortResult(lostTri.sortAttrString, false);
			for (int teamIndex = 0; teamIndex < Database.teamNum; teamIndex++) {
				teamCircleMap.get(teamIndex).setYPosition(leftTopY + this.verticalOrder[teamIndex] * (teamGap));
			}
		}
	}
	
}
