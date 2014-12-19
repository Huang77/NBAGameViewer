package datamodel;

import java.util.ArrayList;

public class WinLostCellData {
	int leftTeam, topTeam;
	int leftWin = 0, topWin = 0;
	ArrayList<Integer> gameIndex = new ArrayList<Integer>();
	
	public int getLeftTeamIndex () {
		return leftTeam;
	}
	
	public int getTopTeamIndex () {
		return topTeam;
	}
	
	public int getLeftWin () {
		return leftWin;
	}
	
	public int getTopWin () {
		return topWin;
	}
	
	public ArrayList<Integer> getGameIndexes () {
		return gameIndex;
	}
	
	public boolean isSelfTeam () {
		return leftTeam == topTeam;
	}
	public boolean hasPlayed () {
		return (leftWin > 0 || topWin > 0);
	}
	
	public int winCompare () {
		if (leftWin > topWin) {
			return 1;
		} else if (leftWin == topWin) {
			return 0;
		} else {
			return -1;
		}
	}
}
