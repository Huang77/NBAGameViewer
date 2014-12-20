package viewmodel;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import datamodel.Database;
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
	
	boolean hover = false;
	
	Rectangle2D.Float leftRect = new Rectangle2D.Float();
	Rectangle2D.Float rightRect = new Rectangle2D.Float();
	
	public AttributeRect (Database database, int teamIndex, String type, float[] value) {
		if (AttributeRect.database == null) {
			AttributeRect.database = database;
		}
		this.teamIndex = teamIndex;
		this.type = type;
		this.value = value[0];
		this.oValue = value[1];
	}
	
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
	public void setYPosition (float y) {
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
		float[] returnValue = database.getMinMaxByType(type);
/*		if (type.equals("FTP%")) {
			System.out.println(type + "   " + value);
			System.out.println(type + " Max: " + returnValue[0] +" Min: " + returnValue[1]);
		}
*/
		return PApplet.map(value, returnValue[1], returnValue[0], minWidth, maxWidth);
	}
	
	public float getLeftX (SeasonCanvas canvas) {
		float temp = 0f;
		if (type.endsWith("%")) {
			temp = canvas.textWidth(String.valueOf(value));
		} else {
			temp = canvas.textWidth(String.valueOf(df.format(value)));
		}
		
		return leftRect.x - temp - 4;
	}
	public float getRightX (SeasonCanvas canvas) {
		float temp = 0f;
		if (type.endsWith("%")) {
			temp = canvas.textWidth(String.valueOf(oValue));
		} else {
			temp = canvas.textWidth(String.valueOf(df.format(oValue)));
		}
		return rightRect.x + rightRect.width + temp + 4;
	}
	public float getMiddleY () {
		return middleY + barHeight / 2;
	}
	
	public void draw (SeasonCanvas canvas) {
		canvas.pushStyle();
		canvas.stroke(180);
		canvas.line(middleX, middleY - 2, middleX, middleY + barHeight + 2);
		canvas.noStroke();
		if (teamIndex == TimeView.hoverTeamIndex) {
			canvas.strokeWeight(2f);
			canvas.stroke(180);
		}
		canvas.fill(colorLeft[0], colorLeft[1], colorLeft[2], 150);
		canvas.rect(leftRect.x, leftRect.y, leftRect.width, leftRect.height);
		canvas.fill(colorRight[0], colorRight[1], colorRight[2], 150);
		canvas.rect(rightRect.x, rightRect.y, rightRect.width, rightRect.height);
		
		canvas.textSize(12);
		canvas.fill(180);
		if (teamIndex == TimeView.hoverTeamIndex) {
			canvas.fill(80);
			canvas.textSize(14);
		}
		canvas.textAlign(PApplet.RIGHT, PApplet.CENTER);
		
		if (type.endsWith("%")) {
			canvas.text(value, leftRect.x, leftRect.y + leftRect.height / 2);
		} else {
			canvas.text(df.format(value), leftRect.x, leftRect.y + leftRect.height / 2);
		}
		canvas.textAlign(PApplet.LEFT, PApplet.CENTER);
		
		if (type.endsWith("%")) {
			canvas.text(oValue, rightRect.x + rightRect.width , rightRect.y + rightRect.height / 2);
		} else {
			canvas.text(df.format(oValue), rightRect.x + rightRect.width , rightRect.y + rightRect.height / 2);
		}
		
		canvas.popStyle();
	}
	
	public void mouseHover (SeasonCanvas canvas) {
		if (leftRect.contains(canvas.mouseX, canvas.mouseY) || rightRect.contains(canvas.mouseX, canvas.mouseY)) {
			hover = true;
			TimeView.hoverTeamIndex = teamIndex;
		} else {
			hover = false;
		}
	}
}
