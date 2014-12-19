package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOpPoints implements Comparator<Team> {

	@Override
	public int compare(Team o1, Team o2) {
		if (o1.oppts < o2.oppts) {
			return 1;
		} else if (o1.oppts == o2.oppts) {
			return 0;
		} else {
			return -1;
		}
	}

}
