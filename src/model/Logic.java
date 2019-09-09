package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.ArrayList;

public class Logic {
	ArrayList<Club> listClubs;
	ArrayList<Owner> listGenericOwners;
	ArrayList<Pet> listGenericPets;
	File fileTxtClubs, fileCSVOwners, fileCSVPets;
	BufferedReader brClubs, brOwners, brPets;

	public Logic() {
		listClubs = new ArrayList<Club>();
		listGenericOwners = new ArrayList<Owner>();
		listGenericPets = new ArrayList<Pet>();

		fileTxtClubs = new File("C:\\Users\\pipeg\\eclipse-workspace\\PetClub\\src\\dataInfo\\toImport\\clubs.txt");
		fileCSVOwners = new File("C:\\Users\\pipeg\\eclipse-workspace\\PetClub\\src\\dataInfo\\toImport\\owner.csv");
		fileCSVPets = new File("C:\\Users\\pipeg\\eclipse-workspace\\PetClub\\src\\dataInfo\\toImport\\pets.csv");

		try {
			brClubs = new BufferedReader(new FileReader(fileTxtClubs));
			brOwners = new BufferedReader(new FileReader(fileCSVOwners));
			brPets = new BufferedReader(new FileReader(fileCSVPets));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		loadInformationClubsFromTXT();
		loadInformationOwners();
		loadInformationPets();
		assignPetsToOwners();
		assignOwnersToClub();

	}

	/**
	 * Method to load the clubs information from plain file.
	 */
	public void loadInformationClubsFromTXT() {
		String st;
		String[] arrayText;
		// ------------------------------
		try {
			while ((st = brClubs.readLine()) != null) {
				// -------------------
				arrayText = st.split("/");
				// -------------------
				int id = Integer.parseInt(arrayText[0]);
				String name = arrayText[1];
				String createdAtString = arrayText[2];
				String[] allowPetsType = arrayText[3].split(",");
				// -------------------
				listClubs.add(new Club(id, name, createdAtString, allowPetsType));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to load the owners information from plain file.
	 */
	public void loadInformationOwners() {
		String st;
		// ------------------------------
		try {
			while ((st = brOwners.readLine()) != null) {
				String[] arrayInfoOwner = st.split(",");
				// -------------------
				int id = Integer.parseInt(arrayInfoOwner[0]);
				String firstName = arrayInfoOwner[1];
				String lastName = arrayInfoOwner[2];
				String birthdayString = arrayInfoOwner[3];
				String typePetsPrefer = arrayInfoOwner[4];
				// -------------------
				listGenericOwners.add(new Owner(id, firstName, lastName, birthdayString, typePetsPrefer));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to load the pets information from plain file.
	 */
	public void loadInformationPets() {
		String st;
		// ------------------------------
		try {
			while ((st = brPets.readLine()) != null) {
				String[] arrayInfoOwner = st.split(",");
				// -------------------
				int id = Integer.parseInt(arrayInfoOwner[0]);
				String firstName = arrayInfoOwner[1];
				String lastName = arrayInfoOwner[2];
				String birthdayString = arrayInfoOwner[3];
				String typePetsPrefer = arrayInfoOwner[4];
				// -------------------
				listGenericPets.add(new Pet(id, firstName, lastName, birthdayString, typePetsPrefer));

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to assign pets to owner with random method
	 */
	public void assignPetsToOwners() {
		for (int i = 0; i < listGenericPets.size(); i++) {
			if (i < listGenericOwners.size()) {
				listGenericOwners.get(i).addPet(listGenericPets.get(i));
			} else {
				int maxIndex = getRandomIntNumber(0, listGenericOwners.size() - 1);
				listGenericOwners.get(maxIndex).addPet(listGenericPets.get(i));
			}
		}
	}

	/**
	 * Method to assign owners to club depends what kind of pets they have
	 */
	public void assignOwnersToClub() {
		for (int i = 0; i < listGenericOwners.size(); i++) {
			boolean check = true;
			while (check) {
				int indexClub = getRandomIntNumber(0, listClubs.size() - 1);
				if (listClubs.get(indexClub).getListPetsAllowString()
						.contains(listGenericOwners.get(i).getListPets().get(0).getType())) {
					listClubs.get(indexClub).addOwner(listGenericOwners.get(i));
					check = false;
				}
			}
		}
		
		
		for (int i = 0; i < listClubs.size(); i++) {
			System.out.println(listClubs.get(i).getListOwners().size());
		}
	}

	private int getRandomIntNumber(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

}
