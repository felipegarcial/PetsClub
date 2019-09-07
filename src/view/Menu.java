package view;

import java.util.Scanner;

public class Menu {
	private Scanner inputOption;
	
	public Menu() {
		
	}
	
	public void selectOption() {
		boolean exitMenu = false;
		int optionMenu = menuOptions();
		
		while (exitMenu) {
			switch (optionMenu) {
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;

			default:
				break;
			}
			
		}
	}
	

	/**
	 * Method to show the menu option to the user
	 * @return int - option selected
	 */
	public int menuOptions() {
		System.out.println("Escoja la opción que desee realizar en el software");
		System.out.println("1. Crear club");
		System.out.println("2. Crear dueño de mascota");
		System.out.println("3. Crear mascota");
		System.out.println("4. Mostrar listados ordenados de entidades ");

		return inputOption.nextInt();
	}
}
