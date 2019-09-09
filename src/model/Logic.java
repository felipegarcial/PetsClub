package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Logic {
	ArrayList<Club> listClubs;
	ArrayList<Owner> listGenericOwners;
	ArrayList<Pet> listGenericPets;
	File fileTxtClubs,fileCSVOwners,fileCSVPets;
	BufferedReader brClubs,brOwners,brPets;

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

	public void loadInformationOwners() {
		String st;
		
		// ------------------------------
		try {
			while ((st = brOwners.readLine()) != null) {
				System.out.println(st);
				String [] arrayInfoOwner = st.split(",");
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
	

	public void loadInformationPets() {
		String st;
		// ------------------------------
		try {
			while ((st = brPets.readLine()) != null) {
				System.out.println(st);
				String [] arrayInfoOwner = st.split(",");
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
	
	public void assignOwnersToClub() {
		
	}
}
