package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOpBlock implements Comparator<Team> {
	@Override
	public int compare(Team o1, Team o2) {
		if (o1.opblk < o2.opblk) {
			return 1;
		} else if (o1.opblk == o2.opblk) {
			return 0;
		} else {
			return -1;
		}
	}
}
