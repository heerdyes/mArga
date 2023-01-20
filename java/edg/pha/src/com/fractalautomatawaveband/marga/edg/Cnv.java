package com.fractalautomatawaveband.marga.edg;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Cnv extends Canvas
{
  Color bg, fg;
  StringBuffer sb;
  Font f;

  public Cnv(int w, int h)
  {
    super();
    setSize(w, h);
    bg = new Color(0, 0, 0, 255);
    fg = new Color(0, 255, 255, 255);
    setBackground(bg);
    sb = new StringBuffer();
    f = new Font("Larabiefont Rg", Font.PLAIN, 14);
  }

  public void paint(Graphics g)
  {
    g.setColor(fg);
    g.setFont(f);
    g.drawString(sb.toString(), 50, 50);
  }

  void keydown(int kc, char c)
  {
    sb.append(c);
    repaint();
  }

}
