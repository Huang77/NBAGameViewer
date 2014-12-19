package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByBlock implements Comparator<Team> {
	@Override
	public int compare(Team o1, Team o2) {
		// TODO Auto-generated method stub
			
		if (o1.blk < o2.blk) {
			return 1;
		} else if (o1.blk == o2.blk) {
			return 0;
		} else {
			return -1;
		}
	}
}
