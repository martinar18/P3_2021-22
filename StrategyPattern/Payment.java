package StrategyPattern;
import java.text.ParseException;

public class Payment {

	public static void main(String[] args) throws ParseException {
		BancomatStrategy c = new BancomatStrategy("Paolo Rossi","1234567890","01-11-2022",123);
		BancomatStrategy d = new BancomatStrategy("Maria","1234588880","01-01-2022",523);
		CreditCardStrategy cc = new CreditCardStrategy("maria","4853759","01-11-2022",456);
		d.pay(10);
		c.pay(12);
		cc.pay(10);
	}

}
