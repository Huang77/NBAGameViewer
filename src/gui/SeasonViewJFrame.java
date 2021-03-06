package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import datamodel.Database;
import viewmodel.SeasonCanvas;

public class SeasonViewJFrame extends JFrame {
	final String teamNameFileName = "data/teamNames.csv";
	final String winLostFileName = "data/winLostMatrix_2013.csv";
	final String allGameStatFileName = "data/gameScoreList_2013.csv";
	final String allGameDataFileName = "data/all_game_record_2013.csv";
	final String efficiencyFile = "data/plus-minus";
	final String teamDataFile = "data/leagues_NBA_2014_team.csv";
	final String oppoDataFile = "data/leagues_NBA_2014_opponent.csv";
	
	SeasonCanvas seasonCanvas;
	Database database;
	
	
	JPanel leftToolBar;
	JPanel mainPanel;
	
	
	JMenuBar menuBar;
	JMenu dataMenu;
	JMenuItem season1314;
	JMenuItem season1213;
	
	JMenu viewMenu;
	JMenuItem oppoViewItem;
	JMenuItem timeViewItem;
	JMenuItem singleGameViewItem;
	
	JMenu toolMenu;
	JMenuItem filterItem;
	
	
	int winWidth = 1800, winHeight = 1000;
	int seasonCanvasWidth = winWidth, seasonCanvasHeight = winHeight;
	
	public SeasonViewJFrame () {
		this.setTitle("Game Viewer");
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setSize(new Dimension(winWidth, winHeight));
		initComponents();
		initVisualization();
		setMenuItemAction();
		setVisible(true);
	}
	
	private void initVisualization () {
		 seasonCanvas.init();
	}

	private void initComponents() {
		assert SwingUtilities.isEventDispatchThread();
		
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		dataMenu = new JMenu("Data");
		viewMenu = new JMenu("View");
		toolMenu = new JMenu("Filter");
		menuBar.add(dataMenu);
		menuBar.add(viewMenu);
		menuBar.add(toolMenu);
		
		oppoViewItem = new JMenuItem("Opponent View");
		timeViewItem = new JMenuItem("Time View");
		singleGameViewItem = new JMenuItem("Game Flow");
		viewMenu.add(timeViewItem);
		viewMenu.add(oppoViewItem);
		viewMenu.add(singleGameViewItem);
				
		season1314 = new JMenuItem("Season 13-14");
		season1213 = new JMenuItem("Season 12-13");
		dataMenu.add(season1314);
		dataMenu.add(season1213);
		
		filterItem = new JMenuItem("Attributes");
		toolMenu.add(filterItem);
		
		
		mainPanel = new JPanel();
		this.add(mainPanel);
		database = new Database(teamNameFileName, winLostFileName, allGameStatFileName, allGameDataFileName, efficiencyFile);
		database.readTeamAvgData(teamDataFile);
		database.readTeamOppoAvgData(oppoDataFile);
		
		seasonCanvas = new SeasonCanvas(database, seasonCanvasWidth, seasonCanvasHeight);
		mainPanel.add(seasonCanvas);
	}
	
	private void setMenuItemAction () {
		oppoViewItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (seasonCanvas != null) {
					seasonCanvas.displayType = 2;
				}
			}
		});
		
		timeViewItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (seasonCanvas != null) {
					seasonCanvas.displayType = 1;
				}
			}
			
		});
		
		filterItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FilterJFrame filerJFrame = new FilterJFrame(seasonCanvas.timeView);
			}
			
		});
	}
	
	public static void main (String[] args) {
		SeasonViewJFrame seaviewFrame = new SeasonViewJFrame();
	}
}
