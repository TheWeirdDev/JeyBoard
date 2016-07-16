package img;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;

public class RadioButtonMenuSample {
  public static void main(String args[]) {
    JFrame f = new JFrame("JRadioButtonMenuItem Sample");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JMenuBar bar = new JMenuBar();
    JMenu menu = new JMenu("Options");
    menu.setMnemonic(KeyEvent.VK_O);

    ButtonGroup group = new ButtonGroup();

    JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem("North");
    group.add(menuItem);
    menu.add(menuItem);

    menuItem = new JRadioButtonMenuItem("East");
    group.add(menuItem);
    menu.add(menuItem);

    menuItem = new JRadioButtonMenuItem("West");
    group.add(menuItem);
    menu.add(menuItem);

    menuItem = new JRadioButtonMenuItem("South");
    group.add(menuItem);
    menu.add(menuItem);

    menuItem = new JRadioButtonMenuItem("Center");
    group.add(menuItem);
    menu.add(menuItem);

    bar.add(menu);
    f.setJMenuBar(bar);
    f.setSize(300, 200);
    f.setVisible(true);
  }
}
