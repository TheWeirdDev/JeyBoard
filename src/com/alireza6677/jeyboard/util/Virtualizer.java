package com.alireza6677.jeyboard.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JButton;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.alireza6677.jeyboard.ui.ShiftUpdater;
import com.alireza6677.jeyboard.ui.VirtualButton;

public class Virtualizer implements NativeKeyListener {

	// Singleton //
	private static Virtualizer INSTANCE;

	public static synchronized Virtualizer getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Virtualizer();
		return INSTANCE;
	}

	public static synchronized Virtualizer getInstance(lockingStateChangeListener cap, lockingStateChangeListener num) {
		if (INSTANCE == null)
			INSTANCE = new Virtualizer(cap, num);
		return INSTANCE;
	}
	// -#-#-#-#-#- //

	public static interface lockingStateChangeListener {
		public void stateChanged(ButtonStates st, boolean newState);
	}

	private lockingStateChangeListener capsChangeListener;
	private lockingStateChangeListener numlChangeListener;
	private lockingStateChangeListener altChangeListener;
	private lockingStateChangeListener ctrlChangeListener;
	private lockingStateChangeListener shiftChangeListener;

	public enum ButtonStates {
		OFF, ON, LOCKED
	}

	private ButtonStates capsState;
	private ButtonStates numlState;
	private ButtonStates shiftState;
	private ButtonStates altState;
	private ButtonStates ctrlState;

	private Robot mainRobot;
	private boolean caps, alt, shift, ctrl, numl;
	// Will be added
	private ShiftUpdater shu;

	private MouseAdapter longPress = new MouseAdapter() {
		private java.util.Timer t;

		public void mousePressed(MouseEvent e) {
			if (t == null) {
				t = new java.util.Timer();
			}
			t.schedule(new TimerTask() {
				public void run() {
					((JButton)e.getSource()).doClick();
				}
			}, 600, 100);
		}

		public void mouseReleased(MouseEvent e) {
			if (t != null) {
				t.cancel();
				t = null;
			}
		}
	};

	private void initVirtualizer() {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		}
		LogManager.getLogManager().reset();
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		GlobalScreen.addNativeKeyListener(this);
	}

	private Virtualizer() {
		try {
			mainRobot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			System.exit(0);
		}

		caps = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
		capsState = caps ? ButtonStates.ON : ButtonStates.OFF;
		numl = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK);
		numlState = numl ? ButtonStates.ON : ButtonStates.OFF;

		alt = shift = ctrl = false;
		altState = shiftState = ctrlState = ButtonStates.OFF;
		initVirtualizer();
	}

	private Virtualizer(lockingStateChangeListener cap, lockingStateChangeListener num) {
		this();
		capsChangeListener = cap;
		numlChangeListener = num;

		System.out.println("aa");
		if (capsChangeListener != null)
			capsChangeListener.stateChanged(capsState, caps);

		if (numlChangeListener != null)
			numlChangeListener.stateChanged(numlState, numl);
	}

	public ButtonStates getCapsState() {
		return capsState;
	}

	public ButtonStates getNumlState() {
		return numlState;
	}

	public void setCapsChangeListener(lockingStateChangeListener lscl) {
		this.capsChangeListener = lscl;
	}

	public void setNumlChangeListener(lockingStateChangeListener lscl) {
		this.numlChangeListener = lscl;
	}

	public lockingStateChangeListener getCapsChangeListener() {
		return capsChangeListener;
	}

	public lockingStateChangeListener getNumlChangeListener() {
		return numlChangeListener;
	}

	public lockingStateChangeListener getAltChangeListener() {
		return altChangeListener;
	}

	public void setAltChangeListener(lockingStateChangeListener altChangeListener) {
		this.altChangeListener = altChangeListener;
	}

	public lockingStateChangeListener getCtrlChangeListener() {
		return ctrlChangeListener;
	}

	public void setCtrlChangeListener(lockingStateChangeListener ctrllChangeListener) {
		this.ctrlChangeListener = ctrllChangeListener;
	}

	public lockingStateChangeListener getShiftChangeListener() {
		return shiftChangeListener;
	}

	public void setShiftChangeListener(lockingStateChangeListener shiftChangeListener) {
		this.shiftChangeListener = shiftChangeListener;
	}

	public void pressButton(int keyCode) {
		mainRobot.keyPress(keyCode);
		mainRobot.keyRelease(keyCode);
	}

	private boolean pressCapsLock() {
		pressButton(KeyEvent.VK_CAPS_LOCK);
		caps = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
		capsState = caps ? ButtonStates.ON : ButtonStates.OFF;
		if (capsChangeListener != null)
			capsChangeListener.stateChanged(capsState, caps);
		return caps;
	}

	private boolean pressAlt() {
		if (alt && altState == ButtonStates.ON) {
			altState = ButtonStates.LOCKED;
		} else if (altState == ButtonStates.LOCKED) {
			mainRobot.keyRelease(KeyEvent.VK_ALT);
			alt = false;
			altState = ButtonStates.OFF;
		} else {
			mainRobot.keyPress(KeyEvent.VK_ALT);
			alt = true;
			altState = alt ? ButtonStates.ON : ButtonStates.OFF;
		}
		if (altChangeListener != null)
			altChangeListener.stateChanged(altState, alt);
		return alt;
	}

	private boolean pressShift() {
		if (shift && shiftState == ButtonStates.ON) {
			shiftState = ButtonStates.LOCKED;
		} else if (shiftState == ButtonStates.LOCKED) {
			mainRobot.keyRelease(KeyEvent.VK_SHIFT);
			shift = false;
			shiftState = ButtonStates.OFF;
		} else {
			mainRobot.keyPress(KeyEvent.VK_SHIFT);
			shift = true;
			shiftState = shift ? ButtonStates.ON : ButtonStates.OFF;
		}
		if (shiftChangeListener != null)
			shiftChangeListener.stateChanged(shiftState, shift);
		return shift;
	}

	private boolean pressCtrl() {
		if (ctrl && ctrlState == ButtonStates.ON) {
			ctrlState = ButtonStates.LOCKED;
		} else if (ctrlState == ButtonStates.LOCKED) {
			mainRobot.keyRelease(KeyEvent.VK_CONTROL);
			ctrl = false;
			ctrlState = ButtonStates.OFF;
		} else {
			mainRobot.keyPress(KeyEvent.VK_CONTROL);
			ctrl = true;
			ctrlState = ctrl ? ButtonStates.ON : ButtonStates.OFF;
		}
		if (ctrlChangeListener != null)
			ctrlChangeListener.stateChanged(ctrlState, ctrl);
		return ctrl;
	}

	private boolean pressNumLock() {
		pressButton(KeyEvent.VK_NUM_LOCK);
		numl = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK);
		numlState = numl ? ButtonStates.ON : ButtonStates.OFF;
		if (numlChangeListener != null)
			numlChangeListener.stateChanged(numlState, numl);
		return caps;
	}

	private void releaseLocks() {
		if (altState == ButtonStates.ON) {
			mainRobot.keyRelease(KeyEvent.VK_ALT);
			alt = false;
			altState = ButtonStates.OFF;
			if (altChangeListener != null)
				altChangeListener.stateChanged(altState, alt);
		}
		if (shiftState == ButtonStates.ON) {
			mainRobot.keyRelease(KeyEvent.VK_SHIFT);
			shift = false;
			shiftState = ButtonStates.OFF;
			if (shiftChangeListener != null)
				shiftChangeListener.stateChanged(shiftState, shift);
		}
		if (ctrlState == ButtonStates.ON) {
			mainRobot.keyRelease(KeyEvent.VK_CONTROL);
			ctrl = false;
			ctrlState = ButtonStates.OFF;
			if (ctrlChangeListener != null)
				ctrlChangeListener.stateChanged(ctrlState, ctrl);
		}
	}

	public void registerButton(JButton btn) {
		btn.addActionListener(a -> {
			pressButton(getKeyCode(btn.getText().charAt(0)));
			releaseLocks();
		});
		btn.addMouseListener(longPress);
	}

	public void registerFnButton(JButton btn) {
		btn.addActionListener(a -> {
			pressButton(KeyEvent.VK_F1 + Integer.parseInt(btn.getText().substring(1)) - 1);
			releaseLocks();
		});
	}
	
	public void register‌BracketButton(JButton btn) {
		btn.addActionListener(a -> {
			pressButton(KeyEvent.VK_OPEN_BRACKET);
			releaseLocks();
		});
	}
	
	public void register‌Bracket1Button(JButton btn) {
		btn.addActionListener(a -> {
			pressButton(KeyEvent.VK_CLOSE_BRACKET);
			releaseLocks();
		});
	}
	public void registerEscButton(VirtualButton btn) {
		btn.addActionListener(a -> {
			pressButton(KeyEvent.VK_ESCAPE);
			releaseLocks();
		});
	}

	public void registerShiftButton(VirtualButton btn) {
		btn.addActionListener(a -> {
			pressShift();
		});
	}

	public void registerAltButton(VirtualButton btn) {
		btn.addActionListener(a -> {
			pressAlt();
		});
	}

	public void registerCtrlButton(VirtualButton btn) {
		btn.addActionListener(a -> {
			pressCtrl();
		});
	}
	
	public void registerCapsButton(JButton b) {
		b.addActionListener(a -> {
			pressCapsLock();
		});
	}

	public void registerContextMenuButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_CONTEXT_MENU);
		});
	}

	public void registerBackSpaceButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_BACK_SPACE);
		});
		b.addMouseListener(longPress);
	}

	public void registerEnterButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_ENTER);
		});
		b.addMouseListener(longPress);
	}

	public void registerPrintScreenButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_PRINTSCREEN);
		});
	}

	public void registerScrollLockButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_SCROLL_LOCK);
		});
	}

	public void registerPausBrButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_PAUSE);
		});
	}

	public void registerInsertButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_INSERT);
		});
	}

	public void registerHomeButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_HOME);
		});
	}

	public void registerPageUpButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_PAGE_UP);
		});
		b.addMouseListener(longPress);
	}

	public void registerPageDownButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_PAGE_DOWN);
		});
		b.addMouseListener(longPress);
	}

	public void registerEndButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_END);
		});
	}

	public void registerUpButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_UP);
		});
		b.addMouseListener(longPress);
	}

	public void registerDownButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_DOWN);
		});
		b.addMouseListener(longPress);
	}

	public void registerRightButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_RIGHT);
		});
		b.addMouseListener(longPress);
	}

	public void registerLeftButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_LEFT);
		});
		b.addMouseListener(longPress);
	}

	public void registerDeleteButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_DELETE);
		});
	}

	public void registerSuperButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_WINDOWS);
		});
	}

	public void registerTabButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_TAB);
		});
		b.addMouseListener(longPress);
	}

	public void registerNumlButton(JButton b) {
		b.addActionListener(a -> {
			pressNumLock();
		});
	}
	
	public void registerNumpadButton(JButton b) {
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_NUMPAD0 + Integer.parseInt(Character.toString(b.getText().charAt(0))));
			releaseLocks();
		});
		b.addMouseListener(longPress);
	}	
	
	public void registerDivisionButton(JButton b){
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_DIVIDE);
			releaseLocks();
		});
		b.addMouseListener(longPress);
	}

	public void registerMultiplyButton(JButton b){
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_MULTIPLY);
			releaseLocks();
		});
		b.addMouseListener(longPress);
	}
	
	public void registerMinusButton(JButton b){
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_MINUS);
			releaseLocks();
		});
		b.addMouseListener(longPress);
	}
	
	public void registerAddButton(JButton b){
		b.addActionListener(a -> {
			pressButton(KeyEvent.VK_ADD);
			releaseLocks();
		});
		b.addMouseListener(longPress);
	}

	private int getKeyCode(final char c) {
		return KeyEvent.getExtendedKeyCodeForChar(c);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		String ss = NativeKeyEvent.getKeyText(e.getKeyCode());
		if (ss.contains("Shift")) {
			shiftState = ButtonStates.ON;
			shift = true;
			if (shiftChangeListener != null)
				shiftChangeListener.stateChanged(shiftState, shift);
		} else if (ss.contains("Control")) {
			ctrlState = ButtonStates.ON;
			ctrl = true;
			if (ctrlChangeListener != null)
				ctrlChangeListener.stateChanged(ctrlState, ctrl);
		} else if (ss.contains("Alt")) {
			altState = ButtonStates.ON;
			alt = true;
			if (altChangeListener != null)
				altChangeListener.stateChanged(altState, alt);
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		String ss = NativeKeyEvent.getKeyText(e.getKeyCode());
		//System.out.println("Key Pressed: " + ss);

		if (ss.equals("Caps Lock")) {
			caps = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
			capsState = caps ? ButtonStates.ON : ButtonStates.OFF;
			if (capsChangeListener != null)
				capsChangeListener.stateChanged(capsState, caps);
		} else if (ss.contains("Shift")) {
			shiftState = ButtonStates.OFF;
			shift = false;
			if (shiftChangeListener != null)
				shiftChangeListener.stateChanged(shiftState, shift);
		} else if (ss.contains("Control")) {
			ctrlState = ButtonStates.OFF;
			ctrl = false;
			if (ctrlChangeListener != null)
				ctrlChangeListener.stateChanged(ctrlState, ctrl);
		} else if (ss.contains("Alt")) {
			altState = ButtonStates.OFF;
			alt = false;
			if (altChangeListener != null)
				altChangeListener.stateChanged(altState, alt);
		} else if (ss.equals("Num Lock")) {
			numl = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK);
			numlState = numl ? ButtonStates.ON : ButtonStates.OFF;
			if (numlChangeListener != null)
				numlChangeListener.stateChanged(numlState, numl);
		}

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {

	}

	public void shutdownVirtualizer() {
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e1) {
			e1.printStackTrace();
		}
		System.runFinalization();
	}
}
