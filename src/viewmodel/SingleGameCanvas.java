package viewmodel;

import datamodel.Database;
import processing.core.PApplet;
import processing.core.PFont;

public class SingleGameCanvas extends PApplet {

	int gameIndex;
	int width, height;
	int textSize = 12;
	PFont font = createFont("Sans Serif", textSize);
	
	int leftTopX = 170, leftTopY = 10;
	
	Database database;
	// test diff score horizon graph
	SingleGameView singleGame;

	public void setSingleGameView (int gameIndex, int startX, int startY, int width, int height) {
		singleGame = new SingleGameView(gameIndex, database);
		singleGame.setPosition(startX, startY, width, height);
		singleGame.setup();
	}
	
	public SingleGameCanvas(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public SingleGameCanvas (int gameIndex, Database database, int width, int height) {
		this.gameIndex = gameIndex;
		this.database = database;
		this.width = width;
		this.height = height;
	}
	

    @Override
    public void setup () {
    	size(this.width, this.height);
    	textFont(font);
		setSingleGameView(gameIndex, leftTopX, leftTopY, (int)(width * 0.86), height);
    }
	
    @Override
	public void draw () {
    	background(240);
    	smooth();
    	singleGame.draw(this);
    }
    
    @Override
    public void mouseMoved () {
    	singleGame.mouseMoved(this);
    }
    
}
