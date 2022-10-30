import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class edg extends JFrame {
  public edg() {
    initUI();
  }

  private void initUI() {
    Surface s = new Surface();
    add(s);

    setSize(900, 600);
    setLocationRelativeTo(null);

    this.addKeyListener(s);
    this.addMouseListener(s);
  }

  static void chkfnt() {
    String[] fntnms=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    for(String s:fntnms) {
      L.d(s);
    }
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            edg ex = new edg();
            ex.setUndecorated(true);
            // ex.setOpacity(0.15f);
            ex.setVisible(true);
          }
        });
  }
}

