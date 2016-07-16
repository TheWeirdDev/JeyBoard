package com.alireza6677.virtualkeys.util;
import javax.swing.*;
import java.awt.event.*;

class RightJMenuBar {
  public static void main(String args[]) {
      new RightJMenuBar().doit();
  }

  public void doit() {
    JFrame frame = new JFrame("Real's HowTo");
    JMenuBar menuBar = new JMenuBar();

    // Create a menu
    JMenu menu = new JMenu("Menu Label");
    JMenuItem item = new JMenuItem("item");
    menu.add(item);

    menuBar.add(menu);

    // shift to the right
    menuBar.add(Box.createGlue());

    // this button will be shifted right on the menubar
    Action actionQuit = new AbstractAction("Quit") {
       public void actionPerformed(ActionEvent evt) {
         System.exit(0);
       }
    };
    menuBar.add(new JButton(actionQuit));

    frame.setJMenuBar(menuBar);
    frame.setSize(300,300);
    frame.setVisible(true);

  }
}