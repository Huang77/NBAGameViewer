package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOpTurnover implements Comparator<Team> {
	@Override
	public int compare(Team o1, Team o2) {
		// TODO Auto-generated method stub
			
		if (o1.optov < o2.optov) {
			return 1;
		} else if (o1.optov == o2.optov) {
			return 0;
		} else {
			return -1;
		}
	}
}
