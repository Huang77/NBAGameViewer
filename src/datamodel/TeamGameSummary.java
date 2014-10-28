package datamodel;

public class TeamGameSummary {
	Team team;
	
	PlayerGameSummary[] playerSummaryList;
	int[] quarterPoints;
	
	// statistics
	float field_goals_made;
	float field_goals_att;
	float field_goals_pct;
	
	float three_points_made;
	float three_points_att;
	float three_points_pct;
	
	float two_points_made;
	float two_points_att;
	float two_points_pct;
	
	float block_att;
	
	float free_throws_made;
	float free_throws_att;
	float free_throws_pct;
	
	float offensive_rebounds;
	float defensive_rebounds;
	float rebounds;
	
	float assist;
	float turnovers;
	float steals;
	float blocks;
	float assists_turnover_ratio;
	float personal_fouls;
	float points;
	float fast_break_pts;
	float paint_pts;
	float second_chance_pts;
	float team_turnovers;
	float points_off_turnovers;
	float team_rebounds;
	float flagrant_fouls;
	float player_tech_fouls;
	float team_tech_fouls;
	float coach_tech_fouls;
}
