package com.fractalautomatawaveband.marga.edg;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class Edg extends Frame implements KeyListener
{
  int w = 600, h = 400;
  Cnv x;

  public Edg()
  {
    x = new Cnv(w, h);
    add(x);
    addKeyListener(this);
    setLocation(300, 200);
    setSize(w, h);
    setUndecorated(true);
    setLayout(null);
    setVisible(true);
  }

  @Override
  public void keyTyped(KeyEvent e)
  {
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    int kc = e.getKeyCode();
    if (kc == 27)
    {
      System.exit(0);
    }
    x.keydown(kc, e.getKeyChar());
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
  }
}
