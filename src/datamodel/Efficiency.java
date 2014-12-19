package datamodel;

public class Efficiency {
	float ratio;
	int value;
	
	public Efficiency (float ratio, int value) {
		this.ratio = ratio;
		this.value = value;
	}
	public float getRatio () {
		return ratio;
	}
	public int getValue () {
		return value;
	}
}
