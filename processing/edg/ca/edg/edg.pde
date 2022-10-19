PFont ocrbw;
color grn=color(0, 255, 0);
color dgrn=color(0, 96, 0);
float txtsz=24;
float k=0.75;
float lngap=1.5;
PrintWriter pw;
Buf buf;
File pwd;
boolean DEBUGGING=false;

void setup() {
  size(1280, 720);
  background(0);
  textAlign(CENTER, CENTER);
  textSize(txtsz);
  ocrbw=createFont("bitwise-font/Bitwise-m19x.ttf", txtsz);
  textFont(ocrbw);
  pwd=new File(".");
  buf=new Buf(20, 20, width-40, height-40, lngap, txtsz);
  buf.rndr();
}

void draw() {
}

void keyPressed() {
  buf.xkey(key, keyCode);
  background(0);
  buf.rndr();
}
