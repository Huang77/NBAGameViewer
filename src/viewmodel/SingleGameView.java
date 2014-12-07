package viewmodel;

import java.util.ArrayList;

public class SingleGameView {
	int gameIndex;
	public DiffHorizonGraph diffGraph;
	public ArrayList<ScoreEventCircleLine> leftCircleLines = new ArrayList<ScoreEventCircleLine>();
	public ArrayList<ScoreEventCircleLine> rightCircleLines = new ArrayList<ScoreEventCircleLine>();
	
	public SingleGameView (int gameIndex) {
		this.gameIndex = gameIndex;
	}
	
	public void draw (SeasonCanvas canvas) {
		diffGraph.draw(canvas);
		for (int i = 0; i < leftCircleLines.size(); i++) {
			leftCircleLines.get(i).draw(canvas);
		}
		for (int i = 0; i < rightCircleLines.size(); i++) {
			rightCircleLines.get(i).draw(canvas);
		}
	}
}
