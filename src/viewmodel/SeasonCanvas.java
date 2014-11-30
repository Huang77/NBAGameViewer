package viewmodel;

import java.util.ArrayList;

import datamodel_new.Team;
import datamodel_new.Database;
import datamodel_new.GameStatData;
import datamodel_new.WinLostCellData;
import processing.core.PApplet;
import processing.core.PFont;

public class SeasonCanvas extends PApplet {
	final int[] colorForWinMore = {252,146,114};
	final int[] colorForWinLess = {173,221,142};
	final int[] colorForWinEqual = {222,235,247};
	final int[] colorForWinBar = {99,99,99};
	final int[] colorForLoseBar = {240,240,240};
	final int[] whiteColor = {240,240,240};
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int width, height;
	int textSize = 12;
	PFont font = createFont("Sans Serif", textSize);
	
	// size of the winLostCell
	int leftTopX = 150, leftTopY = 50;
	int cellSize;
	int cellGap = 2;
	
	int teamBarLeftTopX = 20, teamBarLeftTopY = leftTopY;
	int leftTeamBarWidth = leftTopX - teamBarLeftTopX - 5;
	
	Database database;
	WinLostCell[] winLostCellList;
	LeftTeamBar[] leftTeamBarList;
	
	
	int leftHoverTextIndex, topHoverTextIndex;
	
	public SeasonCanvas() {
		
	}
	
	public SeasonCanvas (Database database, int width, int height) {
		this.database = database;
		this.width = width;
		this.height = height;
	}
	
	public void setupWinLostCell () {
		cellSize = (int) (height * 0.8 / database.teamNum - cellGap);
		this.winLostCellList = new WinLostCell[database.teamNum * database.teamNum];
		int tempX = leftTopX, tempY = leftTopY - cellSize - cellGap;
		
		WinLostCellData tempCellData;
		for (int i = 0; i < database.winLostCellList.size(); i++) {
			tempCellData = database.winLostCellList.get(i);
			
			if (i % 30 == 0) {
				tempX = leftTopX;
				tempY = tempY + cellSize + cellGap;
			} else {
				tempX = tempX + cellSize + cellGap;
			}
			winLostCellList[i] = new WinLostCell(tempX, tempY, cellSize, cellSize);
			if (tempCellData.isSelfTeam()) {  // the cell is a self cell
				winLostCellList[i].setColor(whiteColor);
			} else if (!tempCellData.hasPlayed()) { // the two teams have not played a game yet
				winLostCellList[i].setColor(whiteColor);
			} else {  // the two games have player at least one game, and here will set the game bar 
				int winCompare = tempCellData.winCompare();
				switch (winCompare) {
					case 1 : winLostCellList[i].setColor(colorForWinMore); break;
					case 0 : winLostCellList[i].setColor(colorForWinEqual); break;
					case -1: winLostCellList[i].setColor(colorForWinLess); break;
				}
				ArrayList<Integer> gameIndex = tempCellData.getGameIndexes();
				int gameCount = gameIndex.size();
				int scoreDiff;
				int x = tempX + 2, y = tempY + 2;
				int barGap = 2;
				int barHeight = (cellSize - 2 - 3 * barGap) / 4;
				int minWidth = 1;
				int maxWidth = cellSize - 4;
				int barWidth;
				int[] barColor;
				GameStatData tempData;
				for (int j = 0; j < gameCount; j++) {
					tempData = database.gameStatDataList.get(gameIndex.get(j));
					scoreDiff = Math.abs(tempData.leftScore - tempData.rightScore);
					barColor = tempData.getWinTeamIndex() == tempCellData.getLeftTeamIndex() ? this.colorForWinBar : this.colorForLoseBar;
					barWidth = (int) map(scoreDiff, database.minScoreDiff, database.maxScoreDiff, minWidth, maxWidth);
					winLostCellList[i].addLittleGameBar(x, y + j * (barHeight + barGap), barWidth, barHeight, barColor);
				}
			}
		}
	}
	
	public void setupLeftTeamBar () {
		leftTeamBarList = new LeftTeamBar[database.teamNum];
		int frontWidth;
		Team tempTeam;
		int[] tempWinLost;
		for (int i = 0; i < database.teamNum; i++) {
			tempTeam = database.teams[i];
			tempWinLost = tempTeam.getOverall();
			leftTeamBarList[i] = new LeftTeamBar(tempTeam.index);
			leftTeamBarList[i].setBackgroundRectSize(teamBarLeftTopX, teamBarLeftTopY + i * (cellSize + cellGap), leftTeamBarWidth, cellSize);
			frontWidth = (int) map(tempWinLost[0], 0, tempWinLost[0] + tempWinLost[1], 0, leftTeamBarWidth);
			leftTeamBarList[i].setFrontRectSize(teamBarLeftTopX, teamBarLeftTopY + i * (cellSize + cellGap), frontWidth, cellSize);
		}
	}

    @Override
    public void setup () {
    	size(this.width, this.height);
    	textFont(font);
    	
    	setupWinLostCell();
    	setupLeftTeamBar();
    
    }
	
    @Override
	public void draw () {
    	background(255);
    	smooth();

    	drawAllWinLostCells();
    	drawLeftTeamBars();
    	//drawLeftTeamNames();
    	drawTopTeamNames();

    }
    
    public void drawAllWinLostCells () {
    	for (int i = 0; i < 900; i++) {
    		winLostCellList[i].draw(this);
    	}
    }
    
    public void drawLeftTeamBars () {
    	for (int i = 0; i < leftTeamBarList.length; i++) {
    		leftTeamBarList[i].draw(this);
    	}
    }
    
    public void drawLeftTeamNames () {
    	int x = leftTopX - 5;
    	int y = leftTopY;
    	this.pushStyle();
    	
    	this.textAlign(PApplet.RIGHT, PApplet.TOP);
    	for (int i = 0; i < database.teamNum; i++) {
    		this.fill(0);
    		if (i == leftHoverTextIndex) {
    			this.fill(250,0,0);
    		}
    		this.text(database.teams[i].name, x, y + i * (cellSize + cellGap));
    	}
    	this.popStyle();
    }
    
    public void drawTopTeamNames () {
    	int x = leftTopX + cellSize / 2;
    	int y = leftTopY - 2;
    	//this.rotate(-PApplet.QUARTER_PI);
    	this.pushStyle();
    	this.textAlign(PApplet.LEFT);
    	for (int i = 0; i < database.teamNum; i++) {
        	this.fill(0);
        	if (i == topHoverTextIndex) {
        		this.fill(250,0,0);
        	}
    		this.pushMatrix();
    		this.translate(x + i * (cellSize + cellGap), y);
    		this.rotate(-PApplet.QUARTER_PI);
    		this.text(database.teams[i].shortName, 0, 0);
    		this.popMatrix();
    	}
    	this.popStyle();
    }
    
    public void mouseMoved () {
    	if (mouseX > leftTopX && mouseY > leftTopY
    			&& mouseX < leftTopX + database.teamNum * (cellSize + cellGap)
    			&& mouseY < leftTopY + database.teamNum * (cellSize + cellGap)) {
    		topHoverTextIndex = (mouseX - leftTopX) / (cellSize + cellGap);
    		leftHoverTextIndex = (mouseY - leftTopY) / (cellSize + cellGap);
    	} else {
    		leftHoverTextIndex = -1;
    		topHoverTextIndex = -1;
    	}
    }
}
