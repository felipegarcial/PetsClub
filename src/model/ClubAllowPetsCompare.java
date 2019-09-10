package model;

import java.util.Comparator;

public class ClubAllowPetsCompare implements Comparator<Club> {

	public int compare(Club clubOne, Club clubTwo) {
		return clubOne.getName().compareTo(clubTwo.getName()); 
	}
}
