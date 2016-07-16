package com.alireza6677.virtualkeys;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.alireza6677.virtualkeys.ui.VirtualButton;
import com.alireza6677.virtualkeys.util.Prefs;
import com.alireza6677.virtualkeys.util.Virtualizer;
import com.alireza6677.virtualkeys.util.Virtualizer.ButtonStates;
import com.alireza6677.virtualkeys.util.Virtualizer.lockingStateChangeListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.SwingDispatchService;

import javax.swing.event.ChangeEvent;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;

public class Main extends JFrame {

	private static final long serialVersionUID = -6008540027701710232L;
	private JPanel contentPane;
	private static final int VK_OEM_3 = (int) '~';
	private static final int VK_BACK_TICK = (int) '`';
	private TrayIcon trayIcon;
	private SystemTray tray;
	private static Prefs p;
	private Virtualizer vt;
	private ImageIcon green;
	private ImageIcon red;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			p = new Prefs();
			// p.saveTheme("Smart");
			UIManager.setLookAndFeel(p.laodTheme());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setLAF(final String laf) {
		try {
			Class cls = Class.forName("com.jtattoo.plaf." + laf.toLowerCase() + "." + laf + "LookAndFeel");
			UIManager.setLookAndFeel((LookAndFeel) cls.newInstance());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void setLAF(final LookAndFeel laf) {
		try {
			UIManager.setLookAndFeel(laf);
			SwingUtilities.updateComponentTreeUI(this);
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private void initTray() {
		System.out.println("creating instance");

		if (SystemTray.isSupported()) {
			System.out.println("system tray supported");
			tray = SystemTray.getSystemTray();

			Image image = null;
			try {
				image = ImageIO.read(getClass().getResource("/img/up.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			ActionListener exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Exiting....");
					System.exit(0);
				}
			};
			PopupMenu popup = new PopupMenu();
			MenuItem defaultItem = new MenuItem("Exit");
			defaultItem.addActionListener(exitListener);
			popup.add(defaultItem);
			defaultItem = new MenuItem("Open");
			defaultItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(true);
					setExtendedState(JFrame.NORMAL);
				}
			});
			popup.add(defaultItem);
			trayIcon = new TrayIcon(image, "SystemTray Demo", popup);
			// trayIcon.setImageAutoSize(true);
		} else {
			System.out.println("system tray not supported");
		}
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if (e.getNewState() == ICONIFIED) {
					try {
						tray.add(trayIcon);
						setVisible(false);
						System.out.println("added to SystemTray");
					} catch (AWTException ex) {
						System.out.println("unable to add to tray");
					}
				}
				if (e.getNewState() == 7) {
					try {
						tray.add(trayIcon);
						setVisible(false);
						System.out.println("added to SystemTray");
					} catch (AWTException ex) {
						System.out.println("unable to add to system tray");
					}
				}
				if (e.getNewState() == MAXIMIZED_BOTH) {
					tray.remove(trayIcon);
					setVisible(true);
					System.out.println("Tray icon removed");
				}
				if (e.getNewState() == NORMAL) {
					tray.remove(trayIcon);
					setVisible(true);
					System.out.println("Tray icon removed");
				}
			}
		});
		// setIconImage(Toolkit.getDefaultToolkit().getImage("Duke256.png"));
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("alireza6677 Keyboard");
		// initTray();
		setAlwaysOnTop(true);
		setResizable(false);
		setFocusable(false);
		setFocusableWindowState(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1090, 342);


		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(a -> System.exit(0));
		mnNewMenu.add(mntmExit);

		JMenu mnNewMenu_1 = new JMenu("View");
		menuBar.add(mnNewMenu_1);

		JMenu mnNewMenu_3 = new JMenu("Theme");
		mnNewMenu_1.add(mnNewMenu_3);

		ButtonGroup group = new ButtonGroup();

		ArrayList<JRadioButtonMenuItem> tl = new ArrayList<>();

		JRadioButtonMenuItem rdbtnmntmNewRadioItem = new JRadioButtonMenuItem("Acryl");
		group.add(rdbtnmntmNewRadioItem);
		mnNewMenu_3.add(rdbtnmntmNewRadioItem);
		tl.add(rdbtnmntmNewRadioItem);

		JRadioButtonMenuItem rdbtnmntmAero = new JRadioButtonMenuItem("Aero");
		group.add(rdbtnmntmAero);
		mnNewMenu_3.add(rdbtnmntmAero);
		tl.add(rdbtnmntmAero);

		JRadioButtonMenuItem rdbtnmntmBernestein = new JRadioButtonMenuItem("Bernstein");
		group.add(rdbtnmntmBernestein);
		mnNewMenu_3.add(rdbtnmntmBernestein);
		tl.add(rdbtnmntmBernestein);

		JRadioButtonMenuItem rdbtnmntmFast = new JRadioButtonMenuItem("Fast");
		group.add(rdbtnmntmFast);
		mnNewMenu_3.add(rdbtnmntmFast);
		tl.add(rdbtnmntmFast);

		JRadioButtonMenuItem rdbtnmntmGraphite = new JRadioButtonMenuItem("Graphite");
		group.add(rdbtnmntmGraphite);
		mnNewMenu_3.add(rdbtnmntmGraphite);
		tl.add(rdbtnmntmGraphite);

		JRadioButtonMenuItem rdbtnmntmHifi = new JRadioButtonMenuItem("HiFi");
		group.add(rdbtnmntmHifi);
		mnNewMenu_3.add(rdbtnmntmHifi);
		tl.add(rdbtnmntmHifi);

		JRadioButtonMenuItem rdbtnmntmLuna = new JRadioButtonMenuItem("Luna");
		group.add(rdbtnmntmLuna);
		mnNewMenu_3.add(rdbtnmntmLuna);
		tl.add(rdbtnmntmLuna);

		JRadioButtonMenuItem rdbtnmntmMcwin = new JRadioButtonMenuItem("McWin");
		group.add(rdbtnmntmMcwin);
		mnNewMenu_3.add(rdbtnmntmMcwin);
		tl.add(rdbtnmntmMcwin);

		JRadioButtonMenuItem rdbtnmntmMint = new JRadioButtonMenuItem("Mint");
		group.add(rdbtnmntmMint);
		mnNewMenu_3.add(rdbtnmntmMint);
		tl.add(rdbtnmntmMint);

		JRadioButtonMenuItem rdbtnmntmSmart = new JRadioButtonMenuItem("Smart");
		group.add(rdbtnmntmSmart);
		mnNewMenu_3.add(rdbtnmntmSmart);
		tl.add(rdbtnmntmSmart);

		JRadioButtonMenuItem rdbtnmntmTexture = new JRadioButtonMenuItem("Texture");
		mnNewMenu_3.add(rdbtnmntmTexture);
		group.add(rdbtnmntmTexture);
		tl.add(rdbtnmntmTexture);

		tl.stream().forEach(x -> {
			// x.addChangeListener(a -> {
			// EventQueue.invokeLater(new Runnable() {
			// @Override
			// public void run() {
			//
			// }
			// });
			//
			// });
			x.addActionListener(r -> {
				setLAF(x.getText());
				p.saveTheme(x.getText());
			});
			if (x.getText().equals(p.getThemeName()))
				x.setSelected(true);
		});

		JMenu mnNewMenu_4 = new JMenu("Opacity");
		mnNewMenu_1.add(mnNewMenu_4);

		JPanel panel_1 = new JPanel();
		mnNewMenu_4.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(270, 353));
		panel_2.setVisible(false);

		JLabel label = new JLabel("");
		label.setVisible(false);
		label.setToolTipText("Your OS or Window Manager does not support opacity.");
		label.setIcon(new ImageIcon(Main.class.getResource("/img/warning.png")));
		panel_1.add(label, BorderLayout.WEST);

		JLabel lblNewLabel = new JLabel("Change Window Opacity");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel, BorderLayout.NORTH);

		JSlider slider = new JSlider();
		slider.setMinimum(20);
		slider.setMaximum(100);
		slider.setValue(100);
		panel_1.add(slider, BorderLayout.CENTER);
		slider.addChangeListener(e -> {
			try {
				setOpacity(((JSlider) e.getSource()).getValue() / 100f);
			} catch (Exception ex) {
				ex.printStackTrace();
				label.setVisible(true);
				slider.setEnabled(false);
				lblNewLabel.setEnabled(false);
			}
		});

		slider.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					p.saveOpacity(getOpacity());
				} catch (Exception ez) {
					ez.printStackTrace();
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					p.saveOpacity(getOpacity());
				} catch (Exception ez) {
					ez.printStackTrace();
				}
			}
		});

		// ## Setting the opacity ##//
		try {
			float op = p.loadOpacity();
			setOpacity(op);
			slider.setValue((int) (op * 100));

			label.setVisible(false);
		} catch (Exception e) {
			label.setVisible(true);
			slider.setEnabled(false);
			lblNewLabel.setEnabled(false);
		}

		// ## Setting saved theme ##//
		// setLAF(p.laodTheme());

		JCheckBoxMenuItem chckbxmntmExpand = new JCheckBoxMenuItem("Optional Keys");
		chckbxmntmExpand.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxmntmExpand.isSelected()) {
					Main.this.setSize(1360, 353);
					panel_2.setVisible(true);
				} else {
					Main.this.setSize(1090, 353);
					panel_2.setVisible(false);
				}

			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				vt.shutdownVirtualizer();
				super.windowClosing(e);
			}

		});
		mnNewMenu_1.add(chckbxmntmExpand);

		JMenu mnNewMenu_2 = new JMenu("Help");
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmContactUs = new JMenuItem("Feedback");
		mnNewMenu_2.add(mntmContactUs);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(e -> JOptionPane.showMessageDialog(this, " Virtual Keyboard v1.0"));

		mnNewMenu_2.add(mntmAbout);

		Component glue = Box.createGlue();
		menuBar.add(glue);

		green = new ImageIcon(Main.class.getResource("/img/green_icon.png"));
		red = new ImageIcon(Main.class.getResource("/img/red_icon.png"));
		
		JLabel lblNewLabel_2 = new JLabel("NumLock");
		lblNewLabel_2.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblNewLabel_2.setIcon(green);
		menuBar.add(lblNewLabel_2);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut);

		JLabel lblcaps = new JLabel("Caps");
		lblcaps.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblcaps.setIcon(green);
		menuBar.add(lblcaps);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(1090, 353));
		contentPane.add(panel, BorderLayout.CENTER);

		VirtualButton esc = new VirtualButton("Esc");
		esc.setTextByShift("");

		esc.setFont(new Font("Dialog", Font.PLAIN, 12));
		esc.setMargin(new Insets(0, 0, 0, 0));

		VirtualButton f1 = new VirtualButton("F1");
		f1.setTextByShift("");
		f1.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton f2 = new VirtualButton("F2");
		f2.setTextByShift("");
		f2.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton f3 = new VirtualButton("F3");
		f3.setTextByShift("");
		f3.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton f4 = new VirtualButton("F4");
		f4.setTextByShift("");
		f4.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton f5 = new VirtualButton("F5");
		f5.setTextByShift("");
		f5.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton f6 = new VirtualButton("F6");
		f6.setTextByShift("");
		f6.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton f7 = new VirtualButton("F7");
		f7.setTextByShift("");
		f7.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton f8 = new VirtualButton("F8");
		f8.setTextByShift("");
		f8.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton f9 = new VirtualButton("F9");
		f9.setTextByShift("");
		f9.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton f10 = new VirtualButton("F10");
		f10.setTextByShift("");
		f10.setMargin(new Insets(0, 0, 0, 0));
		f10.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton f11 = new VirtualButton("F11");
		f11.setTextByShift("");
		f11.setFont(new Font("Dialog", Font.PLAIN, 12));
		f11.setMargin(new Insets(0, 0, 0, 0));

		VirtualButton f12 = new VirtualButton("F12");
		f12.setTextByShift("");
		f12.setFont(new Font("Dialog", Font.PLAIN, 12));
		f12.setMargin(new Insets(0, 0, 0, 0));

		VirtualButton prscr = new VirtualButton("<html><center>Print<br/>Screen</center></html>");
		prscr.setTextByShift("");
		prscr.setFont(new Font("Dialog", Font.PLAIN, 12));
		prscr.setMargin(new Insets(0, 0, 0, 0));

		VirtualButton scrlck = new VirtualButton("<html><center>Scroll<br/>Lock</center></html>");
		scrlck.setTextByShift("");
		scrlck.setFont(new Font("Dialog", Font.PLAIN, 12));
		scrlck.setMargin(new Insets(0, 0, 0, 0));

		VirtualButton psebrk = new VirtualButton("<html><center>Pause<br/>Break</center></html>");
		psebrk.setTextByShift("");
		psebrk.setFont(new Font("Dialog", Font.PLAIN, 12));
		psebrk.setMargin(new Insets(0, 0, 0, 0));

		VirtualButton btnoem3 = new VirtualButton("~");
		btnoem3.setNormalText("`");
		btnoem3.setText("`");

		VirtualButton button_1 = new VirtualButton("1");
		button_1.setTextByShift("!");

		VirtualButton button_2 = new VirtualButton("2");
		button_2.setTextByShift("@");

		VirtualButton button_3 = new VirtualButton("3");
		button_3.setTextByShift("#");

		VirtualButton button_4 = new VirtualButton("4");
		button_4.setTextByShift("$");

		VirtualButton button_5 = new VirtualButton("5");
		button_5.setTextByShift("%");

		VirtualButton button_6 = new VirtualButton("6");
		button_6.setTextByShift("^");

		VirtualButton button_7 = new VirtualButton("7");
		button_7.setTextByShift("&");

		VirtualButton button_8 = new VirtualButton("8");
		button_8.setTextByShift("*");

		VirtualButton button_9 = new VirtualButton("9");
		button_9.setTextByShift("(");

		VirtualButton button_0 = new VirtualButton("0");
		button_0.setTextByShift(")");

		VirtualButton btn_dash = new VirtualButton("-");
		btn_dash.setTextByShift("_");

		VirtualButton btn_eq = new VirtualButton("=");
		btn_eq.setTextByShift("+");

		VirtualButton btnBSlash = new VirtualButton("\\");
		btnBSlash.setTextByShift("|");

		VirtualButton btnBackspace = new VirtualButton("");
		btnBackspace.setToolTipText("BackSpace");
		btnBackspace.setIcon(new ImageIcon(Main.class.getResource("/img/backspace.gif")));
		btnBackspace.setFont(new Font("Dialog", Font.PLAIN, 11));

		VirtualButton btnTab = new VirtualButton("Tab");
		btnTab.setTextByShift("");
		btnTab.setText("Tab  ");
		btnTab.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnTab.setIcon(new ImageIcon(Main.class.getResource("/img/tab.png")));
		btnTab.setHorizontalAlignment(SwingConstants.LEFT);

		VirtualButton btnQ = new VirtualButton("q");

		VirtualButton btnW = new VirtualButton("w");

		VirtualButton btnE = new VirtualButton("e");

		VirtualButton btnR = new VirtualButton("r");

		VirtualButton btnT = new VirtualButton("t");

		VirtualButton btnY = new VirtualButton("y");

		VirtualButton btnU = new VirtualButton("u");

		VirtualButton btnI = new VirtualButton("i");

		VirtualButton btnO = new VirtualButton("o");

		VirtualButton btnP = new VirtualButton("p");

		VirtualButton btnBracket = new VirtualButton("[");
		btnBracket.setTextByShift("{");

		VirtualButton btnCBracket = new VirtualButton("]");
		btnCBracket.setTextByShift("}");

		VirtualButton btnContext = new VirtualButton("");
		btnContext.setToolTipText("Right Click Menu");
		btnContext.setIcon(new ImageIcon(Main.class.getResource("/img/contextMenuCursor.png")));

		VirtualButton btnCaps = new VirtualButton("Caps");
		btnCaps.setText("Caps Lock");
		btnCaps.setNormalText("Caps Lock");
		btnCaps.setTextByShift("");
		btnCaps.setHorizontalAlignment(SwingConstants.LEFT);

		VirtualButton btnA = new VirtualButton("a");

		VirtualButton btnS = new VirtualButton("s");

		VirtualButton btnD = new VirtualButton("d");

		VirtualButton btnF = new VirtualButton("f");

		VirtualButton btnG = new VirtualButton("g");

		VirtualButton btnH = new VirtualButton("h");

		VirtualButton btnJ = new VirtualButton("j");

		VirtualButton btnK = new VirtualButton("k");

		VirtualButton btnL = new VirtualButton("l");

		VirtualButton btnSemiColon = new VirtualButton(";");
		btnSemiColon.setTextByShift(":");

		VirtualButton btnQoute = new VirtualButton("'");
		btnQoute.setTextByShift("\"");

		VirtualButton btnEnter = new VirtualButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEnter.setTextByShift("");
		btnEnter.setIcon(new ImageIcon(Main.class.getResource("/img/enter.gif")));
		btnEnter.setHorizontalAlignment(SwingConstants.LEFT);

		VirtualButton btnShift = new VirtualButton("Shift");
		btnShift.setTextByShift("");
		btnShift.setHorizontalAlignment(SwingConstants.LEFT);
		btnShift.setIcon(new ImageIcon(Main.class.getResource("/img/shift.gif")));

		VirtualButton btnZ = new VirtualButton("z");

		VirtualButton btnX = new VirtualButton("x");

		VirtualButton btnC = new VirtualButton("c");

		VirtualButton btnN = new VirtualButton("n");

		VirtualButton btnV = new VirtualButton("v");

		VirtualButton btnB = new VirtualButton("b");

		VirtualButton btnComma = new VirtualButton(",");
		btnComma.setTextByShift("<");

		VirtualButton btnM = new VirtualButton("m");
		btnM.setToolTipText("");

		VirtualButton btnDot = new VirtualButton(".");
		btnDot.setTextByShift(">");

		VirtualButton btnSlash = new VirtualButton("/");
		btnSlash.setTextByShift("?");

		VirtualButton btnShift2 = new VirtualButton("Shift");
		btnShift2.setHorizontalAlignment(SwingConstants.LEFT);

		btnShift2.setIcon(new ImageIcon(Main.class.getResource("/img/shift.gif")));

		VirtualButton btnInsert = new VirtualButton("Insert");
		btnInsert.setTextByShift("");
		btnInsert.setMargin(new Insets(0, 0, 0, 0));
		btnInsert.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton btnHome = new VirtualButton("Home");
		btnHome.setTextByShift("");
		btnHome.setMargin(new Insets(0, 0, 0, 0));
		btnHome.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton page_up = new VirtualButton("<html><center>Page<br/>Up</center></html>");
		page_up.setTextByShift("");
		;
		page_up.setMargin(new Insets(0, 0, 0, 0));
		page_up.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton btnDelete = new VirtualButton("Delete");
		btnDelete.setText("Del");
		btnDelete.setNormalText("Del");
		btnDelete.setTextByShift("");
		btnDelete.setMargin(new Insets(0, 0, 0, 0));
		btnDelete.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton btnEnd = new VirtualButton("End");
		btnEnd.setMargin(new Insets(0, 0, 0, 0));
		btnEnd.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton page_down = new VirtualButton("<html><center>Page<br/>Down</center></html>");
		page_down.setTextByShift("");
		page_down.setMargin(new Insets(0, 0, 0, 0));
		page_down.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton left = new VirtualButton("");
		left.setIcon(new ImageIcon(Main.class.getResource("/img/left.png")));
		left.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton down = new VirtualButton("");
		down.setIcon(new ImageIcon(Main.class.getResource("/img/down.png")));
		down.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton right = new VirtualButton("");
		right.setIcon(new ImageIcon(Main.class.getResource("/img/right.png")));
		right.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton up = new VirtualButton("");
		up.setIcon(new ImageIcon(Main.class.getResource("/img/up.png")));
		up.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton btnCtrl = new VirtualButton("a");
		btnCtrl.setTextByShift("");
		btnCtrl.setText("Ctrl");
		btnCtrl.setNormalText("Ctrl");

		VirtualButton btnSuper = new VirtualButton("a");
		btnSuper.setIcon(new ImageIcon(Main.class.getResource("/img/windows8.png")));
		btnSuper.setTextByShift("");
		btnSuper.setText("");
		btnSuper.setNormalText("");

		VirtualButton btnAlt = new VirtualButton("a");
		btnAlt.setTextByShift("");
		btnAlt.setText("Alt");
		btnAlt.setNormalText("Alt");

		VirtualButton btnCtrl2 = new VirtualButton("a");
		btnCtrl2.setTextByShift("");
		btnCtrl2.setText("Ctrl");
		btnCtrl2.setNormalText("Ctrl");

		VirtualButton btnSuper2 = new VirtualButton("a");
		btnSuper2.setIcon(new ImageIcon(Main.class.getResource("/img/windows8.png")));
		btnSuper2.setTextByShift("");
		btnSuper2.setText("");
		btnSuper2.setNormalText("");

		VirtualButton btnAlt2 = new VirtualButton("a");
		btnAlt2.setTextByShift("");
		btnAlt2.setText("Alt");
		btnAlt2.setNormalText("Alt");

		VirtualButton btnSpace = new VirtualButton("a");
		btnSpace.setToolTipText("Space");
		btnSpace.setTextByShift("");
		btnSpace.setText(" ");
		btnSpace.setNormalText(" ");
		
		vt = Virtualizer.getInstance((a, b) -> {
			if (!b) {
				btnCaps.setBackground(null);
				lblcaps.setIcon(red);
			} else {
				btnCaps.setBackground(new Color(0, 255, 0));
				lblcaps.setIcon(green);
			}
			
		} , null);		
		//// Button Register
		vt.registerButton(btnA);
		vt.registerButton(btnB);
		vt.registerButton(btnC);
		vt.registerButton(btnD);
		vt.registerButton(btnE);
		vt.registerButton(btnF);
		vt.registerButton(btnG);
		vt.registerButton(btnH);
		vt.registerButton(btnI);
		vt.registerButton(btnJ);
		vt.registerButton(btnK);
		vt.registerButton(btnL);
		vt.registerButton(btnM);
		vt.registerButton(btnN);
		vt.registerButton(btnO);
		vt.registerButton(btnP);
		vt.registerButton(btnQ);
		vt.registerButton(btnR);
		vt.registerButton(btnS);
		vt.registerButton(btnT);
		vt.registerButton(btnU);
		vt.registerButton(btnV);
		vt.registerButton(btnW);
		vt.registerButton(btnX);
		vt.registerButton(btnY);
		vt.registerButton(btnZ);
		vt.registerButton(button_1);
		vt.registerButton(button_2);
		vt.registerButton(button_3);
		vt.registerButton(button_4);
		vt.registerButton(button_5);
		vt.registerButton(button_6);
		vt.registerButton(button_7);
		vt.registerButton(button_8);
		vt.registerButton(button_9);
		vt.registerButton(button_0);
		vt.registerButton(btnoem3);
		vt.registerButton(btn_dash);
		vt.registerButton(btn_eq);
		vt.registerButton(btnBracket);
		vt.registerButton(btnCBracket);
		vt.registerButton(btnSemiColon);
		vt.registerButton(btnQoute);
		vt.registerButton(btnComma);
		vt.registerButton(btnDot);
		vt.registerButton(btnSlash);
		vt.registerButton(btnBSlash);
		vt.registerButton(btnSpace);
		vt.registerFnButton(f1);
		vt.registerFnButton(f2);
		vt.registerFnButton(f3);
		vt.registerFnButton(f4);
		vt.registerFnButton(f5);
		vt.registerFnButton(f6);
		vt.registerFnButton(f7);
		vt.registerFnButton(f8);
		vt.registerFnButton(f9);
		vt.registerFnButton(f10);
		vt.registerFnButton(f11);
		vt.registerFnButton(f12);
		vt.registerEscButton(esc);
		vt.registerAltButton(btnAlt);
		vt.registerAltButton(btnAlt2);
		vt.registerShiftButton(btnShift);
		vt.registerShiftButton(btnShift2);
		vt.registerCtrlButton(btnCtrl);
		vt.registerCtrlButton(btnCtrl2);
		vt.registerCapsButton(btnCaps);
		vt.registerBackSpaceButton(btnBackspace);
		vt.registerSuperButton(btnSuper);
		vt.registerSuperButton(btnSuper2);
		vt.registerEnterButton(btnEnter);
		vt.registerPrintScreenButton(prscr);
		vt.registerScrollLockButton(scrlck);
		vt.registerPausBrButton(psebrk);
		vt.registerUpButton(up);
		vt.registerDownButton(down);
		vt.registerRightButton(right);
		vt.registerLeftButton(left);
		vt.registerDeleteButton(btnDelete);
		vt.registerEndButton(btnEnd);
		vt.registerHomeButton(btnHome);
		vt.registerInsertButton(btnInsert);
		vt.registerPageUpButton(page_up);
		vt.registerPageDownButton(page_down);
		vt.registerContextMenuButton(btnContext);
		vt.registerTabButton(btnTab);

		vt.setShiftChangeListener((a, b) -> {
			if (a == ButtonStates.OFF) {
				btnShift.setBackground(null);
				btnShift2.setBackground(null);
			} else if (a == ButtonStates.ON) {
				btnShift.setBackground(new Color(0, 255, 0));
				btnShift2.setBackground(new Color(0, 255, 0));
			} else if (a == ButtonStates.LOCKED) {
				btnShift.setBackground(new Color(255, 0, 0));
				btnShift2.setBackground(new Color(255, 0, 0));
			}
		});

		vt.setAltChangeListener((a, b) -> {
			if (a == ButtonStates.OFF) {
				btnAlt.setBackground(null);
				btnAlt2.setBackground(null);
			} else if (a == ButtonStates.ON) {
				btnAlt.setBackground(new Color(0, 255, 0));
				btnAlt2.setBackground(new Color(0, 255, 0));
			} else if (a == ButtonStates.LOCKED) {
				btnAlt.setBackground(new Color(255, 0, 0));
				btnAlt2.setBackground(new Color(255, 0, 0));
			}
		});

		vt.setCtrlChangeListener((a, b) -> {
			if (a == ButtonStates.OFF) {
				btnCtrl.setBackground(null);
				btnCtrl2.setBackground(null);
			} else if (a == ButtonStates.ON) {
				btnCtrl.setBackground(new Color(0, 255, 0));
				btnCtrl2.setBackground(new Color(0, 255, 0));
			} else if (a == ButtonStates.LOCKED) {
				btnCtrl.setBackground(new Color(255, 0, 0));
				btnCtrl2.setBackground(new Color(255, 0, 0));
			}
		});

		vt.setNumlChangeListener((a, b) -> {
			if (a == ButtonStates.OFF) {
				btnCtrl.setBackground(null);
			} else if (a == ButtonStates.ON) {
				btnCtrl.setBackground(new Color(0, 255, 0));
			}
		});

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(btnCtrl, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSuper, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnAlt, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSpace, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnAlt2, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSuper2, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										ComponentPlacement.RELATED)
								.addComponent(btnCtrl2, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(btnCaps, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnA, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnS, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnD, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnF, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnG, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnH, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnJ, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnK, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnL, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnSemiColon, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnQoute, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnEnter, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(esc, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
										.addComponent(btnoem3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
								.addGap(5)
								.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel.createSequentialGroup()
												.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 44,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 44,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 44,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(button_5, GroupLayout.PREFERRED_SIZE, 44,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(button_6, GroupLayout.PREFERRED_SIZE, 44,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(button_7, GroupLayout.PREFERRED_SIZE, 44,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(button_8, GroupLayout.PREFERRED_SIZE, 44,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(button_9, GroupLayout.PREFERRED_SIZE, 44,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(button_0, GroupLayout.PREFERRED_SIZE, 44,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn_dash, GroupLayout.PREFERRED_SIZE, 44,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(Alignment.TRAILING,
												gl_panel.createSequentialGroup()
														.addComponent(f1, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(f2, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(f3, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(f4, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(f5, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(f6, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(f7, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(f8, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addGap(24)))
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_panel.createSequentialGroup()
												.addComponent(btn_eq, GroupLayout.PREFERRED_SIZE, 44,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnBSlash, GroupLayout.PREFERRED_SIZE, 44,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnBackspace,
														GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
										.addGroup(gl_panel.createSequentialGroup()
												.addComponent(f9, GroupLayout.PREFERRED_SIZE, 50,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(f10, GroupLayout.PREFERRED_SIZE, 50,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(f11, GroupLayout.PREFERRED_SIZE, 50,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(f12,
														GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(btnTab, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnQ, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnW, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnE, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnR, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnT, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnY, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnU, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnI, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnO, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnP, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnBracket, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnCBracket, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnContext, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(btnShift, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnZ, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnX, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnC, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnV, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnB, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnN, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnM, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnComma, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(btnDot, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSlash, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnShift2, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)))
						.addGap(25)
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(left, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(btnInsert, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(prscr, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(down, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(right, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
								.addComponent(up, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
												.addComponent(btnEnd, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnHome, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(scrlck, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 59,
														Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(page_down, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(page_up, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(psebrk, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))))
						.addGap(20)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel
						.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
								.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(prscr, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(scrlck, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(psebrk, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnInsert, GroupLayout.PREFERRED_SIZE, 33,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnHome, GroupLayout.PREFERRED_SIZE, 33,
														GroupLayout.PREFERRED_SIZE))
										.addComponent(page_up, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(page_down, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 33,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnEnd, GroupLayout.PREFERRED_SIZE, 33,
														GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel
								.createParallelGroup(Alignment.LEADING)
								.addComponent(esc, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(f9, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(f10, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(f11, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(f12, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(f5, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(f6, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(f7, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(f8, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(f1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(f2, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(f3, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(f4, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
								.addGap(18)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnoem3, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(button_5, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(button_6, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(button_7, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(button_8, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(button_9, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(button_0, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btn_dash, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btn_eq, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnBSlash, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE))
										.addComponent(btnBackspace, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btnTab, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnContext, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnQ, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnW, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnE, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnR, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnT, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnY, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnU, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnI, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnO, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnP, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnBracket, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnCBracket, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE))))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnA, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnS, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnD, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnF, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnG, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnH, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnJ, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnK, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnL, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSemiColon, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnQoute, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEnter, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnCaps, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnZ, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnX, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnC, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnV, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnB, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnN, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnM, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnComma, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnShift, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnDot, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnSlash, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnShift2, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(up, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(down, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnCtrl, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSuper, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAlt, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnCtrl2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSuper2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAlt2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSpace, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(right, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(left, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(21, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		contentPane.add(panel_2, BorderLayout.EAST);

		VirtualButton vrtlbtnNumLock = new VirtualButton("Delete");
		vrtlbtnNumLock.setTextByShift("");
		vrtlbtnNumLock.setText("<html><center>Num<br/>Lock</center></html>");
		vrtlbtnNumLock.setNormalText("<html><center>Num<br/>Lock</center></html>");
		vrtlbtnNumLock.setMargin(new Insets(0, 0, 0, 0));
		vrtlbtnNumLock.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_5 = new VirtualButton("Delete");
		virtualButton_5.setTextByShift("");
		virtualButton_5.setText("/");
		virtualButton_5.setNormalText("/");
		virtualButton_5.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_5.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_7 = new VirtualButton("Delete");
		virtualButton_7.setTextByShift("");
		virtualButton_7.setText("*");
		virtualButton_7.setNormalText("*");
		virtualButton_7.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_7.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_8 = new VirtualButton("Delete");
		virtualButton_8.setTextByShift("");
		virtualButton_8.setText("-");
		virtualButton_8.setNormalText("-");
		virtualButton_8.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_8.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_9 = new VirtualButton("Delete");
		virtualButton_9.setTextByShift("");
		virtualButton_9.setText("7");
		virtualButton_9.setNormalText("7");
		virtualButton_9.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_9.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_10 = new VirtualButton("Delete");
		virtualButton_10.setTextByShift("");
		virtualButton_10.setText("8");
		virtualButton_10.setNormalText("8");
		virtualButton_10.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_10.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_11 = new VirtualButton("Delete");
		virtualButton_11.setTextByShift("");
		virtualButton_11.setText("9");
		virtualButton_11.setNormalText("9");
		virtualButton_11.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_11.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_12 = new VirtualButton("Delete");
		virtualButton_12.setTextByShift("");
		virtualButton_12.setText("4");
		virtualButton_12.setNormalText("4");
		virtualButton_12.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_12.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_13 = new VirtualButton("Delete");
		virtualButton_13.setTextByShift("");
		virtualButton_13.setText("5");
		virtualButton_13.setNormalText("5");
		virtualButton_13.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_13.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_14 = new VirtualButton("Delete");
		virtualButton_14.setTextByShift("");
		virtualButton_14.setText("6");
		virtualButton_14.setNormalText("6");
		virtualButton_14.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_14.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_15 = new VirtualButton("Delete");
		virtualButton_15.setTextByShift("");
		virtualButton_15.setText("1");
		virtualButton_15.setNormalText("1");
		virtualButton_15.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_15.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_16 = new VirtualButton("Delete");
		virtualButton_16.setTextByShift("");
		virtualButton_16.setText("2");
		virtualButton_16.setNormalText("2");
		virtualButton_16.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_16.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_17 = new VirtualButton("Delete");
		virtualButton_17.setTextByShift("");
		virtualButton_17.setText("3");
		virtualButton_17.setNormalText("3");
		virtualButton_17.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_17.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_18 = new VirtualButton("Delete");
		virtualButton_18.setTextByShift("");
		virtualButton_18.setText("0");
		virtualButton_18.setNormalText("0");
		virtualButton_18.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_18.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_19 = new VirtualButton("Delete");
		virtualButton_19.setTextByShift("");
		virtualButton_19.setText(".");
		virtualButton_19.setNormalText(".");
		virtualButton_19.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_19.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton virtualButton_20 = new VirtualButton("Delete");
		virtualButton_20.setTextByShift("");
		virtualButton_20.setText("+");
		virtualButton_20.setNormalText("+");
		virtualButton_20.setMargin(new Insets(0, 0, 0, 0));
		virtualButton_20.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton vrtlbtnEnter = new VirtualButton("Delete");
		vrtlbtnEnter.setTextByShift("");
		vrtlbtnEnter.setText("Enter");
		vrtlbtnEnter.setNormalText("Del");
		vrtlbtnEnter.setMargin(new Insets(0, 0, 0, 0));
		vrtlbtnEnter.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton vrtlbtnVol = new VirtualButton("Delete");
		vrtlbtnVol.setTextByShift("");
		vrtlbtnVol.setText("Vol-");
		vrtlbtnVol.setNormalText("Vol-");
		vrtlbtnVol.setMargin(new Insets(0, 0, 0, 0));
		vrtlbtnVol.setFont(new Font("Dialog", Font.PLAIN, 12));

		VirtualButton vrtlbtnVol_1 = new VirtualButton("Delete");
		vrtlbtnVol_1.setTextByShift("");
		vrtlbtnVol_1.setText("Vol+");
		vrtlbtnVol_1.setNormalText("Vol+");
		vrtlbtnVol_1.setMargin(new Insets(0, 0, 0, 0));
		vrtlbtnVol_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2
						.createSequentialGroup().addContainerGap().addGroup(gl_panel_2
								.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2
										.createSequentialGroup().addGroup(
												gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2
														.createSequentialGroup()
														.addComponent(virtualButton_15, GroupLayout.PREFERRED_SIZE, 54,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(virtualButton_16, GroupLayout.PREFERRED_SIZE, 54,
																GroupLayout.PREFERRED_SIZE))
														.addComponent(virtualButton_18, GroupLayout.PREFERRED_SIZE, 114,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(virtualButton_19, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(virtualButton_17, GroupLayout.DEFAULT_SIZE, 54,
														Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(vrtlbtnEnter,
												GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_2.createSequentialGroup()
										.addGroup(gl_panel_2
												.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2
														.createSequentialGroup()
														.addComponent(virtualButton_9, GroupLayout.PREFERRED_SIZE, 54,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(virtualButton_10, GroupLayout.PREFERRED_SIZE, 54,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(virtualButton_11, GroupLayout.PREFERRED_SIZE, 54,
																GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_panel_2.createSequentialGroup()
														.addComponent(virtualButton_12, GroupLayout.PREFERRED_SIZE, 54,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(virtualButton_13, GroupLayout.PREFERRED_SIZE, 54,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(virtualButton_14, GroupLayout.PREFERRED_SIZE, 54,
																GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(virtualButton_20,
												GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
								.addGroup(
										gl_panel_2.createSequentialGroup()
												.addComponent(vrtlbtnNumLock, GroupLayout.PREFERRED_SIZE, 54,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_panel_2
														.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2
																.createSequentialGroup()
																.addComponent(vrtlbtnVol, GroupLayout.PREFERRED_SIZE,
																		54, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(
																		vrtlbtnVol_1, GroupLayout.PREFERRED_SIZE, 54,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_panel_2.createSequentialGroup()
																.addComponent(virtualButton_5,
																		GroupLayout.PREFERRED_SIZE, 54,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(virtualButton_7,
																		GroupLayout.PREFERRED_SIZE, 54,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(virtualButton_8,
																		GroupLayout.PREFERRED_SIZE, 54,
																		GroupLayout.PREFERRED_SIZE)))))
						.addContainerGap(24, Short.MAX_VALUE)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(vrtlbtnVol, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(vrtlbtnVol_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(virtualButton_8, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(virtualButton_7, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(virtualButton_5, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(vrtlbtnNumLock, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING,
						false)
						.addGroup(gl_panel_2
								.createSequentialGroup()
								.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(virtualButton_9, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(virtualButton_10, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(virtualButton_11, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(virtualButton_12, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(virtualButton_13, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(virtualButton_14, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)))
						.addComponent(virtualButton_20, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING,
						false)
						.addGroup(gl_panel_2
								.createSequentialGroup()
								.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(virtualButton_15, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(virtualButton_16, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(virtualButton_17, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(virtualButton_18, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(virtualButton_19, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)))
						.addComponent(vrtlbtnEnter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE))
				.addContainerGap(31, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

	}
}
