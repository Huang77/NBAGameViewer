package datamodel;

public class TurnoverEvent extends Event {
	
	String turnoverType;
	boolean stole = false;
	String stealPlayer;
	
	public TurnoverEvent(String type, String actionPlayer, int quarter,
			int timeIndex, int actionTeamIndex, int curLeftScore,
			int curRightScore) {
		super(type, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore,
				curRightScore);
		// TODO Auto-generated constructor stub
	}
	
	public void setTurnoverType (String turnoverType) {
		this.turnoverType = turnoverType;
	}
	
	public void setStealPlayer (String player) {
		stole = true;
		stealPlayer = player;
	}
	
	public boolean isStole () {
		return stole;
	}
	
	public String getStealPlayer () {
		return stealPlayer;
	}
	
	public String getTurnoverType () {
		return turnoverType;
	}
	
}
