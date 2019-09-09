package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pet implements Serializable{
	private int id;
	private String name,gender,type;
	private Date birthday;
	public Pet(int id,String name, String birthdayString,String gender,String type) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.type = type; 
		
		try {
			this.birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Pet(int id,String name, Date birthday,String gender,String type) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.type = type; 
		this.birthday = birthday;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
