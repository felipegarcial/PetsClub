package view;

import java.util.Scanner;

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
				System.out.println("Entro 1");
				createElementEntity();
				break;
			case 2:
				System.out.println("Entro 2");
				break;
			case 3:
				System.out.println("Entro 3");
				break;
			case 4:
				System.out.println("Entro 4");
				break;
			case 5:
				exit = true;
				break;

			default:
				System.out.println("Entro 4");
				break;
			}
		}

		System.out.println("***************************************");
		System.out.println("GRACIAS POR USAR EL PROGRAMA");
		System.out.println("***************************************");
	}

	private void showBasicInfo() {
		System.out.println("Escoja la opción que desee realizar en el software");
		System.out.println("El programa cuenta con tres entidades: Club, Dueños de mascota y Mascota");
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
		System.out.println("Escoja una opción:");
		System.out.println("1. Crear nuevo elemente en alguna entidad");
		System.out.println("2. Mostrar listados ordenados de entidades");
		System.out.println("3. Borrar elemento en alguna entidad");
		System.out.println("4. Buscar elemento de una entidad");
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
	private void createClub() {
		System.out.println("Por favor introducir Id club");
		int id = inputOption.nextInt();
		System.out.println("Por favor introducir nombre club");
		String name = inputOption.next();
		System.out.println("Por favor introducir fecha de creación club en formato yyyy/MM/DD ejemplo: 2019/06/26");
		String createdAtString = inputOption.next();
		System.out.println("Por favor introducir cuantos tipos de mascotas aceptan en el club");
		// -----------------------------------------
		int numberTypesPets = inputOption.nextInt();
		String typePetsAllowed = "";
		// -----------------------------------------
		for (int i = 0; i < numberTypesPets; i++) {
			System.out.println("Por favor introducir tipo " + (i + 1) + " de mascota permitida");
			if(!typePetsAllowed.contains("dog")) {
				System.out.println("1. Dog");
			}
			if(!typePetsAllowed.contains("cat")) {
				System.out.println("2. Cat");
			}
			if(!typePetsAllowed.contains("hamster")) {
				System.out.println("3. Hamster");
			}
			if(!typePetsAllowed.contains("turtle")) {
				System.out.println("4. Turtle");
			}
			if(!typePetsAllowed.contains("horse")) {
				System.out.println("5. Horse");
			}
			if(!typePetsAllowed.contains("cow")) {
				System.out.println("6. Cow");
			}
			if(!typePetsAllowed.contains("bird")) {
				System.out.println("7. Bird");
			}
			if(!typePetsAllowed.contains("rabbit")) {
				System.out.println("8. Rabbit");
			}
			// -----------------------------------------
			int typePet = inputOption.nextInt();
			typePetsAllowed += covertOptionInNameCare(typePet) + ",";
		}
		logic.addClub(id, name, createdAtString, typePetsAllowed.split(","));
	}
	
	/**
	 * 
	 */
	public void createOwner() {
		System.out.println("Please enter the club id");
		int idClub = inputOption.nextInt();
		System.out.println("Please enter the owner id");
		int idOwner = inputOption.nextInt();
		System.out.println("Please enter firstname");
		String firstName = inputOption.next();
		System.out.println("Please enter lastname");
		String lastName = inputOption.next();
		System.out.println("Please enter the birthday in this format yyyy/MM/DD example: 2019/06/26");
		String createdAtString = inputOption.next();
		printListTypePets(); 
		int optionTypePet = inputOption.nextInt();
		String typePetPrefer = covertOptionInNameCare(optionTypePet);
		System.out.println("=================================");
		System.out.println(idClub);
		System.out.println(idOwner);
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(createdAtString);
		System.out.println(typePetPrefer);
	}
	
	
	/**
	 * 
	 */
	public void createPet() {
		System.out.println("Please enter the club id");
		int idClub = inputOption.nextInt();
		System.out.println("Please enter the owner id");
		int idOwner = inputOption.nextInt();
		System.out.println("Please enter the pet id");
		int idPet = inputOption.nextInt();
		System.out.println("Please enter name");
		String name = inputOption.next();
		System.out.println("Please enter gender");
		String gender = inputOption.next();
		System.out.println("Please enter the birthday in this format yyyy/MM/DD example: 2019/06/26");
		String birthday = inputOption.next();
		printListTypePets(); 
		int optionTypePet = inputOption.nextInt();
		String typePet = covertOptionInNameCare(optionTypePet);
		System.out.println("=================================");
		System.out.println(idClub);
		System.out.println(idOwner);
		System.out.println(idPet);
		System.out.println(name);
		System.out.println(gender);
		System.out.println(birthday);
		System.out.println(typePet);
	}
	
	
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
	
}



