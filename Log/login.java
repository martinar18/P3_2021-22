package Log;


//DB:
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Connection.DBConnection;
import Entities.Admin;
import Entities.Customer;
import Frames.AdminMenu;
import Frames.UserMenu;
import StatePattern.*;
import StrategyPattern.BancomatStrategy;
import StrategyPattern.CreditCardStrategy;

//BOUNDARY:

//Pattern in uso SINGLETON
public class login {
private String username;
private String password;
private static login instance;
/*
 * instance è static 
 */

public login() {
	//this constructor is empty.
}
public static login getInstance() {
	if(instance==null) {
		instance=new login();
	}
	return instance;
}
//Get&Set methods
public void setUsername(String username) {
	this.username=username;
}
public void setPassword(char[] cs) {
	this.password=String.valueOf(cs);
	/*
	 * E' stato necessario poichè nella 
	 * finestra del login, il metodo .getPassword
	 * restituisce un char[] e non uno String.
	 */
}
public String getUsername() {
	return username;
}
public String getPassword() {
	return password;
}
//Metodo per il login quando si preme il bottone di accesso nel form...
public void log() {
	//deve necessariamente diramarsi in CUSTOMER / ADMIN / INCORRECTLOGIN
	Connection connect = DBConnection.openConnection();
	PreparedStatement prepstat;
	try {
		System.out.println(this.username);
		System.out.println(this.password);
		//La query per interrogare il DB
		prepstat = connect.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?;");
		prepstat.setString(1,this.username);
		prepstat.setString(2,this.password);
		ResultSet res = (ResultSet) prepstat.executeQuery();
		//Esito dell'interrogazione:
			//La colonna 1 di user è type che assume valori:
			// 0 per amministratore e 1 per cliente.
			if(res.next()) {
			if(((ResultSet)res).getInt(1)==0) {
				String name = ((ResultSet)res).getString(4);
				String surname = ((ResultSet)res).getString(5);
				String username = ((ResultSet)res).getString(2);
				Admin a = new Admin(name, surname, username);
				System.out.println(a.getUsername());
				System.out.println(a.getSurname());
				System.out.println(a.getName());
				AdminMenu am = new AdminMenu();
				am.main(null);
			}
			else if (((ResultSet)res).getInt(1)==1) {
				String name = ((ResultSet)res).getString(4);
				String surname = ((ResultSet)res).getString(5);
				String username = ((ResultSet)res).getString(2);
				String drivelicense = ((ResultSet)res).getString(6);
				Customer c = new Customer(name, surname, username, drivelicense);
				initializeCustomerCar(c);
				initializePayMethod(c);
				UserMenu cm = new UserMenu(c);
			}
			}
		else {
			JOptionPane.showMessageDialog(null, "Invalid username or password. Retry.");
			
		}

		connect.close();
		prepstat.close();
		
	} catch (SQLException except) {
		except.printStackTrace();
	}
}
//Metodo per registrarsi
public void register(String usr, String psw, String n, String s, String dl ) {
	Connection connect = DBConnection.openConnection();
	PreparedStatement prepstat;
	try {
		//La query per interrogare il DB
		prepstat = connect.prepareStatement(
				"INSERT INTO user(type, username, password, name, surname, drive_license)"+" VALUES (?,?,?,?,?,?)");
		prepstat.setInt(1, 1); //1 vale per l'utente comune
		prepstat.setString(2,usr);
		prepstat.setString(3,psw);
		prepstat.setString(4,n);
		prepstat.setString(5,s);
		prepstat.setString(6,dl);
		if(prepstat.executeUpdate()>0) {
			/*Dopo la registrazione, se questa è andata a buon fine,
			 * l'utente risulta già collegato
			 * quindi viene lanciato il menu utente del customer c
			 * e i suoi dati sono inseriti nel db.
			 */
			Customer c = new Customer(n, s, usr, dl);
			UserMenu um = new UserMenu(c);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
}

public void initializeCustomerCar(Customer cust) {
	Connection c;
	PreparedStatement p;
	ResultSet r;
	c = DBConnection.openConnection();
	
	try {
		p=c.prepareStatement("SELECT * FROM rent join car on car.plate=rent.carplate WHERE rent.username=? AND date_return IS NULL");
		p.setString(1, this.username);
		r = (ResultSet)p.executeQuery();
		if(r.next()) {
			String plate =r.getString(6);
			String model =  r.getString(7);
			String parking =  r.getString(8);
			Car mycar = new Car(plate,model,parking);
			mycar.switchState(mycar);
			cust.setCar(mycar);
			System.out.println("My car: "+cust.getCar().getPlate()+cust.getCar().getState());
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void initializePayMethod(Customer client) {
	Connection c;
	PreparedStatement p,q;
	ResultSet r,s;
	c = DBConnection.openConnection();
	 try {
		 p=c.prepareStatement("SELECT * FROM paymethod WHERE user=? AND type=0"); //Bancomat
		 p.setString(1, client.getUsername());
		 r = (ResultSet)p.executeQuery();
		 if(r.next()) {
			 String own = r.getString(4);
			 String exp = r.getString(3);
			 String numb = r.getString(2);
			 int pin = r.getInt(5);
			 System.out.println("Loading bancomat of "+client.getUsername());
			 BancomatStrategy b = new BancomatStrategy(own, numb, exp, pin);
			 client.setBanc(b);
			 System.out.println(client.getBanc());
		 }
	 } catch (SQLException e) {
		 e.printStackTrace();
	 }
	 try {
		 q=c.prepareStatement("SELECT * FROM paymethod WHERE user=? AND type=1"); //Bancomat
		 q.setString(1, client.getUsername());
		 s = (ResultSet)q.executeQuery();
		 if(s.next()) {
			 String own = s.getString(4);
			 String exp = s.getString(3);
			 String numb = s.getString(2);
			 int cvv = s.getInt(5);
			 System.out.println("Loading credit card of "+client.getUsername());
			 CreditCardStrategy cc= new  CreditCardStrategy(own, numb, exp, cvv);
			 client.setCC(cc);
			 System.out.println(client.getCC());
		 }
	 } catch (SQLException e) {
		 e.printStackTrace();
	 }
}
}
