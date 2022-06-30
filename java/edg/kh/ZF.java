import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class ZF extends JFrame implements KeyListener
{
  ZC c;
  
  public ZF()
  {
    addKeyListener(this);
    c=new ZC();
    add(c,BorderLayout.CENTER);
    pack();
  }
  
  public void keyPressed(KeyEvent ke)
  {
    int kc=ke.getKeyCode();
    System.out.println("[keyPressed] "+kc);
    if(kc==27)
    {
      System.exit(0);
    }
    c.keydown(kc);
  }
  
  public void keyReleased(KeyEvent ke)
  {}
  
  public void keyTyped(KeyEvent ke)
  {}
}

