package com.alireza6677.virtualkeys.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.omg.PortableServer.ServantRetentionPolicyOperations;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class About extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			About dialog = new About(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public About(JFrame a) {
		setUndecorated(true);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(248, 248, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel ver = new JLabel("v1.0");
		ver.setFont(new Font("Dialog", Font.PLAIN, 12));
		ver.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(ver, BorderLayout.SOUTH);
		
		JLabel lblNewLabel = new JLabel("New label");
		contentPanel.add(lblNewLabel, BorderLayout.NORTH);
		setLocationRelativeTo(a);
		setVisible(true);
	}

}
