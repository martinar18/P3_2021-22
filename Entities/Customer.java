package Entities;


//Pattern
import StatePattern.Car;
import StrategyPattern.BancomatStrategy;
import StrategyPattern.CreditCardStrategy; 


public class Customer {
private String name;
private String surname;
private String username;
private String drive_license;
private Car car;
private CreditCardStrategy cc;
private BancomatStrategy bc;

public Customer(String n, String s, String u, String dl) {
	this.name=n;
	this.surname=s;
	this.username=u;
	this.drive_license=dl;
	this.car=null;
	this.cc=null;
	this.bc=null;
}

//Get&Set methods
public void setName(String name) {
	this.name=name;
}
public void setSurname(String surname) {
	this.surname=surname;
}
public void setUsername(String username) {
	this.username=username;
}
public void setDriveLicense(String drive_license) {
	this.drive_license=drive_license;
}

public String getName() {
	return name;
}
public String getSurname() {
	return surname;
}
public String getUsername() {
	return username;
}
public String getDriveLicense() {
	return drive_license;
}
public Car getCar() {
	return this.car;
}
public void setCar(Car car) {
	this.car=car;
}
public CreditCardStrategy getCC() {
	return this.cc;
}
public void setCC(CreditCardStrategy cc) {
	this.cc=cc;
}
public BancomatStrategy getBanc() {
	return this.bc;
}
public void setBanc(BancomatStrategy bc) {
	this.bc=bc;
}
}

