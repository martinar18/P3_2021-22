package Frames;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Connection.DBConnection;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

import java.awt.SystemColor;

import java.awt.BorderLayout;

import java.awt.List;

public class Delays {

	private JFrame delaysframe;

	public Delays() {
		delaysframe = new JFrame();
		delaysframe.setVisible(true);
		delaysframe.setResizable(false);
		delaysframe.setBounds(100, 100, 691, 463);
		delaysframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		delaysframe.setTitle("RENT IT! - Delays");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		delaysframe.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		
		JButton BackButton = new JButton("Back");
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delaysframe.dispose();
				AdminMenu back = new AdminMenu();
				//main?
				
			}
		});
		BackButton.setForeground(new Color(0, 139, 139));
		BackButton.setBackground(new Color(255, 255, 255));
		BackButton.setFont(new Font("Arial", Font.BOLD, 19));
		BackButton.setBounds(175, 353, 119, 31);
		panel.add(BackButton);
		
		JLabel lblNewLabel = new JLabel("Delays details");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(95, 30, 269, 46);
		panel.add(lblNewLabel);
		
		List delayslist = new List();
		delayslist.setForeground(new Color(0, 128, 128));
		delayslist.setFont(new Font("Arial", Font.PLAIN, 16));
		delayslist.setBackground(new Color(255, 250, 250));
		delayslist.setBounds(91, 75, 508, 245);
		panel.add(delayslist);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delayslist.removeAll();
				Connection c;
				PreparedStatement p;
				ResultSet r;
				c = DBConnection.openConnection();
				try {
					delayslist.removeAll();
					p = c.prepareStatement("SELECT * FROM rent WHERE SYSDATE()>date_end AND date_return IS NULL");
					r = (ResultSet)p.executeQuery();				
					while(r.next()) {
						delayslist.add("From date "+r.getDate(4)+" to "+r.getDate(6)+" user "+r.getString(2)+" collected car no."+r.getString(3)+".");
						delayslist.add("-------------------------------------------------------------------------------------------------------");
					}
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
				try {
					c.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
			});
		btnNewButton.setForeground(new Color(0, 139, 139));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 19));
		btnNewButton.setBounds(382, 353, 128, 31);
		panel.add(btnNewButton);
		
	}
	}
