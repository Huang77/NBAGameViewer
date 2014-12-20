package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOpFoul  implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.oppf < o2.oppf) {
			return 1;
		} else if (o1.oppf == o2.oppf) {
			return 0;
		} else {
			return -1;
		}
	}
}
