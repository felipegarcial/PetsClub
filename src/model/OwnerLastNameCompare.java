package model;

import java.util.Comparator;

public class OwnerLastNameCompare implements Comparator<Owner> {
	public int compare(Owner ownerOne, Owner ownerTwo) {
		return ownerOne.getLastName().compareTo(ownerTwo.getLastName()); 
	}
}
