package StatePattern;
/*
 * Il pattern State Ŕ utilizzato per gestire la disponibilitÓ
 * delle auto, in particolare la possibilitÓ di effettuare dei
 * cambi di stato al verificarsi di una prenotazione e di un reso.
*/
public interface CarState {
public void switchState(Car car);
}
