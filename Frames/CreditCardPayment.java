package Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

import Entities.Customer;
import StrategyPattern.CreditCardStrategy;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Connection.DBConnection;

import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.DropMode;

public class CreditCardPayment {

	private JFrame frame;
	private JTextField owner;
	private JTextField cardnumber;
	private JTextField expirydate;
	private JTextField txtMm;
	private JTextField txtYyyy;
	private JTextField cvv;

	public CreditCardPayment(Customer customer, double amt) {

	
		frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 485, 546);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("RENT IT! - CreditCard");

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 139, 139));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		owner = new JTextField();
		owner.setForeground(new Color(0, 128, 128));
		owner.setFont(new Font("Arial", Font.PLAIN, 14));
		owner.setBackground(new Color(255, 255, 255));
		owner.setToolTipText("Insert bancomat owner.");
		owner.setHorizontalAlignment(SwingConstants.LEFT);
		owner.setBounds(125, 116, 204, 31);
		panel.add(owner);
		owner.setColumns(10);
		
		cardnumber = new JTextField();
		cardnumber.setToolTipText("Insert Credit Card owner.");
		cardnumber.setHorizontalAlignment(SwingConstants.LEFT);
		cardnumber.setForeground(new Color(0, 128, 128));
		cardnumber.setFont(new Font("Arial", Font.PLAIN, 14));
		cardnumber.setColumns(10);
		cardnumber.setBackground(new Color(255, 255, 255));
		cardnumber.setBounds(125, 180, 204, 31);
		panel.add(cardnumber);
		
		expirydate = new JTextField();
		expirydate.setText("DD");
		expirydate.setToolTipText("Insert expiry date.");
		expirydate.setHorizontalAlignment(SwingConstants.LEFT);
		expirydate.setForeground(new Color(0, 139, 139));
		expirydate.setFont(new Font("Arial", Font.PLAIN, 14));
		expirydate.setColumns(10);
		expirydate.setBackground(new Color(255, 255, 255));
		expirydate.setBounds(125, 241, 57, 31);
		panel.add(expirydate);
		
		JLabel ownerlabel = new JLabel("Owner");
		ownerlabel.setForeground(new Color(255, 255, 255));
		ownerlabel.setFont(new Font("Arial", Font.BOLD, 16));
		ownerlabel.setBounds(125, 98, 80, 13);
		panel.add(ownerlabel);
		
		JLabel lblNumber = new JLabel("Credit Card number");
		lblNumber.setForeground(new Color(255, 255, 255));
		lblNumber.setFont(new Font("Arial", Font.BOLD, 16));
		lblNumber.setBounds(125, 157, 155, 13);
		panel.add(lblNumber);
		
		JLabel lblExpiry = new JLabel("Expiry");
		lblExpiry.setForeground(new Color(255, 255, 255));
		lblExpiry.setFont(new Font("Arial", Font.BOLD, 16));
		lblExpiry.setBounds(125, 221, 80, 13);
		panel.add(lblExpiry);
		
		JLabel lblPin = new JLabel("CVV");
		lblPin.setForeground(new Color(255, 255, 255));
		lblPin.setFont(new Font("Arial", Font.BOLD, 16));
		lblPin.setBounds(125, 282, 80, 13);
		panel.add(lblPin);
		
		JRadioButton remember = new JRadioButton("Save for future payments");
		
		remember.setOpaque(false);
		remember.setFont(new Font("Arial", Font.BOLD, 16));
		remember.setForeground(new Color(255, 255, 255));
		remember.setBounds(109, 352, 244, 31);
		panel.add(remember);
		
		JButton paybutton = new JButton("Pay");
		paybutton.setBackground(new Color(255, 255, 255));
		paybutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verifyInsertion()) {
					//Costruisco la stringa della data mediante concatenazione dei vari pezzi.
					Connection c = DBConnection.openConnection();
					String d=expirydate.getText();
					String mm=txtMm.getText();
					String yy=txtYyyy.getText();
						String s =	d.concat("-").concat(mm).concat("-").concat(yy);
						System.out.println(s);
						int pincode = Integer.valueOf(cvv.getText());
						CreditCardStrategy ccs = new CreditCardStrategy(owner.getText(),cardnumber.getText(),s,pincode);
						customer.setCC(ccs);
						
						//Viene passato il valore amount della fattura da pagare.
						try {
						
							if(customer.getCC().pay(amt)) {
								PreparedStatement p2;
						try {
								p2=c.prepareStatement("UPDATE bill SET paid=1 WHERE user=? AND paid=0");
								p2.setString(1, customer.getUsername());
									p2.executeUpdate();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						JOptionPane.showMessageDialog(null, "Payment submitted. Thank you.");
						Date thisexpiry=null;
						try {
							 thisexpiry=ccs.getDateFormat(s);
						} catch (ParseException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						if(remember.isSelected()) {
							PreparedStatement ps;
							try {
								ps = c.prepareStatement("INSERT INTO paymethod(user,number, expiry, owner, code, type)"+" VALUES(?,?,?,?,?,?)");
								ps.setString(1,customer.getUsername() );
								ps.setString(2, cardnumber.getText());
								ps.setString(4, owner.getText());
								String dt = thisexpiry.toString();	
								ps.setString(3, dt); 
								ps.setInt(5, pincode);
								ps.setInt(6,1);
								ps.executeUpdate();
								JOptionPane.showMessageDialog(null, "Payment method saved successfully.");
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						}
						else {//Se è scaduto il metodo di pagamento
							JOptionPane.showMessageDialog(null, "Expired or invalid length. Retry.");
						}
					} catch (ParseException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});
		paybutton.setForeground(new Color(0, 128, 128));
		paybutton.setFont(new Font("Arial", Font.BOLD, 19));
		paybutton.setBounds(162, 431, 131, 37);
		panel.add(paybutton);
		
		JLabel lblPleaseInsertYour = new JLabel("Please, insert your credit card coordinates.");
		lblPleaseInsertYour.setForeground(new Color(255, 255, 255));
		lblPleaseInsertYour.setFont(new Font("Arial", Font.BOLD, 16));
		lblPleaseInsertYour.setBounds(77, 36, 346, 31);
		panel.add(lblPleaseInsertYour);
		
		txtMm = new JTextField();
		txtMm.setText("MM");
		txtMm.setToolTipText("Insert expiry date.");
		txtMm.setHorizontalAlignment(SwingConstants.LEFT);
		txtMm.setForeground(new Color(0, 128, 128));
		txtMm.setFont(new Font("Arial", Font.PLAIN, 14));
		txtMm.setColumns(10);
		txtMm.setBackground(new Color(255, 255, 255));
		txtMm.setBounds(200, 241, 57, 31);
		panel.add(txtMm);
		
		txtYyyy = new JTextField();
		txtYyyy.setText("YYYY");
		txtYyyy.setToolTipText("Insert expiry date.");
		txtYyyy.setHorizontalAlignment(SwingConstants.LEFT);
		txtYyyy.setForeground(new Color(0, 128, 128));
		txtYyyy.setFont(new Font("Arial", Font.PLAIN, 14));
		txtYyyy.setColumns(10);
		txtYyyy.setBackground(new Color(255, 255, 255));
		txtYyyy.setBounds(267, 241, 57, 31);
		panel.add(txtYyyy);
		
		cvv = new JTextField();
		cvv.setToolTipText("Insert bancomat owner.");
		cvv.setHorizontalAlignment(SwingConstants.LEFT);
		cvv.setForeground(new Color(0, 139, 139));
		cvv.setFont(new Font("Arial", Font.PLAIN, 14));
		cvv.setColumns(10);
		cvv.setBackground(new Color(255, 255, 255));
		cvv.setBounds(125, 305, 204, 31);
		panel.add(cvv);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				UserMenu u = new UserMenu(customer);
			}
		});
		btnBack.setForeground(new Color(0, 128, 128));
		btnBack.setFont(new Font("Arial", Font.BOLD, 19));
		btnBack.setBounds(10, 462, 80, 37);
		panel.add(btnBack);
		
	}
	
	
	public boolean verifyInsertion() {
		if(owner.getText().equals("")||cardnumber.getText().equals("")||cvv.getText().equals("")|| txtMm.getText().equals("")||txtYyyy.getText().equals("")||expirydate.getText().equals(""))
			return false;
		else
			return true;
	}
}
