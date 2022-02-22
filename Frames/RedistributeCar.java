package Frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

import Connection.DBConnection;

public class RedistributeCar {

	private JFrame Redistribute;
	private JTextField CarField;
	private JTextField ParkingField;

	public RedistributeCar() {
		initialize();	
	}

	private void initialize() {
		Redistribute = new JFrame();
		Redistribute.setVisible(true);
		Redistribute.setForeground(Color.BLACK);
		Redistribute.setFont(new Font("Arial", Font.PLAIN, 12));
		Redistribute.setResizable(false);
		Redistribute.setBounds(100, 100, 489, 523);
		Redistribute.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Redistribute.setTitle("RENT IT! - Redistribute car");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		Redistribute.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
	
		
		JLabel lblNewLabel = new JLabel("Write car and destination parking ");
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel.setBounds(81, 10, 311, 43);
		panel.add(lblNewLabel);
		
		JLabel CARLabel = new JLabel("Cars");
		CARLabel.setFont(new Font("Arial", Font.BOLD, 15));
		CARLabel.setForeground(new Color(255, 255, 255));
		CARLabel.setBounds(10, 75, 81, 14);
		panel.add(CARLabel);
		
		JLabel PARKLabel = new JLabel("Parkings");
		PARKLabel.setFont(new Font("Arial", Font.BOLD, 15));
		PARKLabel.setForeground(new Color(255, 255, 255));
		PARKLabel.setBounds(391, 75, 74, 14);
		panel.add(PARKLabel);
		
		JButton Moveit = new JButton("Move it!");
		Moveit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Moveit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verifyInsertion()) {
					Connection c;
					PreparedStatement p;
					c = DBConnection.openConnection();
					try {
						p=c.prepareStatement("UPDATE car SET parking=? WHERE plate=?");
						p.setString(1, ParkingField.getText());
						p.setString(2, CarField.getText());
						p.executeUpdate();
						JOptionPane.showMessageDialog(null, "Car moved to destination parking successfully.");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else
					JOptionPane.showMessageDialog(null, "Missing informations.");
			}
		});
		Moveit.setForeground(new Color(0, 139, 139));
		Moveit.setBackground(new Color(255, 255, 255));
		
		Moveit.setFont(new Font("Arial", Font.BOLD, 19));
		Moveit.setBounds(241, 401, 130, 31);
		panel.add(Moveit);
		
		//Dove inserire la targa dell'auto da spostare
		CarField = new JTextField();
		CarField.setFont(new Font("Arial", Font.BOLD, 13));
		CarField.setForeground(new Color(0, 128, 128));
		CarField.setBackground(new Color(255, 255, 255));
		CarField.setBounds(10, 360, 140, 31);
		panel.add(CarField);
		CarField.setColumns(10);
		
		//Dove inserire il parcheggio di destinazione dell'auto 
		ParkingField = new JTextField();
		ParkingField.setFont(new Font("Arial", Font.BOLD, 13));
		ParkingField.setForeground(new Color(0, 128, 128));
		ParkingField.setBackground(new Color(255, 255, 255));
		ParkingField.setColumns(10);
		ParkingField.setBounds(325, 360, 140, 31);
		panel.add(ParkingField);
		
		List carlist = new List();
		carlist.setForeground(new Color(0, 128, 128));
		carlist.setFont(new Font("Arial", Font.PLAIN, 16));
		carlist.setBackground(new Color(255, 255, 255));
		carlist.setBounds(10, 95, 224, 245);
		panel.add(carlist);
		
		List parklist= new List();
		parklist.setForeground(new Color(0, 128, 128));
		parklist.setFont(new Font("Arial", Font.PLAIN, 16));
		parklist.setBackground(new Color(255, 255, 255));
		parklist.setBounds(241, 95, 224, 245);
		panel.add(parklist);
		
		JButton Refresh = new JButton("Refresh");
		Refresh.setForeground(new Color(0, 128, 128));
		Refresh.setFont(new Font("Arial", Font.BOLD, 19));
		Refresh.setBackground(new Color(255, 255, 255));
		Refresh.setBounds(97, 401, 130, 31);
		panel.add(Refresh);
		Refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection c;
				PreparedStatement p, p1;
				ResultSet r, r1;
				c = DBConnection.openConnection();
				try {
					carlist.removeAll();
					p=c.prepareStatement("SELECT plate, parking FROM car");
					r = (ResultSet)p.executeQuery();
					while(r.next()) {
						carlist.add(r.getString(1)+" placed in "+r.getString(2));
						carlist.add("-------------------------------------------------------");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				try {
					parklist.removeAll();
					p1=c.prepareStatement("SELECT name, address FROM parking");
					r1 = (ResultSet)p1.executeQuery();
					while(r1.next()) {
						parklist.add(r1.getString(1)+" located in "+r1.getString(2));
						carlist.add("--------------------------------------------------------");
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				try {
					c.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		
		JLabel lblThenPressButton = new JLabel("then press button below.");
		lblThenPressButton.setHorizontalAlignment(SwingConstants.CENTER);
		lblThenPressButton.setForeground(new Color(255, 255, 255));
		lblThenPressButton.setFont(new Font("Arial", Font.BOLD, 19));
		lblThenPressButton.setBounds(81, 28, 311, 43);
		panel.add(lblThenPressButton);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Redistribute.dispose();
				AdminMenu am = new AdminMenu();
			}
		});
		btnBack.setForeground(new Color(0, 128, 128));
		btnBack.setFont(new Font("Arial", Font.BOLD, 19));
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setBounds(182, 445, 105, 31);
		panel.add(btnBack);
		
	}
	
		public boolean verifyInsertion() {
			if(CarField.getText().equals("") || ParkingField.getText().equals(""))
				return false;
			else
				return true;
		}
		
		public static void main(String args[]) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						RedistributeCar window = new RedistributeCar();
						window.Redistribute.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
