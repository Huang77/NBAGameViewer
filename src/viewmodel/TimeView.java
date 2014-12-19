package viewmodel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import processing.core.PApplet;
import teamsort.TeamSortByAssist;
import teamsort.TeamSortByBlock;
import teamsort.TeamSortByOpAssist;
import teamsort.TeamSortByOpBlock;
import teamsort.TeamSortByOpPoints;
import teamsort.TeamSortByOpRebound;
import teamsort.TeamSortByOpTurnover;
import teamsort.TeamSortByOverall;
import teamsort.TeamSortByPoints;
import teamsort.TeamSortByRebound;
import teamsort.TeamSortByTurnover;
import teamsort.TeamSortType;
import datamodel.*;

public class TimeView {
	
	static int hoverTeamIndex = -1;
	static int displayAttrNum = 5;
	
	int leftTopX, leftTopY;
	int circleMarginX = 0, circleMarginY = 0;
	int width, height;
	
	int attrBarStartX = 800;
	int attrGap = 155;
	
	Database database;
	int[] verticalOrder;  // vertical order of the teams 
	int teamGap = 28;
	
	HashMap<Integer, WinLostLine_V2> teamCircleMap = new HashMap<Integer, WinLostLine_V2>();
	
	HashMap<Integer, AttributeRect> pointsAttrMap = new HashMap<Integer, AttributeRect>();
	SortTriangle ptsTriangle, opptsTriangle;
	HashMap<Integer, AttributeRect> reboundAttrMap = new HashMap<Integer, AttributeRect>();
	SortTriangle trbTriangle, optrbTriangle;
	HashMap<Integer, AttributeRect> assistAttrMap = new HashMap<Integer, AttributeRect>();
	SortTriangle astTriangle, opastTriangle;
	HashMap<Integer, AttributeRect> turnoverAttrMap = new HashMap<Integer, AttributeRect>();
	SortTriangle tovTriangle, optovTriangle;
	HashMap<Integer, AttributeRect> blockAttrMap = new HashMap<Integer, AttributeRect>();
	SortTriangle blkTriangle, opblkTriangle;
	
	HashMap<String, HashMap<Integer, AttributeRect>> attrMap = new HashMap<String, HashMap<Integer, AttributeRect>>();
	public ArrayList<String> selectedAttr = new ArrayList<String>();
	
	public TimeView (Database database) {
		this.database = database;
		verticalOrder = new int[Database.teamNum];
		selectedAttr.add("Points");
		selectedAttr.add("Rebound");
		selectedAttr.add("Assist");
		selectedAttr.add("Turnover");
		selectedAttr.add("Block");
	}
	
	public void setPosition (int x, int y, int width, int height) {
		this.leftTopX = x;
		this.leftTopY = y;
		this.width = width;
		this.height = height;
	}
	
	public void setAllTeam () {
		setVerticalOrder(TeamSortType.Overall);
		for (int i = 0; i < Database.teamNum; i++) {
			setSingleTeam(i);
			setPointsAttrRect(i);
			setReboundAttrRect(i);
			setAssistAttrRect(i);
			setTurnoverAttrRect(i);
			setBlockAttrRect(i);
		}
		attrMap.put("Points", pointsAttrMap);
		attrMap.put("Rebound", reboundAttrMap);
		attrMap.put("Assist", assistAttrMap);
		attrMap.put("Turnover", turnoverAttrMap);
		attrMap.put("Block", blockAttrMap);
	}
	public void setup () {
		setAllTeam();
		setTriangle();
	}
	
	public void resetAttrDisplay () {
		for (int i = 0; (i < displayAttrNum && i < selectedAttr.size()); i++) {
			System.out.println(selectedAttr.get(i));
		}
	}
	

	public void setSingleTeam (int teamIndex) {
		WinLostLine_V2 line = new WinLostLine_V2(teamIndex, database);
		line.setPosition(leftTopX, leftTopY + this.verticalOrder[teamIndex] * (teamGap));
		teamCircleMap.put(teamIndex, line);
	}
	public void setPointsAttrRect (int teamIndex) {
		AttributeRect rect = new AttributeRect(database, teamIndex, "pts", database.teamsMap.get(teamIndex).pts, database.teamsMap.get(teamIndex).oppts);
		rect.setPosition(leftTopX + attrBarStartX + attrGap , leftTopY + this.verticalOrder[teamIndex] * (teamGap) + 13);
		pointsAttrMap.put(teamIndex, rect);
	}
	public void setReboundAttrRect (int teamIndex) {
		AttributeRect rect = new AttributeRect(database, teamIndex, "trb", database.teamsMap.get(teamIndex).trb, database.teamsMap.get(teamIndex).optrb);
		rect.setPosition(leftTopX + attrBarStartX + 2 * attrGap, leftTopY + this.verticalOrder[teamIndex] * (teamGap)+ 13);
		reboundAttrMap.put(teamIndex, rect);
	}
	public void setAssistAttrRect (int teamIndex) {
		AttributeRect rect = new AttributeRect(database, teamIndex, "ast", database.teamsMap.get(teamIndex).ast, database.teamsMap.get(teamIndex).opast);
		rect.setPosition(leftTopX + attrBarStartX + 3 * attrGap, leftTopY + this.verticalOrder[teamIndex] * (teamGap)+ 13);
		assistAttrMap.put(teamIndex, rect);
	}
	public void setTurnoverAttrRect (int teamIndex) {
		AttributeRect rect = new AttributeRect(database, teamIndex, "tov", database.teamsMap.get(teamIndex).tov, database.teamsMap.get(teamIndex).optov);
		rect.setPosition(leftTopX + attrBarStartX + 4 * attrGap, leftTopY + this.verticalOrder[teamIndex] * (teamGap)+ 13);
		turnoverAttrMap.put(teamIndex, rect);
	}
	public void setBlockAttrRect (int teamIndex) {
		AttributeRect rect = new AttributeRect(database, teamIndex, "blk", database.teamsMap.get(teamIndex).blk, database.teamsMap.get(teamIndex).opblk);
		rect.setPosition(leftTopX + attrBarStartX + 5 * attrGap, leftTopY + this.verticalOrder[teamIndex] * (teamGap)+ 13);
		blockAttrMap.put(teamIndex, rect);
	}
	
	public void setTriangle () {
	 ptsTriangle = new SortTriangle("pts", leftTopX + attrBarStartX + attrGap - 15, leftTopY + 4);
	 opptsTriangle = new SortTriangle("oppts", leftTopX + attrBarStartX + attrGap + 15, leftTopY + 4);

	 trbTriangle = new SortTriangle("trb", leftTopX + attrBarStartX + 2 * attrGap - 15, leftTopY + 4);
	 optrbTriangle = new SortTriangle("optrb", leftTopX + attrBarStartX + 2 * attrGap + 15, leftTopY + 4);

	 astTriangle = new SortTriangle("ast", leftTopX + attrBarStartX + 3 * attrGap - 15, leftTopY + 4);
	 opastTriangle = new SortTriangle("opast", leftTopX + attrBarStartX + 3 * attrGap + 15, leftTopY + 4);

     tovTriangle= new SortTriangle("tov", leftTopX + attrBarStartX + 4 * attrGap - 15, leftTopY + 4);
     optovTriangle = new SortTriangle("optov", leftTopX + attrBarStartX + 4 * attrGap + 15, leftTopY + 4);

     blkTriangle = new SortTriangle("blk", leftTopX + attrBarStartX + 5 * attrGap - 15, leftTopY + 4);
	 opblkTriangle = new SortTriangle("opblk", leftTopX + attrBarStartX + 5 * attrGap + 15, leftTopY + 4);
	}
	
	public void drawTriangle (SeasonCanvas canvas) {
		ptsTriangle.draw(canvas);
		opptsTriangle.draw(canvas);
		trbTriangle.draw(canvas);
		optrbTriangle.draw(canvas);
		astTriangle.draw(canvas);
		opastTriangle.draw(canvas);
		tovTriangle.draw(canvas);
		optovTriangle.draw(canvas);
		blkTriangle.draw(canvas);
		opblkTriangle.draw(canvas);
		
	}
	
	
	public void drawAttrText (SeasonCanvas canvas) {
		canvas.pushStyle();
		canvas.textAlign(PApplet.CENTER, PApplet.BOTTOM);
		canvas.fill(80);
		canvas.textSize(15);
		canvas.text("Points", leftTopX + attrBarStartX + attrGap , leftTopY);
		canvas.text("Rebounds", leftTopX + attrBarStartX + 2 * attrGap, leftTopY);
		canvas.text("Assists", leftTopX + attrBarStartX + 3 * attrGap, leftTopY);
		canvas.text("Turnovers", leftTopX + attrBarStartX + 4 * attrGap, leftTopY);
		canvas.text("Blocks", leftTopX + attrBarStartX + 5 * attrGap, leftTopY);
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
			verticalOrder = new int[Database.teamNum];
		}	
		switch (sortType) {
			case Overall:
				Arrays.sort(database.teams, new TeamSortByOverall());
				for (int i = 0; i < Database.teamNum; i++) {
					verticalOrder[database.teams[i].index] = i;
				}
				break;
			case Points:
				Arrays.sort(database.teams, new TeamSortByPoints());
				for (int i = 0; i < Database.teamNum; i++) {
					verticalOrder[database.teams[i].index] = i;
				}
				break;
			case OpPoints:
				Arrays.sort(database.teams, new TeamSortByOpPoints());
				for (int i = 0; i < Database.teamNum; i++) {
					verticalOrder[database.teams[i].index] = i;
				}
				break;
			case Rebound:
				Arrays.sort(database.teams, new TeamSortByRebound());
				for (int i = 0; i < Database.teamNum; i++) {
					verticalOrder[database.teams[i].index] = i;
				}
				break;
			case OpRebound:
				Arrays.sort(database.teams, new TeamSortByOpRebound());
				for (int i = 0; i < Database.teamNum; i++) {
					verticalOrder[database.teams[i].index] = i;
				}
				break;
			case Assist:
				Arrays.sort(database.teams, new TeamSortByAssist());
				for (int i = 0; i < Database.teamNum; i++) {
					verticalOrder[database.teams[i].index] = i;
				}
				break;
			case OpAssist:
				Arrays.sort(database.teams, new TeamSortByOpAssist());
				for (int i = 0; i < Database.teamNum; i++) {
					verticalOrder[database.teams[i].index] = i;
				}
				break;
			case Turnover:
				Arrays.sort(database.teams, new TeamSortByTurnover());
				for (int i = 0; i < Database.teamNum; i++) {
					verticalOrder[database.teams[i].index] = i;
				}
				break;
			case OpTurnover:
				Arrays.sort(database.teams, new TeamSortByOpTurnover());
				for (int i = 0; i < Database.teamNum; i++) {
					verticalOrder[database.teams[i].index] = i;
				}
				break;
			case Block:
				Arrays.sort(database.teams, new TeamSortByBlock());
				for (int i = 0; i < Database.teamNum; i++) {
					verticalOrder[database.teams[i].index] = i;
				}
				break;
			case OpBlock:
				Arrays.sort(database.teams, new TeamSortByOpBlock());
				for (int i = 0; i < Database.teamNum; i++) {
					verticalOrder[database.teams[i].index] = i;
				}
				break;
			case Name:
			default: 
				for (int i = 0; i < Database.teamNum; i++) {
					verticalOrder[i] = i;
				}
		}
	}
	
	public void draw (SeasonCanvas canvas) {
		canvas.smooth();
		canvas.pushStyle();
		for (int i = 0; i < Database.teamNum; i++) {
			teamCircleMap.get(i).draw(canvas);
			pointsAttrMap.get(i).draw(canvas);
			reboundAttrMap.get(i).draw(canvas);
			assistAttrMap.get(i).draw(canvas);
			blockAttrMap.get(i).draw(canvas);
			turnoverAttrMap.get(i).draw(canvas);
			
			canvas.pushStyle();
			canvas.stroke(210);
			canvas.strokeWeight(1);

			canvas.line(teamCircleMap.get(i).endX + 2, teamCircleMap.get(i).endY, pointsAttrMap.get(i).getLeftX(canvas), pointsAttrMap.get(i).getMiddleY());
			canvas.line(pointsAttrMap.get(i).getRightX(canvas), pointsAttrMap.get(i).getMiddleY(), reboundAttrMap.get(i).getLeftX(canvas), reboundAttrMap.get(i).getMiddleY());
			canvas.line(reboundAttrMap.get(i).getRightX(canvas), reboundAttrMap.get(i).getMiddleY(), assistAttrMap.get(i).getLeftX(canvas), assistAttrMap.get(i).getMiddleY());
			canvas.line(assistAttrMap.get(i).getRightX(canvas), assistAttrMap.get(i).getMiddleY(), turnoverAttrMap.get(i).getLeftX(canvas), turnoverAttrMap.get(i).getMiddleY());
			canvas.line(turnoverAttrMap.get(i).getRightX(canvas), turnoverAttrMap.get(i).getMiddleY(), blockAttrMap.get(i).getLeftX(canvas), blockAttrMap.get(i).getMiddleY());
			canvas.popStyle();
		}
		
		// draw hover line
		if (hoverTeamIndex >= 0) {
			canvas.pushStyle();
			canvas.stroke(150);
			canvas.strokeWeight(2f);
			canvas.line(teamCircleMap.get(hoverTeamIndex).endX + 2, teamCircleMap.get(hoverTeamIndex).endY, pointsAttrMap.get(hoverTeamIndex).getLeftX(canvas), pointsAttrMap.get(hoverTeamIndex).getMiddleY());
			canvas.line(pointsAttrMap.get(hoverTeamIndex).getRightX(canvas), pointsAttrMap.get(hoverTeamIndex).getMiddleY(), reboundAttrMap.get(hoverTeamIndex).getLeftX(canvas), reboundAttrMap.get(hoverTeamIndex).getMiddleY());
			canvas.line(reboundAttrMap.get(hoverTeamIndex).getRightX(canvas), reboundAttrMap.get(hoverTeamIndex).getMiddleY(), assistAttrMap.get(hoverTeamIndex).getLeftX(canvas), assistAttrMap.get(hoverTeamIndex).getMiddleY());
			canvas.line(assistAttrMap.get(hoverTeamIndex).getRightX(canvas), assistAttrMap.get(hoverTeamIndex).getMiddleY(), turnoverAttrMap.get(hoverTeamIndex).getLeftX(canvas), turnoverAttrMap.get(hoverTeamIndex).getMiddleY());
			canvas.line(turnoverAttrMap.get(hoverTeamIndex).getRightX(canvas), turnoverAttrMap.get(hoverTeamIndex).getMiddleY(), blockAttrMap.get(hoverTeamIndex).getLeftX(canvas), blockAttrMap.get(hoverTeamIndex).getMiddleY());
			canvas.popStyle();
		}
		// end draw hover line
		
		drawAttrText(canvas);
		drawTriangle(canvas);
		canvas.popStyle();
	}
	
	public void mouseMoved (SeasonCanvas canvas) {
		TimeView.hoverTeamIndex = -1;
		ptsTriangle.mouseHover(canvas);
		opptsTriangle.mouseHover(canvas);
		trbTriangle.mouseHover(canvas);
		optrbTriangle.mouseHover(canvas);
		astTriangle.mouseHover(canvas);
		opastTriangle.mouseHover(canvas);
		tovTriangle.mouseHover(canvas);
		optovTriangle.mouseHover(canvas);
		blkTriangle.mouseHover(canvas);
		opblkTriangle.mouseHover(canvas);
		
		mouseMovedOnAttributeRect(canvas);
	}
	
	public void mouseMovedOnAttributeRect (SeasonCanvas canvas) {
		for (int i = 0; i < Database.teamNum; i++) {
			teamCircleMap.get(i).mouseHover(canvas);
			pointsAttrMap.get(i).mouseHover(canvas);
			reboundAttrMap.get(i).mouseHover(canvas);
			assistAttrMap.get(i).mouseHover(canvas);
			blockAttrMap.get(i).mouseHover(canvas);
			turnoverAttrMap.get(i).mouseHover(canvas);
		}
	}
	
	public void mouseClicked (SeasonCanvas canvas) {
		triangleClicked(canvas);
	}
	public void triangleClicked (SeasonCanvas canvas) {
		if (ptsTriangle.hover == true) {
			setVerticalOrder(TeamSortType.Points);
			for (int i = 0; i < Database.teamNum; i++) {
				pointsAttrMap.get(i).setPosition(leftTopX + attrBarStartX + attrGap , leftTopY + this.verticalOrder[i] * (teamGap) + 13);
			}
		} else if (opptsTriangle.hover == true) {
			setVerticalOrder(TeamSortType.OpPoints);
			for (int i = 0; i < Database.teamNum; i++) {
				pointsAttrMap.get(i).setPosition(leftTopX + attrBarStartX + attrGap , leftTopY + this.verticalOrder[i] * (teamGap) + 13);
			}
		} else if (trbTriangle.hover == true) {
			setVerticalOrder(TeamSortType.Rebound);
			for (int i = 0; i < Database.teamNum; i++) {
				reboundAttrMap.get(i).setPosition(leftTopX + attrBarStartX + 2 * attrGap , leftTopY + this.verticalOrder[i] * (teamGap) + 13);
			}
		} else if (optrbTriangle.hover == true) {
			setVerticalOrder(TeamSortType.OpRebound);
			for (int i = 0; i < Database.teamNum; i++) {
				reboundAttrMap.get(i).setPosition(leftTopX + attrBarStartX + 2 * attrGap , leftTopY + this.verticalOrder[i] * (teamGap) + 13);
			}
		} else if (astTriangle.hover == true) {
			setVerticalOrder(TeamSortType.Assist);
			for (int i = 0; i < Database.teamNum; i++) {
				assistAttrMap.get(i).setPosition(leftTopX + attrBarStartX + 3 * attrGap , leftTopY + this.verticalOrder[i] * (teamGap) + 13);
			}
		} else if (opastTriangle.hover == true) {
			setVerticalOrder(TeamSortType.OpAssist);
			for (int i = 0; i < Database.teamNum; i++) {
				assistAttrMap.get(i).setPosition(leftTopX + attrBarStartX + 3 * attrGap , leftTopY + this.verticalOrder[i] * (teamGap) + 13);
			}
		} else if (tovTriangle.hover == true) {
			setVerticalOrder(TeamSortType.Turnover);
			for (int i = 0; i < Database.teamNum; i++) {
				turnoverAttrMap.get(i).setPosition(leftTopX + attrBarStartX + 4 * attrGap , leftTopY + this.verticalOrder[i] * (teamGap) + 13);
			}
		} else if (optovTriangle.hover == true) {
			setVerticalOrder(TeamSortType.OpTurnover);
			for (int i = 0; i < Database.teamNum; i++) {
				turnoverAttrMap.get(i).setPosition(leftTopX + attrBarStartX + 4 * attrGap , leftTopY + this.verticalOrder[i] * (teamGap) + 13);
			}
		} else if (blkTriangle.hover == true) {
			setVerticalOrder(TeamSortType.Block);
			for (int i = 0; i < Database.teamNum; i++) {
				blockAttrMap.get(i).setPosition(leftTopX + attrBarStartX + 5 * attrGap , leftTopY + this.verticalOrder[i] * (teamGap) + 13);
			}
		} else if (opblkTriangle.hover == true) {
			setVerticalOrder(TeamSortType.OpBlock);
			for (int i = 0; i < Database.teamNum; i++) {
				blockAttrMap.get(i).setPosition(leftTopX + attrBarStartX + 5 * attrGap , leftTopY + this.verticalOrder[i] * (teamGap) + 13);
			}
		}
	}
	
}
