package StatePattern;
/*
 * Un'auto in questo stato è prenotata, dunque
 * non è disponibile per nuove prenotazioni finchè
 * non verrà restituita e cambiato il suo stato.
 */
public class UnavailableCarState implements CarState {

	static UnavailableCarState instance = new UnavailableCarState();
	public static UnavailableCarState instance() {
		return instance;
	}
	
	public void switchState(Car car) {
		car.setState(AvailableCarState.instance);
		}
}
