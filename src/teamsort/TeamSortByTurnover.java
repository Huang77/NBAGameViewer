package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByTurnover implements Comparator<Team> {
	@Override
	public int compare(Team o1, Team o2) {
		// TODO Auto-generated method stub
			
		if (o1.tov < o2.tov) {
			return 1;
		} else if (o1.tov == o2.tov) {
			return 0;
		} else {
			return -1;
		}
	}
}
