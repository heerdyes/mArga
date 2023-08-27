class LQ<T> {
  ArrayList<T> q;
  int limit;

  LQ(int lim) {
    if (lim <= 0) throw new RuntimeException("Q <= 0");
    q=new ArrayList<>();
    limit = lim;
  }

  void nq(T s) {
    if (q.size() == limit) {
      dq();
    }
    q.add(s);
  }

  void dq() {
    if (q.size() > 0) {
      q.remove(0);
    }
  }
}

class Ts {
  LQ<Msg> _q;
  float x, y, w, h;
  color fg, bg;

  Ts(int n, float xx, float yy, float ww, float hh, color f, color b) {
    _q=new LQ(n);
    x=xx;
    y=yy;
    w=ww;
    h=hh;
    fg = f;
    bg = b;
  }

  void addmsg(Msg s) {
    _q.nq(s);
  }

  void consumemsg() {
    Msg msg = (Msg) mq.dq();
    if (msg != null) {
      addmsg(msg);
    }
  }

  void colorize(String t) {
    if (t.equals("error")) {
      fill(erd);
    } else if (t.equals("echo")) {
      fill(grn);
    } else {
      fill(this.fg);
    }
  }

  void rndr() {
    stroke(this.fg);
    fill(this.bg);
    rect(x, y, w, h);
    float cx = x + 5;
    float cy = y + 16;
    noStroke();
    for (int i = 0; i < _q.q.size(); i++) {
      Msg m = _q.q.get(i);
      colorize(m.tag);
      text(m.data, cx, cy);
      cy += lnht;
    }
  }
}

class Cli {
  float x, y, w, h;
  StringBuffer cmd;
  String prompt;
  color fg, bg;

  Cli(String pr, float xx, float yy, float ww, float hh, color f, color b) {
    prompt = pr;
    x=xx;
    y=yy;
    w=ww;
    h=hh;
    cmd=new StringBuffer();
    fg = f;
    bg = b;
  }

  void addc(char c) {
    cmd.append(c);
  }

  void popc() {
    if (cmd.length() > 0) {
      cmd.deleteCharAt(cmd.length()-1);
    }
  }

  void reset() {
    cmd.setLength(0);
  }

  String getcmd() {
    return cmd.toString();
  }

  void rndr() {
    stroke(this.fg);
    fill(this.bg);
    rect(x, y, w, h);
    fill(this.fg);
    noStroke();
    float xx = x + 8;
    float yy = y + lnht - 3;
    text(prompt + cmd.toString(), xx, yy);
  }
}
