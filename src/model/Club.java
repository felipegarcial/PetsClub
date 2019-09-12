package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;  

public class Club implements Serializable,Comparable<Club>{

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Date createdAt;
	private String [] allowPetsType;
	private ArrayList <Owner> listOwners;
	
	public Club(int id,String name,String createdAtString,String [] allowPetsType) {
		this.id = id;
		this.name = name;
		this.allowPetsType = allowPetsType;
		listOwners = new ArrayList<Owner>();
		try {
			this.createdAt = new SimpleDateFormat("yyyy-MM-dd").parse(createdAtString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String[] getAllowPetsType() {
		return allowPetsType;
	}

	public void setAllowPetsType(String[] allowPetsType) {
		this.allowPetsType = allowPetsType;
	}
	
	/**
	 * Method to register each owner in a club, according to the pets allowed by the club and owner preferences.
	 */
	public void addOwner(int id,String firstName,String lastName,String birthdayString,String typePetsPrefer) {
		listOwners.add(new Owner(id, firstName, lastName, birthdayString, typePetsPrefer));
	}
	
	public void addOwner(Owner owner) {
		listOwners.add(owner);
	}
	
	public String getListPetsAllowString() {
		String stringAllowPetsType = Arrays.toString(allowPetsType);
		stringAllowPetsType = stringAllowPetsType.substring(1, stringAllowPetsType.length() - 1);
		stringAllowPetsType = stringAllowPetsType.trim();
		stringAllowPetsType = stringAllowPetsType.replace(" ", "");
		return stringAllowPetsType;
	}

	public ArrayList<Owner> getListOwners() {
		return listOwners;
	}

	public void setListOwners(ArrayList<Owner> listOwners) {
		this.listOwners = listOwners;
	}

	public int compareTo(Club club) {
		return listOwners.size() - club.getListOwners().size();
	}
	
	public void sortOwnersByPetsNumber() {
		Collections.sort(listOwners);
		for (Owner owner : listOwners) {
			System.out.println(owner.getId()+" "+owner.getFirstName()+" "+owner.getLastName() + " " + "-- Number of pets:" + owner.getListPets().size());
		}
	}
	
	public boolean verifyIfOwnerExist(int idOwner) {
		boolean ownerExist = false;
		for (int i = 0; i < listOwners.size(); i++) {
			System.out.println(listOwners.get(i).getId());
			if(idOwner==listOwners.get(i).getId()) {
				ownerExist = true;
			}
		}
		return ownerExist;
	}
	
	
	public void addPetToOwnerOfClub(int idOwner,int idPet,String namePet,String birthday,String gender,String type) {
		for (int i = 0; i < listOwners.size(); i++) {
			if(idOwner == listOwners.get(i).getId() && !listOwners.get(i).verifyIfPetExist(namePet)) {
				listOwners.get(i).addPet(idPet,namePet,birthday,gender,type);
				break;
			}else {
				System.out.println("No se puede guarda otra mascota con el nombre "+namePet);
			}
		}
	}

	public void removeOwner(int idClub) {
		for (int i = 0; i < listOwners.size(); i++) {
			if(idClub==listOwners.get(i).getId()) {
				listOwners.remove(i);
				System.out.println("Se borró el dueño con id "+idClub);
				break;
			}
		}
	}

	public void removeOwner(String firstName,String lastName) {
		for (int i = 0; i < listOwners.size(); i++) {
			if(firstName.equals(listOwners.get(i).getFirstName()) && lastName.equals(listOwners.get(i).getLastName())) {
				listOwners.remove(i);
				System.out.println("Se borró el dueño con nombre "+firstName+" "+lastName);
				break;
			}
		}
	}
	
	public void removePetFromOwner(String namePet) {
		for (int i = 0; i < listOwners.size(); i++) {
			listOwners.get(i).removePet(namePet);
		}
	}
	
	public void removePetFromOwner(int idPet) {
		for (int i = 0; i < listOwners.size(); i++) {
			listOwners.get(i).removePet(idPet);
		}
	}
}
