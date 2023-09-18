import java.util.*;
import java.io.*;
import java.awt.*;

public class tash {
  int x, y, w, h;
  Color bg, fg;
  ArrayList<StringBuffer> transcript;
  int maxcols;
  int maxrows;
  InputStream is;
  OutputStream os;
  Font fnt;
  float fsz = 14f;

  void setupfont(String ff) {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Font[] fonts = ge.getAllFonts();
    for(Font f : fonts) {
      if(ff.equals(f.getFamily())) {
        fnt = f.deriveFont(fsz);
        break;
      }
    }
    if(fnt == null) {
      fnt = fonts[0].deriveFont(fsz);
    }
  }

  tash(int xx, int yy, int ww, int hh, InputStream in, OutputStream out) {
    x = xx;
    y = yy;
    w = ww;
    h = hh;
    bg = Color.white;
    fg = Color.black;
    transcript = new ArrayList<>();
    transcript.add(new StringBuffer());
    maxcols = ww / ((int)fsz - 4);
    maxrows = hh / ((int)fsz + 5);
    System.out.printf("maxcols: %d, maxrows: %d\n", maxcols, maxrows);
    setupfont("OCRA");
    setio(in, out);
  }

  void setio(InputStream in, OutputStream out) {
    is = in;
    os = out;
  }

  void rndr(Graphics g) {
    g.setColor(bg);
    g.fillRect(x, y, w, h);
    g.setColor(fg);
    g.drawRect(x, y, w, h);
    g.setFont(fnt);
    int cx = x + 6;
    int cy = y + 16;
    for(StringBuffer sb : transcript) {
      g.drawString(sb.toString(), cx, cy);
      cy += 20;
    }
  }

  void sendchars(String s) throws IOException {
    for(int i = 0; i < s.length(); i++) {
      os.write(s.charAt(i));
    }
    os.flush();
  }

  void readinto(StringBuffer dst) throws IOException {
    int x = is.read();
    while(x != 10) {
      dst.append((char)x);
      x = is.read();
    }
    while(dst.length() >= maxcols) {
      dst.deleteCharAt(0);
    }
  }

  void adjustrows() {
    while(transcript.size() >= maxrows) {
      transcript.remove(0);
    }
  }

  void cleanup() {
    try {
      if(is != null) {
        is.close();
      }
      if(os != null) {
        os.close();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  void typechar(int c) {
    if(c == '\n') {
      try {
        String cmd = transcript.get(transcript.size() - 1).toString();
        sendchars(cmd + "\n");
        transcript.add(new StringBuffer());
        adjustrows();
        readinto(transcript.get(transcript.size() - 1));
        transcript.add(new StringBuffer());
        adjustrows();
      } catch (IOException ioe) {
        ioe.printStackTrace();
        transcript.add(new StringBuffer("IOE: " + ioe.getMessage()));
        transcript.add(new StringBuffer());
        adjustrows();
      }
    } else {
      StringBuffer lastbuf = transcript.get(transcript.size() - 1);
      lastbuf.append((char) c);
      while(lastbuf.length() >= maxcols) {
        lastbuf.deleteCharAt(0);
      }
    }
  }
}
