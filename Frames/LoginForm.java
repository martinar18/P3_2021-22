package Frames;


//import control.login;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Log.login;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;

public class LoginForm {

	private login newlogin = login.getInstance();
	private JFrame frmRentIt;
	private JPasswordField pwdPassword;
	private JTextField txtUsername;

	public LoginForm() {
		frmRentIt = new JFrame();
		frmRentIt.setResizable(false);
		frmRentIt.setVisible(true);
		frmRentIt.setBounds(100, 100, 450, 300);
		frmRentIt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRentIt.setTitle("RENT IT! - Login");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		frmRentIt.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setForeground(new Color(0, 128, 128));
		txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		txtUsername.setBackground(new Color(255, 255, 255));
		txtUsername.setToolTipText("Insert your username.");
		txtUsername.setHorizontalAlignment(SwingConstants.LEFT);
		txtUsername.setBounds(140, 92, 140, 31);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setForeground(new Color(0, 128, 128));
		pwdPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		pwdPassword.setBackground(new Color(255, 255, 255));
		pwdPassword.setToolTipText("Insert your password.");
		pwdPassword.setBounds(140, 133, 140, 31);
		panel.add(pwdPassword);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.BOLD, 15));
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setBounds(52, 99, 81, 14);
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Arial", Font.BOLD, 15));
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setBounds(52, 140, 81, 14);
		panel.add(lblPassword);
		
		JButton btnLogin = new JButton("GO!");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmRentIt.dispose();
				newlogin.setPassword(pwdPassword.getPassword());
				newlogin.setUsername(txtUsername.getText());
				newlogin.log();
			}
		});
		btnLogin.setForeground(new Color(0, 128, 128));
		btnLogin.setBackground(new Color(255, 255, 255));
		
		btnLogin.setFont(new Font("Arial", Font.BOLD, 19));
		btnLogin.setBounds(300, 92, 81, 71);
		panel.add(btnLogin);

	}
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm window = new LoginForm();
					window.frmRentIt.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	
}
