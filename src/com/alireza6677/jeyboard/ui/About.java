package com.alireza6677.jeyboard.ui;

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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;

public class About extends JDialog {

	private final JPanel contentPanel = new JPanel();

	

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
		setAlwaysOnTop(true);
	    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 457, 337);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(248, 248, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel ver = new JLabel("v1.0-Beta1");
		ver.setFont(new Font("Dialog", Font.PLAIN, 12));
		ver.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblNewLabel = new JLabel("JeyBoard");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(About.class.getResource("/img/java_logo.png")));
		
		JLabel description = new JLabel("<html><center>A simple powerful virtual keyboard<br/>for any desktop operating system running java.</center></html>");
		description.setFont(new Font("Dialog", Font.PLAIN, 11));
		description.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Powered by Java 8");
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JLabel lblNewLabel_2 = new JLabel("Developer : @Alireza6677");
		lblNewLabel_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(58)
					.addComponent(description, 0, 0, Short.MAX_VALUE)
					.addGap(56))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(100)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(108, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(169)
					.addComponent(lblNewLabel_1)
					.addContainerGap(181, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(190)
					.addComponent(ver, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(195, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(146)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2)
						.addComponent(label))
					.addGap(148))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(description, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ver)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_2)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		setLocationRelativeTo(a);
		setVisible(true);
	}
}
