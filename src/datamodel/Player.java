package datamodel;

public class Player {
	 
	// name attributes
	public String full_name;
	public String first_name;
	public String last_name;
	public String abbr_name;
	
	// body
	public int height;
	public int weight;
	public String birthday;
	
	// play related
	public PositionType position;
	public PositionType primary_position;
	public int jersey_number;
	public int experience;
	
	public Team team;
	
	public int games_played;
	// statistics
	
	public float points;
	public float blocked_att;
	public float turnovers;
	public float free_throws_att;
	public float field_goals_att;
	public float off_rebounds;
	public float two_points_att;
	public float assists;
	public float blocks;
	public float personal_fouls;
	public float two_points_made;
	public float flagrant_fouls;
	public float rebounds;
	public float three_points_made;
	public float minutes;
	public float def_rebounds;
	public float free_throws_made;
	public float field_goals_made;
	public float steals;
	public float three_points_att;
}
