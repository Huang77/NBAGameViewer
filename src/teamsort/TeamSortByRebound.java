package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByRebound implements Comparator<Team> {

	@Override
	public int compare(Team o1, Team o2) {
		if (o1.trb < o2.trb) {
			return 1;
		} else if (o1.trb == o2.trb) {
			return 0;
		} else {
			return -1;
		}
	}

}
