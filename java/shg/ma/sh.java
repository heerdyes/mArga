import java.io.*;

public class sh {
  InputStreamReader rd;
  OutputStreamWriter wr;
  volatile boolean hangup;

  sh(InputStream in, OutputStream out) {
    rd = new InputStreamReader(in);
    wr = new OutputStreamWriter(out);
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

  void sendchars(String s) throws IOException {
    for(int i = 0; i < s.length(); i++) {
      wr.write(s.charAt(i));
    }
    wr.flush();
  }

  void xcommand(String cl) {
    try {
      System.out.println("[xcommand] " + cl);
      if(cl.equals("Q")) {
        sendchars("bye\n");
        hangup = true;
        rd.close();
        wr.close();
      } else if(cl.equals("HELO")) {
        sendchars("greetings infidels!\n");
      } else {
        sendchars("what is " + cl + "?\n");
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  void repl() {
    try {
      StringBuffer buf = new StringBuffer();
      while(!hangup) {
        int x = rd.read();
        if(x == -1) break;
        char c = (char) x;
        if(c == '\n') {
          xcommand(buf.toString());
          buf.setLength(0);
        } else {
          buf.append(c);
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
