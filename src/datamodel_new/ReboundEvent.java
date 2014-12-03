package datamodel_new;

public class ReboundEvent extends Event {
	String reboundType;
	boolean isDefensive = false;
	
	public ReboundEvent(String type, String actionPlayer, int quarter,
			int timeIndex, int actionTeamIndex, int curLeftScore,
			int curRightScore) {
		super(type, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore,
				curRightScore);
		// TODO Auto-generated constructor stub
	}
	public void setReboundType (String reboundType) {
		this.reboundType = reboundType;
		if (reboundType.equals("Defensive")) {
			isDefensive = true;
		} else if (reboundType.equals("Offensive")) {
			isDefensive = false;
		}
	}
	
}
