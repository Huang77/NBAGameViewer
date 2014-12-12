package viewmodel;

import java.util.ArrayList;
import java.util.Arrays;

import datamodel_new.Event;
import datamodel_new.MadeScoreEvent;
import datamodel_new.MissScoreEvent;
import datamodel_new.PlayerGameStat;
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
	
	
	public static int displayType = 1;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int width, height;
	int textSize = 12;
	PFont font = createFont("Sans Serif", textSize);
	
	int leftTopX = 150, leftTopY = 50;
	
	Database database;
	// test diff score horizon graph
	SingleGameView singleGame;
	OppoView oppoView;
	TimeView timeView;
	
	public void setSingleGameView (int gameIndex, int startX, int startY, int width, int height) {
		singleGame = new SingleGameView(gameIndex, database);
		singleGame.setPosition(startX, startY, width, height);
		singleGame.setup();
	}
	public void setOppoView (int startX, int startY, int width, int height) {
		oppoView = new OppoView(database);
		oppoView.setPosition(startX, startY, width, height);
		oppoView.setup();
	}
	public void setTimeView (int startX, int startY, int width, int height) {
		timeView = new TimeView(database);
		timeView.setPosition(startX, startY, width, height);
		timeView.setAllTeam();
	}
	
	
	static public int translateTimeIndexToXPos (int timeIndex, int maxQuarter, int startX, int endX) {
		int maxTimeIndex = 4 * 12 * 60;
		if (maxQuarter < 4) {
			System.out.println("Error: The number of quarter is less than 4!");
			System.exit(1);
		} else if (maxQuarter > 4) {
			maxTimeIndex = maxTimeIndex + (maxQuarter - 4) * 5 * 60;
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
	

    @Override
    public void setup () {
    	size(this.width, this.height);
    	textFont(font);
    	setOppoView(leftTopX, leftTopY, this.width, this.height);
    	setTimeView(leftTopX, leftTopY, this.width, this.height);
		setSingleGameView(15, leftTopX, leftTopY, 1200, this.height);
    }
	
    @Override
	public void draw () {
    	background(240);
    	smooth();
    	if (displayType == 1) {
    		oppoView.draw(this);
    	} else if (displayType == 2) {
    		timeView.draw(this);
    	} else if (displayType == 3) {
    		singleGame.draw(this);
    	}
    }
    
    
    public void mouseMoved () {
    	if (displayType == 1) {
        	if (mouseX > leftTopX && mouseY > leftTopY
        			&& mouseX < leftTopX + database.teamNum * (oppoView.cellSize + oppoView.cellGap)
        			&& mouseY < leftTopY + database.teamNum * (oppoView.cellSize + oppoView.cellGap)) {
        		oppoView.topHoverTextIndex = (mouseX - leftTopX) / (oppoView.cellSize + oppoView.cellGap);
        		oppoView.leftHoverTextIndex = oppoView.teamSortIndex[(mouseY - leftTopY) / (oppoView.cellSize + oppoView.cellGap)];
        	} else {
        		oppoView.leftHoverTextIndex = -1;
        		oppoView.topHoverTextIndex = -1;
        	}
    	}

    }
}
