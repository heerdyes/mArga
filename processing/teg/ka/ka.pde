import java.io.*;

float tx = 6;
float ty = 4;
float conht = 28;
float lnht = 22;
float ltwh = 20;
int nlns = 31;
Process shproc;
Sndr q;
Rcvr r, rerr;
Thread rt, rerrt;
Ts term;
Cli con;
MQ mq;

void initsh(String... args) {
  try {
    ProcessBuilder pb = new ProcessBuilder(args);
    shproc = pb.start();
    q = new Sndr(shproc.getOutputStream());
    r = new Rcvr("stdout", shproc.getInputStream());
    rt = new Thread(r);
    rerr = new Rcvr("stderr", shproc.getErrorStream());
    rerrt = new Thread(rerr);
    rt.start();
    rerrt.start();
  }
  catch(IOException ioe) {
    ioe.printStackTrace();
  }
}

void initterm() {
  term = new Ts(nlns, tx, ty, width-(2*tx), height-conht-2, trn, blk);
  con = new Cli("> ", tx, height-conht-2, width-(2*tx), conht, trn, blk);
  mq = new MQ();
}

void setup() {
  size(1280, 720);
  background(240);
  textFont(createFont("OCRA", 14));
  initsh("/usr/bin/sh");
  initterm();
  background(blk);
}

void draw() {
  term.consumemsg();
  term.rndr();
  con.rndr();
}

void exit() {
  r.off();
  rerr.off();
  q.off();
  shproc.destroy();
  System.exit(0);
}

void keyPressed() {
  println(keyCode);
  if (key == '\n') {
    String cmd = con.getcmd();
    term.addmsg(new Msg("echo", "-> " + cmd));
    con.reset();
    q.sendstr(cmd);
  } else if (keyCode == 8) {
    con.popc();
  } else if (keyCode == 16) {
    d("<SHIFT>");
  } else if (keyCode == 20) {
    d("<CAPS_LOCK>");
  } else if (keyCode == 17) {
    d("<CTRL>");
  } else if (keyCode == 18) {
    d("<ALT>");
  } else {
    con.addc(key);
  }
}
