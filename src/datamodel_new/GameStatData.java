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
	
	public boolean isLeftWin () {
		return leftScore > rightScore ? true : false;
	}
	public int getWinTeamIndex () {
		return isLeftWin() ? leftTeamIndex : rightTeamIndex;
	}
	public ArrayList<Event> getEventList () {
		return this.eventList;
	}
}
