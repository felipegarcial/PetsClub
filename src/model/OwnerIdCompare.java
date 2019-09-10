package model;

import java.util.Comparator;

public class OwnerIdCompare implements Comparator<Owner> {
	public int compare(Owner ownerOne, Owner ownerTwo) {
		if (ownerOne.getId() < ownerTwo.getId()) {
			return -1;
		}
		if (ownerOne.getId() > ownerTwo.getId()) {
			return 1;
		} else {
			return 0;
		}
	}
}
