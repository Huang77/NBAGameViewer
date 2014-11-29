package viewmodel;

import java.util.ArrayList;

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
	int leftTopX = 20, leftTopY = 20;
	int cellSize;
	int cellGap = 2;
	
	Database database;
	WinLostCell[] winLostCellList;
	
	public SeasonCanvas() {
		
	}
	
	public SeasonCanvas (Database database, int width, int height) {
		this.database = database;
		this.width = width;
		this.height = height;
	}
	
	public void setupWinLostCell () {
		cellSize = (int) (width * 0.8 / database.teamNum - cellGap);
		this.winLostCellList = new WinLostCell[database.teamNum * database.teamNum];
		int tempX = leftTopX, tempY = leftTopY;
		
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
				int x = tempX + 5, y = tempY + 5;
				int barGap = 2;
				int barHeight = (cellSize - 5 - 3 - 3 * barGap) / 4;
				int minWidth = 5;
				int maxWidth = cellSize - 10;
				int barWidth;
				int[] barColor;
				GameStatData tempData;
				for (int j = 0; j < gameCount; j++) {
					tempData = database.gameStatDataList.get(gameIndex.get(j));
					scoreDiff = Math.abs(tempData.leftScore - tempData.rightScore);
					System.out.println(scoreDiff);
					barColor = tempData.getWinTeamIndex() == tempCellData.getLeftTeamIndex() ? this.colorForWinBar : this.colorForLoseBar;
					barWidth = (int) this.map(scoreDiff, database.minScoreDiff, database.maxScoreDiff, minWidth, maxWidth);
					winLostCellList[i].addLittleGameBar(x, y + j * (barHeight + barGap), barWidth, barHeight, barColor);
				}
			}
		}
	}

    @Override
    public void setup () {
    	size(this.width, this.height);
    	textFont(font);
    	
    	setupWinLostCell();
    	System.out.println("Finish Setup");
    }
	
    @Override
	public void draw () {
    	background(255);
    	smooth();
/*    		winLostCellList[0].draw(this);
    		winLostCellList[1].draw(this);
    		winLostCellList[2].draw(this);
    		winLostCellList[3].draw(this);
    		winLostCellList[4].draw(this);
    		winLostCellList[5].draw(this);*/
    	drawAllWinLostCells();

    }
    
    public void drawAllWinLostCells () {
    	for (int i = 0; i < winLostCellList.length; i++) {
    		System.out.println(i);
    		winLostCellList[i].draw(this);
    	}
    }
}
