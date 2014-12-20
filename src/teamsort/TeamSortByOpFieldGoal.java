package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOpFieldGoal implements Comparator<Team> {

	@Override
	public int compare(Team o1, Team o2) {
		if (o1.opfg < o2.opfg) {
			return 1;
		} else if (o1.opfg == o2.opfg) {
			return 0;
		} else {
			return -1;
		}
	}

}