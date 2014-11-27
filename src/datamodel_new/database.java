package datamodel_new;

import java.util.ArrayList;

public class database {
	ArrayList<GameStatData> gameStatDataList;
	ArrayList<WinLostCellData> winLostCellList;
	
	public database () {
		gameStatDataList = new ArrayList<GameStatData>();
		winLostCellList = new ArrayList<WinLostCellData>();
	}
	
	public void readWinLostCellData (String fileName) {
		
	}
	
	public void readAllGameStatData (String fileName) {
		
	}
}
