package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


import viewmodel.SingleGameCanvas;
import datamodel_new.Database;

public class SingleGameJFrame extends JFrame {
	SingleGameCanvas singleGameCanvas;
	Database database;
	int gameIndex;
	
	JPanel leftToolBar;
	JPanel mainPanel;
	
	
	int winWidth = 1440, winHeight = 1000;
	int singleGameCanvasWidth = winWidth, singleGameCanvasHeight = winHeight;
	
	public SingleGameJFrame (int gameIndex, Database database) {
		this.gameIndex = gameIndex;
		this.database = database;
		this.setTitle("Game Viewer");
		setSize(new Dimension(winWidth, winHeight));
		initComponents();
		initVisualization();
		setVisible(true);
	}
	
	private void initVisualization () {
		singleGameCanvas.init();
	}

	private void initComponents() {
		assert SwingUtilities.isEventDispatchThread();
		mainPanel = new JPanel();
		this.add(mainPanel);
		singleGameCanvas = new SingleGameCanvas(gameIndex, database, singleGameCanvasWidth, singleGameCanvasHeight);
		mainPanel.add(singleGameCanvas);
	}
}
