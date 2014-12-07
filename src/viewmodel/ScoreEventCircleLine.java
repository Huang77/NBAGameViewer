package viewmodel;

import java.util.ArrayList;

public class ScoreEventCircleLine {
	ArrayList<ShootCircle> circleList = new ArrayList<ShootCircle>();
	ArrayList<ShootCircleConnectBar> bars = new ArrayList<ShootCircleConnectBar>();
	int barHeight = 6;
	int startX, startY; // 
	boolean made;
	
	public ScoreEventCircleLine (int startX, int startY, boolean made) {
		this.startX = startX;
		this.startY = startY; 
		this.made = made;
	}
	public void addShootCircle (ShootCircle circle) {
		if (circleList == null) {
			circleList = new ArrayList<ShootCircle>();
		}
		circleList.add(circle);
		int lastIndex = circleList.size() - 1;
		if (lastIndex > 0) {
			int x = (int) circleList.get(lastIndex - 1).circle.x;
			int y = startY - barHeight / 2 ;
			int w = (int) (circleList.get(lastIndex).circle.x - circleList.get(lastIndex - 1).circle.x);
			ShootCircleConnectBar bar = new ShootCircleConnectBar(x, y, w, barHeight);
			if (circleList.get(0).made == true) {
				bar.setColor(circleList.get(0).color[3], circleList.get(0).color[4], circleList.get(0).color[5]);
			} else {
				bar.setColor(circleList.get(0).color[0], circleList.get(0).color[1], circleList.get(0).color[2]);
			}
			bars.add(bar);
		}
	}
	
	public void draw (SeasonCanvas canvas) {
		// draw bars
		for (int i = 0; i < bars.size(); i++) {
			bars.get(i).draw(canvas);
		}
		// draw circles
		for (int i = 0; i < circleList.size(); i++) {
			circleList.get(i).draw(canvas);
		}
	}
	
}
