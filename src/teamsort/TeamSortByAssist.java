package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByAssist implements Comparator<Team> {
	@Override
	public int compare(Team o1, Team o2) {
		// TODO Auto-generated method stub
			
		if (o1.ast < o2.ast) {
			return 1;
		} else if (o1.ast == o2.ast) {
			return 0;
		} else {
			return -1;
		}
	}
}
