package Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
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
import javax.swing.JPanel;

import Connection.DBConnection;
import Entities.Customer;

public class DisplayHistory {

	private JFrame frame;
	

	public DisplayHistory(Customer c) {
		initialize(c);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Customer cust) {
		frame = new JFrame();
		frame.setBounds(100, 100, 691, 463);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton BackButton = new JButton("Back");
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				UserMenu back = new UserMenu(cust);	
			}
		});
		BackButton.setForeground(new Color(0, 128, 128));
		BackButton.setBackground(new Color(255, 255, 255));
		BackButton.setFont(new Font("Arial", Font.BOLD, 19));
		BackButton.setBounds(175, 353, 102, 31);
		panel.add(BackButton);

		JLabel lblNewLabel = new JLabel("Rent: ");
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(31, 30, 269, 46);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel2 = new JLabel("Payments: ");
		lblNewLabel2.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel2.setForeground(new Color(255, 255, 255));
		lblNewLabel2.setBounds(519, 30, 269, 46);
		panel.add(lblNewLabel2);
		
		List rentlist = new List();
		rentlist.setForeground(new Color(0, 128, 128));
		rentlist.setFont(new Font("Arial", Font.PLAIN, 18));
		rentlist.setBackground(Color.WHITE);
		rentlist.setBounds(28, 82, 295, 245);
		panel.add(rentlist);
		
		List paylist = new List();
		paylist.setForeground(new Color(0, 128, 128));
		paylist.setFont(new Font("Arial", Font.PLAIN, 18));
		paylist.setBackground(Color.WHITE);
		paylist.setBounds(350, 82, 295, 245);
		panel.add(paylist);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rentlist.removeAll();
			
				Connection c;
				PreparedStatement p,p1;
				ResultSet r,r1;
				c = DBConnection.openConnection();
				try {
					rentlist.removeAll();
					p = c.prepareStatement("SELECT * FROM rent WHERE username=?");
					p.setString(1, cust.getUsername());
					r = (ResultSet)p.executeQuery();
					while(r.next()) {
						rentlist.add("From day "+r.getDate(4)+" to "+r.getString(6)+" you had car no."+r.getString(3)+".");
						rentlist.add("This car was returned on day "+r.getDate(5)+".");
						rentlist.add("-------------------------------------------------------------------------------------------");
					}
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
				try {
					paylist.removeAll();
					p1 = c.prepareStatement("SELECT * FROM bill WHERE user=? AND paid=1");
					p1.setString(1, cust.getUsername());
					r1 = (ResultSet)p1.executeQuery();
					while(r1.next()) {
						paylist.add("On date "+r1.getDate(2)+" was calculated amount of "+r1.getDouble(3)+"€.");
						paylist.add("-------------------------------------------------------------------------------------------");
					}
					
				} catch(SQLException e2) {
					e2.printStackTrace();				}
				try {
					c.close();
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
			}	
			});
		btnNewButton.setForeground(new Color(0, 128, 128));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton.setBounds(408, 353, 102, 31);
		panel.add(btnNewButton);
		
		
	}
}
