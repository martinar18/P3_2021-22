package Frames;
/*import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
*/
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
//import com.mysql.jdbc.Connection;

import javax.swing.JButton;

import Connection.DBConnection;


public class AddNewCar {

	private JFrame frmAddnewcar;
	private JTextField Car_Model;
	private JTextField Car_LicensePlate;
	private JTextField Car_Color;
	private JTextField Car_Parking;

	/**
	 * Costruttore che lancia initialize().
	 */
	public AddNewCar() {
		initialize();	
	}

	private void initialize() {
		frmAddnewcar = new JFrame();
		frmAddnewcar.setResizable(false);
		frmAddnewcar.setVisible(true);
		frmAddnewcar.setBounds(100, 100, 487, 353);
		frmAddnewcar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAddnewcar.setTitle("AddNewCar");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		frmAddnewcar.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		Car_Model = new JTextField();
		Car_Model.setForeground(new Color(0, 128, 128));
		Car_Model.setFont(new Font("Arial", Font.PLAIN, 14));
		Car_Model.setBackground(new Color(255, 255, 255));
		Car_Model.setToolTipText("Insert model.");
		Car_Model.setHorizontalAlignment(SwingConstants.LEFT);
		Car_Model.setBounds(86, 92, 140, 31);
		panel.add(Car_Model);
		Car_Model.setColumns(10);
		
		Car_Color = new JTextField();
		Car_Color.setToolTipText("Insert color.");
		Car_Color.setHorizontalAlignment(SwingConstants.LEFT);
		Car_Color.setForeground(new Color(0, 128, 128));
		Car_Color.setFont(new Font("Arial", Font.PLAIN, 14));
		Car_Color.setColumns(10);
		Car_Color.setBackground(new Color(255, 255, 255));
		Car_Color.setBounds(245, 92, 140, 31);
		panel.add(Car_Color);
		
		Car_Parking = new JTextField();
		Car_Parking.setToolTipText("Insert parking.");
		Car_Parking.setHorizontalAlignment(SwingConstants.LEFT);
		Car_Parking.setForeground(new Color(0, 128, 128));
		Car_Parking.setFont(new Font("Arial", Font.PLAIN, 14));
		Car_Parking.setColumns(10);
		Car_Parking.setBackground(new Color(255, 255, 255));
		Car_Parking.setBounds(244, 133, 140, 31);
		panel.add(Car_Parking);
		
		Car_LicensePlate = new JTextField();
		Car_LicensePlate.setToolTipText("Insert plate.");
		Car_LicensePlate.setHorizontalAlignment(SwingConstants.LEFT);
		Car_LicensePlate.setForeground(new Color(0, 128, 128));
		Car_LicensePlate.setFont(new Font("Arial", Font.PLAIN, 14));
		Car_LicensePlate.setColumns(10);
		Car_LicensePlate.setBackground(new Color(255, 255, 255));
		Car_LicensePlate.setBounds(88, 133, 140, 31);
		panel.add(Car_LicensePlate);
		
		JLabel ModelLabel = new JLabel("Model");
		ModelLabel.setBackground(new Color(255, 255, 255));
		ModelLabel.setFont(new Font("Arial", Font.BOLD, 15));
		ModelLabel.setForeground(new Color(255, 255, 255));
		ModelLabel.setBounds(26, 100, 81, 14);
		panel.add(ModelLabel);
		
		JLabel PlateLabel = new JLabel("Plate");
		PlateLabel.setFont(new Font("Arial", Font.BOLD, 15));
		PlateLabel.setForeground(new Color(255, 255, 255));
		PlateLabel.setBounds(26, 142, 103, 14);
		panel.add(PlateLabel);
		
		JButton btnLogin = new JButton("Add new car");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verifyInsertion()) {
					try {
							Connection c;
							PreparedStatement p,p1,p2;
							ResultSet r,r2;
							c = DBConnection.openConnection();
							/*
							 * Prima di inserire un'auto va preservato il vincolo di
							 * unicità della targa e che il parcheggio di ubicaazione
							 * sia registrato preventivamente.
							 */
							
							p1 = c.prepareStatement("SELECT * FROM car WHERE plate=?");
							p1.setString(1, Car_LicensePlate.getText());
							r = (ResultSet)p1.executeQuery();
							p2 = c.prepareStatement("SELECT * FROM parking WHERE name=?");
							p2.setString(1, Car_Parking.getText());
							r2= (ResultSet)p2.executeQuery();
							if(r.next()==false && r2.next()==true) {
							p = c.prepareStatement("INSERT INTO car(plate, model, parking, color)"+" VALUES (?,?,?,?)");
							p.setString(1,Car_LicensePlate.getText());
							p.setString(2,Car_Model.getText());
							p.setString(3,Car_Parking.getText());
							p.setString(4,Car_Color.getText());
							p.executeUpdate();
							JOptionPane.showMessageDialog(null, "New car addedd successfully");
							}
							else {
								JOptionPane.showMessageDialog(null, "This car already exists or invalid parking.");
							}
					} catch (HeadlessException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"Compile all fields.");
				}
			}
		});
		
		JLabel ColorLabel = new JLabel("Color");
		ColorLabel.setForeground(new Color(255, 255, 255));
		ColorLabel.setFont(new Font("Arial", Font.BOLD, 15));
		ColorLabel.setBounds(392, 100, 81, 14);
		panel.add(ColorLabel);
		
		JLabel ParkingLabel = new JLabel("Parking");
		ParkingLabel.setForeground(new Color(255, 255, 255));
		ParkingLabel.setFont(new Font("Arial", Font.BOLD, 15));
		ParkingLabel.setBounds(392, 133, 66, 31);
		panel.add(ParkingLabel);
		btnLogin.setForeground(new Color(0, 128, 128));
		btnLogin.setBackground(new Color(255, 255, 255));
		
		
		btnLogin.setFont(new Font("Arial", Font.BOLD, 19));
		btnLogin.setBounds(147, 209, 176, 31);
		panel.add(btnLogin);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAddnewcar.dispose();
				AdminMenu am = new AdminMenu();
			}
		});
		back.setForeground(new Color(0, 128, 128));
		back.setFont(new Font("Arial", Font.BOLD, 19));
		back.setBackground(new Color(255, 255, 255));
		back.setBounds(181, 250, 103, 31);
		panel.add(back);
		
		
	}
	
	public boolean verifyInsertion() {
		if(Car_Parking.getText().equals("") ||Car_Color.getText().equals("") || Car_LicensePlate.getText().equals("") || Car_Model.getText().equals(""))
			return false;
		else
			return true;
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddNewCar window = new AddNewCar();
					window.frmAddnewcar.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	}


