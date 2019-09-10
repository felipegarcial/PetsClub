package model;

import java.util.Comparator;

public class OwnerFirstNameCompare implements Comparator<Owner> {
	public int compare(Owner ownerOne, Owner ownerTwo) {
		return ownerOne.getFirstName().compareTo(ownerTwo.getFirstName()); 
	}
}
