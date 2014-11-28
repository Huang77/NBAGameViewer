package viewmodel;

import datamodel_new.Database;
import processing.core.PApplet;
import processing.core.PFont;

public class SeasonCanvas extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int width, height;
	int textSize = 12;
	PFont font = createFont("Sans Serif", textSize);
	
	// size of the winLostCell
	int cellSize;
	int cellGap;
	
	Database database;
	WinLostCell[] winLostCellList;
	
	
	public SeasonCanvas (Database database, int width, int height) {
		this.database = database;
		this.width = width;
		this.height = height;
	}
	
	public void initWinLostCell () {
		this.winLostCellList = new WinLostCell[database.teamNum * database.teamNum];
		for (int i = 0; i < database.winLostCellList.size(); i++) {
			
		}
	}

    @Override
    public void setup () {
    	size(width, height);
    	textFont(font);
    }
	
    @Override
	public void draw () {
    	
    }
}
