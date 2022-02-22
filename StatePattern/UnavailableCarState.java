package StatePattern;
/*
 * Un'auto in questo stato � prenotata, dunque
 * non � disponibile per nuove prenotazioni finch�
 * non verr� restituita e cambiato il suo stato.
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
