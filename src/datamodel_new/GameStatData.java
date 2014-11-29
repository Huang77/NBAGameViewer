package datamodel_new;

public class GameStatData {
	public int index;
	public String date;
	public int leftTeamIndex;
	public int rightTeamIndex;
	public int leftScore;
	public int rightScore;
	public boolean overtime = false;
	
	public boolean isLeftWin () {
		return leftScore > rightScore ? true : false;
	}
	public int getWinTeamIndex () {
		return isLeftWin() ? leftTeamIndex : rightTeamIndex;
	}
}
