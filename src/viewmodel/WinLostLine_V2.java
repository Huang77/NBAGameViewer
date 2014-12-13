package viewmodel;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;




import processing.core.PApplet;
import datamodel_new.Database;
import datamodel_new.Team;

public class WinLostLine_V2 {
	static final int[] colorWin = {252,146,114};
	static final int[] colorLost = {173,221,142};
	static final int[] colorOff = {220,220,220};
	
	static final int barHeight = 20;
	static final int barWidth = 8;
	static final int verticalGap = 10;
	static final int horizonGap = 2;
	
	static final int numOfGame = 82;
	
	Database database;
	int teamIndex;
	
	DiffBar[] bars = new DiffBar[numOfGame];  
	int startX, startY;
	
	public WinLostLine_V2 (int teamIndex, Database database) {
		this.teamIndex = teamIndex;
		this.database = database;
	}
	
	public void setPosition (int x, int y) {
		startX = x;
		startY = y;
		setDiffBars();
	}
	
	public void setDiffBars () {
		Team curTeam = database.teamsMap.get(teamIndex);
		int x, y;
		
		int scoreDiff;
		
		int barH;
		int[] color;
		boolean away;
		boolean win;
		
		ArrayList<Integer> gameIndex = curTeam.getGameIndex();
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

			bars[i] = new DiffBar(x, y, barWidth, barH);
			bars[i].setColor(color);
			bars[i].setAway(away);
			bars[i].setWinLost(win);
		}
	}

	public void drawTeamName (SeasonCanvas canvas) {
		canvas.pushStyle();
		canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
		canvas.fill(0);
		canvas.text(database.teamsMap.get(teamIndex).name, startX - 10, startY + barHeight);
		canvas.popStyle();
	}
	
	public void draw (SeasonCanvas canvas) {
		canvas.pushStyle();
		for (int i = 0; i < bars.length; i++) {
			bars[i].draw(canvas);
			if (i > 0 && bars[i - 1].win == bars[i].win) {
				canvas.stroke(50);
				if (bars[i].win == true) {
					canvas.line(bars[i - 1].rect.x + barWidth / 2, bars[i - 1].rect.y, bars[i].rect.x + barWidth / 2, bars[i].rect.y);
				} else {
					canvas.line(bars[i - 1].rect.x + barWidth / 2, bars[i - 1].rect.y + bars[i - 1].rect.height, 
							bars[i].rect.x + barWidth / 2, bars[i].rect.y + bars[i].rect.height);
				}
				
			}
		}
		drawTeamName(canvas);
		canvas.popStyle();
	}
	

	
	class DiffBar {
		Rectangle2D.Float rect = new Rectangle2D.Float();
		int[] color = new int[3];
		
		boolean away = false;
		boolean win = false;
		int oppoIndex;
		int scoreDiff;
		
		public DiffBar (int x, int y, int width, int height) {
			rect.x = x;
			rect.y = y;
			rect.width = width;
			rect.height = height;
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
			}
			canvas.fill(color[0], color[1], color[2]);
			canvas.rect(rect.x, rect.y, rect.width, rect.height);
			canvas.popStyle();
		}
		public boolean isMouseHover (SeasonCanvas canvas) {
			if (rect.contains(canvas.mouseX, canvas.mouseY)) {
				return true;
			} else {
				return false;
			}
		}
	}
}
