package viewmodel;

import java.util.ArrayList;

public class SingleGameView {
	int gameIndex;
	public DiffHorizonGraph diffGraph;
	public ArrayList<ScoreEventCircleLine> circleLines = new ArrayList<ScoreEventCircleLine>();
	
	public SingleGameView (int gameIndex) {
		this.gameIndex = gameIndex;
	}
	
	public void draw (SeasonCanvas canvas) {
		for (int i = 0; i < circleLines.size(); i++) {
			circleLines.get(i).draw(canvas);
		}
	}
}
