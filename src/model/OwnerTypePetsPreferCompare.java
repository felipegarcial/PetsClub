package model;

import java.util.Comparator;

public class OwnerTypePetsPreferCompare implements Comparator<Owner> {
	public int compare(Owner ownerOne, Owner ownerTwo) {
		return ownerOne.getTypePetsPrefer().compareTo(ownerTwo.getTypePetsPrefer()); 
	}
}
