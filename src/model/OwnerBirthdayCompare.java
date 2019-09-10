package model;

import java.util.Comparator;

public class OwnerBirthdayCompare implements Comparator<Owner> {
	public int compare(Owner ownerOne, Owner ownerTwo) {
		if (ownerOne.getBirthday().before(ownerTwo.getBirthday())) {
			return -1;
		}
		if (ownerOne.getBirthday().after(ownerTwo.getBirthday())) {
			return 1;
		} else {
			return 0;
		}
	}
}
