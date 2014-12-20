package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOp3Points implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.op_3p < o2.op_3p) {
			return 1;
		} else if (o1.op_3p == o2.op_3p) {
			return 0;
		} else {
			return -1;
		}
	}
}