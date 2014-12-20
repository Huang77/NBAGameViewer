package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortBy3PA implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1._3pa < o2._3pa) {
			return 1;
		} else if (o1._3pa == o2._3pa) {
			return 0;
		} else {
			return -1;
		}
	}
}