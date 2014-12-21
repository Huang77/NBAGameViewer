package viewmodel;

import processing.core.PApplet;
import datamodel.Database;
import datamodel.GameStatData;

public class GameInfoTag {
	int gameIndex = -1;
	String leftTeamName, rightTeamName;
	int leftScore, rightScore;
	String date;
	float barWidth;
	float barHeight = 60;
	float gap = 5;
	
	public GameInfoTag () {
		
	}
	
	public void setInfo (Database database, int gameIndex) {
		if (gameIndex == -1) {
			return;
		}
		this.gameIndex = gameIndex;
		GameStatData game = database.gameMap.get(gameIndex);
		leftTeamName = database.teamsMap.get(game.leftTeamIndex).name;
		rightTeamName = database.teamsMap.get(game.rightTeamIndex).name;
		leftScore = game.leftScore;
		rightScore = game.rightScore;
		date = game.date;
	}
	
	public void draw (SeasonCanvas canvas) {
		if (gameIndex == -1) {
			return;
		}
		canvas.pushStyle();
		canvas.textSize(15);
		barWidth = canvas.textWidth(leftTeamName + " VS. " + rightTeamName) + 18;
		canvas.fill(240,240,240,200);
		canvas.rect(canvas.mouseX + 3, canvas.mouseY - 3 - barHeight, barWidth, barHeight);
		canvas.textAlign(PApplet.LEFT, PApplet.TOP);
		canvas.fill(0);
		canvas.text(leftTeamName + " VS. " + rightTeamName, canvas.mouseX + 3 + gap, canvas.mouseY - 3 - barHeight + gap);
		canvas.text(leftScore + " - " + rightScore, canvas.mouseX + 3 + gap, canvas.mouseY - 3 - barHeight + gap + 15);
		canvas.text(date, canvas.mouseX + 3 + gap, canvas.mouseY - 3 - barHeight + gap + 15 * 2);
		canvas.popStyle();
	}
}
