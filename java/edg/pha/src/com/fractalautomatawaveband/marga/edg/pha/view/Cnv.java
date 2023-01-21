package com.fractalautomatawaveband.marga.edg.pha.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.fractalautomatawaveband.marga.edg.pha.event.BufObs;
import com.fractalautomatawaveband.marga.edg.pha.model.Buf;
import com.fractalautomatawaveband.marga.edg.pha.model.CList;
import com.fractalautomatawaveband.marga.edg.pha.model.CNode;
import com.fractalautomatawaveband.marga.edg.pha.model.FileBuf;

@SuppressWarnings("serial")
public class Cnv extends Canvas implements BufObs
{
  protected Color bg, fg;
  protected Font f;
  protected FileBuf buf;
  protected int mrgnleft = 7;
  protected int mrgntop = 24;
  protected int linegap = 5;
  protected int lineht = 14;
  protected int colwh = 14;

  public Cnv(int w, int h, FileBuf b)
  {
    super();
    setSize(w, h);
    bg = new Color(0, 0, 0, 255);
    fg = new Color(0, 255, 255, 255);
    setBackground(bg);
    f = new Font("Larabiefont Rg", Font.PLAIN, 14);
    buf = b;
    buf.addObs(this);
    setFocusable(false);
  }

  public void setBuf(FileBuf b)
  {
    buf = b;
  }

  void rndrbuf(CList cl, Graphics g)
  {
    int row = 0, col = 0;
    int cx = mrgnleft + col * colwh;
    int cy = mrgntop + row * (lineht + linegap);
    for (CNode cn = cl.getHead(); cn != null; cn = cn.getNext())
    {
      char c = cn.getSymbol();
      if (c == '\n')
      {
        row++;
        col = 0;
      }
      else
      {
        g.drawString("" + c, cx, cy);
        col++;
      }
      cx = mrgnleft + col * colwh;
      cy = mrgntop + row * (lineht + linegap);
      if (cn == buf.getCursor())
      {
        g.drawLine(cx, cy + 2, cx + 10, cy + 2);
      }
    }
  }

  public void paint(Graphics g)
  {
    g.setColor(fg);
    g.setFont(f);
    CList cl = buf.getData();
    rndrbuf(cl, g);
  }

  @Override
  public void signal(Object src, Object msg)
  {
    char sm = (Character) msg;
    repaint();
  }

}
