package model;

import java.util.Comparator;

public class ClubAllowPetsCompare implements Comparator<Club> {

	public int compare(Club clubOne, Club clubTwo) {
		if (clubOne.getListPetsAllowString().length() < clubTwo.getListPetsAllowString().length()) {
			return -1;
		}
		if (clubOne.getListPetsAllowString().length() > clubTwo.getListPetsAllowString().length()) {
			return 1;
		} else {
			return 0;
		}
	}
}
