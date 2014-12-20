package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOpFreeThrow implements Comparator<Team> {

		@Override
		public int compare(Team o1, Team o2) {
			if (o1.opft < o2.opft) {
				return 1;
			} else if (o1.opft == o2.opft) {
				return 0;
			} else {
				return -1;
			}
		}

	
}
