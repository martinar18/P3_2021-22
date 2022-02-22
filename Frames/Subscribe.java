package Frames;

import Log.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.SwingConstants;

import Connection.DBConnection;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Subscribe {

	private login newlogin = login.getInstance();
	
	private JFrame Subscribe;
	private JTextField txtName;
	private JPasswordField ConfirmPass;
	private JPasswordField Pass;
	private JTextField txtUsername;
	private JTextField txtSurname;
	private JTextField txtDriveLicense;

	public Subscribe() {
		initialize();	
	}

	private void initialize() {
		Subscribe = new JFrame();
		Subscribe.setResizable(false);
		Subscribe.setVisible(true);
		Subscribe.setBounds(100, 100, 471, 546);
		Subscribe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Subscribe.setTitle("RENT IT! - Subscribe");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		Subscribe.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		txtName = new JTextField();
		txtName.setForeground(new Color(0, 128, 128));
		txtName.setFont(new Font("Arial", Font.PLAIN, 14));
		txtName.setBackground(SystemColor.controlHighlight);
		txtName.setToolTipText("Insert your name.");
		txtName.setHorizontalAlignment(SwingConstants.LEFT);
		txtName.setBounds(123, 64, 204, 31);
		panel.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.BOLD, 15));
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setBounds(123, 230, 81, 14);
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Arial", Font.BOLD, 15));
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setBounds(123, 295, 81, 14);
		panel.add(lblPassword);
		

		JLabel Pass_Confirm = new JLabel("Confirm password");
		Pass_Confirm.setHorizontalAlignment(SwingConstants.TRAILING);
		Pass_Confirm.setForeground(new Color(255, 255, 255));
		Pass_Confirm.setFont(new Font("Arial", Font.BOLD, 15));
		Pass_Confirm.setBounds(123, 354, 137, 14);
		panel.add(Pass_Confirm);
		
		ConfirmPass = new JPasswordField();
		ConfirmPass.setToolTipText("Insert your password.");
		ConfirmPass.setForeground(new Color(0, 128, 128));
		ConfirmPass.setFont(new Font("Arial", Font.PLAIN, 14));
		ConfirmPass.setBackground(SystemColor.controlHighlight);
		ConfirmPass.setBounds(123, 373, 204, 31);
		panel.add(ConfirmPass);
		
		Pass = new JPasswordField();
		Pass.setToolTipText("Insert your password.");
		Pass.setForeground(new Color(0, 128, 128));
		Pass.setFont(new Font("Arial", Font.PLAIN, 14));
		Pass.setBackground(SystemColor.controlHighlight);
		Pass.setBounds(123, 313, 204, 31);
		panel.add(Pass);
		
		txtUsername = new JTextField();
		txtUsername.setToolTipText("Insert your username.");
		txtUsername.setHorizontalAlignment(SwingConstants.LEFT);
		txtUsername.setForeground(new Color(0, 128, 128));
		txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		txtUsername.setColumns(10);
		txtUsername.setBackground(SystemColor.controlHighlight);
		txtUsername.setBounds(123, 254, 204, 31);
		panel.add(txtUsername);
		
		txtSurname = new JTextField();
		txtSurname.setToolTipText("Insert your surname.");
		txtSurname.setHorizontalAlignment(SwingConstants.LEFT);
		txtSurname.setForeground(new Color(0, 128, 128));
		txtSurname.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSurname.setColumns(10);
		txtSurname.setBackground(SystemColor.controlHighlight);
		txtSurname.setBounds(123, 123, 204, 31);
		panel.add(txtSurname);
		
		JLabel lblDriveLicense = new JLabel("Drive License");
		lblDriveLicense.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDriveLicense.setForeground(new Color(255, 255, 255));
		lblDriveLicense.setFont(new Font("Arial", Font.BOLD, 15));
		lblDriveLicense.setBounds(111, 164, 118, 24);
		panel.add(lblDriveLicense);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSurname.setForeground(new Color(255, 255, 255));
		lblSurname.setFont(new Font("Arial", Font.BOLD, 15));
		lblSurname.setBounds(111, 102, 81, 24);
		panel.add(lblSurname);
		
		JLabel lblname = new JLabel("Name");
		lblname.setHorizontalAlignment(SwingConstants.TRAILING);
		lblname.setForeground(new Color(255, 255, 255));
		lblname.setFont(new Font("Arial", Font.BOLD, 15));
		lblname.setBounds(83, 44, 81, 14);
		panel.add(lblname);
		
		txtDriveLicense = new JTextField();
		txtDriveLicense.setToolTipText("Insert your drive license");
		txtDriveLicense.setHorizontalAlignment(SwingConstants.LEFT);
		txtDriveLicense.setForeground(new Color(0, 128, 128));
		txtDriveLicense.setFont(new Font("Arial", Font.PLAIN, 14));
		txtDriveLicense.setColumns(10);
		txtDriveLicense.setBackground(SystemColor.controlHighlight);
		txtDriveLicense.setBounds(123, 189, 204, 31);
		panel.add(txtDriveLicense);
		
		JButton btnSubscribenow = new JButton("Subscribe now!");
		btnSubscribenow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pass = String.valueOf(Pass.getPassword()); 
				String conf = String.valueOf(ConfirmPass.getPassword());
				if(conf.equals(pass)) {
					/*Se le password coincidono posso 
					 * controllare che non esista un utente con quell'
					 * username nel database per preservarne il vincolo
					 * di unicità.
					 */

					Connection c = DBConnection.openConnection();
					PreparedStatement ps;
					ResultSet rs;
					try {
						ps=c.prepareStatement("SELECT * FROM user WHERE username=?");
						ps.setString(1, txtUsername.getText());
						rs=(ResultSet)ps.executeQuery();
						if(rs.next()==false) { 
							/*
							 * Se non c'è nemmeno una tupla nel DB con quell'username
							 * procedo alla registrazione.
							 */
							Subscribe.dispose();
							newlogin.setPassword(Pass.getPassword());
							newlogin.setUsername(txtUsername.getText());
							String p = String.valueOf( Pass.getPassword());
							newlogin.register(txtUsername.getText(),p,txtName.getText(), txtSurname.getText(), txtDriveLicense.getText());
						}
						else {
							JOptionPane.showMessageDialog(null, "This username is already taken.");
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Password confirmation doesn't match.");
				}
			}
		});
		
		btnSubscribenow.setForeground(new Color(0, 128, 128));
		btnSubscribenow.setBackground(new Color(255, 255, 255));
		
		btnSubscribenow.setFont(new Font("Arial", Font.BOLD, 19));
		btnSubscribenow.setBounds(108, 439, 253, 42);
		panel.add(btnSubscribenow);
		
	}
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Subscribe window = new Subscribe();
					window.Subscribe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
