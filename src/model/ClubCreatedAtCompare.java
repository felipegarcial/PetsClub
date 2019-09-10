package model;

import java.util.Comparator;

public class ClubCreatedAtCompare implements Comparator<Club> {
	public int compare(Club clubOne, Club clubTwo) {
		if (clubOne.getCreatedAt().before(clubTwo.getCreatedAt())) {
			return -1;
		}
		if (clubOne.getCreatedAt().after(clubTwo.getCreatedAt())) {
			return 1;
		} else {
			return 0;
		}
	}
}
