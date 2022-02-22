package StatePattern;


public class Car implements CarState{
private String plate;
private String mod;
private String parking;

private CarState currentState;

public Car(String plate, String mod, String parking) {
	this.plate=plate;
	this.mod=mod;
	this.parking=parking;
	this.currentState=AvailableCarState.instance();
}
//Get&Set methods
public void setPlate(String plate) {
	this.plate=plate;
}
public void setMod(String mod) {
	this.mod=mod;
}

public void setParking(String parking) {
	this.parking=parking;
}

public void setState(CarState status) {
	this.currentState=status;
}
public String getPlate(){
	return this.plate;
}
public String getMod(){
	return this.mod;
}
public String getParking(){
	return this.parking;
}

public CarState getState() {
	return this.currentState;
}
@Override
public void switchState(Car car) { 
	this.currentState.switchState(this);
}
}
