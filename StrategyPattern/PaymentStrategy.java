package StrategyPattern;
import java.text.ParseException;

public interface PaymentStrategy {
public boolean pay(double amount) throws ParseException;
}
