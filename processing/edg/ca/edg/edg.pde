PFont ocrbw;
LineList ll;
color grn=color(0, 255, 0);
color dgrn=color(0, 96, 0);
float txtsz=24;
float k=0.75;
float lngap=1.5;
String tmpfn="tmp.txt";
PrintWriter pw;

void setup() {
  size(1280, 720);
  background(0);
  textAlign(CENTER, CENTER);
  textSize(txtsz);
  ocrbw=createFont("bitwise-font/Bitwise-m19x.ttf", txtsz);
  textFont(ocrbw);
  ll=new LineList();
  ll.rndr(30, 30, lngap*txtsz);
}

void draw() {
}

void keyPressed() {
  ll.xkey(key, keyCode);
  background(0);
  ll.rndr(30, 30, lngap*txtsz);
}
