package StrategyPattern;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreditCardStrategy implements PaymentStrategy{

	private String owner;
	private String ncard;
	private String expiry;
	private int cvv;
	
	public CreditCardStrategy(String owner, String ncard, String expiry, int cvv){
		this.cvv=cvv;
		this.owner=owner;
		this.ncard=ncard;
		this.expiry=expiry;
	}
	
	//Set&Get methods
	public	void setOwner(String owner) {
			this.owner=owner;
		}
	public	void setNcard(String ncard) {
			this.ncard=ncard;
		}
	public	void setCvv(int cvv) {
			this.cvv=cvv;
		}
	public	void setExpiry(String expiry) {
			this.expiry=expiry;
		}
	public	String getOwner() {
			return owner;
		}
	public	String getNcard() {
			return ncard;
		}
	public	int getCvv() {
			return cvv;
		}
	public	String getExpiry() {
			return expiry;
		} 
	
	@Override
	public boolean pay(double amount)  throws ParseException {
		//Prima di ammettere un pagamento, viene effettuato un controllo sulla data di scadenza della carta di credito.
		//Poi un controllo sulla lunghezza della stringa numero.
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date data = format.parse(this.expiry);
		System.out.println(this.ncard.length());
		if(today.compareTo(data)<0) {
			if((this.ncard).length() == 16) { //Sono ammesse carte di credito a 16 cifre.
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
			System.out.println("Your credit card expired.");
			return false;
		}
	}

	public Date getDateFormat(String mydata) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date data = format.parse(mydata);
		return data;
	}
	
}
