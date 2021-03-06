package datamodel;

import java.util.ArrayList;
import java.util.HashMap;



public class GameStatData {
	public int index;
	public String date;
	public int leftTeamIndex;
	public int rightTeamIndex;
	public int leftScore;
	public int rightScore;
	public boolean overtime = false;
	public int maxLeftDiff = 0;
	public int maxRightDiff = 0;
	
	ArrayList<Event> eventList = new ArrayList<Event>();
	ArrayList<PlayerGameStat> leftPlayers = new ArrayList<PlayerGameStat>();
	ArrayList<PlayerGameStat> rightPlayers = new ArrayList<PlayerGameStat>();
	
	HashMap<String, PlayerGameStat> leftPlayersMap = new HashMap<String, PlayerGameStat>();
	HashMap<String, PlayerGameStat> rightPlayersMap = new HashMap<String, PlayerGameStat>();
	
		
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
	public int getMaxScoreDiff () {
		return maxLeftDiff >= maxRightDiff ? maxLeftDiff : maxRightDiff;
	}
}
