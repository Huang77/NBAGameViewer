package datamodel_new;

public class MadeScoreEvent extends Event {
	String assistPlayer;
	int distance;
	int point; // 1 for freethrow, 2 for 2-pt, 3 for 3-pt
	boolean assisted = false;
	
	public MadeScoreEvent(String type, String actionPlayer, int quarter,
			int timeIndex, int actionTeamIndex, int curLeftScore,
			int curRightScore) {
		super(type, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore,
				curRightScore);
		// TODO Auto-generated constructor stub
		this.distance = distance;
		this.point = point;
	}
	
	public void setPoint (int point) {
		this.point = point;
	}
	public void setDistance (int distance) {
		this.distance = distance;
	}
	
	public void setAssistedPlayer(String player) {
		assistPlayer = player;
		assisted = true;
	}
	
	public boolean isAssisted () {
		return assisted;
	}
	
	public String assistedBy () {
		return assistPlayer;
	}
	
}
