import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class shg extends JFrame implements KeyListener {
  tash tx;
  sh z;

  shg(String t) {
    super(t);
    try {
      // create pipes
      PipedInputStream pipe1in = new PipedInputStream();
      PipedOutputStream pipe1out = new PipedOutputStream(pipe1in);
      PipedInputStream pipe2in = new PipedInputStream();
      PipedOutputStream pipe2out = new PipedOutputStream(pipe2in);

      // init shell and term
      tx = new tash(2, 37, 635, 440, pipe1in, pipe2out);
      z = new sh(pipe2in, pipe1out);

      // usual gui stuff
      setSize(640, 480);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      addKeyListener(this);
      setVisible(true);

      // launch shell on a separate thread
      new Thread(z).start();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public void paint(Graphics g) {
    tx.rndr(g);
  }

  public void keyPressed(KeyEvent e) {
    int kc = e.getKeyCode();
    tx.typechar(kc);
    repaint();
  }
  public void keyReleased(KeyEvent e) {
    int kc = e.getKeyCode();
    if(kc == KeyEvent.VK_ESCAPE) {
      tx.cleanup();
      z.cleanup();
      System.exit(0);
    }
  }
  public void keyTyped(KeyEvent e) {}

  public static void main(String[] args) throws IOException {
    shg cli = new shg("shg");
  }
}
