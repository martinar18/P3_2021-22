package Frames;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;


public class LaunchWindow {

	private JFrame frmWelcome;


	/**
	 * Create the application.
	 */
	public LaunchWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWelcome = new JFrame();
		frmWelcome.setVisible(true);
		frmWelcome.setResizable(false);
		frmWelcome.setBounds(100, 100, 487, 300);
		frmWelcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWelcome.setTitle("RENT IT! - Welcome");
			
			JPanel panel = new JPanel();
			panel.setBackground(new Color(0, 139, 139));
			frmWelcome.getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			
			
			JButton Loginb = new JButton("Login");
			
			JLabel title = new JLabel("Welcome");
			title.setBackground(new Color(255, 255, 255));
			title.setHorizontalAlignment(SwingConstants.CENTER);
			title.setForeground(new Color(255, 255, 255));
			title.setFont(new Font("Arial", Font.BOLD, 19));
			title.setBounds(141, 52, 176, 31);
			panel.add(title);
			Loginb.setForeground(new Color(0, 128, 128));
			Loginb.setBackground(new Color(255, 255, 255));
			
			
			Loginb.setFont(new Font("Arial", Font.BOLD, 19));
			Loginb.setBounds(141, 144, 176, 31);
			panel.add(Loginb);
			Loginb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmWelcome.dispose();
					LoginForm logme = new LoginForm();
				}
			});
			
			JButton btnSubscribe = new JButton("Subscribe");
			btnSubscribe.setForeground(new Color(0, 128, 128));
			btnSubscribe.setFont(new Font("Arial", Font.BOLD, 19));
			btnSubscribe.setBackground(new Color(255, 255, 255));
			btnSubscribe.setBounds(141, 185, 176, 31);
			panel.add(btnSubscribe);
			btnSubscribe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmWelcome.dispose();
					Subscribe sub = new Subscribe();
				}
			});
			
			JLabel lblOrWelcomeBack = new JLabel("or welcome back!");
			lblOrWelcomeBack.setForeground(new Color(255, 255, 255));
			lblOrWelcomeBack.setFont(new Font("Arial", Font.BOLD, 19));
			lblOrWelcomeBack.setBounds(141, 74, 176, 31);
			panel.add(lblOrWelcomeBack);
		}


public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				LaunchWindow window = new LaunchWindow();
				window.frmWelcome.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}
}
