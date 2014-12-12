package viewmodel;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



import java.util.Iterator;
import java.util.Map;

import datamodel_new.Database;
import datamodel_new.Team;
import datamodel_new.TeamSortByOverall;
import datamodel_new.TeamSortType;

public class TimeView {

	
	
	int leftTopX, leftTopY;
	int circleMarginX = 50, circleMarginY = 20;
	int width, height;
	
	Database database;
	int[] verticalOrder;
	int teamGap = 20;
	
	
	HashMap<Integer, WinLostLine> teamCircleMap = new HashMap<Integer, WinLostLine>();
	
	
	
	
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
		}
	}
	
	public void setSingleTeam (int teamIndex) {
		WinLostLine line = new WinLostLine(teamIndex, database);
		line.setPosition(leftTopX + circleMarginX, leftTopY + circleMarginY + this.verticalOrder[teamIndex] * (teamGap));
		teamCircleMap.put(teamIndex, line);
	}
	
	public static int convertTimeToIndex (String date) {
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
	}
	
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
			WinLostLine line = (WinLostLine)((Map.Entry)iter.next()).getValue();
			line.draw(canvas);
		}
		canvas.popStyle();
	}
	


	
}
