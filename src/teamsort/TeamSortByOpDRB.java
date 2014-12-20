package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOpDRB implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.opdrb < o2.opdrb) {
			return 1;
		} else if (o1.opdrb == o2.opdrb) {
			return 0;
		} else {
			return -1;
		}
	}
}
