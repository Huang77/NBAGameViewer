package viewmodel;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



import java.util.Iterator;
import java.util.Map;

import processing.core.PApplet;
import datamodel_new.Database;
import datamodel_new.Team;
import datamodel_new.TeamSortByOverall;
import datamodel_new.TeamSortType;

public class TimeView {
	
	int leftTopX, leftTopY;
	int circleMarginX = 0, circleMarginY = 0;
	int width, height;
	
	int attrBarStartX = 800;
	
	Database database;
	int[] verticalOrder;  // vertical order of the teams 
	int teamGap = 28;
	
	HashMap<Integer, WinLostLine_V2> teamCircleMap = new HashMap<Integer, WinLostLine_V2>();
	
	HashMap<Integer, AttributeRect> pointsAttrMap = new HashMap<Integer, AttributeRect>();
	HashMap<Integer, AttributeRect> reboundAttrMap = new HashMap<Integer, AttributeRect>();
	HashMap<Integer, AttributeRect> assistAttrMap = new HashMap<Integer, AttributeRect>();
	HashMap<Integer, AttributeRect> turnoverAttrMap = new HashMap<Integer, AttributeRect>();
	HashMap<Integer, AttributeRect> blockAttrMap = new HashMap<Integer, AttributeRect>();
	
	public TimeView (Database database) {
		this.database = database;
		verticalOrder = new int[database.teamNum];
	}
	
	public void setPosition (int x, int y, int width, int height) {
		this.leftTopX = x;
		this.leftTopY = y;
		this.width = width;
		this.height = height;
	}
	
	public void setAllTeam () {
		setVerticalOrder(TeamSortType.Name);
		for (int i = 0; i < database.teamNum; i++) {
			setSingleTeam(i);
			setPointsAttrRect(i);
			setReboundAttrRect(i);
			setAssistAttrRect(i);
			setTurnoverAttrRect(i);
			setBlockAttrRect(i);
		}
	}
	
	public void setSingleTeam (int teamIndex) {
		WinLostLine_V2 line = new WinLostLine_V2(teamIndex, database);
		line.setPosition(leftTopX, leftTopY + this.verticalOrder[teamIndex] * (teamGap));
		teamCircleMap.put(teamIndex, line);
	}
	public void setPointsAttrRect (int teamIndex) {
		AttributeRect rect = new AttributeRect(database, teamIndex, "pts", database.teamsMap.get(teamIndex).pts, database.teamsMap.get(teamIndex).oppts);
		rect.setPosition(leftTopX + attrBarStartX + 80 , leftTopY + this.verticalOrder[teamIndex] * (teamGap) + 10);
		pointsAttrMap.put(teamIndex, rect);
	}
	public void setReboundAttrRect (int teamIndex) {
		AttributeRect rect = new AttributeRect(database, teamIndex, "trb", database.teamsMap.get(teamIndex).trb, database.teamsMap.get(teamIndex).optrb);
		rect.setPosition(leftTopX + attrBarStartX + 2 * 80, leftTopY + this.verticalOrder[teamIndex] * (teamGap)+ 10);
		reboundAttrMap.put(teamIndex, rect);
	}
	public void setAssistAttrRect (int teamIndex) {
		AttributeRect rect = new AttributeRect(database, teamIndex, "ast", database.teamsMap.get(teamIndex).ast, database.teamsMap.get(teamIndex).ast);
		rect.setPosition(leftTopX + attrBarStartX + 3 * 80, leftTopY + this.verticalOrder[teamIndex] * (teamGap)+ 10);
		assistAttrMap.put(teamIndex, rect);
	}
	public void setTurnoverAttrRect (int teamIndex) {
		AttributeRect rect = new AttributeRect(database, teamIndex, "tov", database.teamsMap.get(teamIndex).tov, database.teamsMap.get(teamIndex).optov);
		rect.setPosition(leftTopX + attrBarStartX + 4 * 80, leftTopY + this.verticalOrder[teamIndex] * (teamGap)+ 10);
		turnoverAttrMap.put(teamIndex, rect);
	}
	public void setBlockAttrRect (int teamIndex) {
		AttributeRect rect = new AttributeRect(database, teamIndex, "blk", database.teamsMap.get(teamIndex).blk, database.teamsMap.get(teamIndex).opblk);
		rect.setPosition(leftTopX + attrBarStartX + 5 * 80, leftTopY + this.verticalOrder[teamIndex] * (teamGap)+ 10);
		blockAttrMap.put(teamIndex, rect);
	}
	
	public void drawAttrText (SeasonCanvas canvas) {
		canvas.pushStyle();
		canvas.textAlign(PApplet.CENTER, PApplet.BOTTOM);
		canvas.fill(80);
		canvas.textSize(15);
		canvas.text("Points", leftTopX + attrBarStartX + 80 , leftTopY);
		canvas.text("Rebounds", leftTopX + attrBarStartX + 2 * 80, leftTopY);
		canvas.text("Assists", leftTopX + attrBarStartX + 3 * 80, leftTopY);
		canvas.text("Turnovers", leftTopX + attrBarStartX + 4 * 80, leftTopY);
		canvas.text("Blocks", leftTopX + attrBarStartX + 5 * 80, leftTopY);
		canvas.popStyle();
	}
	
/*	public static int convertTimeToIndex (String date) {
		String[] array = date.split(" ");
		int index = Integer.parseInt(array[2]);
		if (array[1].equals("Oct")) {
			index = index - 29;
		} else if (array[1].equals("Nov")) {
			index = 2 + index;
		} else if (array[1].equals("Dec")) {
			index = 2 + 30 + index;
		} else if (array[1].equals("Jan")) {
			index = 2 + 30 + 31 + index;
		} else if (array[1].equals("Feb")) {
			index = 2 + 30 + 31 + 31 + index;
		} else if (array[1].equals("Mar")) {
			index = 2 + 30 + 31 + 31 + 28 + index;
		} else if (array[1].equals("Apr")) {
			index = 2 + 30 + 31 + 31 + 28 + 31 + index;
		} else {
			System.out.println("Data Error: " + date);
		}
		return index;
	}*/
	
	public void setVerticalOrder (TeamSortType sortType) {
		if (verticalOrder == null) {
			verticalOrder = new int[database.teamNum];
		}	
		switch (sortType) {
			case Overall:
				Arrays.sort(database.teams, new TeamSortByOverall());
				for (int i = 0; i < database.teamNum; i++) {
					verticalOrder[i] = database.teams[i].index;
				}
				break;
			case Name:
			default: 
				for (int i = 0; i < database.teamNum; i++) {
					verticalOrder[i] = i;
				}
		}
	}
	
	public void draw (SeasonCanvas canvas) {
		canvas.pushStyle();
		Iterator iter = (Iterator) teamCircleMap.entrySet().iterator();
		while (iter.hasNext()) {
			WinLostLine_V2 line = (WinLostLine_V2)((Map.Entry)iter.next()).getValue();
			line.draw(canvas);
		}
		iter = (Iterator) pointsAttrMap.entrySet().iterator();
		while (iter.hasNext()) {
			AttributeRect rect = (AttributeRect)((Map.Entry)iter.next()).getValue();
			rect.draw(canvas);
		}
		iter = (Iterator) reboundAttrMap.entrySet().iterator();
		while (iter.hasNext()) {
			AttributeRect rect = (AttributeRect)((Map.Entry)iter.next()).getValue();
			rect.draw(canvas);
		}
		iter = (Iterator) assistAttrMap.entrySet().iterator();
		while (iter.hasNext()) {
			AttributeRect rect = (AttributeRect)((Map.Entry)iter.next()).getValue();
			rect.draw(canvas);
		}
		iter = (Iterator) blockAttrMap.entrySet().iterator();
		while (iter.hasNext()) {
			AttributeRect rect = (AttributeRect)((Map.Entry)iter.next()).getValue();
			rect.draw(canvas);
		}
		iter = (Iterator) turnoverAttrMap.entrySet().iterator();
		while (iter.hasNext()) {
			AttributeRect rect = (AttributeRect)((Map.Entry)iter.next()).getValue();
			rect.draw(canvas);
		}
		drawAttrText(canvas);
		canvas.popStyle();
	}
	


	
}
