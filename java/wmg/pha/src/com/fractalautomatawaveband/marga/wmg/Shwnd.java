package com.fractalautomatawaveband.marga.wmg;

import java.awt.*;
import static com.fractalautomatawaveband.marga.wmg.L.d;

class Shwnd implements Wnd
{
  int x,y,w,h;
  Color bg,fg,hg,ng;
  StringBuffer sb;
  int mrgnleft,mrgntop;
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
  
  Shwnd(int xx,int yy,int ww,int hh)
  {
    x=xx;
    y=yy;
    w=ww;
    h=hh;
    bg=new Color(0,0,0,255);
    fg=new Color(0,255,255,255);
    hg=new Color(0,255,255,255);
    ng=new Color(0,64,64,255);
    sb=new StringBuffer();
    fnt=new Font("Larabiefont Rg", Font.PLAIN, 14);
    mrgnleft=5;
    mrgntop=15;
  }
  
  void cmdproc(String s)
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
  
  public void onkeyup(char c,int kc)
  {}
  
  public void onclick(int mx,int my)
  {
    //d("onclick",String.format("@ (%03d, %03d)",mx,my));
  }
  
  public void rndr(Graphics2D g2,boolean infocus)
  {
    g2.setColor(infocus?hg:ng);
    g2.drawRect(x-1,y-1,w+1,h+1);
    g2.setPaint(bg);
    g2.fillRect(x,y,w,h);
    g2.setColor(fg);
    g2.setFont(fnt);
    g2.drawString(sb.toString(),x+mrgnleft,y+mrgntop);
  }
  
  public boolean containsPoint(int xx,int yy)
  {
    return x<xx && y<yy && xx<(x+w) && yy<(y+h);
  }
}

