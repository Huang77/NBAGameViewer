package teamsort;
import java.util.Comparator;

import datamodel.Team;

public class TeamSortByLostRatio implements Comparator<Team> {

	@Override
	public int compare(Team o1, Team o2) {
		// TODO Auto-generated method stub
		
		double winRate1 = o1.overall[0] / (1.0 * (o1.overall[0] + o1.overall[1]));
		double winRate2 = o2.overall[0] / (1.0 * (o2.overall[0] + o2.overall[1]));
		
		if (winRate1 > winRate2) {
			return 1;
		} else if (winRate1 == winRate2) {
			return 0;
		} else {
			return -1;
		}
	}

}
