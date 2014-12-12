package viewmodel;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import datamodel_new.Database;
import datamodel_new.Team;

public class WinLostLine {
	static final int[] colorWin = {252,146,114};
	static final int[] colorLost = {173,221,142};
	static final int[] colorOff = {220,220,220};
	
	static final int circleRadius = 8;
	static final int verticalGap = 5;
	static final int horizonGap = 4;
	
	static final int numOfDate = 170;
	
	Database database;
	int teamIndex;
	
	GameCircle[] circles = new GameCircle[82];
	ArrayList<ConnectBar> bars = new ArrayList<ConnectBar>();
	int startX, startY;
	
	public WinLostLine (int teamIndex, Database database) {
		this.teamIndex = teamIndex;
		this.database = database;
	}
	
	public void setPosition (int x, int y) {
		startX = x;
		startY = y;
		setCircles();
	}
	
	public void setCircles () {
		Team curTeam = database.teamsMap.get(teamIndex);
		int y = startY;
		int x;
		for (int i = 0; i < 82; i++) {
			x = i * (circleRadius + horizonGap) + startX;
			circles[i] = new GameCircle(x, y, circleRadius, colorOff);
		}
		
		ArrayList<Integer> gameIndex = curTeam.getGameIndex();
		int index;
		
		//index = TimeView.convertTimeToIndex(database.gameMap.get(gameIndex.get(0)).date);
		index = 0;
		if (teamIndex == database.gameMap.get(gameIndex.get(0)).getWinTeamIndex()) {
			circles[index].setColor(colorWin);
			circles[index].setFlat(1);
		} else {
			circles[index].setColor(colorLost);
			circles[index].setFlat(-1);
		}
		if (teamIndex == database.gameMap.get(gameIndex.get(0)).getAwayTeam()) {
			circles[index].setAway(true);
		} else {
			circles[index].setAway(false);
		}
		int lastIndex = index;
		
		for (int i = 1; i < gameIndex.size(); i++) {
			//index = TimeView.convertTimeToIndex(database.gameMap.get(gameIndex.get(i)).date);
			index = i;
			if (teamIndex == database.gameMap.get(gameIndex.get(i)).getWinTeamIndex()) {
				circles[index].setColor(colorWin);
				circles[index].setFlat(1);
				if (circles[lastIndex].flag == 1) {
					ConnectBar bar = new ConnectBar((int)circles[lastIndex].circle.x, (int)circles[lastIndex].circle.y - 2, 
							(int)(circles[index].circle.x - circles[lastIndex].circle.x), 4);
					bar.setColor(colorWin);
					bars.add(bar);
				}
			} else {
				circles[index].setColor(colorLost);
				circles[index].setFlat(-1);
				if (circles[lastIndex].flag == -1) {
					ConnectBar bar = new ConnectBar((int)circles[lastIndex].circle.x, (int)circles[lastIndex].circle.y - 2, 
							(int)(circles[index].circle.x - circles[lastIndex].circle.x), 4);
					bar.setColor(colorLost);
					bars.add(bar);
				}
			}
			
			if (teamIndex == database.gameMap.get(gameIndex.get(index)).getAwayTeam()) {
				circles[index].setAway(true);
			} else {
				circles[index].setAway(false);
			}

			lastIndex = index;
		}
	}

	
	public void draw (SeasonCanvas canvas) {
		canvas.pushStyle();
		for (int i = 0; i < bars.size(); i++) {
			bars.get(i).draw(canvas);
		}
		for (int i = 0; i < circles.length; i++) {
			circles[i].draw(canvas);
		}
		canvas.popStyle();
	}
	
	class GameCircle {
		int flag = 0; // 1 for win, 0 for off, -1 for lost
		boolean away;
		Ellipse2D.Float circle = new Ellipse2D.Float();
		int[] color = new int[3];
		
		public GameCircle (int rx, int ry, int radius, int[] color) {
			setPosition(rx, ry, radius);
			setColor(color);
		}
		public void setFlat (int flag) {
			this.flag = flag;
		}
		public void setAway (boolean away) {
			this.away = away;
		}
		
		public void setPosition (int rx, int ry, int radius) {
			circle.x = rx;
			circle.y = ry;
			circle.width = radius;
			circle.height = radius;
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
		
		public void draw (SeasonCanvas canvas) {
			canvas.pushStyle();
			canvas.noStroke();
			if (away == true) {
				canvas.stroke(100);
				canvas.strokeWeight(1f);
			}
			
			canvas.fill(color[0], color[1], color[2]);
			canvas.ellipse(circle.x, circle.y, circle.width, circle.height);
			canvas.popStyle();
		}
	}
	
	class ConnectBar {
		Rectangle2D.Float rect = new Rectangle2D.Float();
		int[] color = new int[3];
		
		
		public ConnectBar (int x, int y, int width, int height) {
			rect.x = x;
			rect.y = y;
			rect.width = width;
			rect.height = height;
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
		public void draw (SeasonCanvas canvas) {
			canvas.pushStyle();
			canvas.noStroke();
			canvas.fill(color[0], color[1], color[2]);
			canvas.rect(rect.x, rect.y, rect.width, rect.height);
			canvas.popStyle();
		}
	}
}
