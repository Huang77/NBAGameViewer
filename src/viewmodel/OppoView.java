package viewmodel;

import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import datamodel_new.Database;
import datamodel_new.GameStatData;
import datamodel_new.Team;
import datamodel_new.TeamSortByOverall;
import datamodel_new.TeamSortType;
import datamodel_new.WinLostCellData;

public class OppoView {
	final int[] colorForWinMore = {252,146,114};
	final int[] colorForWinLess = {173,221,142};
	final int[] colorForWinEqual = {222,235,247};
	final int[] colorForWinBar = {99,99,99};
	final int[] colorForLoseBar = {240,240,240};
	final int[] whiteColor = {240,240,240};
	
	int width, height;
	
	// size of the winLostCell
	int leftTopX, leftTopY;
	int cellSize;
	int cellGap = 2;
		
	int teamBarLeftTopX = 20, teamBarLeftTopY;
	int leftTeamBarWidth;
	
	Database database;
	WinLostCell[][] winLostCellList;
	LeftTeamBar[] leftTeamBarList;
	
	int[] teamSortIndex;
	
	int leftHoverTextIndex, topHoverTextIndex;
	
	public OppoView (Database database) {
		this.database = database;
	}
	public void setPosition (int x, int y, int width, int height) {
		leftTopX = x;
		leftTopY = y;
		teamBarLeftTopY = leftTopY;
		leftTeamBarWidth = leftTopX - teamBarLeftTopX - 5;
		this.width = width;
		this.height = height;
	}
	
	public void setup () {
    	setTeamOrder(TeamSortType.Name);
    	setupWinLostCell();
    	setupLeftTeamBar();
    	setTeamOrder(TeamSortType.Overall);
		resetWinLostCellPosition();
		resetTeamBarPosition();
	}
	
	
	public void setupWinLostCell () {
		cellSize = (int) (height * 0.88 / database.teamNum - cellGap);
		this.winLostCellList = new WinLostCell[database.teamNum][database.teamNum];
		int tempX = leftTopX, tempY = leftTopY;
		
		int lineIndex, oppoIndex;
		WinLostCellData tempCellData;
		for (int i = 0; i < database.winLostCellList.size(); i++) {
			tempCellData = database.winLostCellList.get(i);
			
			lineIndex = i / 30;
			oppoIndex = i % 30;
			tempX  = leftTopX + oppoIndex * (cellSize + cellGap);
			tempY = leftTopY + + lineIndex * (cellSize + cellGap);
			winLostCellList[lineIndex][oppoIndex] = new WinLostCell(tempX, tempY , cellSize, cellSize);
			if (tempCellData.isSelfTeam()) {  // the cell is a self cell
				winLostCellList[lineIndex][oppoIndex].setColor(whiteColor);
			} else if (!tempCellData.hasPlayed()) { // the two teams have not played a game yet
				winLostCellList[lineIndex][oppoIndex].setColor(whiteColor);
			} else {  // the two games have player at least one game, and here will set the game bar 
				int winCompare = tempCellData.winCompare();
				switch (winCompare) {
					case 1 : winLostCellList[lineIndex][oppoIndex].setColor(colorForWinMore); break;
					case 0 : winLostCellList[lineIndex][oppoIndex].setColor(colorForWinEqual); break;
					case -1: winLostCellList[lineIndex][oppoIndex].setColor(colorForWinLess); break;
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
					barWidth = (int) PApplet.map(scoreDiff, database.minScoreDiff, database.maxScoreDiff, minWidth, maxWidth);
					winLostCellList[lineIndex][oppoIndex].addLittleGameBar(gameIndex.get(j), x, y + j * (barHeight + barGap), barWidth, barHeight, barColor);
				}
			}
		}
	}
	
	public void resetWinLostCellPosition () {
		for (int i = 0; i < teamSortIndex.length; i++) {
			for (int j = 0; j < database.teamNum; j++) {
				winLostCellList[teamSortIndex[i]][j].setNewPosition(leftTopX + j * (cellSize + cellGap), leftTopY + i * (cellSize + cellGap));
			}
		}
	}
	public void resetTeamBarPosition () {
		for (int i = 0; i < teamSortIndex.length; i++) {
			leftTeamBarList[teamSortIndex[i]].setNewPosition(teamBarLeftTopX, teamBarLeftTopY + i * (cellSize + cellGap));
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
			frontWidth = (int) PApplet.map(tempWinLost[0], 0, tempWinLost[0] + tempWinLost[1], 0, leftTeamBarWidth);
			leftTeamBarList[i].setFrontRectSize(teamBarLeftTopX, teamBarLeftTopY + i * (cellSize + cellGap), frontWidth, cellSize);
		}
	}
	
	public void setTeamOrder (TeamSortType sortType) {
		if (teamSortIndex == null) {
			teamSortIndex = new int[database.teamNum];
		}	
		switch (sortType) {
			case Overall:
				Arrays.sort(database.teams, new TeamSortByOverall());
				for (int i = 0; i < database.teamNum; i++) {
					teamSortIndex[i] = database.teams[i].index;
				}
				break;
			case Name:
			default: 
				for (int i = 0; i < database.teamNum; i++) {
					teamSortIndex[i] = i;
				}
		}
	}
	
	public void draw (SeasonCanvas canvas) {
    	drawAllWinLostCells(canvas);
    	drawLeftTeamBars(canvas);
    	drawTopTeamNames(canvas);
	}
	
	
    public void drawAllWinLostCells (SeasonCanvas canvas) {
    	for (int i = 0; i < database.teamNum; i++) {
    		for (int j = 0; j < database.teamNum; j++) {
    			winLostCellList[i][j].draw(canvas);
    		}
    	}
    }
    
    public void drawLeftTeamBars (SeasonCanvas canvas) {
    	for (int i = 0; i < leftTeamBarList.length; i++) {
    		leftTeamBarList[i].draw(canvas);
    	}
    }

    public void drawTopTeamNames (SeasonCanvas canvas) {
    	int x = leftTopX + cellSize / 2;
    	int y = leftTopY - 2;
    	//this.rotate(-PApplet.QUARTER_PI);
    	canvas.pushStyle();
    	canvas.textAlign(PApplet.LEFT);
    	for (int i = 0; i < database.teamNum; i++) {
    		canvas.fill(0);
        	if (i == topHoverTextIndex) {
        		canvas.fill(250,0,0);
        	}
        	canvas.pushMatrix();
        	canvas.translate(x + i * (cellSize + cellGap), y);
        	canvas.rotate(-PApplet.QUARTER_PI);
        	canvas.text(database.teamsMap.get(i).shortName, 0, 0);
        	canvas.popMatrix();
    	}
    	canvas.popStyle();
    }
	
}
