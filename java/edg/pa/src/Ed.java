import java.util.*;

class Ed {
  ArrayList<StringBuffer> buf;
  int cr;
  int cc;

  Ed() {
    buf = new ArrayList<StringBuffer>();
    buf.add(new StringBuffer());
    cr = 0;
    cc = 0;
  }

  String getln(int num) {
    return buf.get(num).toString();
  }

  void insln(int num, String ln) {
    buf.add(num, new StringBuffer(ln));
    cr += 1;
  }

  void appendln() {
    buf.add(new StringBuffer());
    cr+=1;
    cc=0;
  }

  void insch(char c) {
    StringBuffer row = buf.get(cr);
    row.insert(cc, c);
    cc += 1;
  }

  boolean printable(int kc) {
    boolean isnum = kc>=48 && kc<=57;
    boolean ischar = kc>=65 && kc<=90;
    boolean isspc = kc==32;
    return isnum || ischar || isspc;
  }

  void sendkey(char c, int kc) {
    if (printable(kc)) {
      insch(c);
    } else if(kc==10) {
      appendln();
    } else {
      System.out.println("[sendkey] nonprintable char!");
    }
  }
}
