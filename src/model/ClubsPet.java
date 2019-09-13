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

import exceptions.ExceptionExistClub;
import exceptions.ExceptionNoRepeatIdOwner;
import exceptions.ExceptionNoRepeatNameOrIdClub;
import exceptions.ExceptionNoRepeatNamePetOfOwner;
import exceptions.ExceptionOwnerExist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ClubsPet {
	private ArrayList<Club> listClubs;
	private ArrayList<Owner> listGenericOwners;
	private ArrayList<Pet> listGenericPets;
	private File fileTxtClubs, fileCSVOwners, fileCSVPets;
	private BufferedReader brClubs, brClubsExist, brOwners, brPets;
	// ------------
	// Compare club
	private ClubIdCompare clubIdCompare;
	private ClubNameCompare clubNameCompare;
	private ClubCreatedAtCompare clubCreatedAtCompare;
	private ClubAllowPetsCompare clubAllowPetsCompare;
	// ------------
	// Owner club
	private OwnerIdCompare ownerIdCompare;
	private OwnerFirstNameCompare ownerFirstNameCompare;
	private OwnerLastNameCompare ownerLastNameCompare;
	private OwnerBirthdayCompare ownerBirthdayCompare;
	private OwnerTypePetsPreferCompare ownerTypePetsPreferCompare;
	// ------------
	// Compare club
	private PetIdCompare petIdCompare;
	private PetNameCompare petNameCompare;
	private PetGenderCompare petGenderCompare;
	private PetBirthdayCompare petBirthdayCompare;
	private PetTypeCompare petTypeCompare;

	public ClubsPet() {
		listClubs = new ArrayList<Club>();
		listGenericOwners = new ArrayList<Owner>();
		listGenericPets = new ArrayList<Pet>();
		fileTxtClubs = new File("./src/dataInfo/toImport/clubs.txt");
		fileCSVOwners = new File("./src/dataInfo/toImport/owner.csv");
		fileCSVPets = new File("./src/dataInfo/toImport/pets.csv");

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

		// Sort by criteria----------------------------
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
	 * Method to save one serialize file club
	 * 
	 * @param i
	 * @param nameFile
	 */
	private void saveOneSerializationFileClubs(int i, String nameFile) {
		String nameFileEdited = listClubs.get(i).getName().replace(" ", "-");
		try {
			FileOutputStream fos = new FileOutputStream("./src/dataInfo/serializable/" + nameFileEdited + ".dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(listClubs.get(i));
			oos.close();
			fos.close();
			System.out.println("Se actualizó el archivo " + nameFileEdited);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void deleteFileClub(String nameFileClub) {
		File file = new File("./src/dataInfo/serializable/" + nameFileClub + ".dat");

		if (file.delete()) {
			System.out.println("File deleted successfully");
		} else {
			System.out.println("Failed to delete the file");
		}
		System.out.println();
	}

	/**
	 * Method to read serializable files to get all club information
	 */
	private void readSerializableFilesClub() {
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
	public void sortClubsByOwnersNumber() {
		Collections.sort(listClubs);
		for (Club club : listClubs) {
			System.out.println(
					club.getId() + " " + club.getName() + " " + "-- Number of owners:" + club.getListOwners().size());
		}
	}

	/**
	 * Method to sort owners by pets number of Club
	 * 
	 * @param nameClub
	 */
	public void sortOwnersByPetsNumber(int idClub) {
		int indexClubSelected = 0;
		for (int i = 0; i < listClubs.size(); i++) {
			if (listClubs.get(i).getId() == idClub) {
				listClubs.get(i).sortOwnersByPetsNumber();
				indexClubSelected = i;
				break;
			}
		}
	}

	/**
	 * Method to sort list of clubs by criteria
	 * 
	 * @param nameCriteria
	 */
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
		for (Club club : listClubs) {
			System.out.println(club.getId() + " " + club.getName() + " " + "Created at:" + club.getCreatedAt());
		}
	}

	/**
	 * Method to sort list by bubble sort
	 */
	public void sortClubsByTraditionalsMethods(String methodSort) {
		switch (methodSort) {
		
		case "bubbleSort":
			boolean changes = false;
			while (true) {
				changes = false;
				for (int i = 1; i < listClubs.size(); i++) {
					if (listClubs.get(i).getId() < listClubs.get(i - 1).getId()) {
						Club objectClub = listClubs.get(i);
						listClubs.set(i, listClubs.get(i - 1));
						listClubs.set(i - 1, objectClub);
						changes = true;
					}
				}
				if (changes == false) {
					break;
				}
			}

			break;

		case "selection":
			for (int i = 0; i < listClubs.size() - 1; i++) {
				int index = i;
				for (int j = i + 1; j < listClubs.size(); j++) {
					if (listClubs.get(j).getId() < listClubs.get(index).getId()) {
						index = j;
					}
				}
				Club smallerClub = listClubs.get(index);
				listClubs.set(index, listClubs.get(i));
				listClubs.set(i, smallerClub);
			}
			break;

		case "insertion":
		        for (int i = 1; i < listClubs.size(); ++i) { 
		        	Club club = listClubs.get(i);
		        	int j = i - 1; 
		        	
		            while (j >= 0 && listClubs.get(j).getId() > club.getId()) { 
		                listClubs.set(j+1, listClubs.get(j));
		                j--; 
		            } 
		            listClubs.set(j+1 , club);
		        } 
			break;
		}

		for (Club club : listClubs) {
			System.out.println(club.getId() + " " + club.getName());
		}
	}

	/**
	 * Method to sort list of clubs by criteria
	 * @param idClub
	 * @param nameCriteria
	 */
	public void sortOwnersOfClubByCriteria(int idClub, String nameCriteria) {
		int index = 0;
		for (int i = 0; i < listClubs.size(); i++) {
			if (idClub == listClubs.get(i).getId()) {
				index = i;
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
				break;
			}
		}

		for (Owner owner : listClubs.get(index).getListOwners()) {
			int id = owner.getId();
			String name = owner.getFirstName() + " " + owner.getLastName();
			Date birthday = owner.getBirthday();
			String typePetPrefer = owner.getTypePetsPrefer();

			System.out.println(id + " " + name + " " + birthday + " " + typePetPrefer);
		}
	}

	/**
	 * Method to sort all pets of owner that belongs to Club
	 * 
	 * @param idClub
	 * @param idOwner
	 * @param nameCriteria
	 */
	public void sortPetsOfOwnersOfClubByCriteria(int idClub, int idOwner, String nameCriteria) {
		int indexClub = 0;
		int indexOwner = 0;
		for (int i = 0; i < listClubs.size(); i++) {
			if (idClub == listClubs.get(i).getId()) {
				indexClub = i;
				for (int j = 0; j < listClubs.get(i).getListOwners().size(); j++) {
					if (idClub == listClubs.get(i).getListOwners().get(i).getId()) {
						indexOwner = j;
						switch (nameCriteria) {
						case "id":
							Collections.sort(listClubs.get(i).getListOwners().get(j).getListPets(), petIdCompare);
							break;
						case "name":
							Collections.sort(listClubs.get(i).getListOwners().get(j).getListPets(), petNameCompare);
							break;
						case "gender":
							Collections.sort(listClubs.get(i).getListOwners().get(j).getListPets(), petGenderCompare);
							break;
						case "birthday":
							Collections.sort(listClubs.get(i).getListOwners().get(j).getListPets(), petBirthdayCompare);
							break;
						case "type":
							Collections.sort(listClubs.get(i).getListOwners().get(j).getListPets(), petTypeCompare);
							break;
						default:
							Collections.sort(listClubs.get(i).getListOwners().get(j).getListPets(), petIdCompare);
							break;
						}
					}
				}
			}
		}

		for (Pet pet : listClubs.get(indexClub).getListOwners().get(indexOwner).getListPets()) {
			int id = pet.getId();
			String name = pet.getName();
			Date birthday = pet.getBirthday();
			String type = pet.getType();
			String gender = pet.getGender();
			System.out.println(id + " " + name + " " + birthday + " " + type + " " + gender);
		}
	}

	/**
	 * Method to add club
	 * 
	 * @param id
	 * @param name
	 * @param createdAtString
	 * @param allowPetsType
	 * @throws ExceptionNoRepeatNameOrIdClub 
	 */
	public void addClub(int id, String name, String createdAtString, String[] allowPetsType) throws ExceptionNoRepeatNameOrIdClub {

		boolean canSave = true;

		for (int i = 0; i < listClubs.size(); i++) {
			if (id == listClubs.get(i).getId() || name.equals(listClubs.get(i).getName())) {
				canSave = false;
				break;
			}
		}

		if (canSave) {
			listClubs.add(new Club(id, name, createdAtString, allowPetsType));
			for (int i = 0; i < listClubs.size(); i++) {
				if (id == listClubs.get(i).getId()) {
					saveOneSerializationFileClubs(i, listClubs.get(i).getName());
					break;
				}
			}

		} else {
			throw new ExceptionNoRepeatNameOrIdClub("Can't create Club with the same name or id");
		}
	}

	/**
	 * Method to add new owner in a club
	 * 
	 * @param idClub
	 * @param idOwner
	 * @param firstName
	 * @param lastName
	 * @param birthday
	 * @param typePetsPrefer
	 * @throws ExceptionNoRepeatIdOwner 
	 */
	public void addOwnerToClub(int idClub, int idOwner, String firstName, String lastName, String birthday,
			String typePetsPrefer) throws ExceptionNoRepeatIdOwner {
		
		boolean userExist = false;
		for (int i = 0; i < listClubs.size(); i++) {
			if (!listClubs.get(i).verifyIfOwnerExist(idOwner)) {
				if (idClub == listClubs.get(i).getId()) {
					listClubs.get(i).addOwner(idOwner, firstName, lastName, birthday, typePetsPrefer);
					saveOneSerializationFileClubs(i, listClubs.get(i).getName());
					break;
				}
			}else {
				userExist = true;
				break;
			}
		}
		
		if(userExist) {
			throw new ExceptionNoRepeatIdOwner("Can't create user with the same ID");
		}
	}

	/**
	 * Method to add pet to owner of a club
	 * 
	 * @param idClub
	 * @param idOwner
	 * @param firstName
	 * @param lastName
	 * @param birthday
	 * @param typePetsPrefer
	 * @throws ExceptionOwnerExist 
	 */
	public void addPetToOwner(int idClub, int idOwner, int idPet, String namePet, String birthdayPet, String genderPet,
			String typePet) throws ExceptionOwnerExist {
		for (int i = 0; i < listClubs.size(); i++) {
			if (idClub == listClubs.get(i).getId() && listClubs.get(i).verifyIfOwnerExist(idOwner)) {
				try {
					listClubs.get(i).addPetToOwnerOfClub(idOwner, idPet, namePet, birthdayPet, genderPet, typePet);
				} catch (ExceptionNoRepeatNamePetOfOwner e) {
					System.out.println(e.getMessage());
				}
				// -------------------------
				saveOneSerializationFileClubs(i, listClubs.get(i).getName());
				break;
			} else {
				throw new ExceptionOwnerExist("Owner not found");
			}
		}
	}

	/**
	 * Method to delete a Club by id
	 * 
	 * @param id
	 */
	public void deleteClub(int id) {
		System.out.println(listClubs.size());
		for (int i = 0; i < listClubs.size(); i++) {
			if (id == listClubs.get(i).getId()) {
				listClubs.remove(i);
				deleteFileClub(listClubs.get(i).getName());
				System.out.println("Se borró el club con id " + id);
				break;
			}
		}
		System.out.println(listClubs.size());
	}
	

	/**
	 * Method to search club by Club id with normal search
	 * @param idClub
	 */
	public void normalSearchClub(int idClub) {
		boolean foundClub = true;
		long start = System.currentTimeMillis(); 
		for (int i = 0; i < listClubs.size(); i++) {
			if(listClubs.get(i).getId() == idClub) {
				String name = listClubs.get(i).getName();
				int id = listClubs.get(i).getId();
				Date created = listClubs.get(i).getCreatedAt();
				String petsAllowed = listClubs.get(i).getListPetsAllowString();
				foundClub = false;
				System.out.println(id+" "+name+" "+created+" "+"Pets allows:"+" "+petsAllowed);
				break;
			}
		}
		long end = System.currentTimeMillis(); 
		System.out.println("The algorithm of normal search spent: "+(start - end)+"ms");
		if(foundClub) {
			System.out.println("Not found club with id: "+idClub);
		}
	}
	
	/**
	 * Method to search club by Club Name with normal search
	 * @param nameClub
	 */
	public void normalSearchClub(String nameClub) {
		boolean foundClub = true;
		long start = System.currentTimeMillis(); 
		for (int i = 0; i < listClubs.size(); i++) {
			if(listClubs.get(i).getName().contains(nameClub)) {
				String name = listClubs.get(i).getName();
				int id = listClubs.get(i).getId();
				Date created = listClubs.get(i).getCreatedAt();
				String petsAllowed = listClubs.get(i).getListPetsAllowString();
				foundClub = false;
				System.out.println(id+" "+name+" "+created+" "+"Pets allows:"+" "+petsAllowed);
				break;
			}
		}
		long end = System.currentTimeMillis(); 
		System.out.println("The algorithm of normal search spent: "+(start - end)+"ms");
		if(foundClub) {
			System.out.println("Not found club with name: "+nameClub);
		}
	}
	
	
	/**
	 * Method to search club by Club Id with binary search
	 * @param idClub
	 */
	public void binarySearchClub(int idClub) {
		long startTime = System.currentTimeMillis(); 
		//--------------------------------
		String name = "";
		int id = 0;
		String petsAllowed = "";
		//--------------------------------
		boolean found = false;
		int start = 0;
		int end = listClubs.size() -1;
		//--------------------------------
		Collections.sort(listClubs, clubIdCompare);
		//--------------------------------
		while (start <= end && !found) {
			int mid = Math.round((start + end)/2);
			if(listClubs.get(mid).getId() == idClub) {
				found = true;
				//-----------------------------
				name = listClubs.get(mid).getName();
				id = listClubs.get(mid).getId();
				petsAllowed = listClubs.get(mid).getListPetsAllowString();
			}
			else if(listClubs.get(mid).getId()>idClub) {
				end = mid - 1;
			}else {
				start=mid+1;
			}
		}
		long endTime = System.currentTimeMillis(); 
		System.out.println("The algorithm of binary search spent: "+(startTime - endTime)+"ms");
		if(found) {
			System.out.println(id+" "+name+" "+"Pets allows:"+" "+petsAllowed);
		}else {
			System.out.println("Not found club with id: "+idClub);
		}
	}
	
	
	/**
	 * Method to search club by Club Name with binary search
	 * @param nameClub
	 */
	public void binarySearchClub(String nameClub) {
		long startTime = System.currentTimeMillis(); 
		//--------------------------------
		String name = "";
		int id = 0;
		String petsAllowed = "";
		//--------------------------------
		boolean found = false;
		int start = 0;
		int end = listClubs.size() -1;
		//--------------------------------
		Collections.sort(listClubs, clubNameCompare);
		//--------------------------------
		while (start <= end && !found) {
			int mid = Math.round((start + end)/2);
			if(listClubs.get(mid).getName().contains(nameClub)) {
				found = true;
				//-----------------------------
				name = listClubs.get(mid).getName();
				id = listClubs.get(mid).getId();
				petsAllowed = listClubs.get(mid).getListPetsAllowString();
			}
			else if(listClubs.get(mid).getName().compareTo(nameClub) > 0) {
				end = mid - 1;
			}else {
				start=mid+1;
			}
		}
		long endTime = System.currentTimeMillis(); 
		System.out.println("The algorithm of binary search spent: "+(startTime - endTime)+"ms");
		if(found) {
			System.out.println(id+" "+name+" "+"Pets allows:"+" "+petsAllowed);
		}else {
			System.out.println("Not found club with name: "+nameClub);
		}
	}

	/**
	 * Method to delete a Club by name
	 * 
	 * @param name
	 */
	public void deleteClub(String name) {
		for (int i = 0; i < listClubs.size(); i++) {
			if (name.equals(listClubs.get(i).getName())) {
				listClubs.remove(i);
				deleteFileClub(listClubs.get(i).getName());
				System.out.println("Se borró el club con nombre " + name);
				break;
			}
		}
	}

	/**
	 * Delete owner by id
	 * 
	 * @param id
	 */
	public void deleteOwnerInClub(int idOwner) {
		for (int i = 0; i < listClubs.size(); i++) {
			listClubs.get(i).removeOwner(idOwner);
		}
	}

	/**
	 * Delete owner by name
	 * 
	 * @param name
	 */
	public void deleteOwner(String firstName, String lastName) {
		for (int i = 0; i < listClubs.size(); i++) {
			listClubs.get(i).removeOwner(firstName, lastName);
		}
	}

	/**
	 * Delete owner by id
	 * 
	 * @param id
	 */
	public void deletePet(int id) {
		for (int i = 0; i < listClubs.size(); i++) {
			listClubs.get(i).removePetFromOwner(id);
		}
	}

	/**
	 * Delete Pet by name
	 * 
	 * @param name
	 */
	public void deletePet(String name) {
		for (int i = 0; i < listClubs.size(); i++) {
			listClubs.get(i).removePetFromOwner(name);
		}
	}
}
