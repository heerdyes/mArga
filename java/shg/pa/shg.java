import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class shg extends JFrame implements KeyListener {
  JTextArea ta;
  JScrollPane sp;
  int state = 0;
  int caretpos = 0;
  String tmpcmd = null;
  sh z;
  PipedOutputStream p1o, p2o;
  PipedInputStream p1i, p2i;

  void mkpipes() {
    try {
      p1i = new PipedInputStream();
      p2i = new PipedInputStream();
      p1o = new PipedOutputStream(p1i);
      p2o = new PipedOutputStream(p2i);
      z = new sh(p1i, p2o);
      new Thread(z).start();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  shg(String t) {
    super(t);
    setSize(640, 480);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(new BorderLayout());
    ta = new JTextArea();
    ta.setFont(new Font("OCRA", Font.PLAIN, 14));
    sp = new JScrollPane(ta);
    ta.addKeyListener(this);
    add(sp);
    setVisible(true);
    mkpipes();
  }

  String getcmdln() {
    int len;
    String cl = null;
    try {
      len = ta.getCaretPosition() - caretpos;
      cl = ta.getText(caretpos, len);
    } catch (BadLocationException ble) {
      ble.printStackTrace();
    }
    return cl;
  }

  void xcmd(String cl) {
    try {
      for(int i = 0; i < cl.length(); i++) {
        p1o.write(cl.charAt(i));
      }
      StringBuffer tbuf = new StringBuffer();
      while(true) {
        int d = p2i.read();
        tbuf.append((char)d);
        if(d == 10) break;
      }
      ta.insert(tbuf.toString(), ta.getText().length());
    } catch (IOException ioe) {
      ioe.printStackTrace();
      ta.insert(ioe.getMessage() + "\n", ta.getText().length());
    }
  }

  // event handlers
  public void keyPressed(KeyEvent e) {
    int kc = e.getKeyCode();
    if(state == 0) {
      if(kc == KeyEvent.VK_ENTER) {
        // remain at state 0
      } else {
        state = 1;
        caretpos = ta.getCaretPosition();
      }
    } else if(state == 1) {
      if(kc == KeyEvent.VK_ENTER) {
        tmpcmd = getcmdln();
        state = 2;
      } else {
        // remain at state 1
      }
    } else if(state == 2) {
      if(kc == KeyEvent.VK_ENTER) {
        // remain at state 2
      }
    }
    // make caret stick to end of text
    if(ta.getText().length() > 0) {
      ta.setCaretPosition(ta.getText().length());
    }
  }

  public void keyReleased(KeyEvent e) {
    int kc = e.getKeyCode();
    if(kc == KeyEvent.VK_ESCAPE) {
      System.out.println("bye!");
      System.exit(0);
    }
    if(state == 2) {
      if(kc == KeyEvent.VK_ENTER) {
        xcmd(tmpcmd + "\n");
        state = 0;
      }
    }
  }

  public void keyTyped(KeyEvent e) {}

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        shg z = new shg("shg");
      }
    });
  }
}
