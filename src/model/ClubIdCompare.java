package model;

import java.util.Comparator;

public class ClubIdCompare implements Comparator<Club> {

	public int compare(Club clubOne, Club clubTwo) {
		if (clubOne.getId() < clubTwo.getId()) {
			return -1;
		}
		if (clubOne.getId() > clubTwo.getId()) {
			return 1;
		} else {
			return 0;
		}
	}
}
