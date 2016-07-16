// 
// Decompiled by Procyon v0.5.29
// 

package com.alireza6677.virtualkeys.ui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class ShiftUpdater
{
    private ArrayList<VirtualButton> btnList;
    
    public ShiftUpdater() {
        this.btnList = new ArrayList<VirtualButton>();
    }
    
    public void addVirtualButton(final VirtualButton btn) {
        this.btnList.add(btn);
    }
    
    public void removeButton(final VirtualButton btn) {
        this.btnList.remove(btn);
    }
    
    public void shiftPressed() {
        this.btnList.stream().forEach(vbtn -> vbtn.setText(vbtn.getTextByShift()));
    }
    
    public void shiftReleased() {
        this.btnList.stream().forEach(vbtn -> vbtn.setText(vbtn.getNormalText()));
    }
}
