package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOp2Points implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.op_2p < o2.op_2p) {
			return 1;
		} else if (o1.op_2p == o2.op_2p) {
			return 0;
		} else {
			return -1;
		}
	}
}