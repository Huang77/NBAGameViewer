package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortBy2Points implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1._2p < o2._2p) {
			return 1;
		} else if (o1._2p == o2._2p) {
			return 0;
		} else {
			return -1;
		}
	}
}
