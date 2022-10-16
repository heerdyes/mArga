class LineList {
  LineNode head, tail, cur;
  int nlines;

  LineList() {
    head=tail=cur=null;
    nlines=0;
  }

  void rndr(float x, float y, float offset) {
    int j=0;
    for (LineNode i=head; i!=null; i=i.next, j++) {
      float yoff=y+offset*j;
      i.rndr(x, yoff);
      if (i==cur) {
        stroke(grn);
        line(x, yoff-20, width-1, yoff-20);
      }
    }
    if (cur==null) {
      stroke(grn);
      line(x, y+offset*j, width-1, y+offset*j);
    }
  }

  void xkey(char ch, int code) {
    if (code==10) { // key enter
      insln();
    } else if (code==38) {
      curshu();
    } else if (code==40) {
      curshd();
    } else if (nlines==0) {
      head=tail=new LineNode(new CharList(ch));
      nlines=1;
      cur=null;
    } else if (cur==null) {
      tail.s.xkey(ch, code);
    } else {
      cur.prev.s.xkey(ch, code);
    }
  }

  void curshu() {
    if (cur==null) {
      cur=tail;
    } else if (cur!=head) {
      cur=cur.prev;
    }
  }

  void curshd() {
    if (cur!=null) {
      cur=cur.next;
    }
  }

  void insln() {
    if (cur==null) {
      tail.next=new LineNode(new CharList());
      tail=tail.next;
    } else {
      println("[TODO] insln: curln");
    }
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
  CharNode head, tail, cur;
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

  void xkey(char ch, int code) {
    println("["+code+"]");
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
    if (cur!=null) {
      cur=cur.prev;
    } else {
      cur=tail;
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
