package datamodel_new;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
	public static final int gameNum = 82;
	public static final int teamNum = 30;
	
	public Team[] teams;
	public HashMap<String, Integer> teamIndex;
	public HashMap<Integer, Team> teamsMap;
	public HashMap<Integer, GameStatData> gameMap;
	public ArrayList<GameStatData> gameStatDataList;
	public ArrayList<WinLostCellData> winLostCellList;
	public int maxScoreDiff = Integer.MIN_VALUE;
	public int minScoreDiff = Integer.MAX_VALUE;
	
	public Database () {
		teams = new Team[teamNum];
		teamIndex = new HashMap<String, Integer>();
		teamsMap = new HashMap<Integer, Team>();
		gameMap = new HashMap<Integer, GameStatData>();
		gameStatDataList = new ArrayList<GameStatData>();
		winLostCellList = new ArrayList<WinLostCellData>();
	}
	
	public Database (String teamNameFile, String winLostFile, String gameStatFile, String allGameDataFile, String efficiencyFile) {
		this();
		readTeamNames(teamNameFile);
		readWinLostCellData(winLostFile);
		readAllGameStatData(gameStatFile);
		setGameIndexofCellData();
		readGameEvent(allGameDataFile);
		readPlayerEfficiency(efficiencyFile);
		System.out.println("Database read data finished!");
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
			String line = br.readLine();
			String[] array;
			String[] arrayForCityName;
			int index = 0;
			while ((line = br.readLine()) != null) {
				array = line.split(",");
				arrayForCityName = array[1].split(" ");
				
				teams[index] = new Team(arrayForCityName[0], arrayForCityName[arrayForCityName.length - 1], array[array.length - 1]);
				teams[index].index = index;
				teamIndex.put(array[1], index);
				teamsMap.put(index, teams[index]);
				
				teams[index].setOverall(splitStub(array[2]));
				teams[index].setHome(splitStub(array[3]));
				teams[index].setRoad(splitStub(array[4]));
				teams[index].setEastOppo(splitStub(array[5]));
				teams[index].setWestOppo(splitStub(array[6]));
				teams[index].setPreAllStar(splitStub(array[13]));
				teams[index].setPostAllStar(splitStub(array[14]));
				teams[index].setMarginLess3(splitStub(array[15]));
				teams[index].setMarginMore10(splitStub(array[16]));
				teams[index].setOct(splitStub(array[17]));
				teams[index].setNov(splitStub(array[18]));
				teams[index].setDec(splitStub(array[19]));
				teams[index].setJan(splitStub(array[20]));
				teams[index].setFeb(splitStub(array[21]));
				teams[index].setMar(splitStub(array[22]));
				teams[index].setApr(splitStub(array[23]));
				
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
				tempStatData.index = gameIndex;
				tempStatData.date = array[0];
				tempStatData.leftTeamIndex = teamIndex.get(array[2]);
				this.teamsMap.get(tempStatData.leftTeamIndex).gameIndex.add(gameIndex);
				
				tempStatData.leftScore = Integer.parseInt(array[3]);
				tempStatData.rightTeamIndex = teamIndex.get(array[4]);
				this.teamsMap.get(tempStatData.rightTeamIndex).gameIndex.add(gameIndex);
				
				tempStatData.rightScore = Integer.parseInt(array[5]);
				if (array.length >= 7 && array[6].equals("OT")) {
					tempStatData.overtime = true;
				}
				gameStatDataList.add(tempStatData);
				gameMap.put(tempStatData.index, tempStatData);
				scoreDiff = Math.abs(tempStatData.leftScore - tempStatData.rightScore);
				if (maxScoreDiff < scoreDiff) {
					maxScoreDiff = scoreDiff;
				}
				if (minScoreDiff > scoreDiff) {
					minScoreDiff = scoreDiff;
				}
				gameIndex++;
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
	
	public void readGameEvent (String fileName) {
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			
			file = new File(fileName);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			String line;
			String[] array;
			
			int gameIndex;  // 0
			int quarter;    // 1
			int timeIndex;  // 2
			int curLeftScore; // 4
			int curRightScore; // 5
			int actionTeamIndex;
			String actionTeam; //8
			String actionPlayer; //9
			String actionType; // 10
				
			GameStatData tempStatData;
			
			int scoreDiff = 0;
			
			while ((line = br.readLine()) != null) {
				array = line.split(",");
				if (array[8].equals("start") || array[8].endsWith("end")) continue;
				gameIndex = Integer.parseInt(array[0]);
				tempStatData = gameStatDataList.get(gameIndex);
				
				quarter = Integer.parseInt(array[1]);
				timeIndex = Integer.parseInt(array[2]);
				curLeftScore = Integer.parseInt(array[4]);
				curRightScore = Integer.parseInt(array[5]);
				
				scoreDiff = curLeftScore - curRightScore;
				if (scoreDiff >= 0) {
					if (tempStatData.maxLeftDiff < scoreDiff) {
						tempStatData.maxLeftDiff = scoreDiff;
					}
				} else {
					if (tempStatData.maxRightDiff < -scoreDiff) {
						tempStatData.maxRightDiff = -scoreDiff;
					}
				}

				
				//System.out.println(gameIndex + " : " + timeIndex);
				
				
				actionTeam = array[8];
				if (actionTeam.startsWith("LA")) {
					actionTeam = array[8].split(" ")[1];
					if (actionTeam.equals(teamsMap.get(tempStatData.leftTeamIndex).name)) {
						actionTeamIndex = tempStatData.leftTeamIndex;
					} else {
						actionTeamIndex = tempStatData.rightTeamIndex;
					}
				} else {
					if (actionTeam.startsWith(teamsMap.get(tempStatData.leftTeamIndex).city)) {
						actionTeamIndex = tempStatData.leftTeamIndex;
					} else {
						actionTeamIndex = tempStatData.rightTeamIndex;
					}
					
				}
				actionPlayer = array[9];
				actionType = array[10];
				
				if (actionType.equals("made")) {
					MadeScoreEvent event = new MadeScoreEvent(actionType, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore, curRightScore);
					int point = 0, distance;
					if (array[11].equals("free") || array[11].equals("clear") || array[11].equals("flagrant")) {
						point = 1;
					} else if (array[11].equals("technical")) {
						point = 2;  // this is technical miss
					} else {
						if (array[11].equals("2-pt")) {
							point = 2;
						} else if (array[11].equals("3-pt")) {
							point = 3;
						}
						
						if (array[12].equals("rim")) {
							distance = -1;
						} else {
							distance = Integer.parseInt(array[12]);
						}
						event.setDistance(distance);
						
						// length > 13 means there is assist
						if (array.length > 13) {
							event.setAssistedPlayer(array[13]);
						}
						
					}
					event.setPoint(point);
					tempStatData.eventList.add(event);
					
				} else if (actionType.equals("miss")) {
					MissScoreEvent event = new MissScoreEvent(actionType, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore, curRightScore);
					int point = 0, distance;
					if (array[11].equals("free") || array[11].equals("clear") || array[11].equals("flagrant")) {
						point = 1;
					} else if (array[11].equals("technical")) {
						point = 2;  // this is technical miss
					} else {
						if (array[11].equals("2-pt")) {
							point = 2;
						} else if (array[11].equals("3-pt")) {
							point = 3;
						}
						
						if (array[12].equals("rim")) {
							distance = -1;
						} else {
							distance = Integer.parseInt(array[12]);
						}
						event.setDistance(distance);
						
						// length > 13 means there is assist
						if (array.length > 13) {
							event.setBlockPlayer(array[13]);
						}
						
					}
					event.setPoint(point);
					tempStatData.eventList.add(event);
					
					
				} else if (actionType.equals("rebound")) {
					ReboundEvent event = new ReboundEvent(actionType, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore, curRightScore);
					event.setReboundType(array[11]);
					tempStatData.eventList.add(event);
					
				} else if (actionType.equals("foul")) {
					FoulEvent event = new FoulEvent(actionType, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore, curRightScore);
					event.setFoulType(array[11]);
					if (array.length > 13) {
						event.setFoulPlayer(array[13]);
					}
					tempStatData.eventList.add(event);
				} else if (actionType.equals("enter")) {
					EnterEvent event = new EnterEvent(actionType, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore, curRightScore);
					event.setLeftPlayer(array[13]);
					tempStatData.eventList.add(event);
				} else if (actionType.equals("turnover")) {
					TurnoverEvent event = new TurnoverEvent(actionType, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore, curRightScore);
					event.setTurnoverType(array[11]);
					if (array.length > 13) {
						event.setStealPlayer(array[13]);
					}
					tempStatData.eventList.add(event);
				} else if (actionType.equals("timeout")) {
					TimeoutEvent event = new TimeoutEvent(actionType, actionPlayer, quarter, timeIndex, actionTeamIndex, curLeftScore, curRightScore);
					event.setTimeoutType(array[11]);
					tempStatData.eventList.add(event);
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
	
	public void readPlayerEfficiency (String folderName) {
		File folder = new File(folderName);
		String[] fileNames = folder.list();
		int index;
		for (int i = 0; i < fileNames.length; i++) {
			index = Integer.parseInt(fileNames[i].split("-")[0]);
			readSingleGameEfficiency(index, folderName + "/" + fileNames[i]);
		}
		
	}
	public void readSingleGameEfficiency (int gameIndex, String fileName) {
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			
			file = new File(fileName);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			GameStatData game = gameMap.get(gameIndex);
			String line = br.readLine();
			String[] array = line.split(",");
			String leftTeamName = array[0];
			PlayerGameStat tempPlayer = new PlayerGameStat(array[1]);
			float sum = 0;
			for (int i = 2; i < array.length; i += 2) {
				sum += Integer.parseInt(array[i].substring(0, array[i].indexOf('p')));
			}
			int tempWidth, tempValue;
			for (int i = 2; i < array.length; i+= 2) {
				tempWidth = Integer.parseInt(array[i].substring(0, array[i].indexOf('p')));
				if (array[i + 1].equals("null")) {
					tempValue = 999;
				} else {
					tempValue = Integer.parseInt(array[i + 1]);
				}
				
				tempPlayer.addEfficiency(1.0f * tempWidth / sum, tempValue);
			}
			game.leftPlayers.add(tempPlayer);
			
			while ((line = br.readLine()) != null) {
				array = line.split(",");
				tempPlayer = new PlayerGameStat(array[1]);
				sum = 0;
				for (int i = 2; i < array.length; i += 2) {
					sum += Integer.parseInt(array[i].substring(0, array[i].indexOf('p')));
				}
				for (int i = 2; i < array.length; i+= 2) {
					tempWidth = Integer.parseInt(array[i].substring(0, array[i].indexOf('p')));
					if (array[i + 1].equals("null")) {
						tempValue = 999;
					} else {
						tempValue = Integer.parseInt(array[i + 1]);
					}
					tempPlayer.addEfficiency(1.0f * tempWidth / sum, tempValue);
				}
				if (array[0].equals(leftTeamName)) {
					game.leftPlayers.add(tempPlayer);
				} else {
					game.rightPlayers.add(tempPlayer);
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
