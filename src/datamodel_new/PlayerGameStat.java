package datamodel_new;

import java.util.ArrayList;

public class PlayerGameStat {
	String name;
	
	ArrayList<Efficiency> effList;
	
	public PlayerGameStat (String name) {
		this.name = name;
		effList = new ArrayList<Efficiency>();
	}
	public void addEfficiency (float ratio, int value) {
		this.effList.add(new Efficiency(ratio, value));
	}
	public ArrayList<Efficiency> getEffList () {
		return effList;
	}
	public String getName () {
		return name;
	}
}
