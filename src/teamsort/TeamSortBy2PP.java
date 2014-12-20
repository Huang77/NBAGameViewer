package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortBy2PP implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1._2pp < o2._2pp) {
			return 1;
		} else if (o1._2pp == o2._2pp) {
			return 0;
		} else {
			return -1;
		}
	}
}
