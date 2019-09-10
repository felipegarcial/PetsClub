package model;

import java.util.Comparator;

public class ClubNameCompare implements Comparator<Club> {
	public int compare(Club clubOne, Club clubTwo) {
		return clubOne.getName().compareTo(clubTwo.getName()); 
	}
}
