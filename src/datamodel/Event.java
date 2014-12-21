package datamodel;

public class Event {
	int quarter;
	int timeIndex;
	String timeDisplay;
	String type;
	
	int actionTeamIndex;
	String actionPlayer;
	public int curLeftScore, curRightScore;
	
	public Event (String type, String actionPlayer, int quarter, int timeIndex, int actionTeamIndex, int curLeftScore, int curRightScore) {
		this.quarter = quarter;
		this.actionTeamIndex = actionTeamIndex;
		this.curLeftScore = curLeftScore;
		this.curRightScore = curRightScore;
		this.type = type;
		this.actionPlayer = actionPlayer;
		this.timeIndex = timeIndex;
	}
	
	
	public static int timeTranslate (String timeDisplay, int quarter) {
		String[] array = timeDisplay.split(":");
		int min = Integer.parseInt(array[0]);
		int sec = Integer.parseInt(array[1]);
		int milSec = Integer.parseInt(array[2]);
		return 720 - (min * 60 + sec);
	}
	public int getTimeIndex () {
		return timeIndex;
	}
	public String getType () {
		return type;
	}
	public int getScoreDiff () {
		return curLeftScore - curRightScore;
	}
	public int getActionTeamIndex () {
		return actionTeamIndex;
	}
	public int getQuarter () {
		return quarter;
	}
}
