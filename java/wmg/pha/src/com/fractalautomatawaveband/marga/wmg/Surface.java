package com.fractalautomatawaveband.marga.wmg;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import static com.fractalautomatawaveband.marga.wmg.L.d;

class Surface extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
  HashMap<String,Wnd> wndmap;
  Color cbg;
  Paint bg, fg;
  Font fnt;
  int mouseX,mouseY;
  int wctr;
  String focusedWnd;

  Surface() {
    super();
    wndmap = new HashMap<>();
    cbg = new Color(0, 0, 0, 0);
    bg = new Color(0, 0, 0, 255);
    fg = new Color(0, 255, 0, 255);
    fnt = new Font("Larabiefont Rg", Font.PLAIN, 14);
    mouseX=0;
    mouseY=0;
    wctr=0;
    focusedWnd="/";
  }

  private void wipe(Graphics2D g2d) {
    g2d.setPaint(bg);
    g2d.fillRect(0,0,getWidth(),getHeight());
    g2d.setPaint(fg);
    g2d.drawRect(0,0,getWidth()-1,getHeight()-1);
  }

  private void draw(Graphics2D g2d) {
    for (String sb : wndmap.keySet()) {
      wndmap.get(sb).rndr(g2d, focusedWnd.equals(sb));
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    wipe(g2);
    draw(g2);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(900,600);
  }
  
  void broadcastKeypress(char c,int kc) {
    for(String k:wndmap.keySet()) {
      wndmap.get(k).sendkey(c,kc);
    }
  }

  public void keyTyped(KeyEvent e) {}
  
  void procKey(char c,int kc){
    if(kc==32){
      String wn=String.format("w%02d",wctr++);
      Wnd x=new Shwnd(mouseX,mouseY,80,40);
      wndmap.put(wn,x);
      d("created wnd:"+wn+" at ("+mouseX+","+mouseY+")!");
    }
  }

  public void keyPressed(KeyEvent e) {
    int kc = e.getKeyCode();
    if (kc == 27) {
      d("keyPressed", "goodbye!");
      System.exit(0);
    }
    // if <SPACE> create a new Shwnd at mouse_location
    if(focusedWnd.equals("/")){
      procKey(e.getKeyChar(),kc);
    }else{
      wndmap.get(focusedWnd).sendkey(e.getKeyChar(), kc);
    }
    repaint();
  }

  public void keyReleased(KeyEvent e) {}

  public void mousePressed(MouseEvent e) {}

  public void mouseReleased(MouseEvent e) {}

  public void mouseMoved(MouseEvent e) {
    mouseX=e.getX();
    mouseY=e.getY();
  }
  
  public void mouseDragged(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mouseEntered(MouseEvent e) {}

  public void mouseClicked(MouseEvent e) {
    d("mouseClicked", String.format("(%d,%d)", e.getX(), e.getY()));
    focusedWnd=findWnd(e.getX(),e.getY());
    repaint();
  }
  
  String findWnd(int mx,int my){
    for(String k:wndmap.keySet()){
      if(wndmap.get(k).containsPoint(mx,my)){
        return k;
      }
    }
    return "/";
  }
}

