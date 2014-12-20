package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByFoul implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.pf < o2.pf) {
			return 1;
		} else if (o1.pf == o2.pf) {
			return 0;
		} else {
			return -1;
		}
	}
}
