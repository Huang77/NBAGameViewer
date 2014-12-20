package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortBy3Points implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1._3p < o2._3p) {
			return 1;
		} else if (o1._3p == o2._3p) {
			return 0;
		} else {
			return -1;
		}
	}
}
