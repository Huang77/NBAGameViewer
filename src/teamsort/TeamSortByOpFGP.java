package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOpFGP implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.opfgp < o2.opfgp) {
			return 1;
		} else if (o1.opfgp == o2.opfgp) {
			return 0;
		} else {
			return -1;
		}
	}
}
