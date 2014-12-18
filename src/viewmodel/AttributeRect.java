package viewmodel;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import datamodel_new.Database;
import processing.core.PApplet;

public class AttributeRect {
	static java.text.DecimalFormat df = new java.text.DecimalFormat("#.0");  
	static final int[] colorLeft = {239,138,98};
	static final int[] colorRight = {103,169,207};
	
	static final float barHeight = 15f;
	
	static final float maxWidth = 30;
	static final float minWidth = 5;
	
	String type;
	float middleX, middleY;
	float value, oValue;
	int teamIndex;
	private static Database database = null;
	
	Rectangle2D.Float leftRect = new Rectangle2D.Float();
	Rectangle2D.Float rightRect = new Rectangle2D.Float();
	
	public AttributeRect (Database database, int teamIndex, String type, float value, float oValue) {
		if (AttributeRect.database == null) {
			AttributeRect.database = database;
		}
		this.teamIndex = teamIndex;
		this.type = type;
		this.value = value;
		this.oValue = oValue;
	}
	public void setPosition (float x, float y) {
		middleX = x;
		middleY = y;
		setRects();
	}
	public void setRects () {
		float leftWidth = getMapResult(type, value);
		float rightWidth = getMapResult(type, oValue);
		leftRect.x = middleX - leftWidth;
		leftRect.y = middleY;
		leftRect.width = leftWidth;
		leftRect.height = barHeight;
		rightRect.x = middleX;
		rightRect.y = middleY;
		rightRect.width = rightWidth;
		rightRect.height = barHeight;
		
	}
	public static float getMapResult (String type, float value) {
		float maxValue = 0, minValue = 0;
		if (type.equals("pts")) {
			maxValue = database.maxpts;
			minValue = database.minpts;
		} else if (type.equals("trb")) {
			maxValue = database.maxtrb;
			minValue = database.mintrb;
		} else if (type.equals("ast")) {
			maxValue = database.maxast;
			minValue = database.minast;
		} else if (type.equals("tov")) {
			maxValue = database.maxtov;
			minValue = database.mintov;
		} else if (type.equals("blk")) {
			maxValue = database.maxblk;
			minValue = database.minblk;
		}
		return PApplet.map(value, minValue, maxValue, minWidth, maxWidth);
	}
	
	public void draw (SeasonCanvas canvas) {
		canvas.pushStyle();
		canvas.stroke(180);
		canvas.line(middleX, middleY - 2, middleX, middleY + barHeight + 2);
		canvas.noStroke();
		canvas.fill(colorLeft[0], colorLeft[1], colorLeft[2], 50);
		canvas.rect(leftRect.x, leftRect.y, leftRect.width, leftRect.height);
		canvas.fill(colorRight[0], colorRight[1], colorRight[2], 50);
		canvas.rect(rightRect.x, rightRect.y, rightRect.width, rightRect.height);
		
		canvas.textSize(12);
		canvas.fill(180);
		canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
		canvas.text(df.format(value), leftRect.x, leftRect.y + leftRect.height / 2);
		canvas.textAlign(PApplet.LEFT, PApplet.CENTER);
		canvas.text(df.format(oValue), rightRect.x + rightRect.width , rightRect.y + rightRect.height / 2);
		canvas.popStyle();
	}
}
