package teamsort;

import java.util.Comparator;

import datamodel.Team;

public class TeamSortByFTP implements Comparator<Team> {
	@Override
	public int compare (Team o1, Team o2) {
		
		if (o1.ftp < o2.ftp) {
			return 1;
		} else if (o1.ftp == o2.ftp) {
			return 0;
		} else {
			return -1;
		}
	}
}
