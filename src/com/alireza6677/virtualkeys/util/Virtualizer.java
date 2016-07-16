package com.alireza6677.virtualkeys.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import com.alireza6677.virtualkeys.ui.ShiftUpdater;

public class Virtualizer {

	// Singleton //
	private static Virtualizer INSTANCE;
	static {
		INSTANCE = new Virtualizer();
	}

	public static synchronized Virtualizer getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Virtualizer();
		return INSTANCE;
	}
	// -#-#-#-#-#- //

	public static interface lockingStateChangeListener {
		public void stateChanged(ButtonStates st, boolean newState);
	}

	private lockingStateChangeListener capsChangeListener;
	private lockingStateChangeListener numlChangeListener;

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
	private ShiftUpdater shu;

	private Virtualizer() {
		try {
			mainRobot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private Virtualizer(lockingStateChangeListener cap, lockingStateChangeListener num) {
		this();
		capsChangeListener = cap;
		numlChangeListener = num;

		caps = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
		capsState = caps ? ButtonStates.ON : ButtonStates.OFF;
		if (capsChangeListener != null)
			capsChangeListener.stateChanged(capsState, caps);
		numl = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK);
		numlState = numl ? ButtonStates.ON : ButtonStates.OFF;
		if (numlChangeListener != null)
			capsChangeListener.stateChanged(numlState, numl);
	}

	public ButtonStates getCapsState() {
		return capsState;
	}

	public ButtonStates getNumlState() {
		return numlState;
	}

	public void setOnCapsChangeListener(lockingStateChangeListener lscl) {
		this.capsChangeListener = lscl;
	}

	public void setOnNumlChangeListener(lockingStateChangeListener lscl) {
		this.numlChangeListener = lscl;
	}

	public lockingStateChangeListener getCapsChangeListener() {
		return capsChangeListener;
	}

	public lockingStateChangeListener getNumlChangeListener() {
		return numlChangeListener;
	}

	public void pressButton(int keyCode) {
		mainRobot.keyPress(keyCode);
		mainRobot.keyRelease(keyCode);
	}

	private boolean pressTriState(boolean mode , ButtonStates state , int keyCode ,   lockingStateChangeListener l){
		if (mode && state == ButtonStates.ON) {
			state = ButtonStates.LOCKED;
		} else {
			mainRobot.keyPress(keyCode);
			if(keyCode == KeyEvent.VK_CAPS_LOCK){
				mainRobot.keyRelease(keyCode);
				mode = Toolkit.getDefaultToolkit().getLockingKeyState(keyCode);
			}
			else mode = true;
			
			state = mode ? ButtonStates.ON : ButtonStates.OFF;
			if (l != null)
				l.stateChanged(state, mode);
		}
		return mode;
	}
	
	public boolean pressAlt() {
		return pressTriState(alt, altState, KeyEvent.VK_ALT, null);

	}
	
	public boolean pressShift() {
		return pressTriState(shift,shiftState, KeyEvent.VK_SHIFT, null);
	}
	
	public boolean pressCtrl() {
		return pressTriState(ctrl, ctrlState, KeyEvent.VK_CONTROL, null);
	}

	
	public boolean pressNumLock() {
			pressButton(KeyEvent.VK_NUM_LOCK);
			numl = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK);
			numlState = numl ? ButtonStates.ON : ButtonStates.OFF;
			if (numlChangeListener != null)
				numlChangeListener.stateChanged(numlState, numl);
		return caps;
	}



public void registerButton(JButton btn){
	btn.addActionListener(a -> {
		pressButton(btn.getText().charAt(0));
	});
}
	

}
