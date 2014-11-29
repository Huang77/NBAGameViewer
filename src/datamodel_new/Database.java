package datamodel_new;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
	public final int teamNum = 30;
	public String[] teamNames;
	public HashMap<String, Integer> teamIndex;
	public ArrayList<GameStatData> gameStatDataList;
	public ArrayList<WinLostCellData> winLostCellList;
	public int maxScoreDiff = Integer.MIN_VALUE;
	public int minScoreDiff = Integer.MAX_VALUE;
	
	public Database () {
		teamNames = new String[teamNum];
		teamIndex = new HashMap<String, Integer>();
		gameStatDataList = new ArrayList<GameStatData>();
		winLostCellList = new ArrayList<WinLostCellData>();
	}
	
	public Database (String teamNameFile, String winLostFile, String gameStatFile) {
		this();
		readTeamNames(teamNameFile);
		readWinLostCellData(winLostFile);
		readAllGameStatData(gameStatFile);
		setGameIndexofCellData();
	}
	
	
	public void readTeamNames (String fileName) {
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			
			file = new File(fileName);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			// reader file content
			if (winLostCellList == null) {
				System.out.println("winLostCellList is null");
				return;
			} 
			String line;
			String[] array;
			int index = 0;
			while ((line = br.readLine()) != null) {
				array = line.split(",");
				teamNames[index] = array[1];
				teamIndex.put(array[1], index);
				index++;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
	
	public void readWinLostCellData (String fileName) {
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			
			file = new File(fileName);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			// reader file content
			if (winLostCellList == null) {
				System.out.println("winLostCellList is null");
				return;
			} 
			String line = br.readLine();
			String[] array;
			int[] win_lost;
			int leftTeamIndex = 0, topTeamIndex;
			
			while ((line = br.readLine()) != null) {
				array = line.split(",");
				for (int i = 2; i < array.length; i++) {
					WinLostCellData tempCellData = new WinLostCellData();
					topTeamIndex = i - 2;
					if (leftTeamIndex == topTeamIndex) {
						tempCellData.leftTeam = leftTeamIndex;
						tempCellData.topTeam = leftTeamIndex;
					} else {
						tempCellData.leftTeam = leftTeamIndex;
						tempCellData.topTeam = topTeamIndex;
						win_lost = splitStub(array[i]);
						tempCellData.leftWin = win_lost[0];
						tempCellData.topWin = win_lost[1];
					}
					winLostCellList.add(tempCellData);
				}
				leftTeamIndex++;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		
	}
	
	private int[] splitStub (String str) {
		int[] result = new int[2];
		String[] array = str.split("-");
		result[0] = Integer.parseInt(array[0]);
		result[1] = Integer.parseInt(array[1]);
		return result;
	}
	
	public void readAllGameStatData (String fileName) {
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			
			file = new File(fileName);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			// reader file content
			if (winLostCellList == null) {
				System.out.println("winLostCellList is null");
				return;
			} 
			String line = br.readLine();
			String[] array;
			int gameIndex = 0;
			int scoreDiff;
			while ((line = br.readLine()) != null) {
				array = line.split(",");
				GameStatData tempStatData = new GameStatData();
				tempStatData.index = gameIndex++;
				tempStatData.date = array[0];
				tempStatData.leftTeamIndex = teamIndex.get(array[2]);
				tempStatData.leftScore = Integer.parseInt(array[3]);
				tempStatData.rightTeamIndex = teamIndex.get(array[4]);
				tempStatData.rightScore = Integer.parseInt(array[5]);
				if (array.length >= 7 && array[6].equals("OT")) {
					tempStatData.overtime = true;
				}
				gameStatDataList.add(tempStatData);
				scoreDiff = Math.abs(tempStatData.leftScore - tempStatData.rightScore);
				if (maxScoreDiff < scoreDiff) {
					maxScoreDiff = scoreDiff;
				}
				if (minScoreDiff > scoreDiff) {
					minScoreDiff = scoreDiff;
				}
			}
			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}

	public void setGameIndexofCellData () {
		if (gameStatDataList.size() == 0 || winLostCellList.size() == 0) {
			System.out.println("gameStatDataList or winLostCellList not added!");
			return;
		}
		GameStatData tempStatData;
		WinLostCellData tempCellData;
		for (int i = 0; i < gameStatDataList.size(); i++) {
			tempStatData = gameStatDataList.get(i);
			for (int j = 0; j < winLostCellList.size(); j++) {
				tempCellData = winLostCellList.get(j);
				if ((tempCellData.leftTeam == tempStatData.leftTeamIndex && tempCellData.topTeam == tempStatData.rightTeamIndex) 
						|| (tempCellData.topTeam == tempStatData.leftTeamIndex && tempCellData.leftTeam == tempStatData.rightTeamIndex)) {
					tempCellData.gameIndex.add(tempStatData.index);
				}
			}
		}
	}
	
/******** read file template **********************************************************
	File file = null;
	FileReader fr = null;
	BufferedReader br = null;
	
	try {
		
		file = new File(fileName);
		fr = new FileReader(file);
		br = new BufferedReader(fr);
		
		// reader file content
		if (winLostCellList == null) {
			System.out.println("winLostCellList is null");
			return;
		} 
		String line;
		while ((line = br.readLine()) != null) {
			
		}
		
		
	} catch (IOException ioe) {
		ioe.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		if (fr != null) {
			try {
				fr.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
*************************************************************************************/
}
