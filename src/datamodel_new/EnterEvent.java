package datamodel_new;

public class EnterEvent extends Event {
	String leftPlayer;
	
	public EnterEvent(String type, String actionPlayer, int quarter,
			int timeIndex, int actionTeamIndex, int curLeftScore,
			int curRightScore) {
		super(type, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore,
				curRightScore);
		// TODO Auto-generated constructor stub
	}	
	public void setLeftPlayer (String player) {
		this.leftPlayer = player;
	}
}
