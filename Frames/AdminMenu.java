package Frames;


import javax.swing.JFrame;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import java.awt.Font;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AdminMenu {

	private JFrame frmUsermenu;

	public AdminMenu(/*Admin a*/) {
		frmUsermenu = new JFrame();
		frmUsermenu.setVisible(true);
		//frmUsermenu.setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenu.class.getResource("/img/Icon.png")));
		frmUsermenu.setResizable(false);
		frmUsermenu.setBounds(100, 100, 604, 383);
		frmUsermenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUsermenu.setTitle("RENT IT! - AdminMenu");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 139, 139));
		frmUsermenu.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Welcome, admin!");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_1.setBounds(20, 133, 170, 34);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Please, select an option.");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_2.setBounds(20, 157, 276, 49);
		panel.add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("Delays");
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setToolTipText("Check info about delays.");
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton_1.setForeground(new Color(0, 139, 139));
		btnNewButton_1.setBounds(394, 253, 186, 66);
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmUsermenu.dispose();
				Delays d = new Delays();
			}
		});
		
		JButton btnNewButton_2 = new JButton("Redistribute");
		btnNewButton_2.setBackground(new Color(255, 255, 255));
		btnNewButton_2.setToolTipText("Redistribute cars in parkings.");
		btnNewButton_2.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton_2.setForeground(new Color(0, 139, 139));
		btnNewButton_2.setBounds(394, 177, 186, 66);
		panel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmUsermenu.dispose();
				RedistributeCar rc = new RedistributeCar();
			}
		});
		
		JButton btnNewButton_3 = new JButton("Add car");
		btnNewButton_3.setBackground(new Color(255, 255, 255));
		btnNewButton_3.setToolTipText("Add new car for this service.");
		btnNewButton_3.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton_3.setForeground(new Color(0, 128, 128));
		btnNewButton_3.setBounds(394, 25, 186, 66);
		panel.add(btnNewButton_3);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmUsermenu.dispose();
				AddNewCar nc = new AddNewCar();
			}
		});
		
		JButton btnNewButton_4 = new JButton("Add parking");
		btnNewButton_4.setBackground(new Color(255, 255, 255));
		btnNewButton_4.setToolTipText("Add new parking for this service.");
		btnNewButton_4.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton_4.setForeground(new Color(0, 139, 139));
		btnNewButton_4.setBounds(394, 101, 186, 66);
		panel.add(btnNewButton_4);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmUsermenu.dispose();
				AddParking np = new AddParking();
			}
		});
		
		JButton btnNewButton_1_1 = new JButton("Logout");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmUsermenu.dispose();
				LaunchWindow lw = new LaunchWindow();
			}
		});
		btnNewButton_1_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1_1.setToolTipText("logout");
		btnNewButton_1_1.setForeground(new Color(0, 128, 128));
		btnNewButton_1_1.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton_1_1.setBounds(20, 262, 121, 49);
		panel.add(btnNewButton_1_1);
		
		JButton btnLogin = new JButton(" ");
		btnLogin.setForeground(Color.RED);
		btnLogin.setBackground(Color.WHITE);
		
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenu window = new AdminMenu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	}
	