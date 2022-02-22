package Frames;

//import entities.Customer;
import javax.swing.JFrame;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import Entities.Customer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.SwingConstants;

public class UserMenu {

	private JFrame frmUsermenu;



	public UserMenu(Customer c) {
		frmUsermenu = new JFrame();
		//frmUsermenu.setIconImage(Toolkit.getDefaultToolkit().getImage(UserMenu.class.getResource("/img/Icon.png")));
		frmUsermenu.setResizable(false);
		frmUsermenu.setVisible(true);
		frmUsermenu.setBounds(100, 100, 508, 384);
		frmUsermenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUsermenu.setTitle("RENT IT! - Menu");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		frmUsermenu.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBackground(new Color(255, 255, 255));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_1.setBounds(64, 117, 256, 34);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setText("Welcome, "+c.getName()+"!");
		
		JLabel lblNewLabel_2 = new JLabel("Please, select an option.");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_2.setBounds(20, 139, 276, 49);
		panel.add(lblNewLabel_2);
		
		JButton btnNewButton_3_1 = new JButton("Payments");
		btnNewButton_3_1.setBackground(new Color(255, 255, 255));
		btnNewButton_3_1.setToolTipText("Check your pendant payments.");
		btnNewButton_3_1.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton_3_1.setForeground(new Color(0, 139, 139));
		btnNewButton_3_1.setBounds(330, 25, 145, 66);
		panel.add(btnNewButton_3_1);
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmUsermenu.dispose();
				Payments pay = new Payments(c);
			}
		});
		
		JButton btnNewButton_2 = new JButton("Return a car");
		btnNewButton_2.setBackground(new Color(255, 255, 255));
		btnNewButton_2.setToolTipText("Return your car at its own parking.");
		btnNewButton_2.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton_2.setForeground(new Color(0, 139, 139));
		btnNewButton_2.setBounds(330, 251, 145, 66);
		panel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmUsermenu.dispose();
				ReturnCar rc = new ReturnCar(c);
			}
		});
		
		JButton btnNewButton_3 = new JButton("Rent a car");
		btnNewButton_3.setBackground(new Color(255, 255, 255));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmUsermenu.dispose();
				RentCar r = new RentCar(c);
			}
		});
		btnNewButton_3.setToolTipText("Pick your favourite car.");
		btnNewButton_3.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton_3.setForeground(new Color(0, 139, 139));
		btnNewButton_3.setBounds(330, 175, 145, 66);
		panel.add(btnNewButton_3);
		
		
		JButton btnNewButton_2_1 = new JButton("History");
		btnNewButton_2_1.setBackground(new Color(255, 255, 255));
		btnNewButton_2_1.setToolTipText("History payments.");
		btnNewButton_2_1.setForeground(new Color(0, 139, 139));
		btnNewButton_2_1.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton_2_1.setBounds(330, 101, 145, 66);
		panel.add(btnNewButton_2_1);
		
		JButton logout = new JButton("Logout");
		logout.setBackground(new Color(255, 255, 255));
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmUsermenu.dispose();
				LaunchWindow lw = new LaunchWindow();
			}
		});
		logout.setToolTipText("Back to launch window.");
		logout.setForeground(new Color(0, 139, 139));
		logout.setFont(new Font("Arial", Font.BOLD, 19));
		logout.setBounds(10, 276, 113, 49);
		panel.add(logout);
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmUsermenu.dispose();
				DisplayHistory dh = new DisplayHistory(c);
			}
		});
		
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setForeground(Color.RED);
		btnLogin.setBackground(Color.WHITE);
		
		
	}
	}
	
	