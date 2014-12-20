package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOp2PA implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.op_2pa < o2.op_2pa) {
			return 1;
		} else if (o1.op_2pa == o2.op_2pa) {
			return 0;
		} else {
			return -1;
		}
	}
}