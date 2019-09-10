package model;

import java.util.Comparator;

public class PetNameCompare implements Comparator<Pet> {
	public int compare(Pet petOne, Pet petTwo) {
		return petOne.getName().compareTo(petTwo.getName()); 
	}

}
