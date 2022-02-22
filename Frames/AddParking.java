package Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Connection.DBConnection;


public class AddParking {

	private JFrame frmAddnewpark;
	private JTextField Parking_Address;
	private JTextField Name_Parking;

	/*
	 * instanziare l'oggetto login qui
	 */

	/**
	 * Costruttore che lancia initialize().
	 */
	public AddParking() {
		initialize();	
	}

	private void initialize() {
		frmAddnewpark = new JFrame();
		frmAddnewpark.setVisible(true);
		frmAddnewpark.setResizable(false);
		frmAddnewpark.setBounds(100, 100, 487, 332);
		frmAddnewpark.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAddnewpark.setTitle("RENT IT! - AddNewParking");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		frmAddnewpark.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		Parking_Address = new JTextField();
		Parking_Address.setToolTipText("Insert your username.");
		Parking_Address.setHorizontalAlignment(SwingConstants.LEFT);
		Parking_Address.setForeground(new Color(0, 128, 128));
		Parking_Address.setFont(new Font("Arial", Font.PLAIN, 14));
		Parking_Address.setColumns(10);
		Parking_Address.setBackground(new Color(255, 255, 255));
		Parking_Address.setBounds(242, 115, 140, 31);
		panel.add(Parking_Address);
		
		Name_Parking = new JTextField();
		Name_Parking.setToolTipText("Insert your username.");
		Name_Parking.setHorizontalAlignment(SwingConstants.LEFT);
		Name_Parking.setForeground(new Color(0, 128, 128));
		Name_Parking.setFont(new Font("Arial", Font.PLAIN, 14));
		Name_Parking.setColumns(10);
		Name_Parking.setBackground(new Color(255, 255, 255));
		Name_Parking.setBounds(82, 115, 140, 31);
		panel.add(Name_Parking);
		
		JButton AddButton = new JButton("Add new parking");
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * Viene in primis accertato la compilazione
				 * di tutti i campi richiesti, dopo di che
				 * per preservare l'integrità dei vincoli di
				 * chiave primaria(nome del parcheggio), controllo
				 * che non esistano tuple preesistenti con il 
				 * suddetto nome.
				 */
				if(verifyInsertion()) {
					try {
						Connection c;
						PreparedStatement p,p1;
						ResultSet r;
						c = DBConnection.openConnection();
						p1= c.prepareStatement("SELECT * FROM parking WHERE name=?");
						p1.setString(1,Name_Parking.getText());
						r=(ResultSet)p1.executeQuery();
						if(r.next()==false) {
						p = c.prepareStatement("INSERT INTO parking(name, address)"+" VALUES (?,?)");
						p.setString(1,Name_Parking.getText());
						p.setString(2,Parking_Address.getText());
						p.executeUpdate();
						JOptionPane.showMessageDialog(null, "New parking addedd successfully");
						}
						else {
							JOptionPane.showMessageDialog(null, "This parking already exists.");
						}

				} catch (HeadlessException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				else {
					JOptionPane.showMessageDialog(null, "Compile all fields.");
				}
			}
		});
		
		JLabel NameLabel = new JLabel("Name");
		NameLabel.setBackground(new Color(230, 230, 250));
		NameLabel.setForeground(new Color(255, 255, 255));
		NameLabel.setFont(new Font("Arial", Font.BOLD, 15));
		NameLabel.setBounds(82, 91, 81, 14);
		panel.add(NameLabel);
		
		JLabel AddressLabel = new JLabel("Address");
		AddressLabel.setForeground(new Color(255, 255, 255));
		AddressLabel.setFont(new Font("Arial", Font.BOLD, 15));
		AddressLabel.setBounds(327, 83, 66, 31);
		panel.add(AddressLabel);
		AddButton.setForeground(new Color(0, 128, 128));
		AddButton.setBackground(new Color(255, 255, 255));
		
		
		AddButton.setFont(new Font("Arial", Font.BOLD, 19));
		AddButton.setBounds(121, 191, 215, 31);
		panel.add(AddButton);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAddnewpark.dispose();
				AdminMenu am = new AdminMenu();
			}
		});
		btnBack.setForeground(new Color(0, 128, 128));
		btnBack.setFont(new Font("Arial", Font.BOLD, 19));
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setBounds(167, 232, 114, 31);
		panel.add(btnBack);
		
		
		
		
	}
	
	public boolean verifyInsertion() {
		if(Name_Parking.getText().equals("") ||Parking_Address.getText().equals("") )
			return false;
		else
			return true;
	}
	
	}
