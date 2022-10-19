enum Mode {
  CLI, EDIT
}

class Buf {
  float x, y, w, h;
  LineList body;
  CharList cli;
  float lngap, txtsz, mbottom=15;
  PrintWriter pw;
  Mode mode;
  String fn;

  Buf(float xx, float yy, float ww, float hh, float lg, float ts) {
    x=xx;
    y=yy;
    w=ww;
    h=hh;
    lngap=lg;
    txtsz=ts;
    body=new LineList();
    cli=new CharList();
    mode=Mode.EDIT;
  }

  void rndr() {
    body.rndr(x, y, lngap*txtsz);
    stroke(dgrn);
    float depth=y+h-txtsz-mbottom;
    stroke(dgrn);
    line(x, depth, x+w, depth);
    fill(dgrn);
    text(">", x, y+h-txtsz);
    cli.rndr(x+txtsz, y+h-txtsz, k*txtsz);
  }

  void loadbuf(String param0) {
    try {
      body.clr();
      fn=param0;
      String fp=pwd.getCanonicalPath()+File.separator+fn;
      BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fp)));
      String ln;
      while ((ln=br.readLine())!=null) {
        body.insln();
        body.cur.s.puts(ln);
      }
      br.close();
      mode=Mode.EDIT;
    }
    catch(IOException ioe) {
      println(ioe.getMessage());
      cli.clr();
      cli.puts("loadbuf failed!");
    }
  }

  void chdir(String param0) {
    String dirpath=param0;
    pwd=new File(dirpath);
    try {
      println("pwd-> "+pwd.getCanonicalPath());
    }
    catch(IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  void cmdexec() {
    String cmdstr=cli.gets();
    cli.clr();
    String[] parts=cmdstr.split(" ");
    String cmd=parts[0];
    if (cmd.equals("w")) {
      String param0=parts[1];
      body.savefile(param0);
    } else if (cmd.equals("cd")) {
      chdir(parts[1]);
    } else if (cmd.equals("o")) {
      loadbuf(parts[1]);
    } else {
      cli.puts("unknown cmd!");
    }
  }

  void handleF2() {
    cli.clr();
    try {
      String fp=pwd.getCanonicalPath()+File.separator+fn;
      cli.puts("w "+fp);
    }
    catch(IOException ioe) {
      throw new RuntimeException(ioe.getMessage());
    }
    mode=Mode.CLI;
  }

  void handleF3() {
    cli.clr();
    try {
      String fp=pwd.getCanonicalPath()+File.separator;
      cli.puts("o "+fp);
    }
    catch(IOException ioe) {
      throw new RuntimeException(ioe.getMessage());
    }
    mode=Mode.CLI;
  }

  void xkey(char k, int kc) {
    d("<"+kc+">");
    if (kc==17 && mode==Mode.CLI) { // CTRL toggles mode
      mode=Mode.EDIT;
      println("mode: "+mode);
    } else if (kc==17 && mode==Mode.EDIT) {
      mode=Mode.CLI;
      println("mode: "+mode);
    } else if (kc==113) {
      handleF2();
    } else if (kc==114) {
      handleF3();
    } else if (mode==Mode.EDIT) {
      body.xkey(k, kc);
    } else if (mode==Mode.CLI) {
      if (kc==10) {
        cmdexec();
      } else {
        cli.xkey(k, kc);
      }
    } else {
      println("unknown condition reached. Don't Panic!");
    }
  }
}
