import java.util.concurrent.*;

class Msg {
  String tag;
  String data;

  Msg(String l, String d) {
    tag = l;
    data = d;
  }
}

class MQ<T> {
  ConcurrentLinkedQueue<T> q;

  MQ() {
    q = new ConcurrentLinkedQueue<>();
  }

  void nq(T t) {
    q.add(t);
  }

  T dq() {
    if (q.isEmpty()) return null;
    return q.remove();
  }
}

class Rcvr implements Runnable {
  String name;
  BufferedReader br;
  volatile boolean hangup;

  public Rcvr(String nm, InputStream is) {
    name = nm;
    br = new BufferedReader(new InputStreamReader(is));
    hangup = false;
  }

  void politelyclose() {
    if (br != null) {
      try {
        br.close();
      }
      catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  public void off() {
    hangup = true;
  }

  public void run() {
    try {
      for (; !hangup; ) {
        String rsp = br.readLine();
        if (rsp == null) break;
        if (name.equals("stderr")) {
          mq.nq(new Msg("error", rsp));
        } else {
          mq.nq(new Msg("output", rsp));
        }
      }
    }
    catch (EOFException eof) {
      d("\n[EOF] " + eof.getMessage());
    }
    catch (IOException ioe) {
      d("\n[IOE] " + ioe.getMessage());
    }
    finally {
      politelyclose();
      d("rcvr:"+name+" hanged up.");
    }
  }
}

class Sndr {
  PrintWriter pw;

  public Sndr(OutputStream os) {
    pw = new PrintWriter(os);
  }

  void sendstr(String s) {
    pw.println(s);
    pw.flush();
  }

  void off() {
    if (pw != null) {
      pw.close();
    }
    pw = null;
  }
}
