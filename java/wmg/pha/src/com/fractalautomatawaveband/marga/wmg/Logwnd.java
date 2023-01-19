package com.fractalautomatawaveband.marga.wmg;

import java.awt.*;
import java.util.*;
import static com.fractalautomatawaveband.marga.wmg.L.d;

class Logwnd implements Wnd
{
  int x,y,w,h;
  Color bg,fg,hg,ng;
  ArrayList<String> lines;
  int mrgnleft,mrgntop;
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
  
  Logwnd(int xx,int yy,int ww,int hh,int fsz)
  {
    x=xx;
    y=yy;
    w=ww;
    h=hh;
    bg=new Color(0,0,0,255);
    fg=new Color(0,255,255,255);
    hg=new Color(0,255,255,255);
    ng=new Color(0,64,64,255);
    lines=new ArrayList<>();
    fnt=new Font("Larabiefont Rg", Font.PLAIN, fsz);
    mrgnleft=5;
    mrgntop=15;
    rowht=12;
    lnlim=hh/rowht-1;
  }
  
  public void onkeydown(char c,int kc) {}
  
  public void onkeyup(char c,int kc) {}
  
  public void onclick(int mx,int my) {}
  
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
    g2.setColor(infocus?hg:ng);
    g2.drawRect(x-1,y-1,w+1,h+1);
    g2.setPaint(bg);
    g2.fillRect(x,y,w,h);
    g2.setColor(fg);
    g2.setFont(fnt);
    renderlines(g2);
  }
  
  public boolean containsPoint(int xx,int yy)
  {
    return x<xx && y<yy && xx<(x+w) && yy<(y+h);
  }
}

