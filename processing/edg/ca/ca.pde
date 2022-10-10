PFont ocrbw;
CharList cl;
color grn=color(0, 255, 0);
float txtsz=24;

void setup() {
  size(1280, 720);
  background(0);
  textAlign(CENTER, CENTER);
  textSize(txtsz);
  ocrbw=createFont("bitwise-font/Bitwise-m19x.ttf", txtsz);
  textFont(ocrbw);
  cl=new CharList();
}

void draw() {
}

void keyPressed() {
  cl.xkey(key, keyCode);
  background(0);
  cl.rndr(40, 40, 0.75*txtsz);
}
