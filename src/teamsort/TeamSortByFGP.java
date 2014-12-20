package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByFGP implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.fgp < o2.fgp) {
			return 1;
		} else if (o1.fgp == o2.fgp) {
			return 0;
		} else {
			return -1;
		}
	}
}