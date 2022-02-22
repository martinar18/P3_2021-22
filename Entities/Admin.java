package Entities;


public class Admin {
	private String username;
	private String name;
	private String surname;
	
	public Admin( String name, String surname, String username) {
		this.username=username;
		this.name=name;
		this.surname=surname;
	}
	//Get&Set methods
	public void setUsername(String username) {
		this.username=username;
	}
	public void setName(String name) {
		this.name=name;
	}
	public void setSurname(String surname) {
		this.surname=surname;
	}
	public String getUsername() {
		return username;
	}
	public String getName() {
		return this.name;
	}
	public String getSurname() {
		return this.surname;
	}
		

}