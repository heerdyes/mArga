import java.io.*;

// funxions
boolean printable(int code) {
  boolean alpha=code>=65 && code<=90;
  boolean num=code>=48 && code<=57;
  boolean minus_equals_backslash=code==45 || code==61 || code==92;
  boolean square_brackets=code==91 || code==93;
  boolean semicolon_quote=code==59 || code==222;
  boolean comma_dot_slash=code==44 || code==46 || code==47;
  boolean backtick=code==192;
  boolean space=code==32;
  return alpha || num || minus_equals_backslash || square_brackets || semicolon_quote || comma_dot_slash || backtick || space;
}

// structures
class LineList {
  LineNode head, tail, cur; // here cur points to the current line
  int nlines;

  LineList() {
    head=tail=cur=null;
    nlines=0;
  }

  void rndr(float x, float y, float offset) {
    int j=0;
    for (LineNode i=head; i!=null; i=i.next, j++) {
      float yoff=y+offset*j;
      i.rndr(x+2*txtsz, yoff);
      text(String.format("%02d", j), x, yoff);
      if (i==cur) {
        stroke(dgrn);
        line(x+25, yoff-txtsz/2-1, width-1, yoff-txtsz/2-1);
        line(x+25, yoff+txtsz/2+1, width-1, yoff+txtsz/2+1);
      }
    }
  }

  void clr() {
    head=tail=cur=null;
    nlines=0;
  }

  void savefile(String fn) {
    if (nlines>0) {
      println("saving file: "+fn);
      pw=createWriter(fn);
      for (LineNode i=head; i!=null; i=i.next) {
        pw.println(i.s.gets());
      }
      pw.flush();
      pw.close();
    }
  }

  void xkey(char ch, int code) {
    if (code==10) { // key enter
      insln();
    } else if (nlines==0) {
      if (printable(code)) {
        head=tail=cur=new LineNode(new CharList(ch));
        nlines=1;
      }
    } else if (code==38) {
      curshu();
    } else if (code==40) {
      curshd();
    } else {
      cur.s.xkey(ch, code);
    }
  }

  void curshu() {
    if (cur!=null && cur!=head) {
      cur=cur.prev;
    }
  }

  void curshd() {
    if (cur!=null && cur!=tail) {
      cur=cur.next;
    }
  }

  void insln() {
    LineNode ln=new LineNode(new CharList());
    if (cur==null) {
      head=tail=cur=ln;
    } else if (cur==tail) {
      cur.next=ln;
      ln.prev=cur;
      tail=ln;
      cur=tail;
    } else {
      ln.next=cur.next;
      ln.prev=cur;
      cur.next.prev=ln;
      cur.next=ln;
      cur=ln;
    }
    nlines++;
  }
}

class LineNode {
  CharList s;
  LineNode prev, next;

  LineNode(CharList ln) {
    s=ln;
    prev=null;
    next=null;
  }

  void rndr(float x, float y) {
    s.rndr(x, y, k*txtsz);
  }
}

class CharList {
  CharNode head, tail, cur; // here cur points to the space b/w chars (i.e. nextchar)
  int nchars;

  CharList() {
    cur=head=tail=null;
    nchars=0;
  }

  CharList(char c) {
    // verify cn is not already a chain
    head=tail=new CharNode(c);
    cur=null;
    nchars=1;
  }

  void xkey(char ch, int code) {
    if (printable(code)) {
      insch(ch);
    } else if (code==37) { // left arrow
      curshl();
    } else if (code==39) { // right arrow
      curshr();
    } else if (code==36) { // home
      cur=head;
    } else if (code==35) { // end
      cur=null;
    } else if (code==8) { // backspace
      bksp();
    } else if (code==127) { // delete
      del();
    }
  }

  void clr() {
    cur=head=tail=null;
    nchars=0;
  }

  void insch(char ch) {
    if (nchars==0) {
      head=tail=new CharNode(ch);
    } else if (cur==null) {
      cur=new CharNode(ch);
      tail.next=cur;
      cur.prev=tail;
      tail=cur;
      cur=null;
    } else {
      CharNode nch=new CharNode(ch);
      CharNode prevbak=cur.prev;
      if (prevbak!=null) {
        prevbak.next=nch;
      } else {
        head=nch;
      }
      nch.prev=prevbak;
      nch.next=cur;
      cur.prev=nch;
    }
    nchars+=1;
  }

  void curshr() {
    if (cur!=null) {
      cur=cur.next;
    }
  }

  void curshl() {
    if (cur==null) {
      cur=tail;
    } else if (cur==head) {
      // do nothing
    } else {
      cur=cur.prev;
    }
  }

  void bksp() {
    if (nchars>0) {
      if (cur==null && head==tail) {
        head=tail=null;
      } else if (cur==null && head!=tail) {
        tail=tail.prev;
        tail.next=null;
      } else if (cur!=head) {
        CharNode delch=cur.prev;
        cur.prev=delch.prev;
        if (delch.prev!=null) {
          delch.prev.next=cur;
        } else {
          head=cur;
        }
      }
      nchars-=1;
    }
  }

  void del() {
    if (cur!=null) {
      CharNode delch=cur;
      if (cur==head) {
        cur=delch.next;
        head=cur;
      } else if (cur==tail) {
        tail=tail.prev;
        tail.next=cur=null;
      } else {
        cur.prev.next=delch.next;
        cur.next.prev=delch.prev;
        cur=delch.next;
        delch=null;
      }
      nchars-=1;
    }
  }

  void puts(String s) {
    for (int i=0; i<s.length(); i++) {
      insch(s.charAt(i));
    }
  }

  String gets() {
    StringBuffer tmp=new StringBuffer();
    for (CharNode i=head; i!=null; i=i.next) {
      tmp.append(i.c);
    }
    return tmp.toString();
  }

  void rndr(float x, float y, float sz) {
    float offset=x;
    float yoff=y+txtsz/2+2;
    for (CharNode i=head; i!=null; i=i.next) {
      i.rndr(offset, y);
      if (i==cur) {
        stroke(grn);
        line(offset-txtsz/4, yoff, offset+txtsz/4-1, yoff);
      }
      offset+=sz;
    }
    if (cur==null) {
      stroke(grn);
      line(offset-txtsz/4, yoff, offset+txtsz/4-1, yoff);
    }
  }
}

class CharNode {
  char c;
  CharNode next, prev;

  CharNode(char ch) {
    c=ch;
    next=null;
    prev=null;
  }

  void rndr(float x, float y) {
    fill(grn);
    noStroke();
    text(""+c, x, y);
  }
}
