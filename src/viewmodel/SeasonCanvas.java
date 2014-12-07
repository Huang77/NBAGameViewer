package viewmodel;

import java.util.ArrayList;
import java.util.Arrays;

import datamodel_new.Event;
import datamodel_new.MadeScoreEvent;
import datamodel_new.MissScoreEvent;
import datamodel_new.Team;
import datamodel_new.Database;
import datamodel_new.GameStatData;
import datamodel_new.TeamSortByOverall;
import datamodel_new.TeamSortType;
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
	WinLostCell[][] winLostCellList;
	LeftTeamBar[] leftTeamBarList;
	
	int[] teamSortIndex;
	
	int leftHoverTextIndex, topHoverTextIndex;
	
	
	// test diff score horizon graph
	SingleGameView singleGame;
	
	
	public void setSingleGameView (int gameIndex, int startX, int startY) {
		int width = 1200;
		singleGame = new SingleGameView(gameIndex);
		singleGame.diffGraph = setDiffGraph(gameIndex, startX, startY, width, 100);
		int leftCircleY = startY + 100 + 100;
		int rightCircleY = leftCircleY + 100;
		ArrayList<Event> eventList = database.gameStatDataList.get(gameIndex).getEventList();
		Event event;
		ScoreEventCircleLine leftTeamLastLine = null;
		ScoreEventCircleLine rightTeamLastLine = null;
		ShootCircle circle;
		int rx, ry = startY;
		for (int i = 0; i < eventList.size(); i++) {
			event = eventList.get(i);
			if (event.getActionTeamIndex() == database.gameStatDataList.get(gameIndex).leftTeamIndex) {
				if (event instanceof MadeScoreEvent) {
					MadeScoreEvent e = (MadeScoreEvent) event;
					circle = new ShootCircle(e.getPoint(), true);
					rx = translateTimeIndexToXPos(e.getTimeIndex(), 4, startX, startX + width);
					circle.setPosition(rx, leftCircleY);
					if (leftTeamLastLine == null) {
						leftTeamLastLine = new ScoreEventCircleLine(rx, leftCircleY, true);
						leftTeamLastLine.addShootCircle(circle);
					} else {
						if (leftTeamLastLine.made == true) {
							leftTeamLastLine.addShootCircle(circle);
						} else {
							singleGame.leftCircleLines.add(leftTeamLastLine);
							leftTeamLastLine = new ScoreEventCircleLine(rx, leftCircleY, true);
							leftTeamLastLine.addShootCircle(circle);
						}
					}
				} else if (event instanceof MissScoreEvent) {
					MissScoreEvent e = (MissScoreEvent) event;
					circle = new ShootCircle(e.getPoint(), false);
					rx = translateTimeIndexToXPos(e.getTimeIndex(), 4, startX, startX + width);
					circle.setPosition(rx, leftCircleY);
					if (leftTeamLastLine == null) {
						leftTeamLastLine = new ScoreEventCircleLine(rx, leftCircleY, false);
						leftTeamLastLine.addShootCircle(circle);
					} else {
						if (leftTeamLastLine.made == false) {
							leftTeamLastLine.addShootCircle(circle);
						} else {
							singleGame.leftCircleLines.add(leftTeamLastLine);
							leftTeamLastLine = new ScoreEventCircleLine(rx, leftCircleY, false);
							leftTeamLastLine.addShootCircle(circle);
						}
					}
				}
			} else {
				if (event instanceof MadeScoreEvent) {
					MadeScoreEvent e = (MadeScoreEvent) event;
					circle = new ShootCircle(e.getPoint(), true);
					rx = translateTimeIndexToXPos(e.getTimeIndex(), 4, startX, startX + width);
					circle.setPosition(rx, rightCircleY);
					if (rightTeamLastLine == null) {
						rightTeamLastLine = new ScoreEventCircleLine(rx, rightCircleY, true);
						rightTeamLastLine.addShootCircle(circle);
					} else {
						if (rightTeamLastLine.made == true) {
							rightTeamLastLine.addShootCircle(circle);
						} else {
							singleGame.rightCircleLines.add(rightTeamLastLine);
							rightTeamLastLine = new ScoreEventCircleLine(rx, rightCircleY, true);
							rightTeamLastLine.addShootCircle(circle);
						}
					}
				} else if (event instanceof MissScoreEvent) {
					MissScoreEvent e = (MissScoreEvent) event;
					circle = new ShootCircle(e.getPoint(), false);
					rx = translateTimeIndexToXPos(e.getTimeIndex(), 4, startX, startX + width);
					circle.setPosition(rx, rightCircleY);
					if (rightTeamLastLine == null) {
						rightTeamLastLine = new ScoreEventCircleLine(rx, rightCircleY, false);
						rightTeamLastLine.addShootCircle(circle);
					} else {
						if (rightTeamLastLine.made == false) {
							rightTeamLastLine.addShootCircle(circle);
						} else {
							singleGame.rightCircleLines.add(rightTeamLastLine);
							rightTeamLastLine = new ScoreEventCircleLine(rx, rightCircleY, false);
							rightTeamLastLine.addShootCircle(circle);
						}
					}
				}
			}
				

		}
		
	}
	
	
	public DiffHorizonGraph setDiffGraph (int gameIndex, int x0, int y0, int width, int height) {
		int graphX = x0, graphY = y0;
		DiffHorizonGraph diffGraph = new DiffHorizonGraph(gameIndex);
		diffGraph.setBackgroundRect(graphX, graphY, width, height);
		ArrayList<Event> eventList = database.gameStatDataList.get(gameIndex).getEventList();
		int i = 0;
		int startX = 0, endX;
		int scoreDiff = 0;
		int numOfEvent = eventList.size();
		for (; i < numOfEvent - 1; i++) {
			if (eventList.get(i) instanceof MadeScoreEvent) {
				
				endX = translateTimeIndexToXPos(eventList.get(i).getTimeIndex(), 4, graphX, graphX + width);
				if (endX > 950) {
					System.out.println(eventList.get(i).getTimeIndex());
				}
				diffGraph.addInsideRectList(scoreDiff, startX, endX - startX);
				startX = endX;
				scoreDiff = eventList.get(i).getScoreDiff();
			}
		}
		endX = graphX + width;
		diffGraph.addInsideRectList(scoreDiff, startX, endX - startX);
		return diffGraph;
	}
	
	public int translateTimeIndexToXPos (int timeIndex, int maxQuarter, int startX, int endX) {
		int maxTimeIndex = 4 * 12 * 60;
		if (maxQuarter < 4) {
			System.out.println("Error: The number of quarter is less than 4!");
			System.exit(1);
		} else if (maxQuarter > 4) {
			// overtime
		}
		
		return (int) PApplet.map(timeIndex, 0, maxTimeIndex , startX , endX);
	}
	
	
	
	
	public SeasonCanvas() {
		
	}
	
	public SeasonCanvas (Database database, int width, int height) {
		this.database = database;
		this.width = width;
		this.height = height;
	}
	
	public void setupWinLostCell () {
		cellSize = (int) (height * 0.8 / database.teamNum - cellGap);
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
					barWidth = (int) map(scoreDiff, database.minScoreDiff, database.maxScoreDiff, minWidth, maxWidth);
					winLostCellList[lineIndex][oppoIndex].addLittleGameBar(x, y + j * (barHeight + barGap), barWidth, barHeight, barColor);
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
			frontWidth = (int) map(tempWinLost[0], 0, tempWinLost[0] + tempWinLost[1], 0, leftTeamBarWidth);
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

    @Override
    public void setup () {
    	size(this.width, this.height);
    	textFont(font);
    	setTeamOrder(TeamSortType.Name);
    	setupWinLostCell();
    	setupLeftTeamBar();
    	setTeamOrder(TeamSortType.Overall);
		resetWinLostCellPosition();
		resetTeamBarPosition();
		setSingleGameView(0, leftTopX, leftTopY);
    }
	
    @Override
	public void draw () {
    	background(255);
    	smooth();

    	//drawAllWinLostCells();
    	//drawLeftTeamBars();
    	//drawLeftTeamNames();
    	//drawTopTeamNames();
    	//diffGraph.draw(this);
    	singleGame.draw(this);

    }
    
    public void drawAllWinLostCells () {
    	for (int i = 0; i < database.teamNum; i++) {
    		for (int j = 0; j < database.teamNum; j++) {
    			winLostCellList[i][j].draw(this);
    		}
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
    		this.text(database.teamIndex.get(i), x, y + i * (cellSize + cellGap));
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
    		this.text(database.teamsMap.get(i).shortName, 0, 0);
    		this.popMatrix();
    	}
    	this.popStyle();
    }
    
    public void mouseMoved () {
    	if (mouseX > leftTopX && mouseY > leftTopY
    			&& mouseX < leftTopX + database.teamNum * (cellSize + cellGap)
    			&& mouseY < leftTopY + database.teamNum * (cellSize + cellGap)) {
    		topHoverTextIndex = (mouseX - leftTopX) / (cellSize + cellGap);
    		leftHoverTextIndex = teamSortIndex[(mouseY - leftTopY) / (cellSize + cellGap)];
    	} else {
    		leftHoverTextIndex = -1;
    		topHoverTextIndex = -1;
    	}
    }
}
