package datamodel;

public class FoulEvent extends Event {

	String foulType;
	boolean fouled = false;
	String foulPlayer;
	
	public FoulEvent(String type, String actionPlayer, int quarter,
			int timeIndex, int actionTeamIndex, int curLeftScore,
			int curRightScore) {
		super(type, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore,
				curRightScore);
		// TODO Auto-generated constructor stub
		
	}
	
	public void setFoulType (String foulType) {
		this.foulType = foulType;
	}
	
	public void setFoulPlayer (String foulPlayer) {
		this.fouled = true;
		this.foulPlayer = foulPlayer;
	}
	
	public boolean isFouled () {
		return fouled;
	}
	
	public String getFoulPlayer () {
		return foulPlayer;
	}

}
