package StrategyPattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BancomatStrategy implements PaymentStrategy{
	
	private String owner;
	private String ncard;
	private int pin;
	private String expiry;

	public BancomatStrategy(String owner, String ncard, String expiry, int pin){
		this.pin=pin;
		this.owner=owner;
		this.ncard=ncard;
		this.expiry=expiry;
	}
	
	@Override
	public boolean pay(double amount)  throws ParseException {
	//Prima di ammettere un pagamento, viene effettuato un controllo sulla data di scadenza del bancomat.
		//Poi un controllo sulla lunghezza della stringa numero.
		Date today= new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date data = format.parse(this.expiry);

		if(today.compareTo(data)<0) {
			if((this.ncard).length()>=14 && (this.ncard).length()<=18 ) { //Esistono Bancomat con stringa da 14-15-16 ecc..
				System.out.println("Welcome "+owner+"!");
						System.out.println("Successful payment. Thanks!");
						return true;
			}
			else {
				System.out.println("Invalid number length.");
				return false;
			}
		}
		else {
			System.out.println("Your bancomat expired.");
			return false;
		}
	} 

	//Set&Get methods
	public void setOwner(String owner) {
		this.owner=owner;
	}
	public void setNcard(String ncard) {
		this.ncard=ncard;
	}
	public void setPin(int pin) {
		this.pin=pin;
	}
	public void setExpiry(String expiry) {
		this.expiry=expiry;
	}
	public String getOwner() {
		return owner;
	}
	public String getNcard() {
		return ncard;
	}
	public int getPin() {
		return pin;
	}
	public String getExpiry() {
		return expiry;
	}
	public Date getDateFormat(String mydata) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date data = format.parse(mydata);
		return data;
	}
}
