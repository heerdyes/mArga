package com.fractalautomatawaveband.marga.wmg;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Wmg extends JFrame {
  public Wmg() {
    initUI();
  }

  private void initUI() {
    Surface s = new Surface();
    add(s);

    setSize(900, 600);
    setLocationRelativeTo(null);

    this.addKeyListener(s);
    this.addMouseListener(s);
    this.addMouseMotionListener(s);
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
            Wmg ex = new Wmg();
            ex.setUndecorated(true);
            ex.setVisible(true);
          }
        });
  }
}

