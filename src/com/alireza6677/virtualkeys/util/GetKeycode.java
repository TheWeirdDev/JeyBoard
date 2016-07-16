package com.alireza6677.virtualkeys.util;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class GetKeycode implements KeyListener{

    private JFrame f;
    private JLabel feld;

    public GetKeycode(){
        f = new JFrame("GetKeycode");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        feld = new JLabel();
        f.add(feld);
        f.pack();
        f.setVisible(true);
        System.out.println(KeyEvent.VK_0);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        feld.setText(e.getKeyCode()+"");        
    }

    public static void main(String[] args) {
        new GetKeycode();
    }

    // Unused:
    @Override public void keyPressed(KeyEvent e) {}

    @Override public void keyTyped(KeyEvent arg0) {}

}