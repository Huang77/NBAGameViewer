package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOpRebound implements Comparator<Team> {
	@Override
	public int compare(Team o1, Team o2) {
		if (o1.optrb < o2.optrb) {
			return 1;
		} else if (o1.optrb == o2.optrb) {
			return 0;
		} else {
			return -1;
		}
	}
}
