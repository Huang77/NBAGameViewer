package teamsort;
import java.util.Comparator;

import datamodel.Team;
public class TeamSortByFGA implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.fga < o2.fga) {
			return 1;
		} else if (o1.fga == o2.fga) {
			return 0;
		} else {
			return -1;
		}
	}
}
