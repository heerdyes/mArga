import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class shg extends Frame implements WindowListener, KeyListener {
  TextArea ta;
  TextField tf;
  Color bg;
  Color fg;
  Font xf;
  float fsz;
  sh z;
  String prompt = "-> ";

  void mkta() {
    ta = new TextArea();
    ta.setBackground(bg);
    ta.setForeground(fg);
    ta.setFont(xf);
  }

  void mktf() {
    tf = new TextField(10);
    tf.setBackground(bg);
    tf.setForeground(fg);
    tf.setFont(xf);
    tf.addKeyListener(this);
  }

  void setstyle() {
    bg = new Color(230, 230, 230);
    fg = new Color(0, 32, 32);
    fsz = 16f;
    Font[] allfs = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    for(Font f : allfs) {
      if("OCRA".equals(f.getFamily())) {
        xf = f.deriveFont(fsz);
        break;
      }
    }
    // worst case
    if(xf == null) {
      xf = allfs[0].deriveFont(fsz);
    }
  }

  shg(String t) {
    super(t);
    setSize(600, 400);
    z = new sh();
    addWindowListener(this);
    setstyle();
    mkta();
    mktf();
    setLayout(new BorderLayout());
    add(ta);
    add(tf, BorderLayout.SOUTH);
    setVisible(true);
    tf.requestFocus();
  }

  public static void main(String... args) {
    u.d("[shg]");
    shg f = new shg("shg");
  }

  // [eventlisteners
  public void windowActivated(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
  public void windowClosing(WindowEvent e) {
    u.d("powering off...");
    System.exit(0);
  }
  public void windowDeactivated(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowOpened(WindowEvent e) {}

  public void keyPressed(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {
    int kc = e.getKeyCode();
    if(kc == KeyEvent.VK_ENTER) {
      String cli = tf.getText();
      tf.setText("");
      ta.append(prompt + cli + "\n");
      ta.append(z.clexec(cli) + "\n");
    } else if(kc == KeyEvent.VK_F6) {
      ta.setText("");
    } else if(kc == KeyEvent.VK_F1) {
      ta.setText(String.join("\n", new String[] {
        "F1 -> help",
        "F6 -> clearscreen",
        "ESC -> exit",
        "type 'help' for sh help"
      }));
    } else if(kc == KeyEvent.VK_ESCAPE) {
      u.d("ESCaping...");
      System.exit(0);
    }
  }
  public void keyTyped(KeyEvent e) {}
  // ]
}

class sh {
  File pwd;

  sh() {
    pwd = new File(".");
  }

  String docd(String d) {
    File td = new File(d.startsWith("/") ? d : (pwd.getAbsolutePath() + File.separator + d));
    if(!td.exists()) { return "path does not exist"; }
    if(!td.isDirectory()) { return "path is not a directory"; }
    try {
      pwd = td.getCanonicalFile();
    } catch (IOException ioe) {
      ioe.printStackTrace();
      return ioe.getMessage();
    }
    return "";
  }

  String docat(String fn) {
    File f = new File(pwd.getAbsolutePath() + File.separator + fn);
    if(!f.exists()) { return "path does not exist"; }
    if(!f.isFile()) { return "path is not a file"; }
    try(FileReader fr = new FileReader(f)) {
      StringBuffer sb = new StringBuffer();
      int d = fr.read();
      while(d != -1) {
        char c = (char) d;
        sb.append(c);
        d = fr.read();
      }
      return sb.toString();
    } catch (IOException ioe) {
      ioe.printStackTrace();
      return ioe.getMessage();
    }
  }

  String dohelp() {
    return String.join("\n", new String[] {
      "pwd  -> working dir",
      "ls   -> list dirs",
      "cd   -> change dir",
      "c    -> cat",
      "help -> this help"
    });
  }

  String clexec(String cli) {
    String[] cmdargv = u.cadr(cli);
    String cmd = cmdargv[0];
    if(cmd.equals("q")) {
      u.d("quitting...");
      System.exit(0);
    }
    if(cmd.equals("pwd")) {
      return pwd.getAbsolutePath();
    } else if(cmd.equals("ls")) {
      return String.join("\n", pwd.list());
    } else if(cmd.equals("cd")) {
      return docd(u.cadr(cmdargv[1])[0]);
    } else if(cmd.equals("c")) {
      return docat(u.cadr(cmdargv[1])[0]);
    } else if(cmd.equals("help")) {
      return dohelp();
    }
    return "unknown command: " + cmd;
  }
}

class u {
  static void d(String s) { System.out.println(s); }

  static String[] cadr(String s) {
    int spidx = s.indexOf(' ');
    if(spidx == -1) { return new String[] { s, "" }; }
    return new String[] { s.substring(0, spidx), s.substring(spidx + 1) };
  }
}
