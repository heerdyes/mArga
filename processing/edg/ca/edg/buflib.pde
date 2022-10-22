enum Mode {
  CLI, EDIT
}

class Buf {
  float x, y, w, h;
  LineList body;
  CharList cli;
  float lngap, txtsz, mbottom=25;
  PrintWriter pw;
  Mode mode;
  String fn;
  PGraphics vu;
  File pwd;
  int wbeg, wend;

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
    fn="tmp.txt";
    pwd=new File(".");
    vu=createGraphics(ceil(w), ceil(h));
    vu.beginDraw();
    vu.textFont(ocrbw);
    vu.textAlign(CENTER, CENTER);
    vu.textSize(txtsz);
    vu.endDraw();
    wbeg=0;
    wend=-1;
  }

  void rndr() {
    vu.beginDraw();
    vu.fill(0);
    vu.stroke(dgrn);
    vu.rect(0, 0, w-1, h-1);
    body.rndr(vu, 15, 15, lngap*txtsz, wbeg, wend);
    vu.stroke(dgrn);
    float depth=h-txtsz-mbottom;
    vu.line(0, depth, w, depth);
    vu.fill(dgrn);
    vu.text(">", 15, h-txtsz);
    cli.rndr(vu, 15+txtsz, h-txtsz, k*txtsz);
    vu.endDraw();
    image(vu, x, y, w, h);
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
      body.savefile(param0, pw);
    } else if (cmd.equals("cd")) {
      chdir(parts[1]);
    } else if (cmd.equals("o")) {
      loadbuf(parts[1]);
    } else if (cmd.equals("q")) {
      exit();
    } else if (cmd.equals(":")) {
      if (parts.length==1) {
        wbeg=0;
        wend=-1;
      } else {
        wbeg=int(parts[1]);
        wend=int(parts[2]);
      }
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
    } else if (kc==112) {
      scalemode=!scalemode;
    } else if (kc==113) {
      handleF2();
    } else if (kc==114) {
      handleF3();
    } else if (mode==Mode.EDIT) {
      body.xkey(k, kc);
    } else if (mode==Mode.CLI) {
      if (kc==10) {
        cmdexec();
        mode=Mode.EDIT;
      } else {
        cli.xkey(k, kc);
      }
    } else {
      println("unknown condition reached. Don't Panic!");
    }
  }
}
