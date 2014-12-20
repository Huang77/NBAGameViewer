package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByOpFTP implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.opftp < o2.opftp) {
			return 1;
		} else if (o1.opftp == o2.opftp) {
			return 0;
		} else {
			return -1;
		}
	}
}

