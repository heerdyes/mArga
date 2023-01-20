package com.fractalautomatawaveband.marga.wmg.wnd;

import java.awt.*;
import static com.fractalautomatawaveband.marga.wmg.util.L.d;

public class ShWnd extends RawWnd
{
  StringBuffer sb;
  Font fnt;
  
  public int getX() { return x; }
  public int getY() { return y; }
  public int getW() { return w; }
  public int getH() { return h; }
  public void setXY(int xx,int yy)
  {
    x=xx;
    y=yy;
  }
  
  public ShWnd(String id,int xx,int yy,int ww,int hh)
  {
    super(id,xx,yy,ww,hh);
    sb=new StringBuffer();
    fnt=new Font("Larabiefont Rg", Font.PLAIN, 14);
  }
  
  public void cmdproc(String s)
  {
    if(s.equals("time"))
    {
      sb.append(""+System.currentTimeMillis());
    }
  }
  
  public void onkeydown(char c,int kc)
  {
    if(kc==10)
    {
      String cmd=sb.toString();
      sb.setLength(0);
      cmdproc(cmd);
    }
    else
    {
      sb.append(c);
    }
  }
  
  public void rndr(Graphics2D g2,boolean infocus)
  {
    super.rndr(g2,infocus);
    g2.setColor(fg);
    g2.setFont(fnt);
    g2.drawString(sb.toString(),x+mrgnleft,y+mrgntop);
  }
}

