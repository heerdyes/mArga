package com.fractalautomatawaveband.marga.wmg;

import java.awt.*;
import java.util.*;
import static com.fractalautomatawaveband.marga.wmg.L.d;

class LogWnd extends RawWnd
{
  ArrayList<String> lines;
  Font fnt;
  int rowht;
  int lnlim;
  
  public int getX() { return x; }
  public int getY() { return y; }
  public int getW() { return w; }
  public int getH() { return h; }
  public void setXY(int xx,int yy)
  {
    x=xx;
    y=yy;
  }
  
  LogWnd(int xx,int yy,int ww,int hh,int fsz)
  {
    super(xx,yy,ww,hh);
    lines=new ArrayList<>();
    fnt=new Font("Larabiefont Rg", Font.PLAIN, fsz);
    rowht=fsz;
    lnlim=hh/rowht-1;
  }
  
  void renderlines(Graphics2D g2)
  {
    int ir=0;
    for(String s:lines)
    {
      g2.drawString(s,x+mrgnleft,y+mrgntop+ir*rowht);
      ir++;
    }
  }
  
  void addln(String s)
  {
    if(lines.size()>=lnlim)
    {
      lines.remove(0);
    }
    lines.add(s);
  }
  
  public void rndr(Graphics2D g2,boolean infocus)
  {
    super.rndr(g2,infocus);
    g2.setColor(fg);
    g2.setFont(fnt);
    renderlines(g2);
  }
}

