package ed;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;

/**
 * smallest graphics text editor program ever
 *
 * @author heerdyes
 * @version 0.0.1 20210817
 */
public class ed00 implements KeyListener,MouseListener,MouseMotionListener
{
  // globals
  static JFrame f;
  static Graphics g;
  static int width=1280;
  static int height=720;
  static int xloc=100;
  static int yloc=100;
  static int mouseX=0;
  static int mouseY=0;
  
  // instance vars
  ArrayList<B> bs;
  int activeb=-1;
  String rsrcdir;
  String sfont="OCRA";
  
  ed00()
  {
    rsrcdir="rsrc";
    bs=new ArrayList<B>();
    initrsrcdir();
    initfonts();
  }
  
  void initrsrcdir()
  {
    File frsrcdir=new File(rsrcdir);
    if(!frsrcdir.exists())
    {
      throw new RuntimeException("[FATAL] resource directory: "+rsrcdir+" absent!");
    }
    U.d("rsrcdir -> "+rsrcdir);
  }
  
  void initfonts()
  {
    try
    {
      GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
      String fp=rsrcdir+File.separator+sfont.toLowerCase()+".ttf";
      File fontfile=new File(fp);
      Font dfont=Font.createFont(Font.TRUETYPE_FONT,fontfile);
      ge.registerFont(dfont);
      U.d("[INFO] "+dfont+" registered");
    }
    catch(IOException|FontFormatException e)
    {
      U.d("exception: "+e.getMessage());
    }
  }
  
  static void initgui(ed00 gt)
  {
    f=new JFrame("ed00");
    f.setSize(width,height);
    f.setLocation(xloc,yloc);
    f.addKeyListener(gt);
    f.addMouseListener(gt);
    f.addMouseMotionListener(gt);
    f.getContentPane().setBackground(Color.BLACK);
    f.setVisible(true);
    g=f.getGraphics();
  }
  
  void f5()
  {
    for(B b:bs)
    {
      b.render(g);
    }
  }
  
  Optional<B> idbox(int mx,int my)
  {
    int ctr=0;
    for(B b:bs)
    {
      if(b.x<mx && b.y<my && (b.x+b.w)>mx && (b.y+b.h)>my)
      {
        activeb=ctr;
        return Optional.of(b);
      }
      ctr++;
    }
    return Optional.empty();
  }

  public static void main(String[] args)
  {
    initgui(new ed00());
  }

  // keyboard stuff
  public void keyPressed(KeyEvent e)
  {
    int kc=e.getKeyCode();
    System.out.printf("[keypress] code: %d\n",kc);
    if(kc==27)
    {
      System.exit(0);
    }
    else if(kc==46) // . (dot) creates a notebook
    {
      String bnm=String.format("tx_%d%d",mouseX,mouseY);
      bs.add(new T(10,bnm,mouseX,mouseY,128,72,sfont));
      activeb++;
    }
    else if(activeb!=-1)
    {
      Object o=bs.get(activeb);
      if(o instanceof T)
      {
        ((T)o).insch(e.getKeyChar());
      }
    }
    f5();
  }

  public void keyTyped(KeyEvent e){}
  public void keyReleased(KeyEvent e){}
  
  // mouse stuff
  public void mouseClicked(MouseEvent e)
  {
    int mx=e.getX();
    int my=e.getY();
    System.out.printf("[mouseclick] (%d, %d)\n",mx,my);
    try
    {
      B cb=idbox(mx,my).get();
      U.d("click",cb.name);
    }
    catch(NoSuchElementException nsee)
    {
      U.d("ERROR","click is outside box ({0})",nsee.getMessage());
    }
  }
  public void mousePressed(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mouseDragged(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mouseMoved(MouseEvent e)
  {
    mouseX=e.getX();
    mouseY=e.getY();
  }
}

