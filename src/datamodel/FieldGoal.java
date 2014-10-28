package datamodel;

public class FieldGoal extends Event {
	int points;
	boolean made;
	Player palyer;
	Player assistPlayer; // if there is no assist, then set to be null
}
