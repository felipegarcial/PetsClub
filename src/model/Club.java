package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import exceptions.ExceptionNoRepeatNamePetOfOwner;  

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

	/**
	 * Method to register each owner in a club, according to the pets allowed by the club and owner preferences.
	 */
	public void addOwner(int id,String firstName,String lastName,String birthdayString,String typePetsPrefer) {
		listOwners.add(new Owner(id, firstName, lastName, birthdayString, typePetsPrefer));
	}
	
	
	/**
	 * Method to register new owner
	 * @param owner
	 */
	public void addOwner(Owner owner) {
		listOwners.add(owner);
	}
	
	/**
	 * Method to get list of pets allowed like a String
	 * @return stringAllowPetsType;
	 */
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
	
	/**
	 * Method to compare 
	 */
	public int compareTo(Club club) {
		return listOwners.size() - club.getListOwners().size();
	}
	
	/**
	 * Method to sort list of owners with the number of their pets
	 */
	public void sortOwnersByPetsNumber() {
		Collections.sort(listOwners);
		for (Owner owner : listOwners) {
			System.out.println(owner.getId()+" "+owner.getFirstName()+" "+owner.getLastName() + " " + "-- Number of pets:" + owner.getListPets().size());
		}
	}
	
	/**
	 * Method to verify if owner exist
	 * @param idOwner
	 * @return
	 */
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
	
	
	/**
	 * Add new pet to owner of a club
	 * @param idOwner
	 * @param idPet
	 * @param namePet
	 * @param birthday
	 * @param gender
	 * @param type
	 * @throws ExceptionNoRepeatNamePetOfOwner
	 */
	public void addPetToOwnerOfClub(int idOwner,int idPet,String namePet,String birthday,String gender,String type) throws ExceptionNoRepeatNamePetOfOwner {
		for (int i = 0; i < listOwners.size(); i++) {
			if(idOwner == listOwners.get(i).getId() && !listOwners.get(i).verifyIfPetExist(namePet)) {
				listOwners.get(i).addPet(idPet,namePet,birthday,gender,type);
				break;
			}else {
				throw new ExceptionNoRepeatNamePetOfOwner("Can't create pet with the same name");
			}
		}
	}
	
	/**
	 * Remove owner with id club
	 * @param idClub
	 */
	public void removeOwner(int idClub) {
		for (int i = 0; i < listOwners.size(); i++) {
			if(idClub==listOwners.get(i).getId()) {
				listOwners.remove(i);
				System.out.println("Se borró el dueño con id "+idClub);
				break;
			}
		}
	}
	
	/**
	 * Method to remove owner with de first and last name
	 * @param firstName
	 * @param lastName
	 */
	public void removeOwner(String firstName,String lastName) {
		for (int i = 0; i < listOwners.size(); i++) {
			if(firstName.equals(listOwners.get(i).getFirstName()) && lastName.equals(listOwners.get(i).getLastName())) {
				listOwners.remove(i);
				System.out.println("Se borró el dueño con nombre "+firstName+" "+lastName);
				break;
			}
		}
	}
	
	/**
	 * Method to remove pet from owner with name of pet
	 * @param namePet
	 */
	public void removePetFromOwner(String namePet) {
		for (int i = 0; i < listOwners.size(); i++) {
			listOwners.get(i).removePet(namePet);
		}
	}
	
	
	/**
	 * Method to remove pet from owner with id of pet
	 * @param idPet
	 */
	public void removePetFromOwner(int idPet) {
		for (int i = 0; i < listOwners.size(); i++) {
			listOwners.get(i).removePet(idPet);
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
	
}
