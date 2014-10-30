package datamodel;

public class Game {
	public int leadChange;
	public int numOfQuarters;
	public Quarter[] quarters;
	
	public String homeTeam;
	public int homeScore;
	public TeamGameSummary homeSummary;
	
	public String awayTeam;
	public int awayScore;
	public TeamGameSummary awaySummary;
}
