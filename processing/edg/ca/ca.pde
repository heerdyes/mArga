PFont ocrbw;
LineList ll;
color grn=color(0, 255, 0);
float txtsz=24;
float k=0.75;

void setup() {
  size(1280, 720);
  background(0);
  textAlign(CENTER, CENTER);
  textSize(txtsz);
  ocrbw=createFont("bitwise-font/Bitwise-m19x.ttf", txtsz);
  textFont(ocrbw);
  ll=new LineList();
  ll.rndr(40, 40, txtsz);
}

void draw() {
}

void keyPressed() {
  ll.xkey(key, keyCode);
  background(0);
  ll.rndr(40, 40, txtsz);
}
