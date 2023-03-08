package com.fractalautomatawaveband.marga.edg.pha;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.fractalautomatawaveband.marga.edg.pha.model.Buf;
import com.fractalautomatawaveband.marga.edg.pha.model.FileBuf;
import com.fractalautomatawaveband.marga.edg.pha.view.Cnv;

@SuppressWarnings("serial")
public class Edg extends Frame implements KeyListener
{
  int w = 1280, h = 720;
  protected Cnv x;
  protected FileBuf buf;

  public Edg()
  {
    buf = new FileBuf("r");
    buf.loadbuf();
    x = new Cnv(w, h, buf);
    add(x);
    addKeyListener(this);
    setLocation(200, 100);
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
    char c = e.getKeyChar();
    buf.keydown(kc, c);
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
  }
}
