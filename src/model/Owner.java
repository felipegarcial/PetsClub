package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Owner implements Serializable, Comparable<Owner>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String firstName,lastName,typePetsPrefer;
	private Date birthday;
	private ArrayList <Pet> listPets;
	
	public Owner(int id,String firstName,String lastName,String birthdayString,String typePetsPrefer) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.typePetsPrefer = typePetsPrefer;
		listPets = new ArrayList<Pet>();
		try {
			this.birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Owner(int id,String firstName,String lastName,Date birthday,String typePetsPrefer) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.typePetsPrefer = typePetsPrefer;
		this.birthday = birthday;
		listPets = new ArrayList<Pet>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTypePetsPrefer() {
		return typePetsPrefer;
	}

	public void setTypePetsPrefer(String typePetsPrefer) {
		this.typePetsPrefer = typePetsPrefer;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public ArrayList<Pet> getListPets() {
		return listPets;
	}

	public void addPet(int id,String name, Date birthday,String gender,String type) {
		listPets.add(new Pet(id,name,birthday,gender,type));
	}
	
	public void addPet(int id,String name, String birthday,String gender,String type) {
		listPets.add(new Pet(id,name,birthday,gender,type));
	}
		
	public void addPet(Pet pet) {
		listPets.add(pet);
	}

	@Override
	public int compareTo(Owner owner) {
		return listPets.size() - owner.getListPets().size();
	}
	
	public boolean verifyIfPetExist(String namePet) {
		boolean existPet = false;
		for (int i = 0; i < listPets.size(); i++) {
			if(namePet.equals(listPets.get(i).getName())){
				existPet = true;
				break;
			}else {
				System.out.println("La mascota ya existe en este usuario");
				break;
			}
		}
		return existPet;
	}
}
