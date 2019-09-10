package model;

import java.util.Comparator;

public class PetTypeCompare implements Comparator<Pet> {
	public int compare(Pet petOne, Pet petTwo) {
		return petOne.getType().compareTo(petTwo.getType()); 
	}

}