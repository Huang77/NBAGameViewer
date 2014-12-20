package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByORB implements Comparator<Team> {
		@Override
		public int compare (Team o1, Team o2) {
			
			if (o1.orb < o2.orb) {
				return 1;
			} else if (o1.orb == o2.orb) {
				return 0;
			} else {
				return -1;
			}
		}
}
