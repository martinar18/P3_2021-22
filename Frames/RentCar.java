package Frames;
import Entities.Customer;
import StatePattern.Car;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.List;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Connection.DBConnection;

import javax.swing.JButton;

public class RentCar {

	private JFrame frame;
	private JTextField plate;
	private Component BackButton;
	private JTextField days;

	public RentCar(Customer c) {
		initialize(c);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws HeadlessException 
	 */
	private void initialize(Customer cust) {
		frame = new JFrame();
		frame.setBounds(100, 100, 861, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 139, 139));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Available cars:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(31, 30, 269, 46);
		panel.add(lblNewLabel);
		
		List rentlist = new List();
		rentlist.setForeground(new Color(0, 128, 128));
		rentlist.setFont(new Font("Arial", Font.PLAIN, 16));
		rentlist.setBackground(Color.WHITE);
		rentlist.setBounds(31, 82, 474, 245);
		panel.add(rentlist);
		
		days = new JTextField();
		days.setToolTipText("Insert your choice here.");
		days.setHorizontalAlignment(SwingConstants.LEFT);
		days.setForeground(Color.DARK_GRAY);
		days.setFont(new Font("Arial", Font.PLAIN, 14));
		days.setColumns(10);
		days.setBackground(Color.WHITE);
		days.setBounds(715, 188, 102, 39);
		panel.add(days);
		
		plate = new JTextField();
		plate.setToolTipText("Insert your choice here.");
		plate.setHorizontalAlignment(SwingConstants.LEFT);
		plate.setForeground(Color.DARK_GRAY);
		plate.setFont(new Font("Arial", Font.PLAIN, 14));
		plate.setColumns(10);
		plate.setBackground(new Color(255, 255, 255));
		plate.setBounds(623, 116, 194, 39);
		panel.add(plate);
		
		JLabel lblInsert = new JLabel("Insert your choice below:");
		lblInsert.setForeground(new Color(255, 255, 255));
		lblInsert.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblInsert.setBounds(548, 30, 269, 46);
		panel.add(lblInsert);

		
		JButton refresh = new JButton("Refresh");
		refresh.setBackground(new Color(255, 255, 255));
		refresh.setForeground(new Color(0, 128, 128));
		refresh.setFont(new Font("Arial", Font.BOLD, 19));
		refresh.setBounds(178, 333, 194, 31);
		panel.add(refresh);
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*Cliccando su refresh è possibile visualizzare
				 * la lista di auto disponibili aggiornata.
				 */
				rentlist.removeAll(); 
				//operazione di pulizia della tabella 
				//altrimenti ad ogni refresh avrei avuto l'elenco ripetuto
				Connection c;
				PreparedStatement p;
				ResultSet r;
				c = DBConnection.openConnection();
				try {
					rentlist.removeAll();
					p=c.prepareStatement("SELECT * FROM car WHERE plate NOT IN (SELECT carplate FROM rent WHERE date_return IS NULL);");
					r=(ResultSet)p.executeQuery();
					while(r.next()) {
						rentlist.add(r.getString(1)+", "+r.getString(2)+", "+r.getString(4)+" available in "+r.getString(3)+".");
						rentlist.add("--------------------------------------------------------------------------------------------------------");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		JButton rentitb = new JButton("Rent it!");
		rentitb.setBackground(new Color(255, 255, 255));
		rentitb.setForeground(new Color(0, 139, 139));
		rentitb.setFont(new Font("Arial", Font.BOLD, 19));
		rentitb.setBounds(715, 248, 102, 31);
		panel.add(rentitb);
		rentitb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * Devo fare 4 check:
				 * 1. Campo non vuoto
				 * 2. Leasing pendenti
				 * 3. Pagamenti pendenti
				 * 4. Targa inserita esiste nel DB
				 * 5. Che l'oggetto Car del cliente
				 *	  sia null.
				 */
				if(verifyInsertion()) {
					Connection c;
					PreparedStatement p,p1,p2,p3;
					ResultSet r,r1,r2;
					
					c = DBConnection.openConnection();
					try {
						p = c.prepareStatement("SELECT * FROM rent WHERE username=? AND date_return IS NULL");
						p.setString(1, cust.getUsername());
						r = (ResultSet)p.executeQuery();
						System.out.println(cust.getUsername()+" compiled all fields.");
						if(r.next()==false) { //In questo caso non ha prenotazioni pendenti
							System.out.println(cust.getUsername()+" has no pendant leasing.");
							p1 = c.prepareStatement("SELECT * FROM bill WHERE user=? AND paid=0");
							p1.setString(1, cust.getUsername());
							r1=(ResultSet)p1.executeQuery();
							if(r1.next()==false) { //In questo caso non ha pagamenti pendenti
								p2 = c.prepareStatement("SELECT * FROM car WHERE plate=?");
								p2.setString(1, plate.getText());
								r2 =(ResultSet)p2.executeQuery();
								System.out.println(cust.getUsername()+" has no pendant payment.");
								if(r2.next()) { //Se esiste questa auto nel DB
									//Estraggo modello e parcheggio al fine di costruire l'oggetto car.
									String tmp_mod =((ResultSet)r2).getString(2);
									String tmp_park = ((ResultSet)r2).getString(3);
									int dd = Integer.parseInt(days.getText());
									p3 = c.prepareStatement("INSERT INTO rent(id, username, carplate, date_rent, date_return, date_end)"+" VALUES(?,?,?,SYSDATE(),?,SYSDATE()+INTERVAL ? DAY)");
									p3.setInt(1, 0); 
									/*Per questo campo ID il valore è irrilevante dal momento che
									 * è stato implementato in MySQL come un campo auto-increment.
									 * Prende la tupla con valore ID massimo e lo incrementa di 1.
									 * Dunque è preservato in ogni caso il vincolo di chiave primaria 
									 * unica.
									 */
									p3.setString(2, cust.getUsername());
									p3.setString(3, plate.getText());
									p3.setDate(4, null);
									p3.setInt(5, dd);
									p3.executeUpdate();
									if(cust.getCar()==null) {
									System.out.println(plate.getText()+" assigned to "+cust.getUsername()+" successfully.");
									Car mycar = new Car(plate.getText(),tmp_mod, tmp_park);
									cust.setCar(mycar);
									System.out.println("Car state before leasing: "+mycar.getState());
									mycar.switchState(mycar);
									System.out.println("Car state after leasing: "+mycar.getState());
									System.out.println("Car info:");
									System.out.println(mycar.getPlate());
									System.out.println(mycar.getMod());
									System.out.println(mycar.getParking());
									
									JOptionPane.showMessageDialog(null, "Request submitted successfully. Pay and collect your car at its own parking.");
									//Bisogna indirizzare al pagamento il cliente qui
									
									p3 = c.prepareStatement("INSERT INTO bill(user,creation_date,amount,paid)"+" VALUES(?,SYSDATE(),?,?)");
									p3.setString(1, cust.getUsername());
									p3.setDouble(2, dd*12);
									p3.setInt(3, 0);//0 indica non pagato.
									p3.executeUpdate();
									frame.dispose();
									Payments payfor = new Payments(cust);
									}
									else {
										JOptionPane.showMessageDialog(null, "Unexpected error has occurred.");
									}
									
								}
								else {
									JOptionPane.showMessageDialog(null, "Invalid request. This car does not exist.");
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "Invalid request. You should pay for your last leasing first.");
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Invalid request. You should return your car first.");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				}
				else {
					JOptionPane.showMessageDialog(null, "Empty fields. Please insert car identifier and interval time.");
				}
			}
		});
		
		
		JButton backb = new JButton("Back");
		backb.setBackground(new Color(255, 255, 255));
		backb.setFont(new Font("Arial", Font.BOLD, 19));
		backb.setForeground(new Color(0, 128, 128));
		backb.setBounds(641, 333, 102, 31);
		panel.add(backb);
		
		
		JLabel lblDays = new JLabel("Days:");
		lblDays.setForeground(Color.WHITE);
		lblDays.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDays.setBounds(715, 150, 91, 46);
		panel.add(lblDays);
		
		JLabel lblCarplate = new JLabel("CarPlate");
		lblCarplate.setForeground(Color.WHITE);
		lblCarplate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCarplate.setBounds(623, 77, 116, 46);
		panel.add(lblCarplate);
		backb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				UserMenu back = new UserMenu(cust);
			}
		});
		
	}
	
	public boolean verifyInsertion() {
		if(plate.getText().equals("")||days.getText().equals("")) 
			return false;
		else
			return true;
	}
}
