package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOp3PP implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.op_3pp < o2.op_3pp) {
			return 1;
		} else if (o1.op_3pp == o2.op_3pp) {
			return 0;
		} else {
			return -1;
		}
	}
}