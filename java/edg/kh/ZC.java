import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class ZC extends JComponent
{
  ZBuf b;
  
  public ZC()
  {
    b=new ZBuf();
  }
  
  public void paintComponent(Graphics g)
  {
    Graphics2D g2=(Graphics2D)g;
    b.render(g2);
  }
  
  public Dimension getPreferredSize()
  {
    return new Dimension(1280,720);
  }
  
  void keydown(int kc)
  {
    b.sendch(kc);
    repaint();
  }
}

