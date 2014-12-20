package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOpFGA implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.opfga < o2.opfga) {
			return 1;
		} else if (o1.opfga == o2.opfga) {
			return 0;
		} else {
			return -1;
		}
	}
}