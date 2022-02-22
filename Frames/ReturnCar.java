package Frames;
import Entities.Customer;
import StatePattern.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTree;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.List;
import java.awt.SystemColor;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingConstants;

import org.xml.sax.ext.DefaultHandler2;

import Connection.DBConnection;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ReturnCar {

	private JFrame frame;


	public ReturnCar(Customer customer) {
		initialize(customer);
	}

	private void initialize(Customer customer) {
		String car=null;
		String rent=null;
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 128, 128));
		frame.setBounds(100, 100, 452, 362);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("Your last leasing:");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel.setBounds(129, 42, 194, 31);
		frame.getContentPane().add(lblNewLabel);
		
		
		List returnlist = new List();
		returnlist.setForeground(new Color(0, 128, 128));
		returnlist.setFont(new Font("Arial", Font.PLAIN, 16));
		returnlist.setBackground(Color.WHITE);
		returnlist.setBounds(45, 89, 366, 60);
		frame.getContentPane().add(returnlist);
		

		
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				UserMenu ur= new UserMenu(customer);
			}
		});
		
		btnBack.setForeground(new Color(0, 128, 128));
		btnBack.setFont(new Font("Arial", Font.BOLD, 19));
		btnBack.setBounds(129, 258, 194, 31);
		frame.getContentPane().add(btnBack);
		

		/*
		 * Per il ritorno dell'auto, si vede prima l'oggetto auto
		 * proprio dell'istanza login. Se è nullo o è su available,
		 * non c'è procedura di ritorno. Se è unavailable, provvedo
		 * a fare update della tupla rent e creo tupla pagamento se 
		 * l'utente ha riconsegnato l'auto in ritardo.
		 */
		
		JButton btnReturn = new JButton("Return");
		btnReturn.setBackground(new Color(255, 255, 255));
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Connection c;
				PreparedStatement p,p1,p2,p3;
				ResultSet rs,r;
				c = DBConnection.openConnection();
				int days=0;
//				double km=Math.round(Math.random()*100);
	try {
		p=c.prepareStatement("SELECT * FROM rent WHERE username=? AND date_return IS NULL");
		p.setString(1, customer.getUsername());
		rs = (ResultSet)p.executeQuery();
		if(rs.next()) { //Hai qualche auto da ritornare
		//Inserisco come date_return la data odierna
		p2 = c.prepareStatement("UPDATE rent SET date_return=SYSDATE() WHERE username=? AND date_return IS NULL");
		p2.setString(1, customer.getUsername());
		p2.executeUpdate();
		//Controllo se la riconsegna è avvenuta in ritardo, in quel caso inserisco nei pagamenti una mora di 25€
		p3=c.prepareStatement("SELECT * FROM rent WHERE username=? AND date_return>date_end AND id=(SELECT max(id) FROM rent where username=?);");
		p3.setString(1, customer.getUsername());
		p3.setString(2, customer.getUsername());
		r=(ResultSet)p3.executeQuery();
		if(r.next()) {
			p1=c.prepareStatement("INSERT INTO bill(user, creation_date, amount, paid) VALUES (?,SYSDATE(),?,?)");
			p1.setString(1, customer.getUsername());
			p1.setDouble(2, 25.0);
			p1.setInt(3, 0);
			p1.executeUpdate();
		}
		if(customer.getCar().getState() instanceof UnavailableCarState) {
			customer.setCar(null);
			System.out.println("This user has no car instance now.");
		}
		else {
			System.out.println("This user has a problem.");
		}
		JOptionPane.showMessageDialog(null, "Successfully returned. Check your bill in Payments section.");
		}
		else {
			JOptionPane.showMessageDialog(null, "No car for return.");
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
			}
		});
		btnReturn.setForeground(new Color(0, 128, 128));
		btnReturn.setFont(new Font("Arial", Font.BOLD, 19));
		btnReturn.setBounds(129, 217, 194, 31);
		frame.getContentPane().add(btnReturn);
		
		JButton REFRESH = new JButton("Refresh");
		REFRESH.setBackground(new Color(255, 255, 255));
		REFRESH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * Con refresh, si visualizza nella list
				 * il riepilogo della prenotazione
				 */
				Connection c;
				PreparedStatement p,p1;
				ResultSet r,r1;
				c = DBConnection.openConnection();
				try {
					returnlist.removeAll();
					p1 = c.prepareStatement("SELECT * FROM rent WHERE username=? AND date_return IS NULL");
					p1.setString(1, customer.getUsername());
					r1 = (ResultSet)p1.executeQuery();
					while(r1.next()) {
						returnlist.add("On date "+r1.getDate(4)+" you collect car no."+r1.getString(3));
					}
					
				} catch(SQLException e2) {
					e2.printStackTrace();				}
			}
		});
		REFRESH.setForeground(new Color(0, 128, 128));
		REFRESH.setFont(new Font("Arial", Font.BOLD, 19));
		REFRESH.setBounds(129, 176, 194, 31);
		frame.getContentPane().add(REFRESH);
		


		
		
		
	}
}
