package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortBy2PA implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1._2pa < o2._2pa) {
			return 1;
		} else if (o1._2pa == o2._2pa) {
			return 0;
		} else {
			return -1;
		}
	}
}