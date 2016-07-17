package com.alireza6677.jeyboard.util;

import java.util.prefs.Preferences;

import javax.swing.LookAndFeel;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

public class Prefs {
	
	private static final String OPACITY_KEY = "WINDOW_OPACITY";
	private static final String THEME_KEY = "APP_THEME";
	private static final String EXPANDED_KEY = "WINDOW_EXPANDED";
	
	private Preferences p;
	
	public Prefs(){
		p = Preferences.userRoot();
	}
	
	public synchronized void saveOpacity(float o){
		p.putFloat(OPACITY_KEY, o);
	}
	
	public float loadOpacity(){
		return p.getFloat(OPACITY_KEY, 1f);
	}
	
	public String getThemeName(){
		return p.get(THEME_KEY, "Graphite");
	}
	
	public synchronized void saveTheme(String t){
		p.put(THEME_KEY, t);
	}
	
	public LookAndFeel laodTheme(){
		String s = p.get(THEME_KEY, "Graphite");
		try {
			Class cls = Class.forName("com.jtattoo.plaf."+s.toLowerCase()+"."+s+"LookAndFeel");
			return (LookAndFeel)cls.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return new GraphiteLookAndFeel();
	}
	
	public boolean getExpandedState(){
		return p.getBoolean(EXPANDED_KEY, false);
	}
	
	public void saveExpandedState(boolean state){
		p.putBoolean(EXPANDED_KEY, state);
	}
	
}
