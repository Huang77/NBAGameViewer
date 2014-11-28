package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import datamodel_new.Database;
import viewmodel.SeasonCanvas;

public class SeasonViewJFrame extends JFrame {
	final String teamNameFileName = "C:/Users/HXX/Desktop/NBAGameViewer/data/teamNames.csv";
	final String winLostFileName = "C:/Users/HXX/Desktop/NBAGameViewer/data/winLostMatrix_2013.csv";
	final String allGameStatFileName = "C:/Users/HXX/Desktop/NBAGameViewer/data/gameScoreList.csv";

	SeasonCanvas seasonCanvas;
	Database database;
	JPanel leftToolBar;
	
	int winWidth = 1400, winHeight = 840;
	int seasonCanvasWidth, seasonCanvasHeight;
	
	public SeasonViewJFrame () {
		database = new Database(teamNameFileName, winLostFileName, allGameStatFileName);
		seasonCanvas = new SeasonCanvas(database, seasonCanvasWidth, seasonCanvasHeight);
	}
}
