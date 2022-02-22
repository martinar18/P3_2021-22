package Frames;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JFrame;

import Entities.Customer;
import StrategyPattern.BancomatStrategy;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Connection.DBConnection;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.awt.event.ActionEvent;


public class BancomatPayment {

	private JFrame frame;
	private JTextField owner;
	private JTextField cardnumber;
	private JTextField textField;
	private JTextField txtYyyy;
	private JTextField txtMm;
	private JTextField Pin;

	
	public BancomatPayment(Customer cust, double amt) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setBounds(100, 100, 471, 546);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("RENT IT! - Bancomat");

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
		cardnumber.setToolTipText("Insert bancomat owner.");
		cardnumber.setHorizontalAlignment(SwingConstants.LEFT);
		cardnumber.setForeground(new Color(0, 128, 128));
		cardnumber.setFont(new Font("Arial", Font.PLAIN, 14));
		cardnumber.setColumns(10);
		cardnumber.setBackground(new Color(255, 255, 255));
		cardnumber.setBounds(125, 180, 204, 31);
		panel.add(cardnumber);
		
		JLabel ownerlabel = new JLabel("Owner");
		ownerlabel.setForeground(new Color(255, 255, 255));
		ownerlabel.setFont(new Font("Arial", Font.BOLD, 16));
		ownerlabel.setBounds(125, 98, 80, 13);
		panel.add(ownerlabel);
		
		JLabel lblNumber = new JLabel("Bancomat number");
		lblNumber.setForeground(new Color(255, 255, 255));
		lblNumber.setFont(new Font("Arial", Font.BOLD, 16));
		lblNumber.setBounds(125, 157, 155, 13);
		panel.add(lblNumber);
		
		JLabel lblExpiry = new JLabel("Expiry");
		lblExpiry.setForeground(new Color(255, 255, 255));
		lblExpiry.setFont(new Font("Arial", Font.BOLD, 16));
		lblExpiry.setBounds(125, 221, 80, 13);
		panel.add(lblExpiry);
		
		JLabel lblPin = new JLabel("Pin");
		lblPin.setForeground(new Color(255, 255, 255));
		lblPin.setFont(new Font("Arial", Font.BOLD, 16));
		lblPin.setBounds(125, 282, 80, 13);
		panel.add(lblPin);
		
		JRadioButton remember = new JRadioButton("Save for future payments");
		remember.setBackground(new Color(255, 255, 255));
		remember.setContentAreaFilled(false);
		remember.setForeground(new Color(255, 255, 255));
		remember.setFont(new Font("Arial", Font.BOLD, 16));
		remember.setBounds(114, 358, 233, 31);
		panel.add(remember);
		
		JButton paybutton = new JButton("Pay");
		paybutton.setBackground(new Color(255, 255, 255));
		paybutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(verifyInsertion()) {	
				int pin = Integer.valueOf(Pin.getText());
				String d=textField.getText();
				String mm=txtMm.getText();
				String yy=txtYyyy.getText();
					String s =	d.concat("-").concat(mm).concat("-").concat(yy);
					System.out.println(s);
				BancomatStrategy bs = new BancomatStrategy(owner.getText(),cardnumber.getText(),s,pin);
				cust.setBanc(bs);
				try {
					if(cust.getBanc().pay(amt)) {
					JOptionPane.showMessageDialog(null, "Payment submitted. Thank you.");
					Date thisexpiry=null;
					try {
						 thisexpiry=bs.getDateFormat(s);
					} catch (ParseException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(remember.isSelected()) {
						Connection c = DBConnection.openConnection();
						PreparedStatement ps;
						try {
							ps = c.prepareStatement("INSERT INTO paymethod(user,number,expiry, owner, code, type)"+" VALUES(?,?,?,?,?,?)");
							ps.setString(1, cust.getUsername());
							ps.setString(2, cardnumber.getText());
							ps.setString(4,owner.getText());
							String tmp = thisexpiry.toString();
							ps.setString(3, tmp);
							ps.setInt(5, pin);
							ps.setInt(6, 0);
							ps.executeUpdate();
							JOptionPane.showMessageDialog(null, "Payment method saved successfully.");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					}
					else {
						JOptionPane.showMessageDialog(null, "Expired. Retry.");
					}
					
				} catch (ParseException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
			}
			}	
		});
		paybutton.setForeground(new Color(0, 128, 128));
		paybutton.setFont(new Font("Arial", Font.BOLD, 19));
		paybutton.setBounds(162, 427, 131, 37);
		panel.add(paybutton);
		
		JLabel lblPleaseInsertYour = new JLabel("Please, insert your bancomat coordinates.");
		lblPleaseInsertYour.setForeground(new Color(255, 255, 255));
		lblPleaseInsertYour.setFont(new Font("Arial", Font.BOLD, 16));
		lblPleaseInsertYour.setBounds(77, 36, 346, 31);
		panel.add(lblPleaseInsertYour);
		
		textField = new JTextField();
		textField.setToolTipText("Insert expiry date.");
		textField.setText("DD");
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setForeground(new Color(0, 128, 128));
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		textField.setColumns(10);
		textField.setBackground(new Color(255, 255, 255));
		textField.setBounds(125, 244, 57, 31);
		panel.add(textField);
		
		txtYyyy = new JTextField();
		txtYyyy.setToolTipText("Insert expiry date.");
		txtYyyy.setText("YYYY");
		txtYyyy.setHorizontalAlignment(SwingConstants.LEFT);
		txtYyyy.setForeground(new Color(0, 128, 128));
		txtYyyy.setFont(new Font("Arial", Font.PLAIN, 14));
		txtYyyy.setColumns(10);
		txtYyyy.setBackground(new Color(255, 255, 255));
		txtYyyy.setBounds(259, 244, 57, 31);
		panel.add(txtYyyy);
		
		txtMm = new JTextField();
		txtMm.setToolTipText("Insert expiry date.");
		txtMm.setText("MM");
		txtMm.setHorizontalAlignment(SwingConstants.LEFT);
		txtMm.setForeground(new Color(0, 128, 128));
		txtMm.setFont(new Font("Arial", Font.PLAIN, 14));
		txtMm.setColumns(10);
		txtMm.setBackground(new Color(255, 255, 255));
		txtMm.setBounds(192, 244, 57, 31);
		panel.add(txtMm);
		
		Pin = new JTextField();
		Pin.setToolTipText("Insert bancomat owner.");
		Pin.setHorizontalAlignment(SwingConstants.LEFT);
		Pin.setForeground(new Color(0, 128, 128));
		Pin.setFont(new Font("Arial", Font.PLAIN, 14));
		Pin.setColumns(10);
		Pin.setBackground(new Color(255, 255, 255));
		Pin.setBounds(125, 304, 204, 31);
		panel.add(Pin);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			UserMenu m = new UserMenu(cust);
			}
			
		});
		btnBack.setForeground(new Color(0, 128, 128));
		btnBack.setFont(new Font("Arial", Font.BOLD, 19));
		btnBack.setBounds(10, 462, 80, 37);
		panel.add(btnBack);
		
	}

	
	public boolean verifyInsertion() {
		if(owner.getText().equals("")||cardnumber.getText().equals("")||Pin.getText().equals("")|| txtMm.getText().equals("")||textField.getText().equals("")||txtYyyy.getText().equals(""))
			return false;
		else
			return true;
	}
}
