import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

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
    setSize(900, 600);
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
  HashMap<String, File> pathtable;

  sh() {
    pwd = new File(".");
    pathtable = new HashMap<>();
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
      "pwd                 -> working dir",
      "ls                  -> list dirs",
      "cd                  -> change dir",
      "c                   -> cat",
      "mkpath <name> <dir> -> add to path table",
      "lspath              -> list pathtable",
      "help                -> this help"
    });
  }

  String domkpath(String pk, String p) {
    if(pk.equals("") || p.equals("")) {
      return "E: path key or path is empty";
    }
    String fpath = pwd.getAbsolutePath() + File.separator + p;
    if(p.startsWith("/")) {
      fpath = p;
    }
    File _pf = new File(fpath);
    if(_pf.exists() && _pf.isDirectory()) {
      pathtable.put(pk, _pf);
      return "";
    }
    return "path either nonexistent or nondirectory!";
  }

  String dolspath() {
    StringBuffer sb = new StringBuffer();
    for(String k : pathtable.keySet()) {
      sb.append(String.format("%s -> %s\n", k, pathtable.get(k)));
    }
    return sb.toString();
  }

  File getprogfile(String pfile) {
    File xprog = null;
    for(Map.Entry<String, File> kv : pathtable.entrySet()) {
      File fp = kv.getValue();
      File fxp = new File(fp.getAbsolutePath() + File.separator + pfile);
      if(fxp.exists() && fxp.isFile()) {
        xprog = fxp;
        break;
      }
    }
    return xprog;
  }

  String dorunextprog(String pfile, String pargs) {
    File xprog = getprogfile(pfile);
    if(xprog == null) {
      return String.format("unknown command or file: %s", pfile);
    }
    ProcessBuilder pb = new ProcessBuilder(xprog.getAbsolutePath(), pargs);
    pb.directory(pwd);
    try {
      outputrcvr pout = new outputrcvr(pb.start());
      return pout.collectoutput();
    } catch (IOException ioe) {
      ioe.printStackTrace();
      return ioe.getMessage();
    }
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
    } else if(cmd.equals("mkpath")) {
      String[] p = u.cadr(cmdargv[1]);
      return domkpath(p[0], p[1]);
    } else if(cmd.equals("lspath")) {
      return dolspath();
    } else {
      return dorunextprog(cmd, cmdargv[1]);
    }
  }
}

class outputrcvr {
  Process p;
  tail out, err;
  String outbuf, errbuf;

  outputrcvr(Process _p) {
    p = _p;
    out = new tail(p.getInputStream());
    err = new tail(p.getErrorStream());
  }

  String collectoutput() {
    int exitcode = 0;
    try {
      out.start();
      err.start();
      exitcode = p.waitFor();
      out.join();
      err.join();
      outbuf = out.getdst();
      errbuf = err.getdst();
    } catch (InterruptedException ie) {
      ie.printStackTrace();
    }
    return outbuf + errbuf;
  }
}

class tail extends Thread {
  InputStreamReader isr;
  StringBuffer dst;

  tail(InputStream is) {
    isr = new InputStreamReader(is);
    dst = new StringBuffer();
  }

  String getdst() { return dst.toString(); }

  void consumechars() {
    try {
      int x = isr.read();
      while(x != -1) {
        char c = (char) x;
        dst.append(c);
        x = isr.read();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  void consumelines() {
    try(BufferedReader br = new BufferedReader(isr)) {
      String s = br.readLine();
      while(s != null) {
        dst.append(s + "\n");
        s = br.readLine();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public void run() {
    consumechars();
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
