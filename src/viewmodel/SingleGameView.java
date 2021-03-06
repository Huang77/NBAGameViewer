package viewmodel;

import java.util.ArrayList;

import datamodel.Database;
import datamodel.Event;
import datamodel.GameStatData;
import datamodel.MadeScoreEvent;
import datamodel.MissScoreEvent;
import datamodel.PlayerGameStat;

public class SingleGameView {
	
	int gameIndex;
	Database database;
	
	int x, y, width, height;
	
	int gameWidth;
	float gameWidthRatio = 0.85f;
	
	int diffGraphHeight = 150;
	int playerBarHeight = 12, playerBarGap = 5;
	
	
	public DiffHorizonGraph diffGraph;
	public ArrayList<ScoreEventCircleLine> leftCircleLines = new ArrayList<ScoreEventCircleLine>();
	public ArrayList<ScoreEventCircleLine> rightCircleLines = new ArrayList<ScoreEventCircleLine>();
	public ArrayList<PlayerBar> leftPlayerBar = new ArrayList<PlayerBar>();
	public ArrayList<PlayerBar> rightPlayerBar = new ArrayList<PlayerBar>();
	
	
	
	public SingleGameView (int gameIndex, Database database) {
		this.gameIndex = gameIndex;
		this.database = database;
	}
	
	public void setPosition (int startX, int startY, int width, int height) {
		this.x = startX;
		this.y = startY;
		this.width = width;
		this.gameWidth = (int) (width * gameWidthRatio);
		this.height = height;
	}
	
	public void setup () {
		int startX = x;
		int startY = (height) / 2 - 200;   // y position of DiffHorizonGraph
		
		int leftCircleY = startY - 10;
		int rightCircleY = startY + diffGraphHeight + 10;
		ArrayList<Event> eventList = database.gameStatDataList.get(gameIndex).getEventList();
		int maxQuarter = eventList.get(eventList.size() - 1).getQuarter();
		
		// set diff graph
		this.diffGraph = new DiffHorizonGraph(gameIndex, database, maxQuarter);
		this.diffGraph.setup(startX, startY, gameWidth, diffGraphHeight);
				
		
		Event event;
		ScoreEventCircleLine leftTeamLastLine = null;
		ScoreEventCircleLine rightTeamLastLine = null;
		ShootCircle circle;
		int rx;
		// set shot events
		for (int i = 0; i < eventList.size(); i++) {
			event = eventList.get(i);
			if (event.getActionTeamIndex() == database.gameStatDataList.get(gameIndex).leftTeamIndex) {
				if (event instanceof MadeScoreEvent) {
					MadeScoreEvent e = (MadeScoreEvent) event;
					circle = new ShootCircle(e.getPoint(), true);
					rx = SeasonCanvas.translateTimeIndexToXPos(e.getTimeIndex(), maxQuarter, startX, startX + gameWidth);
					circle.setPosition(rx, leftCircleY);
					if (leftTeamLastLine == null) {
						leftTeamLastLine = new ScoreEventCircleLine(rx, leftCircleY, true);
						leftTeamLastLine.addShootCircle(circle);
					} else {
						if (leftTeamLastLine.made == true) {
							leftTeamLastLine.addShootCircle(circle);
						} else {
							leftCircleLines.add(leftTeamLastLine);
							leftTeamLastLine = new ScoreEventCircleLine(rx, leftCircleY, true);
							leftTeamLastLine.addShootCircle(circle);
						}
					}
				} else if (event instanceof MissScoreEvent) {
					MissScoreEvent e = (MissScoreEvent) event;
					circle = new ShootCircle(e.getPoint(), false);
					rx = SeasonCanvas.translateTimeIndexToXPos(e.getTimeIndex(), maxQuarter, startX, startX + gameWidth);
					circle.setPosition(rx, leftCircleY);
					if (leftTeamLastLine == null) {
						leftTeamLastLine = new ScoreEventCircleLine(rx, leftCircleY, false);
						leftTeamLastLine.addShootCircle(circle);
					} else {
						if (leftTeamLastLine.made == false) {
							leftTeamLastLine.addShootCircle(circle);
						} else {
							leftCircleLines.add(leftTeamLastLine);
							leftTeamLastLine = new ScoreEventCircleLine(rx, leftCircleY, false);
							leftTeamLastLine.addShootCircle(circle);
						}
					}
				}
			} else {
				if (event instanceof MadeScoreEvent) {
					MadeScoreEvent e = (MadeScoreEvent) event;
					circle = new ShootCircle(e.getPoint(), true);
					rx = SeasonCanvas.translateTimeIndexToXPos(e.getTimeIndex(), maxQuarter, startX, startX + gameWidth);
					circle.setPosition(rx, rightCircleY);
					if (rightTeamLastLine == null) {
						rightTeamLastLine = new ScoreEventCircleLine(rx, rightCircleY, true);
						rightTeamLastLine.addShootCircle(circle);
					} else {
						if (rightTeamLastLine.made == true) {
							rightTeamLastLine.addShootCircle(circle);
						} else {
							rightCircleLines.add(rightTeamLastLine);
							rightTeamLastLine = new ScoreEventCircleLine(rx, rightCircleY, true);
							rightTeamLastLine.addShootCircle(circle);
						}
					}
				} else if (event instanceof MissScoreEvent) {
					MissScoreEvent e = (MissScoreEvent) event;
					circle = new ShootCircle(e.getPoint(), false);
					rx = SeasonCanvas.translateTimeIndexToXPos(e.getTimeIndex(), maxQuarter, startX, startX + gameWidth);
					circle.setPosition(rx, rightCircleY);
					if (rightTeamLastLine == null) {
						rightTeamLastLine = new ScoreEventCircleLine(rx, rightCircleY, false);
						rightTeamLastLine.addShootCircle(circle);
					} else {
						if (rightTeamLastLine.made == false) {
							rightTeamLastLine.addShootCircle(circle);
						} else {
							rightCircleLines.add(rightTeamLastLine);
							rightTeamLastLine = new ScoreEventCircleLine(rx, rightCircleY, false);
							rightTeamLastLine.addShootCircle(circle);
						}
					}
				}
			}
		}
		
		// set player bar
		int x = startX, y = leftCircleY - 50;
		ArrayList<PlayerGameStat> leftPlayers = database.gameStatDataList.get(gameIndex).getLeftPlayers();
		for (int i = 0; i < leftPlayers.size(); i++) {
			PlayerBar curBar = new PlayerBar(leftPlayers.get(i), startX, y - i * (playerBarHeight + playerBarGap), gameWidth, playerBarHeight);
			curBar.setInsideBar();
			leftPlayerBar.add(curBar);
		}
		y = rightCircleY + 50;
		ArrayList<PlayerGameStat> rightPlayers = database.gameStatDataList.get(gameIndex).getRightPlayers();
		for (int i = 0; i < rightPlayers.size(); i++) {
			PlayerBar curBar = new PlayerBar(rightPlayers.get(i), startX, y + i * (playerBarHeight + playerBarGap), gameWidth, playerBarHeight);
			curBar.setInsideBar();
			leftPlayerBar.add(curBar);
		}
	}
	
	
	
	
	public void draw (SingleGameCanvas canvas) {
		diffGraph.draw(canvas);
		for (int i = 0; i < leftCircleLines.size(); i++) {
			leftCircleLines.get(i).draw(canvas);
		}
		for (int i = 0; i < rightCircleLines.size(); i++) {
			rightCircleLines.get(i).draw(canvas);
		}
		for (int i = 0; i < leftPlayerBar.size(); i++) {
			leftPlayerBar.get(i).draw(canvas);
		}
		for (int i = 0; i < rightPlayerBar.size(); i++) {
			rightPlayerBar.get(i).draw(canvas);
		}
	}
	
	public void mouseMoved (SingleGameCanvas canvas) {
		
	}
}
