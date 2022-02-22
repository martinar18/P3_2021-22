package StatePattern;
/*
 * In fase di creazione dell'oggetto car, questo è
 * lo stato di default dal momento che l'auto risulta
 * disponibile. Un'eventuale prenotazione modifichere
 * il suo stato in Unavailable.
 */
public class AvailableCarState implements CarState {

	static AvailableCarState instance = new AvailableCarState();
	public static AvailableCarState instance() {
		return instance;
	}
	
	@Override
	public void switchState(Car car) {
		car.setState(UnavailableCarState.instance);
	}

}
