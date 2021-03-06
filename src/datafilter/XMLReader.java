package datafilter;



import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import datamodel.Game;
import datamodel.Player;
import datamodel.PositionType;
import datamodel.Quarter;
import datamodel.Team;
import datamodel.Event;

public class XMLReader {
	
	public static void main (String[] args) {
		XMLReader reader = new XMLReader();
		HashMap<String, Team> teams = reader.readTeamData("C:\\Users\\HXX\\Desktop\\sportdataanalysis\\NBA_Data\\Regular\\Team_Profile");
		
		Iterator iter = teams.values().iterator();
		while (iter.hasNext()) {
			System.out.println(((Team)iter.next()).name);
		}
		
	}
	
	public void getPlayerData (HashMap<String, Player> players, String folderName) {
		players = readPlayerData(folderName);
	}
	public void getTeamData (HashMap<String, Team> teams, String folderName) {
		teams = readTeamData(folderName);
	}

	

	private Document getDocument (String fileName) throws DocumentException {
		SAXReader reader = new SAXReader();
		return reader.read(new File(fileName));
	}
	/**
	 * read all the player information from the folder "C:\\Users\\HXX\\Desktop\\sportdataanalysis\\NBA_Data\\Regular\\Player_Profile"
	 * @param folderName
	 * @return
	 */
	private HashMap<String, Player> readPlayerData (String folderName) {
		File folder = new File(folderName);
		String[] fileNames = folder.list();
		int numOfPlayer = fileNames.length;
		HashMap<String, Player> players = new HashMap<String, Player>();
		
		Document doc;
		for (int i = 0; i < fileNames.length; i++) {
			try {
				doc = getDocument(folderName + "\\" + fileNames[i]);
				//System.out.println(fileNames[i]);
				Player player = readSinglePlayer(doc.getRootElement());
				players.put(player.full_name, player);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		//System.out.println(fileNames.length);
		//System.out.println(players.size());
		return players;
	}
	/** 
	 * read single player information
	 * @param playerElement
	 * @return
	 */
	private Player readSinglePlayer (Element playerElement) {
		Player player = new Player();
		
		player.birthday = playerElement.attributeValue("birthdate") == null ? null : playerElement.attributeValue("birthdate");
		player.experience = Integer.parseInt(playerElement.attributeValue("experience") == null ? "-1" : playerElement.attributeValue("experience")) ;
		player.jersey_number = Integer.parseInt(playerElement.attributeValue("jersey_number") == null ? "-1" : playerElement.attributeValue("jersey_number"));
		player.weight = Integer.parseInt(playerElement.attributeValue("weight"));
		player.height = Integer.parseInt(playerElement.attributeValue("height"));
		player.abbr_name = playerElement.attributeValue("abbr_name");
		player.last_name = playerElement.attributeValue("last_name");
		player.first_name = playerElement.attributeValue("first_name");
		player.full_name = playerElement.attributeValue("full_name");
		
		player.position = getPositionType(playerElement.attributeValue("position"));
		player.primary_position = getPositionType(playerElement.attributeValue("primary_position"));
	
		// read statistics data
		if (playerElement.element("seasons") != null) {
			player.team = playerElement.element("seasons").element("season").element("team").attributeValue("name");
			Element statistics = playerElement.element("seasons").element("season").element("team").element("statistics").element("total");
			player.games_played = Integer.parseInt(statistics.attributeValue("games_played"));
			
			statistics = playerElement.element("seasons").element("season").element("team").element("statistics").element("average");
			player.two_points_att = Float.parseFloat(statistics.attributeValue("two_points_att"));
			player.two_points_made = Float.parseFloat(statistics.attributeValue("two_points_made"));
			player.three_points_att = Float.parseFloat(statistics.attributeValue("three_points_att"));
			player.three_points_made = Float.parseFloat(statistics.attributeValue("three_points_made"));
			player.rebounds = Float.parseFloat(statistics.attributeValue("rebounds"));
			player.flagrant_fouls = Float.parseFloat(statistics.attributeValue("flagrant_fouls"));
			player.points = Float.parseFloat(statistics.attributeValue("points"));
			player.personal_fouls = Float.parseFloat(statistics.attributeValue("personal_fouls"));
			player.blocks = Float.parseFloat(statistics.attributeValue("blocks"));
			player.steals = Float.parseFloat(statistics.attributeValue("steals"));
			player.turnovers = Float.parseFloat(statistics.attributeValue("turnovers"));
			player.free_throws_att = Float.parseFloat(statistics.attributeValue("free_throws_att"));
			player.free_throws_made = Float.parseFloat(statistics.attributeValue("free_throws_made"));
			player.blocked_att = Float.parseFloat(statistics.attributeValue("blocked_att"));
			player.field_goals_att = Float.parseFloat(statistics.attributeValue("field_goals_att"));
			player.field_goals_made = Float.parseFloat(statistics.attributeValue("field_goals_made"));
			player.minutes = Float.parseFloat(statistics.attributeValue("minutes"));
			player.def_rebounds = Float.parseFloat(statistics.attributeValue("def_rebounds"));
			player.off_rebounds = Float.parseFloat(statistics.attributeValue("off_rebounds"));
			
		}

		return player;
	}
	/**
	 * translate input position string to enum PositionType
	 * @param position
	 * @return
	 */
	private PositionType getPositionType (String position){
		if (position.equals("G")) {
			return PositionType.G;
		} else if (position.equals("PG")) {
			return PositionType.PG;
		} else if (position.equals("SG")) {
			return PositionType.SG;
		} else if (position.equals("F")) {
			return PositionType.F;
		} else if (position.equals("SF")) {
			return PositionType.SF;
		} else if (position.equals("PF")) {
			return PositionType.PF;
		} else if (position.equals("C")) {
			return PositionType.C;
		} else if (position.equals("G-F")) {
			return PositionType.G_F;
		} else if (position.equals("C-F")) {
			return PositionType.C_F;
		} else if (position.equals("F-C")) {
			return PositionType.F_C;
		} else if (position.equals("F-G")) {
			return PositionType.F_G;
		} else {
			System.out.println("Wrong Position Input :" + position);
			return null;
		}
	}
	
	/**
	 * read all team data
	 * @param folderName
	 * @return
	 */
	private HashMap<String, Team> readTeamData (String folderName) {
		File folder =  new File(folderName);
		String[] fileNames = folder.list();
		HashMap<String, Team> teams= new HashMap<String, Team>();
		
		Document doc;
		for (int i = 0; i < fileNames.length; i++) {
			try {
				doc = getDocument(folderName + "\\" + fileNames[i]);
				Team team = readSingleTeam(doc.getRootElement());
				teams.put(team.name, team);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return teams;
	}
	
	private Team readSingleTeam (Element root) {
		Team team = new Team();
		team.alias = root.attributeValue("alias");
		team.name = root.attributeValue("name");
		if (root.element("coaches") != null) {
			team.coach = root.element("coaches").element("coach").attributeValue("full_name");
		} else {
			team.coach = "null";
		}
		
		team.conference = root.element("hierarchy").element("conference").attributeValue("alias");
		team.division = root.element("hierarchy").element("division").attributeValue("alias");
		
		int num = root.element("players").elements().size();
		team.players = new String[num];
		Iterator<Element> iter = root.elementIterator("players");
		num = 0;
		while (iter.hasNext()) {
			team.players[num++] = iter.next().attributeValue("full_name");
		}
		return team;
	}
	
	
	public Game readSingleGameData (String fileName) {
		Game game = new Game();
		Document doc;
		try {
			doc = getDocument(fileName);
			Element gameRoot = doc.getRootElement();
			game.leadChange = Integer.parseInt(gameRoot.attributeValue("lead_changes"));
			game.numOfQuarters = Integer.parseInt(gameRoot.attributeValue("quarter"));
			game.quarters = new Quarter[game.numOfQuarters];
			
			Element temp = gameRoot.element("scoring").element("home");
			game.homeTeam = temp.attributeValue("name");
			game.homeScore = Integer.parseInt(temp.attributeValue("points"));
			temp = gameRoot.element("scoring").element("away");
			game.awayTeam = temp.attributeValue("name");
			game.awayScore = Integer.parseInt(temp.attributeValue("points"));
			
			Iterator<Element> quarterIter = gameRoot.elementIterator("quarter");
			Element tempQuarter;
			int index;
			while (quarterIter.hasNext()) {
				tempQuarter = (Element)quarterIter.next();
				index = Integer.parseInt(tempQuarter.attributeValue("number")) - 1;
				readGameEvent(tempQuarter, game.quarters[index]);
			}
			
			
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return game;
		
	}
	
	private void readGameEvent (Element quarterElement, Quarter quarter) {
		Element temp = quarterElement.element("scoring");
		quarter.homeScore = Integer.parseInt(temp.element("home").attributeValue("points"));
		quarter.awayScore = Integer.parseInt(temp.element("away").attributeValue("points"));
		int numOfEvent = quarterElement.elements().size();
		quarter.events = new Event[numOfEvent];
		int index = 0;
		Iterator<Element> eventIter = quarterElement.element("quarter").element("events").elementIterator();
		while (eventIter.hasNext()) {
			quarter.events[index++] = readSingleEvent((Element)eventIter.next());
		}
		
	}
	
	private Event readSingleEvent (Element eventElement) {
		String event_type = eventElement.attributeValue("event_type");
		Event event;
		if (event_type.equals("rebound")) {
			
		} else if (event_type.equals("twopointmiss")) {
			
		} else if (event_type.equals("threepointmiss")) {
			
		} else if (event_type.equals("freethrowmiss")) {
			
		} else if (event_type.equals("twopointmade")) {
		
		} else if (event_type.equals("threepointmade")) {
			
		} else if (event_type.equals("freethrowmade")) {
			
		} else if (event_type.equals("shootingfoul")) {
			
		} else if (event_type.equals("personalfoul")) {
			
		} else if (event_type.equals("turnover")) {
			
		} else if (event_type.equals("teamtimeout")) {
			
		} else if (event_type.equals("endperiod")) {
			
		}
		
	}
	
}


























