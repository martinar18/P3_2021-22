package StatePattern;
/*
 * Il pattern State è utilizzato per gestire la disponibilità
 * delle auto, in particolare la possibilità di effettuare dei
 * cambi di stato al verificarsi di una prenotazione e di un reso.
*/
public interface CarState {
public void switchState(Car car);
}
