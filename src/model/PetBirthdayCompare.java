package model;

import java.util.Comparator;

public class PetBirthdayCompare implements Comparator<Pet> {
	public int compare(Pet petOne, Pet petTwo) {
		if (petOne.getBirthday().before(petTwo.getBirthday())) {
			return -1;
		}
		if (petOne.getBirthday().after(petTwo.getBirthday())) {
			return 1;
		} else {
			return 0;
		}
	}

}