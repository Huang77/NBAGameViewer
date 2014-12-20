package teamsort;

import java.util.Arrays;

import datamodel.Database;

public enum TeamSortType {
	Name,
	
	Overall,
	
	Home,
	Road,
	EastOppo,
	WestOppo,
	PreAllStar,
	PostAllStar,
	MarginLess3,
	MarginMore10,
	
	Oct,
	Nov,
	Dec,
	Jan,
	Feb,
	Mar,
	Apr,
	
	Points,
	OpPoints,
	Rebound,
	OpRebound,
	

	Assist,
	OpAssist,
	Block,
	OpBlock,
	Turnover,
	OpTurnover,
	
	FieldGoal,
	FieldGoalA,
	FieldGoalP,
	OpFieldGoal,
	OpFieldGoalA,
	OpFieldGoalP,
	
	TwoPoint,
	TwoPointA,
	TwoPointP,
	OpTwoPoint,
	OpTwoPointA,
	OpTwoPointP,
	
	ThreePoint,
	ThreePointA,
	ThreePointP,
	OpThreePoint,
	OpThreePointA,
	OpThreePointP,
	
	FreeThrow,
	FreeThrowA,
	FreeThrowP,
	OpFreeThrow,
	OpFreeThrowA,
	OpFreeThrowP,
	
	DFRebound,
	OpDFRebound,
	OFRebound,
	OpOFRebound,
	
	Foul,
	OpFoul;
	
	static public int[] getSortResult (String type, boolean oppo) {
		if (Database.teams == null) {
			System.out.println("Database.teams not initialized!");
			System.exit(0);
		}
		int[] verticalOrder = new int[Database.teamNum];
		
		if (type.equals("Points")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByPoints());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpPoints());
			}
		} else if (type.equals("Rebound")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByRebound());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpRebound());
			}
		} else if (type.equals("Assist")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByAssist());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpAssist());
			}
		}  else if (type.equals("Block")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByBlock());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpBlock());
			}
		} else if (type.equals("Turnover")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByTurnover());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpTurnover());
			}
		} else if (type.equals("Field Goal")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByFieldGoal());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpFieldGoal());
			}
		} else if (type.equals("FGA")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByFGA());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpFGA());
			}
		} else if (type.equals("FGP%")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByFGP());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpFGP());
			}
		} else if (type.equals("2-Points")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortBy2Points());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOp2Points());
			}
		} else if (type.equals("2PA")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortBy2PA());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOp2PA());
			}
		} else if (type.equals("2PP%")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortBy2PP());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOp2PP());
			}
		} else if (type.equals("3-Points")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortBy3Points());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOp3Points());
			}
		} else if (type.equals("3PA")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortBy3PA());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOp3PA());
			}
		} else if (type.equals("3PP%")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortBy3PP());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOp3PP());
			}
		} else if (type.equals("Free Throw")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByFreeThrow());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpFreeThrow());
			}
		} else if (type.equals("FTA")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByFTA());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpFTA());
			}
		} else if (type.equals("FTP%")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByFTP());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpFTP());
			}
		} else if (type.equals("Personal Foul")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByFoul());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpFoul());
			}
		} else if (type.equals("Offense RB")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByORB());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpORB());
			}
		} else if (type.equals("Defense RB")) {
			if (!oppo) {
				Arrays.sort(Database.teams, new TeamSortByDRB());
			} else {
				Arrays.sort(Database.teams, new TeamSortByOpDRB());
			}
		} else if (type.equals("WinRatio")) {
			Arrays.sort(Database.teams, new TeamSortByOverall());
		} else if (type.equals("LostRatio")) {
			Arrays.sort(Database.teams, new TeamSortByLostRatio());
		} else {
			for (int i = 0; i < Database.teamNum; i++) {
				verticalOrder[i] = i;
			}
			return verticalOrder;
		}
		
		for (int i = 0; i < Database.teamNum; i++) {
			verticalOrder[Database.teams[i].index] = i;
		}
		return verticalOrder;
	}
	
	
	
	static public TeamSortType getSortTypeByString (String type, boolean oppo) {
		if (type.equals("Points")) {
			if (!oppo) {
				return TeamSortType.Points;
			} else {
				return TeamSortType.OpPoints;
			}
		} else if (type.equals("Rebound")) {
			if (!oppo) {
				return TeamSortType.Rebound;
			} else {
				return TeamSortType.OpRebound;
			}
		} else if (type.equals("Assist")) {
			if (!oppo) {
				return TeamSortType.Assist;
			} else {
				return TeamSortType.OpAssist;
			}
		}  else if (type.equals("Block")) {
			if (!oppo) {
				return TeamSortType.Block;
			} else {
				return TeamSortType.OpBlock;
			}
		} else if (type.equals("Turnover")) {
			if (!oppo) {
				return TeamSortType.Turnover;
			} else {
				return TeamSortType.OpTurnover;
			}
		} else if (type.equals("Field Goal")) {
			if (!oppo) {
				return TeamSortType.FieldGoal;
			} else {
				return TeamSortType.OpFieldGoal;
			}
		} else if (type.equals("FGA")) {
			if (!oppo) {
				return TeamSortType.FieldGoalA;
			} else {
				return TeamSortType.OpFieldGoalA;
			}
		} else if (type.equals("FGP%")) {
			if (!oppo) {
				return TeamSortType.FieldGoalP;
			} else {
				return TeamSortType.OpFieldGoalP;
			}
		} else if (type.equals("2-Points")) {
			if (!oppo) {
				return TeamSortType.TwoPoint;
			} else {
				return TeamSortType.OpTwoPoint;
			}
		} else if (type.equals("2PA")) {
			if (!oppo) {
				return TeamSortType.TwoPointA;
			} else {
				return TeamSortType.OpTwoPointA;
			}
		} else if (type.equals("2PP%")) {
			if (!oppo) {
				return TeamSortType.TwoPointP;
			} else {
				return TeamSortType.OpTwoPointP;
			}
		} else if (type.equals("3-Points")) {
			if (!oppo) {
				return TeamSortType.ThreePoint;
			} else {
				return TeamSortType.OpThreePoint;
			}
		} else if (type.equals("3PA")) {
			if (!oppo) {
				return TeamSortType.ThreePointA;
			} else {
				return TeamSortType.OpThreePointA;
			}
		} else if (type.equals("3PP%")) {
			if (!oppo) {
				return TeamSortType.ThreePointP;
			} else {
				return TeamSortType.OpThreePointP;
			}
		} else if (type.equals("Free Throw")) {
			if (!oppo) {
				return TeamSortType.FreeThrow;
			} else {
				return TeamSortType.OpFreeThrow;
			}
		} else if (type.equals("FTA")) {
			if (!oppo) {
				return TeamSortType.FreeThrowA;
			} else {
				return TeamSortType.OpFreeThrowA;
			}
		} else if (type.equals("FTP%")) {
			if (!oppo) {
				return TeamSortType.FreeThrowP;
			} else {
				return TeamSortType.OpFreeThrowP;
			}
		} else if (type.equals("Personal Foul")) {
			if (!oppo) {
				return TeamSortType.Foul;
			} else {
				return TeamSortType.OpFoul;
			}
		} else if (type.equals("Offense RB")) {
			if (!oppo) {
				return TeamSortType.OFRebound;
			} else {
				return TeamSortType.OpOFRebound;
			}
		} else if (type.equals("Defense RB")) {
			if (!oppo) {
				return TeamSortType.DFRebound;
			} else {
				return TeamSortType.OpDFRebound;
			}
		}
		
		return TeamSortType.Name;
	}
	
}
