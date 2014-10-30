package datafilter;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import datamodel.Player;
import datamodel.PositionType;

public class XMLReader {
	
	public static void main (String[] args) {
		XMLReader reader = new XMLReader();
		Player[] players = reader.readPlayerData("C:\\Users\\HXX\\Desktop\\sportdataanalysis\\NBA_Data\\Regular\\Player_Profile");
		
		for (int i = 0; i < players.length; i++) {
			System.out.println(players[i].full_name);
		}
	}

	public Document getDocument (String fileName) throws DocumentException {
		SAXReader reader = new SAXReader();
		return reader.read(new File(fileName));
	}
	/**
	 * read all the player information from the folder "C:\\Users\\HXX\\Desktop\\sportdataanalysis\\NBA_Data\\Regular\\Player_Profile"
	 * @param folderName
	 * @return
	 */
	public Player[] readPlayerData (String folderName) {
		File folder = new File(folderName);
		String[] fileNames = folder.list();
		int numOfPlayer = fileNames.length;
		Player[] players = new Player[numOfPlayer];
		
		Document doc;
		for (int i = 0; i < fileNames.length; i++) {
			try {
				doc = getDocument(folderName + "\\" + fileNames[i]);
				//System.out.println(fileNames[i]);
				players[i] = readSinglePlayer(doc.getRootElement());
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
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
		} else {
			System.out.println("Wrong Position Input :" + position);
			return null;
		}
	}
	
}


























