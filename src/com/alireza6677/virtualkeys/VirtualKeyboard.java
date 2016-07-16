package com.alireza6677.virtualkeys;

//
//Decompiled by Procyon v0.5.29
//
import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.alireza6677.virtualkeys.ui.ShiftUpdater;
import com.alireza6677.virtualkeys.ui.VirtualButton;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.fast.FastLookAndFeel;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.luna.LunaLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.mint.MintLookAndFeel;
import com.jtattoo.plaf.smart.SmartLookAndFeel;
import com.jtattoo.plaf.texture.TextureLookAndFeel;

//import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
//import com.jtattoo.plaf.aero.AeroLookAndFeel;
//import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
//import com.jtattoo.plaf.fast.FastLookAndFeel;
//import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
//import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
//import com.jtattoo.plaf.luna.LunaLookAndFeel;
//import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
//import com.jtattoo.plaf.mint.MintLookAndFeel;
//import com.jtattoo.plaf.smart.SmartLookAndFeel;
//import com.jtattoo.plaf.texture.TextureLookAndFeel;
public class VirtualKeyboard extends JFrame {

	private static final long serialVersionUID = 4594683669739680020L;
	private JPanel contentPane;
	private Robot r;
	private boolean cap;
	private boolean al;
	private boolean ct;
	private boolean sh;
	private JToggleButton virtualButton_30;
	private JToggleButton virtualButton_40;
	private JToggleButton virtualButton_14;
	private JToggleButton virtualButton_18;
	private JToggleButton virtualButton_15;
	private JToggleButton virtualButton_17;
	private JToggleButton virtualButton_20;
	private ShiftUpdater su;

	public static void main(String[] args) {	
		try
		    {
		
		      UIManager.setLookAndFeel(new SmartLookAndFeel());
		    }
		    catch (UnsupportedLookAndFeelException e)
		    {
		      e.printStackTrace();
		    }
		new VirtualKeyboard();
	}

	public VirtualKeyboard() {

		// setUndecorated(true);
		// AWTUtilities.setWindowOpacity(this, 0.2f);
		// setUndecorated(false);
		this.cap = Toolkit.getDefaultToolkit().getLockingKeyState(20);
		this.al = false;
		this.ct = false;
		this.sh = false;
		this.setTitle("dasssnj Virtual Keyboard");
		this.setResizable(false);
		try {
			this.setIconImage(ImageIO.read(this.getClass().getResource("/img/icon.png")));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		this.su = new ShiftUpdater();

		final ActionListener a = e -> {
			int i;
			i = this.getKeyCode(((VirtualButton) e.getSource()).getNormalText().charAt(0));
			this.r.keyPress(i);
			this.r.keyRelease(i);
			return;
		};
		final ActionListener tab = e -> {
			this.r.keyPress(9);
			this.r.keyRelease(9);
			return;
		};
		final ActionListener caps = e -> {
			this.r.keyPress(20);
			this.r.keyRelease(20);
			this.cap = ((JToggleButton) e.getSource()).isSelected();
			return;
		};
		final ActionListener win = e -> {
			this.r.keyPress(524);
			this.r.keyRelease(524);
			return;
		};
		final ActionListener shft = e -> {
			if (((JToggleButton) e.getSource()).isSelected()) {
				this.virtualButton_30.setSelected(true);
				this.virtualButton_40.setSelected(true);
				this.r.keyPress(16);
				this.su.shiftPressed();
			} else {
				this.virtualButton_30.setSelected(false);
				this.virtualButton_40.setSelected(false);
				this.r.keyRelease(16);
				this.su.shiftReleased();
			}
			return;
		};
		final ActionListener ctrl = e -> {
			if (((JToggleButton) e.getSource()).isSelected()) {
				this.virtualButton_15.setSelected(true);
				this.virtualButton_18.setSelected(true);
				this.r.keyPress(17);
			} else {
				this.virtualButton_15.setSelected(false);
				this.virtualButton_18.setSelected(false);
				this.r.keyRelease(17);
			}
			return;
		};
		final ActionListener alt = e -> {
			if (((JToggleButton) e.getSource()).isSelected()) {
				this.virtualButton_17.setSelected(true);
				this.virtualButton_20.setSelected(true);
				this.r.keyPress(18);
			} else {
				this.virtualButton_17.setSelected(false);
				this.virtualButton_20.setSelected(false);
				this.r.keyRelease(18);
			}
			return;
		};
		this.setAlwaysOnTop(true);
		try {
			this.r = new Robot();
		} catch (AWTException ex) {
		}
		this.setFocusableWindowState(false);
		this.setDefaultCloseOperation(3);
		this.setBounds(100, 100, 952, 371);
		final JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		final JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		final JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		final JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		final JMenu mnTheme = new JMenu("Theme");
		mnView.add(mnTheme);
		final JMenuItem mntmAcryl = new JMenuItem("Acryl");
		mntmAcryl.addActionListener(e -> this.setLAF((LookAndFeel) new AcrylLookAndFeel()));
		mnTheme.add(mntmAcryl);
		final JMenuItem mntmAero = new JMenuItem("Aero");
		mntmAero.addActionListener(e -> this.setLAF((LookAndFeel) new AeroLookAndFeel()));
		mnTheme.add(mntmAero);
		final JMenuItem mntmBernstein = new JMenuItem("Bernstein");
		mntmBernstein.addActionListener(e -> this.setLAF((LookAndFeel) new BernsteinLookAndFeel()));
		mnTheme.add(mntmBernstein);
		final JMenuItem mntmNewMenuItem = new JMenuItem("Fast");
		mntmNewMenuItem.addActionListener(e -> this.setLAF((LookAndFeel) new FastLookAndFeel()));
		mnTheme.add(mntmNewMenuItem);
		final JMenuItem mntmGraphite = new JMenuItem("Graphite");
		mntmGraphite.addActionListener(e -> this.setLAF((LookAndFeel) new GraphiteLookAndFeel()));
		mnTheme.add(mntmGraphite);
		final JMenuItem mntmHifi = new JMenuItem("HiFi");
		mntmHifi.addActionListener(e -> this.setLAF((LookAndFeel) new HiFiLookAndFeel()));
		mnTheme.add(mntmHifi);
		final JMenuItem mntmNewMenuItem_1 = new JMenuItem("Luna");
		mntmNewMenuItem_1.addActionListener(e -> this.setLAF((LookAndFeel) new LunaLookAndFeel()));
		mnTheme.add(mntmNewMenuItem_1);
		final JMenuItem mntmMcwin = new JMenuItem("McWin");
		mntmMcwin.addActionListener(e -> this.setLAF((LookAndFeel) new McWinLookAndFeel()));
		mnTheme.add(mntmMcwin);
		final JMenuItem mntmNewMenuItem_2 = new JMenuItem("Mint");
		mntmNewMenuItem_2.addActionListener(e -> this.setLAF((LookAndFeel) new MintLookAndFeel()));
		mnTheme.add(mntmNewMenuItem_2);
		final JMenuItem mntmSmart = new JMenuItem("Smart");
		mntmSmart.addActionListener(e -> this.setLAF((LookAndFeel) new SmartLookAndFeel()));
		mnTheme.add(mntmSmart);
		final JMenuItem mntmNewMenuItem_3 = new JMenuItem("Texture");
		mntmNewMenuItem_3.addActionListener(e -> this.setLAF((LookAndFeel) new TextureLookAndFeel()));
		mnTheme.add(mntmNewMenuItem_3);
		final JMenu mnOpacity = new JMenu("Opacity");
		mnView.add(mnOpacity);
		final JMenuItem mntmNewMenuItem_4 = new JMenuItem("20%");
		mntmNewMenuItem_4.addActionListener(e -> this.setOpacity(0.2f));
		mnOpacity.add(mntmNewMenuItem_4);
		final JMenuItem mntmNewMenuItem_5 = new JMenuItem("30%");
		mnOpacity.add(mntmNewMenuItem_5);
		mntmNewMenuItem_5.addActionListener(e -> this.setOpacity(0.3f));
		final JMenuItem mntmNewMenuItem_6 = new JMenuItem("40%");
		mnOpacity.add(mntmNewMenuItem_6);
		mntmNewMenuItem_6.addActionListener(e -> this.setOpacity(0.4f));
		final JMenuItem mntmNewMenuItem_7 = new JMenuItem("50%");
		mnOpacity.add(mntmNewMenuItem_7);
		mntmNewMenuItem_7.addActionListener(e -> this.setOpacity(0.5f));
		final JMenuItem mntmNewMenuItem_8 = new JMenuItem("60%");
		mnOpacity.add(mntmNewMenuItem_8);
		mntmNewMenuItem_8.addActionListener(e -> this.setOpacity(0.6f));
		final JMenuItem mntmNewMenuItem_9 = new JMenuItem("70%");
		mnOpacity.add(mntmNewMenuItem_9);
		mntmNewMenuItem_9.addActionListener(e -> this.setOpacity(0.7f));
		final JMenuItem mntmNewMenuItem_10 = new JMenuItem("80%");
		mnOpacity.add(mntmNewMenuItem_10);
		mntmNewMenuItem_10.addActionListener(e -> this.setOpacity(0.8f));
		final JMenuItem mntmNewMenuItem_11 = new JMenuItem("90%");
		mnOpacity.add(mntmNewMenuItem_11);
		mntmNewMenuItem_11.addActionListener(e -> this.setOpacity(0.9f));
		final JMenuItem mntmNewMenuItem_12 = new JMenuItem("100%");
		mnOpacity.add(mntmNewMenuItem_12);
		mntmNewMenuItem_12.addActionListener(e -> this.setOpacity(1.0f));
		final JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		final JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(r -> JOptionPane.showMessageDialog(this, "dasssnj Virtual Keyboard v1.0"));
		mnHelp.add(mntmAbout);
		(this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(this.contentPane);
		final VirtualButton btnNewButton = new VirtualButton("Esc");
		btnNewButton.setTextByShift("|");
		btnNewButton.addActionListener(e -> {
			this.r.keyPress(27);
			this.r.keyRelease(27);
			return;
		});
		final VirtualButton virtualButton = new VirtualButton("1");
		virtualButton.setTextByShift("!");
		virtualButton.addActionListener(a);
		this.su.addVirtualButton(virtualButton);
		final VirtualButton virtualButton_1 = new VirtualButton("2");
		virtualButton_1.setTextByShift("\"");
		virtualButton_1.addActionListener(a);
		this.su.addVirtualButton(virtualButton_1);
		final VirtualButton virtualButton_2 = new VirtualButton("3");
		virtualButton_2.setTextByShift("#");
		virtualButton_2.addActionListener(a);
		this.su.addVirtualButton(virtualButton_2);
		final VirtualButton virtualButton_3 = new VirtualButton("4");
		virtualButton_3.setTextByShift("$");
		virtualButton_3.addActionListener(a);
		this.su.addVirtualButton(virtualButton_3);
		final VirtualButton virtualButton_4 = new VirtualButton("5");
		virtualButton_4.setTextByShift("%");
		virtualButton_4.addActionListener(a);
		this.su.addVirtualButton(virtualButton_4);
		final VirtualButton virtualButton_5 = new VirtualButton("6");
		virtualButton_5.setTextByShift("^");
		virtualButton_5.addActionListener(a);
		this.su.addVirtualButton(virtualButton_5);
		final VirtualButton virtualButton_6 = new VirtualButton("7");
		virtualButton_6.setTextByShift("&");
		virtualButton_6.addActionListener(a);
		this.su.addVirtualButton(virtualButton_6);
		final VirtualButton virtualButton_7 = new VirtualButton("8");
		virtualButton_7.setTextByShift("*");
		virtualButton_7.addActionListener(a);
		this.su.addVirtualButton(virtualButton_7);
		final VirtualButton virtualButton_8 = new VirtualButton("9");
		virtualButton_8.setTextByShift("(");
		virtualButton_8.addActionListener(a);
		this.su.addVirtualButton(virtualButton_8);
		final VirtualButton virtualButton_9 = new VirtualButton("0");
		virtualButton_9.setTextByShift(")");
		virtualButton_9.addActionListener(a);
		this.su.addVirtualButton(virtualButton_9);
		final VirtualButton virtualButton_10 = new VirtualButton("-");
		virtualButton_10.setTextByShift("_");
		virtualButton_10.addActionListener(a);
		this.su.addVirtualButton(virtualButton_10);
		final VirtualButton virtualButton_11 = new VirtualButton("=");
		virtualButton_11.setTextByShift("+");
		virtualButton_11.addActionListener(a);
		this.su.addVirtualButton(virtualButton_11);
		final VirtualButton virtualButton_12 = new VirtualButton("\\");
		virtualButton_12.setTextByShift("|");
		virtualButton_12.addActionListener(a);
		this.su.addVirtualButton(virtualButton_12);
		final VirtualButton virtualButton_13 = new VirtualButton("");
		virtualButton_13.setIcon((Icon) new ImageIcon(VirtualKeyboard.class.getResource("/img/backspace.gif")));
		virtualButton_13.addActionListener(e -> {
			this.r.keyPress(8);
			this.r.keyRelease(8);
			return;
		});
		final VirtualButton vrtlbtnTab = new VirtualButton("Tab");
		vrtlbtnTab.addActionListener(tab);
		final VirtualButton vrtlbtnQ = new VirtualButton("q");
		vrtlbtnQ.setTextByShift("Q");
		vrtlbtnQ.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnQ);
		final VirtualButton vrtlbtnW = new VirtualButton("w");
		vrtlbtnW.setTextByShift("W");
		vrtlbtnW.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnW);
		final VirtualButton vrtlbtnE = new VirtualButton("e");
		vrtlbtnE.setTextByShift("E");
		vrtlbtnE.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnE);
		final VirtualButton vrtlbtnR = new VirtualButton("r");
		vrtlbtnR.setTextByShift("R");
		vrtlbtnR.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnR);
		final VirtualButton vrtlbtnT = new VirtualButton("t");
		vrtlbtnT.setTextByShift("T");
		vrtlbtnT.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnT);
		final VirtualButton vrtlbtnY = new VirtualButton("y");
		vrtlbtnY.setTextByShift("Y");
		vrtlbtnY.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnY);
		final VirtualButton vrtlbtnU = new VirtualButton("u");
		vrtlbtnU.setTextByShift("U");
		vrtlbtnU.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnU);
		final VirtualButton vrtlbtnI = new VirtualButton("i");
		vrtlbtnI.setTextByShift("I");
		vrtlbtnI.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnI);
		final VirtualButton vrtlbtnO = new VirtualButton("o");
		vrtlbtnO.setTextByShift("O");
		vrtlbtnO.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnO);
		final VirtualButton vrtlbtnP = new VirtualButton("p");
		vrtlbtnP.setTextByShift("P");
		vrtlbtnP.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnP);
		final VirtualButton virtualButton_14 = new VirtualButton("[");
		virtualButton_14.setTextByShift("{");
		virtualButton_14.addActionListener(a);
		this.su.addVirtualButton(virtualButton_14);
		final VirtualButton virtualButton_15 = new VirtualButton("]");
		virtualButton_15.setTextByShift("}");
		virtualButton_15.addActionListener(a);
		this.su.addVirtualButton(virtualButton_15);
		final VirtualButton vrtlbtnX = new VirtualButton("");
		vrtlbtnX.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				VirtualKeyboard.this.r.keyPress(525);
				VirtualKeyboard.this.r.keyRelease(525);
			}
		});
		vrtlbtnX.setIcon((Icon) new ImageIcon(VirtualKeyboard.class.getResource("/img/contextMenuCursor.png")));
		(this.virtualButton_14 = new JToggleButton("Caps")).setSelected(this.cap);
		this.virtualButton_14.addActionListener(caps);
		final VirtualButton vrtlbtnA = new VirtualButton("a");
		vrtlbtnA.setTextByShift("A");
		vrtlbtnA.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnA);
		final VirtualButton vrtlbtnS = new VirtualButton("s");
		vrtlbtnS.setTextByShift("S");
		vrtlbtnS.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnS);
		final VirtualButton vrtlbtnD = new VirtualButton("d");
		vrtlbtnD.setTextByShift("D");
		vrtlbtnD.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnD);
		final VirtualButton vrtlbtnF = new VirtualButton("f");
		vrtlbtnF.setTextByShift("F");
		vrtlbtnF.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnF);
		final VirtualButton vrtlbtnF_1 = new VirtualButton("g");
		vrtlbtnF_1.setTextByShift("G");
		vrtlbtnF_1.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnF_1);
		final VirtualButton vrtlbtnH = new VirtualButton("h");
		vrtlbtnH.setTextByShift("H");
		vrtlbtnH.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnH);
		final VirtualButton vrtlbtnJ = new VirtualButton("j");
		vrtlbtnJ.setTextByShift("J");
		vrtlbtnJ.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnJ);
		final VirtualButton vrtlbtnK = new VirtualButton("k");
		vrtlbtnK.setTextByShift("K");
		vrtlbtnK.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnK);
		final VirtualButton vrtlbtnL = new VirtualButton("l");
		vrtlbtnL.setTextByShift("L");
		vrtlbtnL.addActionListener(a);
		this.su.addVirtualButton(vrtlbtnL);
		final VirtualButton virtualButton_16 = new VirtualButton(";");
		virtualButton_16.setTextByShift(":");
		virtualButton_16.addActionListener(a);
		this.su.addVirtualButton(virtualButton_16);
		final VirtualButton virtualButton_17 = new VirtualButton("'");
		virtualButton_17.setTextByShift("@");
		virtualButton_17.addActionListener(a);
		this.su.addVirtualButton(virtualButton_17);
		final VirtualButton vrtlbtnEnter = new VirtualButton("");
		vrtlbtnEnter.setText("Enter");
		vrtlbtnEnter.setIcon((Icon) new ImageIcon(VirtualKeyboard.class.getResource("/img/enter.gif")));
		vrtlbtnEnter.addActionListener(e -> {
			this.r.keyPress(10);
			this.r.keyRelease(10);
			return;
		});
		(this.virtualButton_30 = new JToggleButton("Shift"))
				.setIcon(new ImageIcon(VirtualKeyboard.class.getResource("/img/shift.gif")));
		this.virtualButton_30.addActionListener(shft);
		final VirtualButton virtualButton_19 = new VirtualButton("z");
		virtualButton_19.setTextByShift("Z");
		virtualButton_19.addActionListener(a);
		this.su.addVirtualButton(virtualButton_19);
		final VirtualButton virtualButton_20 = new VirtualButton("x");
		virtualButton_20.setTextByShift("X");
		virtualButton_20.addActionListener(a);
		this.su.addVirtualButton(virtualButton_20);
		final VirtualButton virtualButton_21 = new VirtualButton("c");
		virtualButton_21.setTextByShift("C");
		virtualButton_21.addActionListener(a);
		this.su.addVirtualButton(virtualButton_21);
		final VirtualButton virtualButton_22 = new VirtualButton("v");
		virtualButton_22.setTextByShift("V");
		virtualButton_22.addActionListener(a);
		this.su.addVirtualButton(virtualButton_22);
		final VirtualButton virtualButton_23 = new VirtualButton("b");
		virtualButton_23.setTextByShift("B");
		virtualButton_23.addActionListener(a);
		this.su.addVirtualButton(virtualButton_23);
		final VirtualButton virtualButton_24 = new VirtualButton("n");
		virtualButton_24.setTextByShift("N");
		virtualButton_24.addActionListener(a);
		this.su.addVirtualButton(virtualButton_24);
		final VirtualButton virtualButton_25 = new VirtualButton("m");
		virtualButton_25.setTextByShift("M");
		virtualButton_25.addActionListener(a);
		this.su.addVirtualButton(virtualButton_25);
		final VirtualButton virtualButton_26 = new VirtualButton(",");
		virtualButton_26.setTextByShift("<");
		virtualButton_26.addActionListener(a);
		this.su.addVirtualButton(virtualButton_26);
		final VirtualButton virtualButton_27 = new VirtualButton(".");
		virtualButton_27.setTextByShift(">");
		virtualButton_27.addActionListener(a);
		this.su.addVirtualButton(virtualButton_27);
		(this.virtualButton_40 = new JToggleButton("Shift"))
				.setIcon(new ImageIcon(VirtualKeyboard.class.getResource("/img/shift.gif")));
		this.virtualButton_40.addActionListener(shft);
		(this.virtualButton_15 = new JToggleButton("Ctrl")).addActionListener(ctrl);
		final VirtualButton virtualButton_28 = new VirtualButton("");
		virtualButton_28.setIcon((Icon) new ImageIcon(VirtualKeyboard.class.getResource("/img/windows8.png")));
		virtualButton_28.addActionListener(win);
		(this.virtualButton_17 = new JToggleButton("Alt")).addActionListener(alt);
		(this.virtualButton_18 = new JToggleButton("Ctrl")).addActionListener(ctrl);
		final VirtualButton virtualButton_29 = new VirtualButton("");
		virtualButton_29.addActionListener(win);
		virtualButton_29.setIcon((Icon) new ImageIcon(VirtualKeyboard.class.getResource("/img/windows8.png")));
		(this.virtualButton_20 = new JToggleButton("Alt")).addActionListener(alt);
		final VirtualButton virtualButton_30 = new VirtualButton(" ");
		virtualButton_30.addActionListener(a);
		final VirtualButton virtualButton_31 = new VirtualButton("");
		virtualButton_31.setIcon((Icon) new ImageIcon(VirtualKeyboard.class.getResource("/img/left.png")));
		virtualButton_31.addActionListener(e -> {
			this.r.keyPress(37);
			this.r.keyRelease(37);
			return;
		});
		final VirtualButton virtualButton_32 = new VirtualButton("");
		virtualButton_32.setIcon((Icon) new ImageIcon(VirtualKeyboard.class.getResource("/img/down.png")));
		virtualButton_32.addActionListener(e -> {
			this.r.keyPress(40);
			this.r.keyRelease(40);
			return;
		});
		final VirtualButton virtualButton_33 = new VirtualButton("");
		virtualButton_33.setIcon((Icon) new ImageIcon(VirtualKeyboard.class.getResource("/img/right.png")));
		virtualButton_33.addActionListener(e -> {
			this.r.keyPress(39);
			this.r.keyRelease(39);
			return;
		});
		final VirtualButton virtualButton_34 = new VirtualButton("");
		virtualButton_34.setIcon((Icon) new ImageIcon(VirtualKeyboard.class.getResource("/img/up.png")));
		virtualButton_34.addActionListener(e -> {
			this.r.keyPress(38);
			this.r.keyRelease(38);
			return;
		});
		final VirtualButton virtualButton_35 = new VirtualButton("Insert");
		virtualButton_35.addActionListener(e -> {
			this.r.keyPress(155);
			this.r.keyRelease(155);
			return;
		});
		final VirtualButton virtualButton_36 = new VirtualButton("Del");
		virtualButton_36.addActionListener(e -> {
			this.r.keyPress(127);
			this.r.keyRelease(127);
			return;
		});
		final VirtualButton virtualButton_37 = new VirtualButton("End");
		virtualButton_37.addActionListener(e -> {
			this.r.keyPress(35);
			this.r.keyRelease(35);
			return;
		});
		final VirtualButton virtualButton_38 = new VirtualButton("Home");
		virtualButton_38.addActionListener(e -> {
			this.r.keyPress(36);
			this.r.keyRelease(36);
			return;
		});
		final VirtualButton virtualButton_39 = new VirtualButton("<html>Page<br/> Up </html>");
		virtualButton_39.addActionListener(e -> {
			this.r.keyPress(33);
			this.r.keyRelease(33);
			return;
		});
		final VirtualButton virtualButton_40 = new VirtualButton("<html>Page<br/> Down </html>");
		virtualButton_40.addActionListener(e -> {
			this.r.keyPress(34);
			this.r.keyRelease(34);
			return;
		});
		final VirtualButton virtualButton_41 = new VirtualButton("/");
		virtualButton_41.setTextByShift("?");
		virtualButton_41.addActionListener(a);
		this.su.addVirtualButton(virtualButton_41);
		final GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(this.virtualButton_30, -2, 118, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_19, -2, 42, -2).addGap(6)
										.addComponent((Component) virtualButton_20, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_21, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_22, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_23, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_24, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_25, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_26, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_27, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_41, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.virtualButton_40, -1, 128, 32767))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(this.virtualButton_14, -2, 94, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnA, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnS, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnD, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnF, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnF_1, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnH, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnJ, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnK, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnL, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_16, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_17, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnEnter, -1, 106, 32767))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(this.virtualButton_15, -2, 50, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_28, -2, 50, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.virtualButton_17, -2, 50, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_30, -2, 392, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.virtualButton_20, -2, 50, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_29, -2, 50, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.virtualButton_18, -2, 50, -2))
								.addGroup(gl_contentPane
										.createSequentialGroup().addComponent((Component) vrtlbtnTab, -2, 76, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(
												(Component) vrtlbtnQ, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnW, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnE, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnR, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnT, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnY, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnU, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnI, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnO, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnP, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_14, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_15, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) vrtlbtnX, -1, 77, 32767))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent((Component) btnNewButton, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_1, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_2, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_3, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_4, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_5, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_6, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_7, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_8, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_9, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_10, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_11, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_12, -2, 42, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_13, -1, 64, 32767)))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING)
												.addComponent((Component) virtualButton_36, -2, 50, -2)
												.addComponent((Component) virtualButton_35, -2, 50, -2))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING)
												.addComponent((Component) virtualButton_38, -2, 50, -2)
												.addComponent((Component) virtualButton_37, -2, 50, -2))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
										.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent((Component) virtualButton_40, -2, 50, -2).addComponent(
														(Component) virtualButton_39, -2, 50, -2)))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent((Component) virtualButton_31, -2, 50, -2).addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent((Component) virtualButton_34, -2, 50, -2)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent((Component) virtualButton_32, -2, 50, -2)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addComponent((Component) virtualButton_33, -2, 50, -2)))))
						.addGap(29)));
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup().addContainerGap().addGroup(gl_contentPane
								.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent((Component) virtualButton_35, -2, 38, -2)
												.addComponent((Component) virtualButton_38, -2, 38, -2)
												.addComponent((Component) virtualButton_39, -2, 38,
														-2))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent((Component) virtualButton_40, -2, 38, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
										.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent((Component) virtualButton_34, -2, 38, -2)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(gl_contentPane
																.createParallelGroup(GroupLayout.Alignment.LEADING)
																.addComponent((Component) virtualButton_32, -2, 38, -2)
																.addComponent((Component) virtualButton_31, -2, 38,
																		-2)))
												.addComponent((Component) virtualButton_33, -2, 38, -2)))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent((Component) virtualButton_12, -2, 38, -2)
												.addComponent((Component) virtualButton_11, -2, 38, -2)
												.addComponent((Component) virtualButton_10, -2, 38, -2)
												.addComponent((Component) virtualButton_9, -2, 38, -2)
												.addComponent((Component) virtualButton_8, -2, 38, -2)
												.addComponent((Component) virtualButton_7, -2, 38, -2)
												.addComponent((Component) virtualButton_6, -2, 38, -2)
												.addComponent((Component) virtualButton_5, -2, 38, -2)
												.addComponent((Component) virtualButton_4, -2, 38, -2)
												.addComponent((Component) virtualButton_3, -2, 38, -2)
												.addComponent((Component) virtualButton_2, -2, 38, -2)
												.addComponent((Component) virtualButton_1, -2, 38, -2)
												.addComponent((Component) virtualButton, -2, 38, -2)
												.addComponent((Component) btnNewButton, -2, 38, -2)
												.addComponent((Component) virtualButton_13, -2, 38, -2))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent((Component) vrtlbtnQ, -2, 38, -2)
												.addComponent((Component) vrtlbtnW, -2, 38, -2)
												.addComponent((Component) vrtlbtnE, -2, 38, -2)
												.addGroup(gl_contentPane
														.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent((Component) vrtlbtnR, -2, 38, -2)
														.addComponent((Component) vrtlbtnT, -2, 38, -2))
												.addComponent((Component) vrtlbtnY, -2, 38, -2)
												.addComponent((Component) vrtlbtnU, -2, 38, -2)
												.addComponent((Component) vrtlbtnI, -2, 38, -2)
												.addComponent((Component) vrtlbtnO, -2, 38, -2)
												.addComponent((Component) vrtlbtnP, -2, 38, -2)
												.addComponent((Component) virtualButton_14, -2, 38, -2)
												.addComponent((Component) virtualButton_15, -2, 38, -2)
												.addComponent((Component) vrtlbtnTab, -2, 38, -2)
												.addGroup(gl_contentPane
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent((Component) vrtlbtnX, -2, 38, -2)
														.addComponent((Component) virtualButton_36, -2, 38, -2)
														.addComponent((Component) virtualButton_37, -2, 38, -2)))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(this.virtualButton_14, -2, 38, -2)
												.addComponent((Component) vrtlbtnA, -2, 38, -2)
												.addComponent((Component) vrtlbtnS, -2, 38, -2)
												.addComponent((Component) vrtlbtnD, -2, 38, -2)
												.addComponent((Component) vrtlbtnF, -2, 38, -2)
												.addComponent((Component) vrtlbtnF_1, -2, 38, -2)
												.addComponent((Component) vrtlbtnH, -2, 38, -2)
												.addComponent((Component) vrtlbtnJ, -2, 38, -2)
												.addComponent((Component) vrtlbtnK, -2, 38, -2)
												.addComponent((Component) vrtlbtnL, -2, 38, -2)
												.addComponent((Component) virtualButton_16, -2, 38, -2)
												.addComponent((Component) virtualButton_17, -2, 38, -2)
												.addComponent((Component) vrtlbtnEnter, -2, 38, -2))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(this.virtualButton_40, -2, 38, -2)
												.addGroup(gl_contentPane
														.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent((Component) virtualButton_19, -2, 38, -2)
														.addComponent((Component) virtualButton_20, -2, 38, -2)
														.addComponent((Component) virtualButton_21, -2, 38, -2)
														.addComponent((Component) virtualButton_22, -2, 38, -2)
														.addComponent((Component) virtualButton_23, -2, 38, -2)
														.addComponent((Component) virtualButton_24, -2, 38, -2)
														.addComponent((Component) virtualButton_25, -2, 38, -2)
														.addComponent((Component) virtualButton_26, -2, 38, -2)
														.addComponent((Component) virtualButton_27, -2, 38, -2)
														.addComponent((Component) virtualButton_41, -2, 38, -2))
												.addComponent(this.virtualButton_30, -2, 38, -2))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(this.virtualButton_15, -2, 38, -2)
												.addComponent((Component) virtualButton_28, -2, 38, -2)
												.addComponent(this.virtualButton_17, -2, 38, -2)
												.addGroup(gl_contentPane
														.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent((Component) virtualButton_30, -2, 38, -2)
														.addComponent(this.virtualButton_20, -2, 38, -2))
												.addComponent(this.virtualButton_18, -2, 38, -2)
												.addComponent((Component) virtualButton_29, -2, 38, -2))))
						.addContainerGap()));
		this.contentPane.setLayout(gl_contentPane);
		this.contentPane.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(final KeyEvent e) {
				System.out.println("e");
			}

			@Override
			public void keyReleased(final KeyEvent e) {
			}

			@Override
			public void keyPressed(final KeyEvent e) {
			}
		});
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
		this.setLocationRelativeTo(null);
	}

	private void setLAF(final LookAndFeel laf) {
		try {
			UIManager.setLookAndFeel(laf);
			SwingUtilities.updateComponentTreeUI(this);
		} catch (UnsupportedLookAndFeelException ex) {
		}
	}

	private int getKeyCode(final char c) {
		return KeyEvent.getExtendedKeyCodeForChar(c);
	}
}
