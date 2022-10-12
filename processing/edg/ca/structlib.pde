class CharList {
  CharNode head, tail, cur;
  int nchars;

  CharList() {
    cur=head=tail=new CharNode('\0');
    nchars=0;
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
      cur=tail;
    } else if (code==8) { // backspace
      bksp();
    } else if (code==127) { // delete
      del();
    }
  }

  void insch(char ch) {
    if (cur==head) {
      head=new CharNode(ch);
      head.next=cur;
      cur.prev=head;
    } else {
      CharNode nch=new CharNode(ch);
      CharNode prevbak=cur.prev;
      prevbak.next=nch;
      nch.prev=prevbak;
      nch.next=cur;
      cur.prev=nch;
    }
    nchars+=1;
  }

  void curshr() {
    if (cur.next!=null) {
      cur=cur.next;
    }
  }

  void curshl() {
    if (cur.prev!=null) {
      cur=cur.prev;
    }
  }

  void bksp() {
    if (cur.prev!=null) {
      CharNode delch=cur.prev;
      cur.prev=delch.prev;
      if (delch.prev!=null) {
        delch.prev.next=cur;
      } else {
        head=cur;
      }
      delch=null;
      nchars-=1;
    }
  }

  void del() {
    if (cur.c!='\0') {
      CharNode delch=cur;
      if (cur==head) {
        cur=delch.next;
        head=cur;
      } else {
        cur.prev.next=delch.next;
        cur.next.prev=delch.prev;
        cur=delch.next;
      }
      nchars-=1;
    }
  }

  String gets() {
    StringBuffer tmp=new StringBuffer();
    for (CharNode i=head; i.c!='\0'; i=i.next) {
      tmp.append(i.c);
    }
    return tmp.toString();
  }

  void rndr(float x, float y, float sz) {
    int j=0;
    for (CharNode i=head; i!=null; i=i.next, j++) {
      float offset=x+sz*j;
      i.rndr(offset, y);
      if (i==cur) {
        float yoff=y+txtsz/2+2;
        float xl=offset-txtsz/4;
        float xr=offset+txtsz/4-1;
        stroke(grn);
        line(xl, yoff, xr, yoff);
      }
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
    if (c!='\0') {
      fill(grn);
      noStroke();
      text(""+c, x, y);
    }
  }
}
