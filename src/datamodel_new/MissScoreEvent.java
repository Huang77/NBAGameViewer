package datamodel_new;

public class MissScoreEvent extends Event {
	String blockPlayer;
	int distance;
	int point; // 1 for freethrow, 2 for 2-pt, 3 for 3-pt
	boolean blocked = false;

	public MissScoreEvent(String type, String actionPlayer, int quarter,
			int timeIndex, int actionTeamIndex, int curLeftScore,
			int curRightScore) {
		super(type, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore,
				curRightScore);
		// TODO Auto-generated constructor stub

	}
	
	public void setPoint (int point) {
		this.point = point;
	}
	
	public void setDistance (int distance) {
		this.distance = distance;
	}
	
	public void setBlockPlayer (String player) {
		blockPlayer = player;
		blocked = true;
	}
	
	public boolean isBlocked () {
		return blocked;
	}
	
	public String blockedBy () {
		return blockPlayer;
	}

}
