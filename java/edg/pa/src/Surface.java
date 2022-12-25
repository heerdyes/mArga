import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import javax.swing.*;

class Surface extends JPanel implements KeyListener, MouseListener {
  Ed ed;
  Color cbg;
  Paint bg, fg;
  Font fnt;

  Surface() {
    super();
    ed = new Ed();
    cbg = new Color(0, 0, 0, 0);
    bg = new Color(0, 0, 0, 255);
    fg = new Color(0, 255, 0, 255);
    fnt = new Font("Larabiefont Rg", Font.PLAIN, 14);
  }

  void d(String ctx, String msg) {
    System.out.printf("[%s] %s\n", ctx, msg);
  }

  private void wipe(Graphics2D g2d) {
    g2d.setPaint(bg);
    g2d.fillRect(0,0,getWidth(),getHeight());
    g2d.setPaint(fg);
    g2d.drawRect(0,0,getWidth()-1,getHeight()-1);
  }

  private void draw(Graphics2D g2d) {
    g2d.setPaint(fg);
    g2d.setFont(fnt);
    int i = 0;
    for (StringBuffer sb : ed.buf) {
      g2d.drawString(String.format("%03d",i), 10, 20+i*16);
      g2d.drawString(sb.toString(), 50, 20+i*16);
      i += 1;
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

  public void keyTyped(KeyEvent e) {}

  public void keyPressed(KeyEvent e) {
    int kc = e.getKeyCode();
    d("keyPressed", String.format("[%d]", e.getKeyCode()));
    if (kc == 27) {
      d("keyPressed", "goodbye!");
      System.exit(0);
    }
    ed.sendkey(e.getKeyChar(), kc);
    repaint();
  }

  public void keyReleased(KeyEvent e) {}

  public void mousePressed(MouseEvent e) {}

  public void mouseReleased(MouseEvent e) {}

  public void mouseMoved(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mouseEntered(MouseEvent e) {}

  public void mouseClicked(MouseEvent e) {
    d("mouseClicked", String.format("(%d,%d)", e.getX(), e.getY()));
  }
}
