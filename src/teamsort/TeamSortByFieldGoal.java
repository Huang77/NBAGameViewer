package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByFieldGoal implements Comparator<Team> {

	@Override
	public int compare(Team o1, Team o2) {
		if (o1.fg < o2.fg) {
			return 1;
		} else if (o1.fg == o2.fg) {
			return 0;
		} else {
			return -1;
		}
	}

}
