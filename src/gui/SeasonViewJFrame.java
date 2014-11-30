package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import datamodel_new.Database;
import viewmodel.SeasonCanvas;

public class SeasonViewJFrame extends JFrame {
	final String teamNameFileName = "C:/Users/HXX/Desktop/NBAGameViewer/data/teamNames.csv";
	final String winLostFileName = "C:/Users/HXX/Desktop/NBAGameViewer/data/winLostMatrix_2013.csv";
	final String allGameStatFileName = "C:/Users/HXX/Desktop/NBAGameViewer/data/gameScoreList_2013.csv";

	SeasonCanvas seasonCanvas;
	Database database;
	JPanel leftToolBar;
	JPanel mainPanel;
	
	int winWidth = 1440, winHeight = 880;
	int seasonCanvasWidth = winWidth, seasonCanvasHeight = winHeight;
	
	public SeasonViewJFrame () {
		setSize(new Dimension(winWidth, winHeight));
		initComponents();
		initVisualization();
		setVisible(true);
	}
	
	private void initVisualization () {
		 seasonCanvas.init();
	}

	private void initComponents() {
		assert SwingUtilities.isEventDispatchThread();
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		database = new Database(teamNameFileName, winLostFileName, allGameStatFileName);
		seasonCanvas = new SeasonCanvas(database, seasonCanvasWidth, seasonCanvasHeight);
		mainPanel.add(seasonCanvas);
	}
	
	public static void main (String[] args) {
		SeasonViewJFrame seaviewFrame = new SeasonViewJFrame();
	}
}
