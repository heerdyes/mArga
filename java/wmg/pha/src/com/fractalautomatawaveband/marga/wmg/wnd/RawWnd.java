package com.fractalautomatawaveband.marga.wmg.wnd;

import java.awt.*;
import static com.fractalautomatawaveband.marga.wmg.util.L.d;
import com.fractalautomatawaveband.marga.wmg.evt.*;
import java.util.*;

public class RawWnd implements Wnd
{
  protected int x,y,w,h;
  protected Color bg,fg,hg,ng,dfg;
  protected int mrgnleft,mrgntop;
  HashSet<WndObs> observers;
  String id;
  
  public int getX() { return x; }
  public int getY() { return y; }
  public int getW() { return w; }
  public int getH() { return h; }
  public void setXY(int xx,int yy)
  {
    x=xx;
    y=yy;
  }
  public void setWH(int ww,int hh)
  {
    w=ww;
    h=hh;
  }
  public String getID() { return id; }
  public void setID(String _id) { id=_id; }
  
  public RawWnd(String _id,int xx,int yy,int ww,int hh)
  {
    id=_id;
    x=xx;
    y=yy;
    w=ww;
    h=hh;
    bg=new Color(0,0,0,255);
    dfg=new Color(0,255,255,255);
    hg=new Color(0,255,255,255);
    ng=new Color(0,128,128,255);
    fg=dfg;
    mrgnleft=5;
    mrgntop=15;
    observers=new HashSet<>();
  }
  
  public void onkeydown(char c,int kc)
  {}
  
  public void onkeyup(char c,int kc)
  {}
  
  public void onclick(int mx,int my)
  {}
  
  public void rndr(Graphics2D g2,boolean infocus)
  {
    fg=infocus?hg:ng;
    g2.setColor(fg);
    g2.drawRect(x-1,y-1,w+1,h+1);
    g2.setPaint(bg);
    g2.fillRect(x,y,w,h);
  }
  
  public boolean containsPoint(int xx,int yy)
  {
    return x<xx && y<yy && xx<(x+w) && yy<(y+h);
  }
  
  public void addWndObserver(WndObs wo)
  {
    observers.add(wo);
  }
  
  public void removeWndObserver(WndObs wo)
  {
    observers.remove(wo);
  }
  
  public void cleanup()
  {}
}

