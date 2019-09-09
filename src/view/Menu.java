package view;

import java.util.Scanner;

import model.Logic;

public class Menu {
	private Scanner inputOption;
	private Logic logic; 
	
	public Menu() {
		inputOption = new Scanner(System.in);
		logic		= new Logic();
	}
	
	public void selectOption() {
		boolean exit = false;
		showBasicInfo();
		
		while (!exit) {
			int optionMenu = showMenuOptions();
			
			switch (optionMenu) {
			case 1:
					System.out.println("Entro 1");
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
}
