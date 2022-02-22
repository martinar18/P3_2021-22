package StatePattern;
/*
 * Il pattern State � utilizzato per gestire la disponibilit�
 * delle auto, in particolare la possibilit� di effettuare dei
 * cambi di stato al verificarsi di una prenotazione e di un reso.
*/
public interface CarState {
public void switchState(Car car);
}
