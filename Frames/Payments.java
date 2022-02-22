package Frames;
import Entities.Customer;
import StrategyPattern.BancomatStrategy;
import StrategyPattern.CreditCardStrategy;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JProgressBar;

import Connection.DBConnection;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Payments {

	private JFrame frmRentIt;

	public Payments(Customer c) {

		frmRentIt = new JFrame();
		frmRentIt.setResizable(false);
		frmRentIt.setVisible(true);
		frmRentIt.setBounds(100, 100, 508, 384);
		frmRentIt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRentIt.setTitle("RENT IT! - Payment");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		frmRentIt.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblAmount = new JLabel("");
		lblAmount.setBounds(61, 63, 280, 66);
		lblAmount.setForeground(new Color(255, 255, 255));
		lblAmount.setFont(new Font("Arial", Font.BOLD, 19));
		lblAmount.setBounds(118, 41, 280, 34);
		panel.add(lblAmount);
	
		Connection conn;
		PreparedStatement p;
		ResultSet r;
		conn = DBConnection.openConnection();
		Double tot = null;
		try {
			//Controllo se ho un pagamento pendente
			p = conn.prepareStatement("SELECT SUM(amount) FROM bill WHERE user = ? AND paid=0 ");
			p.setString(1, c.getUsername());
			r=(ResultSet)p.executeQuery();
			while(r.next()) {
			tot=((ResultSet)r).getDouble(1);
			}
		}catch(SQLException e) {
		e.printStackTrace();
	}
		lblAmount.setText("Your total amount: "+tot+" €");

		
		JLabel lblNewLabel_2 = new JLabel("Please, select your payment method.");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_2.setBounds(80, 120, 360, 49);
		panel.add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("Bancomat");
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setToolTipText("Pay with your bancomat.");
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton_1.setForeground(new Color(0, 128, 128));
		btnNewButton_1.setBounds(295, 221, 145, 66);
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn;
				PreparedStatement p,p1,p2;
				ResultSet r,r1;
				conn = DBConnection.openConnection();
				Double tot = null;
				try {
					//Controllo se ho un pagamento pendente
					p = conn.prepareStatement("SELECT amount FROM bill WHERE user = ? AND paid=0 AND creation_date = (SELECT MAX(creation_date) FROM bill WHERE user = ?)");
					p.setString(1, c.getUsername());
					p.setString(2, c.getUsername());
					r=(ResultSet)p.executeQuery();
					while(r.next()) {
						tot=((ResultSet)r).getDouble(1);
						}
				}catch(SQLException e1) {
				e1.printStackTrace();
			}
				if(tot!=null) {
//				try {
//					p1=conn.prepareStatement("SELECT * FROM paymethod WHERE user=? AND type=0");
//					p1.setString(1, c.getUsername());
//					r1=(ResultSet)p1.executeQuery();
//					if(r1.next()==false) {
					if(c.getBanc()==null) { // Se non ci sono metodi di pagamento di tipo Bancomat collegati all'utente va necessariamente inserito ex-novo
						frmRentIt.dispose();
						BancomatPayment b = new BancomatPayment(c,tot);
					}
					else { 
						//Se c'è già un bancomat inserito:
						try {
							p2=conn.prepareStatement("UPDATE bill SET paid=1 WHERE user=? AND paid=0");
							p2.setString(1, c.getUsername());
							p2.executeUpdate();
							
						} catch (SQLException exc) {
							exc.printStackTrace();
						}
						
						JOptionPane.showMessageDialog(null, "Payment submitted.");
						
					}
				}
					
//				} catch(SQLException e2) {
//					e2.printStackTrace();
//				}
//				}
				else {
					JOptionPane.showMessageDialog(null, "No pendant payment.");
				}
				//BancomatPayment b = new BancomatPayment(c,tot);
				//b.main(null);
			}
		});
		
		JButton btnNewButton_2 = new JButton("Credit Card");
		btnNewButton_2.setBackground(new Color(255, 255, 255));
		btnNewButton_2.setToolTipText("Pay with credit card.");
		btnNewButton_2.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton_2.setForeground(new Color(0, 128, 128));
		btnNewButton_2.setBounds(73, 221, 145, 66);
		panel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn;
				PreparedStatement p,p1,p2;
				ResultSet r,r1;
				conn = DBConnection.openConnection();
				Double tot = null;
				try {
					//Controllo se ho un pagamento pendente
					p = conn.prepareStatement("SELECT amount FROM bill WHERE user = ? AND paid=0  AND creation_date = (SELECT MAX(creation_date) FROM bill WHERE user = ?)");
					p.setString(1, c.getUsername());
					p.setString(2, c.getUsername());
					r=(ResultSet)p.executeQuery();
					while(r.next()) {
						tot=((ResultSet)r).getDouble(1);
						}
				}catch(SQLException e1) {
				e1.printStackTrace();
			}
				if(tot!=null) {
//				try {
//					p1=conn.prepareStatement("SELECT * FROM paymethod WHERE user=? AND type=1");
//					p1.setString(1, c.getUsername());
//					r1=(ResultSet)p1.executeQuery();
//					if(r1.next()==false) { // Se non ci sono metodi di pagamento di tipo CC collegati all'utente va necessariamente inserito ex-novo
						if(c.getCC()==null) {
						frmRentIt.dispose();
						CreditCardPayment b = new CreditCardPayment(c,tot);
					}
					else { 
						//Se c'è già una carta di credito inserita viene utilizzata quella.
							try {
								p2=conn.prepareStatement("UPDATE bill SET paid=1 WHERE user=? AND paid=0");
								p2.setString(1, c.getUsername());
								p2.executeUpdate();
								
							} catch (SQLException exc) {
								exc.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "Payment submitted.");
					}
				}
//				} catch(SQLException e2) {
//					e2.printStackTrace();
//				}
//				}
				else {
					JOptionPane.showMessageDialog(null, "No pendant payment.");
				}
							}
		});
		
		JButton btnNewButton_2_1 = new JButton("Back");
		btnNewButton_2_1.setBackground(new Color(255, 255, 255));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmRentIt.dispose();
				UserMenu umm = new UserMenu(c);
			}
		});
		btnNewButton_2_1.setToolTipText("back");
		btnNewButton_2_1.setForeground(new Color(0, 128, 128));
		btnNewButton_2_1.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton_2_1.setBounds(184, 297, 131, 34);
		panel.add(btnNewButton_2_1);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setForeground(Color.RED);
		btnLogin.setBackground(Color.WHITE);
	}

	
}
