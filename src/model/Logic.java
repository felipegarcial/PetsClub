package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Logic {
	ArrayList<Club> listClubs;
	ArrayList<Owner> listGenericOwners;
	ArrayList<Pet> listGenericPets;
	File fileTxtClubs, fileCSVOwners, fileCSVPets;
	BufferedReader brClubs, brClubsExist, brOwners, brPets;
	// ------------
	// Compare club
	ClubIdCompare clubIdCompare;
	ClubNameCompare clubNameCompare;
	ClubCreatedAtCompare clubCreatedAtCompare;
	ClubAllowPetsCompare clubAllowPetsCompare;
	// ------------
	// Owner club
	OwnerIdCompare ownerIdCompare;
	OwnerFirstNameCompare ownerFirstNameCompare;
	OwnerLastNameCompare ownerLastNameCompare;
	OwnerBirthdayCompare ownerBirthdayCompare;
	OwnerTypePetsPreferCompare ownerTypePetsPreferCompare;
	// ------------
	// Compare club
	PetIdCompare petIdCompare;
	PetNameCompare petNameCompare;
	PetGenderCompare petGenderCompare;
	PetBirthdayCompare petBirthdayCompare;
	PetTypeCompare petTypeCompare;

	public Logic() {
		listClubs = new ArrayList<Club>();
		listGenericOwners = new ArrayList<Owner>();
		listGenericPets = new ArrayList<Pet>();

		fileTxtClubs = new File("C:\\Users\\pipeg\\eclipse-workspace\\PetClub\\src\\dataInfo\\toImport\\clubs.txt");
		fileCSVOwners = new File(
				"C:\\Users\\pipeg\\eclipse-workspace\\PetClub\\src\\dataInfo\\shortToImport\\owner.csv");
		fileCSVPets = new File("C:\\Users\\pipeg\\eclipse-workspace\\PetClub\\src\\dataInfo\\shortToImport\\pets.csv");

		try {
			brClubsExist = new BufferedReader(new FileReader(fileTxtClubs));
			brClubs = new BufferedReader(new FileReader(fileTxtClubs));
			brOwners = new BufferedReader(new FileReader(fileCSVOwners));
			brPets = new BufferedReader(new FileReader(fileCSVPets));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (checkSerializableFiles()) {
			loadInformationClubsFromTXT();
			loadInformationOwners();
			loadInformationPets();
			assignPetsToOwners();
			assignOwnersToClub();
			saveSerializationFileClubs();
		} else {
			readSerializableFilesClub();
		}

		// Natural sort -----------------------------
		sortClubsByOwnersNumber();
		sortOwnersByPetsNumber(5);
		// Sort by criteria-----------------------------

		// Club----
		clubIdCompare = new ClubIdCompare();
		clubNameCompare = new ClubNameCompare();
		clubCreatedAtCompare = new ClubCreatedAtCompare();
		clubAllowPetsCompare = new ClubAllowPetsCompare();
		// Owner---
		ownerIdCompare = new OwnerIdCompare();
		ownerFirstNameCompare = new OwnerFirstNameCompare();
		ownerLastNameCompare = new OwnerLastNameCompare();
		ownerBirthdayCompare = new OwnerBirthdayCompare();
		ownerTypePetsPreferCompare = new OwnerTypePetsPreferCompare();
		// Pet-----
		petIdCompare = new PetIdCompare();
		petNameCompare = new PetNameCompare();
		petGenderCompare = new PetGenderCompare();
		petBirthdayCompare = new PetBirthdayCompare();
		petTypeCompare = new PetTypeCompare();
	}

	/**
	 * Method to save each club in file serializable
	 */
	private void saveSerializationFileClubs() {
		for (int i = 0; i < listClubs.size(); i++) {
			String nameFile = listClubs.get(i).getName().replace(" ", "-");
			try {
				FileOutputStream fos = new FileOutputStream("./src/dataInfo/serializable/" + nameFile + ".dat");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(listClubs.get(i));
				oos.close();
				fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * Method to read serializable files to get all club information
	 */
	private void readSerializableFilesClub() {
		System.out.println(listClubs.size());
		File dir = new File("./src/dataInfo/serializable/");
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {

				try {
					FileInputStream fi = new FileInputStream(new File(child.getPath()));
					ObjectInputStream oi = new ObjectInputStream(fi);
					Club club = (Club) oi.readObject();
					listClubs.add(club);

				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Method to
	 * 
	 * @return boolean - Return boolean depends if all clubs exist like serializable
	 *         file
	 */
	private boolean checkSerializableFiles() {
		String allFilesExist = "";
		String st;
		String[] arrayText;
		// -------------------
		try {
			while ((st = brClubsExist.readLine()) != null) {
				// -------------------
				arrayText = st.split("/");
				String nameClubFile = arrayText[1];
				// -------------------
				nameClubFile = nameClubFile.replace(" ", "-");
				File f = new File("./src/dataInfo/serializable/" + nameClubFile + ".dat");
				boolean result = f.exists();
				allFilesExist += allFilesExist + result;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return allFilesExist.contains("false");
	};

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
	}

	/**
	 * Method to get random int number in a range
	 * 
	 * @param min base int
	 * @param max limit int
	 * @return int Number random
	 */
	private int getRandomIntNumber(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	/**
	 * Method to sort club by owners number
	 */
	private void sortClubsByOwnersNumber() {
		Collections.sort(listClubs);
		for (Club club : listClubs) {
			System.out.println(club.getName() + " " + "-- Number of owners:" + club.getListOwners().size());
		}
	}

	/**
	 * Method to sort owners by pets number of Club
	 * 
	 * @param nameClub
	 */
	public void sortOwnersByPetsNumber(int idClub) {
		for (int i = 0; i < listClubs.size(); i++) {
			if (listClubs.get(i).getId() == idClub) {
				listClubs.get(i).sortOwnersByPetsNumber();
				break;
			}
		}
	}

	public void sortClubByCriteria(String nameCriteria) {
		switch (nameCriteria) {
		case "id":
			Collections.sort(listClubs, clubIdCompare);
			break;

		case "name":
			Collections.sort(listClubs, clubNameCompare);
			break;
		case "createdAt":
			Collections.sort(listClubs, clubCreatedAtCompare);
			break;
		case "allowPetsType":
			Collections.sort(listClubs, clubAllowPetsCompare);
			break;
		default:
			Collections.sort(listClubs, clubIdCompare);
			break;
		}
	}

	public void sortOwnersOfClubByCriteria(int idClub,String nameCriteria) {
		for (int i = 0; i < listClubs.size(); i++) {
			switch (nameCriteria) {
			case "id":
				Collections.sort(listClubs.get(i).getListOwners(), ownerIdCompare);
				break;
			case "firstName":
				Collections.sort(listClubs.get(i).getListOwners(), ownerFirstNameCompare);
				break;
			case "lastName":
				Collections.sort(listClubs.get(i).getListOwners(), ownerLastNameCompare);
				break;
			case "birthday":
				Collections.sort(listClubs.get(i).getListOwners(), ownerBirthdayCompare);
				break;
			case "typePetsPrefer":
				Collections.sort(listClubs.get(i).getListOwners(), ownerTypePetsPreferCompare);
				break;
			default:
				Collections.sort(listClubs.get(i).getListOwners(), ownerIdCompare);
				break;
			}
		}
	}
	
	/**
	 * Method to sort all pets of owner of 
	 * @param idClub
	 * @param idOwner
	 * @param nameCriteria
	 */
	public void sortPetsOfOwnersOfClubByCriteria(int idClub, int idOwner,String nameCriteria) {
		for (int i = 0; i < listClubs.size(); i++) {
			for (int j = 0; j < listClubs.get(i).getListOwners().size(); j++) {
				switch (nameCriteria) {
				case "id":
						Collections.sort(listClubs.get(i).getListOwners().get(j).getListPets(),petIdCompare);
					break;
				case "name":
					Collections.sort(listClubs.get(i).getListOwners().get(j).getListPets(),petNameCompare);
					break;
				case "gender":
					Collections.sort(listClubs.get(i).getListOwners().get(j).getListPets(),petGenderCompare);
					break;
				case "birthday":
					Collections.sort(listClubs.get(i).getListOwners().get(j).getListPets(),petBirthdayCompare);
					break;
				case "type":
					Collections.sort(listClubs.get(i).getListOwners().get(j).getListPets(),petTypeCompare);
					break;
				default:
					Collections.sort(listClubs.get(i).getListOwners().get(j).getListPets(),petIdCompare);
					break;
				}
			}
		}
	}
	
	/**
	 * Method to add club
	 * @param id
	 * @param name
	 * @param createdAtString
	 * @param allowPetsType
	 */
	public void addClub(int id, String name, String createdAtString,String [] allowPetsType) {
		boolean canSave = true;
		for (int i = 0; i < listClubs.size(); i++) {
			if(id==listClubs.get(i).getId() || name.equals(listClubs.get(i).getName())) {
				canSave = false;
				break;
			}
		}
		if(canSave) {
			listClubs.add(new Club(id, name, createdAtString, allowPetsType));
		}
	}
	
	
	/**
	 * Method to add new owner in a club
	 * @param idClub
	 * @param idOwner
	 * @param firstName
	 * @param lastName
	 * @param birthday
	 * @param typePetsPrefer
	 */
	public void addOwnerToClub(int idClub, int idOwner,String firstName,String lastName,String birthday, String typePetsPrefer) {
		for (int i = 0; i < listClubs.size(); i++) {
			if(idClub == listClubs.get(i).getId() && !listClubs.get(i).verifyIfOwnerExist(idOwner)) {
				listClubs.get(i).addOwner(idOwner, firstName, lastName, birthday, typePetsPrefer);
				break;
			}
			else {
				System.out.println("No se puede registrar un usuario con el mismo ID");
			}
		}
	}
	
	/**
	 * 
	 * @param idClub
	 * @param idOwner
	 * @param firstName
	 * @param lastName
	 * @param birthday
	 * @param typePetsPrefer
	 */
	public void addPetToOwner(int idClub, int idOwner,String firstName,String lastName,String birthday, String typePetsPrefer) {
		for (int i = 0; i < listClubs.size(); i++) {
			if(idClub == listClubs.get(i).getId() && !listClubs.get(i).verifyIfOwnerExist(idOwner)) {
				listClubs.get(i).addOwner(idOwner, firstName, lastName, birthday, typePetsPrefer);
				break;
			}
			else {
				System.out.println("No se puede registrar un usuario con el mismo ID");
			}
		}
	}

}
