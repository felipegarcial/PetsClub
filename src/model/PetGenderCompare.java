package model;

import java.util.Comparator;

public class PetGenderCompare implements Comparator<Pet> {
	public int compare(Pet petOne, Pet petTwo) {
		return petOne.getGender().compareTo(petTwo.getGender()); 
	}

}