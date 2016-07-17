// 
// Decompiled by Procyon v0.5.29
// 

package com.alireza6677.jeyboard.ui;

import javax.swing.JButton;

public class VirtualButton extends JButton
{
    private String shift;
    private String txt;
    
    public VirtualButton(final String text, final String sh) {
        super(text);
        //this.setFocusable(true);
        this.shift = sh;
        this.txt = text;
    }
    
    public VirtualButton(final String text) {
        super(text);
       // this.setFocusable(true);
        this.shift = "";
        this.txt = text;
        setTextByShift(text.toUpperCase());
    }
    
    public String getTextByShift() {
        return this.shift;
    }
    
    public void setTextByShift(final String s) {
        this.shift = s;
    }
    
    public String getNormalText() {
        return this.txt;
    }
    
    public void setNormalText(final String a) {
        this.txt = a;
    }
    
    public boolean hasShift(){
    	return !shift.isEmpty() && shift != null;
    }
}
