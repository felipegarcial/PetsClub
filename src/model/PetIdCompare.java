package model;

import java.util.Comparator;

public class PetIdCompare implements Comparator<Pet> {
	public int compare(Pet petOne, Pet petTwo) {
		if (petOne.getId() < petTwo.getId()) {
			return -1;
		}
		if (petOne.getId() > petTwo.getId()) {
			return 1;
		} else {
			return 0;
		}
	}

}
