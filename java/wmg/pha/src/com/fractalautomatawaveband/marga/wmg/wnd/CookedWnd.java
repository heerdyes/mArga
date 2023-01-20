package com.fractalautomatawaveband.marga.wmg.wnd;

import java.awt.*;

public class CookedWnd extends RawWnd
{
  protected Font f;
  protected int tbarht=14;
  protected boolean minified=false;
  int hbak;
  protected String title;
  
  public boolean isMinified() { return minified; }
  
  public CookedWnd(String id,int x,int y,int w,int h,String t)
  {
    super(id,x,y,w,h);
    hbak=h;
    f=new Font("Larabiefont Rg", Font.PLAIN, 12);
    title=t;
  }
  
  public void rndr(Graphics2D g2,boolean infocus)
  {
    super.rndr(g2,infocus);
    g2.setColor(fg);
    g2.drawLine(x,y+tbarht,x+w-1,y+tbarht);
    g2.setFont(f);
    g2.drawString(title,x+5,y+tbarht-2);
  }
  
  public void onclick(int mx,int my)
  {
    if(minified && mx<w && my<tbarht)
    {
      h=hbak;
      minified=false;
    }
    else if(!minified && mx<w && my<tbarht)
    {
      h=tbarht;
      minified=true;
    }
  }
}

