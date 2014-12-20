package viewmodel;

import gui.SingleGameJFrame;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;









import javax.swing.JFrame;

import processing.core.PApplet;
import datamodel.Database;
import datamodel.Team;

public class WinLostLine_V2 {
	static final int[] colorWin = {252,146,114};
	static final int[] colorLost = {173,221,142};
	static final int[] colorOff = {220,220,220};
	
	static final int barHeight = 20;
	static final int barWidth = 8;
	static final int verticalGap = 10;
	static final int horizonGap = 2;
	
	static final int numOfGame = 82;
	
	static final int leftTeamBarWidth = 120;
	
	Database database;
	int teamIndex;
	boolean hover = false;
	
	LeftTeamBar leftTeamBar;
	ArrayList<WinLostStreak> streaks = new ArrayList<WinLostStreak>();  
	int startX, startY;
	int endX, endY;
	
	public WinLostLine_V2 (int teamIndex, Database database) {
		this.teamIndex = teamIndex;
		this.database = database;
		this.leftTeamBar = new LeftTeamBar(teamIndex);
	}
	
	public void setPosition (int x, int y) {
		startX = x;
		startY = y;
		endX = startX + 82 * (horizonGap + barWidth);
		endY = startY + barHeight;
		setStreaks();
		setupLeftTeamBar();
	}
	
	public void setYPosition (int y) {
		startY = y;
		endX = startX + 82 * (horizonGap + barWidth);
		endY = startY + barHeight;
		setStreaks();
		setupLeftTeamBar();
	}
	
	public void setupLeftTeamBar () {
		int frontWidth;
		Team tempTeam = database.teamsMap.get(teamIndex);;
		int[] tempWinLost;
		tempWinLost = tempTeam.getOverall();
		this.leftTeamBar.setBackgroundRectSize(startX - 10 - leftTeamBarWidth, startY + barHeight / 2, leftTeamBarWidth, 20);
		frontWidth = (int) PApplet.map(tempWinLost[0], 0, tempWinLost[0] + tempWinLost[1], 0, leftTeamBarWidth);
		this.leftTeamBar.setFrontRectSize(startX - 10 - leftTeamBarWidth, startY + barHeight / 2, frontWidth, 20);
	}
	
	public void setStreaks () {
		streaks.clear();
		Team curTeam = database.teamsMap.get(teamIndex);
		int x, y;
		
		int scoreDiff;
		
		int barH;
		int[] color;
		boolean away;
		boolean win;
		
		ArrayList<Integer> gameIndex = curTeam.getGameIndex();
		DiffBar curBar;
		WinLostStreak curStreak;
		for (int i = 0; i < numOfGame; i++) {
			x = startX + i * (barWidth + horizonGap);
			scoreDiff = database.gameMap.get(gameIndex.get(i)).getScoreDiff();
			barH = (int) PApplet.map(scoreDiff, database.minScoreDiff, database.maxScoreDiff, 1, barHeight);
			if (teamIndex == database.gameMap.get(gameIndex.get(i)).getWinTeamIndex()) {
				y = startY + barHeight - barH;
				color = colorWin;
				win = true;
			} else {
				y = startY + barHeight;
				color = colorLost;
				win = false;
			}
			if (teamIndex == database.gameMap.get(gameIndex.get(i)).getAwayTeam()) {
				away = true;
			} else {
				away = false;
			}

			curBar = new DiffBar(x, y, barWidth, barH, gameIndex.get(i), database);
			curBar.setColor(color);
			curBar.setAway(away);
			curBar.setWinLost(win);
			
			if (streaks.size() > 0) {
				curStreak = streaks.get(streaks.size() - 1);
				if (curStreak.win == curBar.win) {
					curStreak.addGameBar(curBar);
					curBar.setParent(curStreak);
				} else {
					curStreak = new WinLostStreak(curBar.win);
					curStreak.setNumPosY(startY + barHeight);
					curStreak.addGameBar(curBar);
					curBar.setParent(curStreak);
					streaks.add(curStreak);
				}
			} else {
				curStreak = new WinLostStreak(curBar.win);
				curStreak.setNumPosY(startY + barHeight);
				curStreak.addGameBar(curBar);
				curBar.setParent(curStreak);
				streaks.add(curStreak);
			}
		}
	}

	public void drawTeamName (SeasonCanvas canvas) {
		canvas.pushStyle();
		canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
		canvas.textSize(15);
		canvas.fill(0);
		if (hover == true) {
			canvas.fill(250, 0, 0);
		}
		canvas.text(database.teamsMap.get(teamIndex).name, startX - 10, startY + barHeight);
		canvas.popStyle();
	}
	
	public void draw (SeasonCanvas canvas) {
		canvas.pushStyle();
		for (int i = 0; i < streaks.size(); i++) {
			streaks.get(i).draw(canvas);
		}
		//drawTeamName(canvas);
		leftTeamBar.draw(canvas, hover);
		canvas.popStyle();
	}
	
	public void mouseHover (SeasonCanvas canvas) {
		hover = false;
		leftTeamBar.mouseHover(canvas);
		for (int i = 0; i < streaks.size(); i++) {
			if (streaks.get(i).hover == true) {
				hover = true;
				TimeView.hoverTeamIndex = teamIndex;
			}
		}
	}
	
	public class WinLostStreak {
		ArrayList<DiffBar> bars = new ArrayList<DiffBar>();
		boolean win = false;
		int numPosX, numPosY;
		boolean hover = false;
		boolean gray = false;
		
		
		public WinLostStreak (boolean win) {
			this.win = win;
		}
		public void setNumPosY (int y) {
			numPosY = y;
		}
		public void addGameBar (DiffBar bar) {
			if (bars.size() > 1) {
				numPosX = (int) ((bar.rect.x + bars.get(0).rect.x) / 2);
			}
			this.bars.add(bar);
		}
		public int getGameNum () {
			return bars.size();
		}
		public void draw (SeasonCanvas canvas) {
			hover = false;
			canvas.pushStyle();
			for (int i = 0; i < bars.size(); i++) {
				bars.get(i).draw(canvas);
				if (bars.get(i).isMouseHover(canvas)) {
					hover = true;
				}
				if (i >= 1) {
					canvas.stroke(50);
					if (TimeView.filterStreakNum > getGameNum()) {
						canvas.stroke(180);
					}
					if (win == true) {
						canvas.line(bars.get(i - 1).rect.x + barWidth / 2, bars.get(i - 1).rect.y, bars.get(i).rect.x + barWidth / 2, bars.get(i).rect.y);
					} else {
						canvas.line(bars.get(i - 1).rect.x + barWidth / 2, bars.get(i - 1).rect.y + bars.get(i - 1).rect.height, 
								bars.get(i).rect.x + barWidth / 2, bars.get(i).rect.y + bars.get(i).rect.height);
					}
				}
			}
			
			if (bars.size() > 0) {
				canvas.textAlign(PApplet.CENTER, PApplet.CENTER);
				canvas.textSize(10);
				canvas.fill(0);
				if (TimeView.filterStreakNum > getGameNum()) {
					canvas.fill(180);
				}
				if (win) {
					canvas.text(bars.size(), numPosX, numPosY + 5);
				} else {
					canvas.text(bars.size(), numPosX, numPosY - 8);
				}
			}
			
			
			canvas.popStyle();
		}
	}
	
	
	public class DiffBar {
		SingleGameJFrame singleGameFrame;
		Rectangle2D.Float rect = new Rectangle2D.Float();
		int[] color = new int[3];
		
		boolean away = false;
		boolean win = false;
		int oppoIndex;
		int scoreDiff;
		int gameIndex;
		Database database;
		WinLostStreak parent;
		boolean gray = false;
		
		
		public DiffBar (int x, int y, int width, int height, int gameIndex, Database database) {
			rect.x = x;
			rect.y = y;
			rect.width = width;
			rect.height = height;
			this.gameIndex = gameIndex;
			this.database = database;
			this.scoreDiff = database.gameMap.get(gameIndex).getScoreDiff();
			
		}
		
		public void setParent (WinLostStreak parent) {
			this.parent = parent;
		}
		
		public void setWinLost (boolean win) {
			this.win = win;
		}
		
		
		public void setColor (int[] color) {
			this.color[0] = color[0];
			this.color[1] = color[1];
			this.color[2] = color[2];
		}
		public void setColor (int r, int g, int b) {
			this.color[0] = r;
			this.color[1] = g;
			this.color[2] = b;
		}
		public void setAway (boolean away) {
			this.away = away;
		}
		public void draw (SeasonCanvas canvas) {
			canvas.pushStyle();
			canvas.noStroke();
			if (isMouseHover(canvas)) {
				canvas.stroke(0);
				canvas.strokeWeight(2);
				if (canvas.mousePressed == true) {
					if (singleGameFrame == null) {
						singleGameFrame = new SingleGameJFrame(gameIndex, database);
						System.out.println(gameIndex);
					}
				}
			}
			canvas.fill(color[0], color[1], color[2]);
			if (scoreDiff < TimeView.filterDiffScore || parent.getGameNum() < TimeView.filterStreakNum) {
				gray = true;
			} else {
				gray = false;
			}
			if (gray == true) {
				canvas.fill(color[0], color[1], color[2], 50);
			}
			canvas.rect(rect.x, rect.y, rect.width, rect.height);
			canvas.popStyle();
		}
		
		public boolean isMouseHover (SeasonCanvas canvas) {
			if (gray == true) {
				return false;
			}
			if (rect.contains(canvas.mouseX, canvas.mouseY)) {
				return true;
			} else {
				return false;
			}
		}
	}
}
