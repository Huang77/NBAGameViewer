package datamodel_new;

import java.util.ArrayList;



public class GameStatData {
	public int index;
	public String date;
	public int leftTeamIndex;
	public int rightTeamIndex;
	public int leftScore;
	public int rightScore;
	public boolean overtime = false;
	
	ArrayList<Event> eventList = new ArrayList<Event>();
	ArrayList<PlayerGameStat> leftPlayers = new ArrayList<PlayerGameStat>();
	ArrayList<PlayerGameStat> rightPlayers = new ArrayList<PlayerGameStat>();
		
	public boolean isLeftWin () {
		return leftScore > rightScore ? true : false;
	}
	public int getWinTeamIndex () {
		return isLeftWin() ? leftTeamIndex : rightTeamIndex;
	}
	public int getAwayTeam () {
		return leftTeamIndex;
	}
	public int getScoreDiff () {
		return Math.abs(leftScore - rightScore);
	}
	public ArrayList<Event> getEventList () {
		return this.eventList;
	}
	public ArrayList<PlayerGameStat> getLeftPlayers () {
		return leftPlayers;
	}
	public ArrayList<PlayerGameStat> getRightPlayers () {
		return rightPlayers;
	}
}
