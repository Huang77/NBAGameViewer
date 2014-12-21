package datamodel;

import java.util.ArrayList;

public class PlayerGameStat {
	String name;
	
	ArrayList<Efficiency> effList;
	ArrayList<Event> eventList;
	
	public PlayerGameStat (String name) {
		this.name = name;
		effList = new ArrayList<Efficiency>();
		eventList = new ArrayList<Event>();
	}
	public void addEfficiency (float ratio, int value) {
		this.effList.add(new Efficiency(ratio, value));
	}
	public ArrayList<Efficiency> getEffList () {
		return effList;
	}
	public ArrayList<Event> getEventList () {
		return eventList;
	}
	public String getName () {
		return name;
	}
}
