package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByFTA implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.fta < o2.fta) {
			return 1;
		} else if (o1.fta == o2.fta) {
			return 0;
		} else {
			return -1;
		}
	}
}
