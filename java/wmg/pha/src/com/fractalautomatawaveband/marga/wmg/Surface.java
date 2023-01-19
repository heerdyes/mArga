package com.fractalautomatawaveband.marga.wmg;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import static com.fractalautomatawaveband.marga.wmg.L.d;

class Surface extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{
  HashMap<String,Wnd> wndmap;
  Color cbg;
  Paint bg, fg;
  Font fnt;
  int mouseX,mouseY;
  int wctr,wlog;
  String focusedWnd;
  boolean whold=false;
  int hx,hy;

  Surface()
  {
    super();
    wndmap = new HashMap<>();
    cbg = new Color(0, 0, 0, 0);
    bg = new Color(0, 0, 0, 255);
    fg = new Color(0, 255, 0, 255);
    fnt = new Font("Courier", Font.PLAIN, 14);
    mouseX=0;
    mouseY=0;
    hx=0;
    hy=0;
    wctr=0;
    wlog=0;
    focusedWnd="/";
    mksyslog();
  }
  
  void mksyslog()
  {
    String lk="/sys/log";
    if(wndmap.containsKey(lk))
    {
      return;
    }
    Wnd x=new Logwnd(5,600-42,900-11,40,10);
    wndmap.put(lk,x);
  }

  private void wipe(Graphics2D g2d)
  {
    g2d.setPaint(bg);
    g2d.fillRect(0,0,getWidth(),getHeight());
    g2d.setPaint(fg);
    g2d.drawRect(0,0,getWidth()-1,getHeight()-1);
  }
  
  void log(String msg)
  {
    if(wndmap.containsKey("/sys/log"))
    {
      Logwnd lw=(Logwnd)wndmap.get("/sys/log");
      lw.addln(msg);
      repaint();
    }
    else
    {
      d(msg);
    }
  }
  
  void log(String ctx,String msg)
  {
    log(String.format("[%s] %s",ctx,msg));
  }

  private void draw(Graphics2D g2d)
  {
    for (String sb : wndmap.keySet())
    {
      wndmap.get(sb).rndr(g2d, focusedWnd.equals(sb));
    }
  }

  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    wipe(g2);
    draw(g2);
  }

  @Override
  public Dimension getPreferredSize()
  {
    return new Dimension(900,600);
  }
  
  public void keyTyped(KeyEvent e) {}
  
  void onkeydown(char c,int kc)
  {
    log("keydown: "+kc);
    // if <SPACE> create a new Shwnd at mouse_location
    if(kc==32)
    {
      String wn=String.format("w%02d",wctr++);
      Wnd x=new Shwnd(mouseX,mouseY,144,81);
      wndmap.put(wn,x);
      log("created wnd:"+wn+" at ("+mouseX+","+mouseY+")!");
    }
  }
  
  void onkeyup(char c,int kc)
  {}
  
  void onclick(int mx,int my)
  {}
  
  boolean ismodkey(int kc)
  {
    return kc==16 || kc==27;
  }
  
  void onmodkeydown(int kc)
  {
    if(kc==27)
    {
      log("keyPressed", "goodbye!");
      System.exit(0);
    }
    else if(kc==16)
    {
      focusedWnd=findWnd(mouseX,mouseY);
      if(focusedWnd.equals("/"))
      { /* no-op */ }
      else
      {
        whold=true;
        Wnd w=wndmap.get(focusedWnd);
        hx=mouseX-w.getX();
        hy=mouseY-w.getY();
      }
    }
  }
  
  void onmodkeyup(int kc)
  {
    if(kc==16)
    {
      whold=false;
    }
  }

  public void keyPressed(KeyEvent e)
  {
    int kc = e.getKeyCode();
    if(ismodkey(kc))
    {
      onmodkeydown(kc);
    }
    else if(focusedWnd.equals("/"))
    {
      onkeydown(e.getKeyChar(),kc);
    }
    else
    {
      wndmap.get(focusedWnd).onkeydown(e.getKeyChar(), kc);
    }
    repaint();
  }

  public void keyReleased(KeyEvent e)
  {
    int kc=e.getKeyCode();
    if(ismodkey(kc))
    {
      onmodkeyup(kc);
    }
    else if(focusedWnd.equals("/"))
    {
      onkeyup(e.getKeyChar(),kc);
    }
    else
    {
      wndmap.get(focusedWnd).onkeyup(e.getKeyChar(), kc);
    }
    repaint();
  }

  public void mousePressed(MouseEvent e) {}

  public void mouseReleased(MouseEvent e) {}

  public void mouseMoved(MouseEvent e)
  {
    mouseX=e.getX();
    mouseY=e.getY();
    if(whold)
    {
      wndmap.get(focusedWnd).setXY(mouseX-hx,mouseY-hy);
      repaint();
    }
  }
  
  public void mouseDragged(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mouseEntered(MouseEvent e) {}

  public void mouseClicked(MouseEvent e)
  {
    focusedWnd=findWnd(e.getX(),e.getY());
    if(focusedWnd.equals("/"))
    {
      onclick(e.getX(), e.getY());
    }
    else
    {
      Wnd w=wndmap.get(focusedWnd);
      w.onclick(e.getX()-w.getX(), e.getY()-w.getY());
    }
    repaint();
  }
  
  String findWnd(int mx,int my)
  {
    for(String k:wndmap.keySet())
    {
      if(wndmap.get(k).containsPoint(mx,my))
      {
        return k;
      }
    }
    return "/";
  }
}

