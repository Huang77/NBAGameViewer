package viewmodel;

import datamodel_new.Database;
import processing.core.PApplet;

public class SeasonCanvas extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int width, height;
	Database database;
	
	
	public SeasonCanvas (Database database, int width, int height) {
		this.database = database;
		this.width = width;
		this.height = height;
	}

    @Override
    public void setup () {
    	size(width, height);
    }
	
    @Override
	public void draw () {
    	
    }
}
