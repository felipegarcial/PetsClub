package view;

import java.util.Scanner;

import exceptions.ExceptionExistClub;
import exceptions.ExceptionNoRepeatIdOwner;
import exceptions.ExceptionNoRepeatNameOrIdClub;
import exceptions.ExceptionOwnerExist;
import model.Logic;

public class Menu {
	private Scanner inputOption;
	private Logic logic;

	public Menu() {
		inputOption = new Scanner(System.in);
		logic = new Logic();
	}

	public void selectOption() {
		boolean exit = false;
		showBasicInfo();
		while (!exit) {
			int optionMenu = showMenuOptions();

			switch (optionMenu) {
			case 1:
				createElementEntity();
				break;
			case 2:
				sortEntity();
				break;
			case 3:
				deleteEntity();
				break;
			case 4:
				searchClub();
				break;
			case 5:
				exit = true;
				break;

			default:
				break;
			}
		}

		System.out.println("***************************************");
		System.out.println("GRACIAS POR USAR EL PROGRAMA");
		System.out.println("***************************************");
	}

	private void showBasicInfo() {
		System.out.println("Escoja la opci�n que desee realizar en el software");
		System.out.println("El programa cuenta con tres entidades: Club, Due�os de mascota y Mascota");
		System.out.println("***************************************");
		System.out.println("***************************************");
	}

	/**
	 * Method to show the menu option to the user
	 * 
	 * @return int - option selected
	 */
	private int showMenuOptions() {
		System.out.println("");
		System.out.println("Escoja una opci�n:");
		System.out.println("1. Crear nuevo elemente en alguna entidad");
		System.out.println("2. Mostrar listados ordenados de entidades");
		System.out.println("3. Borrar elemento en alguna entidad");
		System.out.println("4. Buscar un club");
		System.out.println("5. Salir del programa");
		int value = inputOption.nextInt();
		return value;
	}

	private void createElementEntity() {
		System.out.println("1. Crear un Club");
		System.out.println("2. Crear un Duenio");
		System.out.println("3. Crear una Mascota");

		int value = inputOption.nextInt();

		switch (value) {
		case 1:
			createClub();
			break;
		case 2:
			createOwner();
			break;
		case 3:
			createPet();
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @param option
	 * @return
	 */
	private String covertOptionInNameCare(int option) {
		switch (option) {
		case 1:
			return "dog";
		case 2:
			return "cat";
		case 3:
			return "hamster";
		case 4:
			return "turtle";
		case 5:
			return "horse";
		case 6:
			return "cow";
		case 7:
			return "bird";
		case 8:
			return "rabbit";
		default:
			return "dog";
		}
	}

	/**
	 * 
	 */

	// TODO Poner excepeciones para menejar el formato y la entrada de lo que se
	// requiere
	private void createClub() {
		System.out.println("Por favor introducir Id club");
		int id = inputOption.nextInt();
		System.out.println("Por favor introducir nombre club");
		String name = inputOption.next();
		System.out.println("Por favor introducir fecha de creaci�n club en formato yyyy/MM/DD ejemplo: 2019/06/26");
		String createdAtString = inputOption.next();
		System.out.println("Por favor introducir cuantos tipos de mascotas aceptan en el club");
		// -----------------------------------------
		int numberTypesPets = inputOption.nextInt();
		String typePetsAllowed = "";
		// -----------------------------------------
		for (int i = 0; i < numberTypesPets; i++) {
			System.out.println("Por favor introducir tipo " + (i + 1) + " de mascota permitida");
			if (!typePetsAllowed.contains("dog")) {
				System.out.println("1. Dog");
			}
			if (!typePetsAllowed.contains("cat")) {
				System.out.println("2. Cat");
			}
			if (!typePetsAllowed.contains("hamster")) {
				System.out.println("3. Hamster");
			}
			if (!typePetsAllowed.contains("turtle")) {
				System.out.println("4. Turtle");
			}
			if (!typePetsAllowed.contains("horse")) {
				System.out.println("5. Horse");
			}
			if (!typePetsAllowed.contains("cow")) {
				System.out.println("6. Cow");
			}
			if (!typePetsAllowed.contains("bird")) {
				System.out.println("7. Bird");
			}
			if (!typePetsAllowed.contains("rabbit")) {
				System.out.println("8. Rabbit");
			}
			// -----------------------------------------
			int typePet = inputOption.nextInt();
			typePetsAllowed += covertOptionInNameCare(typePet) + ",";
		}
		try {
			logic.addClub(id, name, createdAtString, typePetsAllowed.split(","));
		} catch (ExceptionNoRepeatNameOrIdClub e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 */

	private void createOwner() {
		System.out.println("Please enter the club id");
		int idClub = inputOption.nextInt();
		System.out.println("Please enter the owner id");
		int idOwner = inputOption.nextInt();
		System.out.println("Please enter firstname");
		String firstName = inputOption.next();
		System.out.println("Please enter lastname");
		String lastName = inputOption.next();
		System.out.println("Please enter the birthday in this format yyyy-MM-DD example: 2019-06-26");
		String birthday = inputOption.next();
		printListTypePets();
		int optionTypePet = inputOption.nextInt();
		String typePetPrefer = covertOptionInNameCare(optionTypePet);
		// -----------------------------------------
		try {
			logic.addOwnerToClub(idClub, idOwner, firstName, lastName, birthday, typePetPrefer);
		} catch (ExceptionNoRepeatIdOwner e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 */
	private void createPet() {
		System.out.println("Please enter the club id");
		int idClub = inputOption.nextInt();
		System.out.println("Please enter the owner id");
		int idOwner = inputOption.nextInt();
		System.out.println("Please enter the pet id");
		int idPet = inputOption.nextInt();
		System.out.println("Please enter name");
		String namePet = inputOption.next();
		System.out.println("Please enter gender");
		System.out.println("1. Male");
		System.out.println("2. Famele");
		String genderPet = inputOption.nextInt() == 1 ? "Male" : "Famele";
		System.out.println("Please enter the birthday in this format yyyy/MM/DD example: 2019/06/26");
		String birthdayPet = inputOption.next();
		printListTypePets();
		int optionTypePet = inputOption.nextInt();
		String typePet = covertOptionInNameCare(optionTypePet);
		// -----------------------------------------
		try {
			logic.addPetToOwner(idClub, idOwner, idPet, namePet, birthdayPet, genderPet, typePet);
		} catch (ExceptionOwnerExist e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Print all options of type of pets
	 */
	private void printListTypePets() {
		System.out.println("Please enter yor favorite type of pet");
		System.out.println("1. Dog");
		System.out.println("2. Cat");
		System.out.println("3. Hamster");
		System.out.println("4. Turtle");
		System.out.println("5. Horse");
		System.out.println("6. Cow");
		System.out.println("7. Bird");
		System.out.println("8. Rabbit");
	};

	private void deleteEntity() {
		System.out.println("1. Delete a Club");
		System.out.println("2. Delete a Owner");
		System.out.println("3. Delete a Pet");

		int value = inputOption.nextInt();

		switch (value) {
		case 1:
			deleteClub();
			break;
		case 2:
			deleteOwner();
			break;
		case 3:
			deletePet();
			break;
		}
	}

	private void sortEntity() {
		System.out.println("1. Show clubs sorted");
		System.out.println("2. Show owners sorted");
		System.out.println("3. Show pets sorted");

		int value = inputOption.nextInt();

		switch (value) {
		case 1:
			showClubsSorted();
			break;
		case 2:
			showOwnersSorted();
			break;
		case 3:
			showPetsSorted();
			break;
		}
	}

	private void showPetsSorted() {
		System.out.println("Sort Pets of owner:");
		// --------------------------
		System.out.println("Select the club");
		logic.sortClubByCriteria("id");
		int idClub = inputOption.nextInt();
		// --------------------------
		System.out.println("Select the Owner");
		logic.sortOwnersOfClubByCriteria(idClub, "id");
		;
		int idOwner = inputOption.nextInt();
		// --------------------------
		System.out.println("1. By id");
		System.out.println("2. By name");
		System.out.println("3. By birthday");
		System.out.println("4. By type of pet");
		System.out.println("5. By gender");
		// --------------------------
		int value = inputOption.nextInt();
		switch (value) {
		case 1:
			logic.sortPetsOfOwnersOfClubByCriteria(idClub, idOwner, "id");
			break;
		case 2:
			logic.sortPetsOfOwnersOfClubByCriteria(idClub, idOwner, "name");
			break;
		case 3:
			logic.sortPetsOfOwnersOfClubByCriteria(idClub, idOwner, "birthday");
			break;
		case 4:
			logic.sortPetsOfOwnersOfClubByCriteria(idClub, idOwner, "type");
			break;
		case 5:
			logic.sortPetsOfOwnersOfClubByCriteria(idClub, idOwner, "gender");
			break;
		default:
			logic.sortPetsOfOwnersOfClubByCriteria(idClub, idOwner, "id");
			break;
		}
	}

	private void showOwnersSorted() {
		System.out.println("Sort Owners:");
		System.out.println("Select the club");
		logic.sortClubByCriteria("id");
		int idClub = inputOption.nextInt();
		// --------------------------
		System.out.println("1. By number of pets (Comparable)");
		System.out.println("2. By id owner");
		System.out.println("3. By firstname owner");
		System.out.println("4. By lastname owner");
		System.out.println("5. By types of pets prefer owner");
		System.out.println("6. By birthday");
		// --------------------------
		int value = inputOption.nextInt();
		switch (value) {
		case 1:
			logic.sortOwnersByPetsNumber(idClub);
			break;
		case 2:
			logic.sortOwnersOfClubByCriteria(idClub, "id");
			break;
		case 3:
			logic.sortOwnersOfClubByCriteria(idClub, "firstName");
			break;
		case 4:
			logic.sortOwnersOfClubByCriteria(idClub, "lastName");
			break;
		case 5:
			logic.sortOwnersOfClubByCriteria(idClub, "typePetsPrefer");
			break;
		case 6:
			logic.sortOwnersOfClubByCriteria(idClub, "birthday");
			break;
		default:
			logic.sortClubsByOwnersNumber();
			break;
		}

	}

	private void showClubsSorted() {
		System.out.println("Sort Clubs:");
		System.out.println("1. By number of owners (Comparable)");
		System.out.println("2. By id club");
		System.out.println("3. By name club");
		System.out.println("4. By types of pets club");
		System.out.println("5. By date of create club");
		System.out.println("6. By id with burble sort");
		System.out.println("7. By id with selection sort");
		System.out.println("8. By id with insertion sort");
		int value = inputOption.nextInt();
		switch (value) {
		case 1:
			logic.sortClubsByOwnersNumber();
			break;
		case 2:
			logic.sortClubByCriteria("id");
			break;
		case 3:
			logic.sortClubByCriteria("name");
			break;
		case 4:
			logic.sortClubByCriteria("allowPetsType");
			break;
		case 5:
			logic.sortClubByCriteria("createdAt");
			break;
		case 6:
			logic.sortClubsByTraditionalsMethods("bubbleSort");
			break;
		case 7:
			logic.sortClubsByTraditionalsMethods("selection");
			break;
		case 8:
			logic.sortClubsByTraditionalsMethods("insertion");
			break;
		default:
			logic.sortClubsByOwnersNumber();
			break;
		}
	}

	private void deletePet() {
		System.out.println("Delete pet:");
		System.out.println("1. Delete for name");
		System.out.println("2. Delete for id");
		int value = inputOption.nextInt();
		if (value == 1) {
			System.out.println("Enter the id of the pet to delete");
			String name = inputOption.next();
			logic.deletePet(name);
		} else if (value == 2) {
			System.out.println("Enter the id of the pet to delete");
			int id = inputOption.nextInt();
			logic.deletePet(id);
		}
	}

	private void deleteOwner() {
		System.out.println("Delete owner:");
		System.out.println("1. Delete for name");
		System.out.println("2. Delete for id");
		int value = inputOption.nextInt();
		if (value == 1) {
			System.out.println("Enter the firstname of the owner to delete");
			String firstName = inputOption.next();
			System.out.println("Enter the lastname of the owner to delete");
			String lastName = inputOption.next();
			logic.deleteOwner(firstName, lastName);
		} else if (value == 2) {
			System.out.println("Enter the id of the owner to delete");
			int id = inputOption.nextInt();
			logic.deleteOwnerInClub(id);
		}
	}

	private void deleteClub() {
		System.out.println("Delete club:");
		System.out.println("1. Delete for name");
		System.out.println("2. Delete for id");
		int value = inputOption.nextInt();
		if (value == 1) {
			System.out.println("Enter the name of the club to delete");
			String name = inputOption.next();
			logic.deleteClub(name);
		} else if (value == 2) {
			System.out.println("Enter the id of the club to delete");
			int id = inputOption.nextInt();
			logic.deleteClub(id);
		}

	}

	private void searchClub() {
		System.out.println("Search club:");
		System.out.println("1. By name club");
		System.out.println("2. By id club");
		int value = inputOption.nextInt();
		if (value == 1) {
			System.out.println("Enter the name of the club to find");
			String name = inputOption.next();
			logic.normalSearchClub(name);
			logic.binarySearchClub(name);
		} else if (value == 2) {
			System.out.println("Enter the id of the club to find");
			int id = inputOption.nextInt();
			
			logic.normalSearchClub(id);
			logic.binarySearchClub(id);
		}

	}

}
