package com.fractalautomatawaveband.marga.wmg;

import java.awt.Graphics2D;
import java.awt.Color;

class Shwnd implements Wnd {
  int x,y,w,h;
  Color bg,fg,hg;
  StringBuffer sb;
  
  Shwnd(int xx,int yy,int ww,int hh) {
    x=xx;
    y=yy;
    w=ww;
    h=hh;
    bg=new Color(92,92,92,255);
    fg=new Color(0,255,255,255);
    hg=new Color(240,120,0,255);
    sb=new StringBuffer();
  }
  
  public void sendkey(char c,int kc) {
    sb.append(c);
  }
  
  public void rndr(Graphics2D g2,boolean infocus){
    if(infocus) {
      g2.setColor(hg);
      g2.drawRect(x-1,y-1,w+1,h+1);
    }
    g2.setPaint(bg);
    g2.fillRect(x,y,w,h);
    g2.setColor(fg);
    g2.drawString(sb.toString(),x+5,y+10);
  }
  
  public boolean containsPoint(int xx,int yy){
    return x<xx && y<yy && xx<(x+w) && yy<(y+h);
  }
}

