import java.io.*;

public class sh implements Runnable {
  BufferedReader rd;
  PrintWriter wr;
  volatile boolean hangup;

  sh(InputStream in, OutputStream out) {
    rd = new BufferedReader(new InputStreamReader(in));
    wr = new PrintWriter(out);
    hangup = false;
  }

  void cleanup() {
    if(!hangup) {
      hangup = true;
    }
    try {
      if(rd != null) {
        rd.close();
      }
      if(wr != null) {
        wr.close();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  void xcommand(String cl) {
    try {
      System.out.println("[xcommand] " + cl);
      if(cl.equals("Q")) {
        wr.println("bye");
        hangup = true;
        rd.close();
        wr.close();
      } else if(cl.equals("moshimoshi")) {
        wr.println("konichiwa!");
      } else {
        wr.println("what is " + cl + "?");
      }
      wr.flush();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  void repl() {
    try {
      StringBuffer buf = new StringBuffer();
      while(!hangup) {
        String ln = rd.readLine();
        if(ln == null) break;
        xcommand(ln);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public void run() {
    repl();
  }
}
