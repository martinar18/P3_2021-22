package StatePattern;
/*
 * Questa classe con l'uso del main prova 
 * il funzionamento del pattern istanziando 
 * la classe car e stabilendone gli stati.
 */
public class StateTest {
	public static void main(String[] args) {
		//Creo oggetti 
		Car c1 = new Car("12345", "RENAULT SCENIC","NAPOLI PARK");
		
		System.out.println(c1.getState());
		c1.switchState(c1);
		System.out.println(c1.getState());
		if(c1.getState() instanceof UnavailableCarState ) {
			System.out.println("hello");
		}
		
	}
}
