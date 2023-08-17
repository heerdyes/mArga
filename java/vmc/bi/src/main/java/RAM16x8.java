public class RAM16x8{
  boolean a3,a2,a1,a0;
  boolean di7,di6,di5,di4,di3,di2,di1,di0;
  boolean do7,do6,do5,do4,do3,do2,do1,do0;
  boolean w;
  Decoder1to2 wd;
  Decoder1to2 d7,d6,d5,d4,d3,d2,d1,d0;
  Selector2to1 s7,s6,s5,s4,s3,s2,s1,s0;
  RAM8x8 r1,r0;
  
  RAM16x8(){
    r0=new RAM8x8();
    r1=new RAM8x8();
    wd=new Decoder1to2();
    d7=new Decoder1to2();
    d6=new Decoder1to2();
    d5=new Decoder1to2();
    d4=new Decoder1to2();
    d3=new Decoder1to2();
    d2=new Decoder1to2();
    d1=new Decoder1to2();
    d0=new Decoder1to2();
    s7=new Selector2to1();
    s6=new Selector2to1();
    s5=new Selector2to1();
    s4=new Selector2to1();
    s3=new Selector2to1();
    s2=new Selector2to1();
    s1=new Selector2to1();
    s0=new Selector2to1();
  }
  
  void displayDecoderStage(){
    // debugging
    d7.display();
    d6.display();
    d5.display();
    d4.display();
    d3.display();
    d2.display();
    d1.display();
    d0.display();
  }
  
  void displaySelectorStage(){
    s7.display();
    s6.display();
    s5.display();
    s4.display();
    s3.display();
    s2.display();
    s1.display();
    s0.display();
  }
  
  void feed(boolean a3,boolean a2,boolean a1,boolean a0,
            boolean w,
            boolean di7,boolean di6,boolean di5,boolean di4,boolean di3,boolean di2,boolean di1,boolean di0){
    this.a3=a3;this.a2=a2;this.a1=a1;this.a0=a0;
    this.w=w;
    this.di0=di0;
    this.di1=di1;
    this.di2=di2;
    this.di3=di3;
    this.di4=di4;
    this.di5=di5;
    this.di6=di6;
    this.di7=di7;
    d7.feed(a3,di7);
    d6.feed(a3,di6);
    d5.feed(a3,di5);
    d4.feed(a3,di4);
    d3.feed(a3,di3);
    d2.feed(a3,di2);
    d1.feed(a3,di1);
    d0.feed(a3,di0);
    wd.feed(a3,w);
    //displayDecoderStage();
    r0.feed(a2,a1,a0,wd.o0,d7.o0,d6.o0,d5.o0,d4.o0,d3.o0,d2.o0,d1.o0,d0.o0);
    r1.feed(a2,a1,a0,wd.o1,d7.o1,d6.o1,d5.o1,d4.o1,d3.o1,d2.o1,d1.o1,d0.o1);
    s7.feed(a3,r1.do7,r0.do7);
    s6.feed(a3,r1.do6,r0.do6);
    s5.feed(a3,r1.do5,r0.do5);
    s4.feed(a3,r1.do4,r0.do4);
    s3.feed(a3,r1.do3,r0.do3);
    s2.feed(a3,r1.do2,r0.do2);
    s1.feed(a3,r1.do1,r0.do1);
    s0.feed(a3,r1.do0,r0.do0);
    //displaySelectorStage();
    do7=s7.q;
    do6=s6.q;
    do5=s5.q;
    do4=s4.q;
    do3=s3.q;
    do2=s2.q;
    do1=s1.q;
    do0=s0.q;
  }
  
  void feed(String addr,String w,String di){
    if(addr.length()!=4 || w.length()!=1 || di.length()!=8){
      throw new RuntimeException("illegal feed in data!");
    }
    feed(addr.charAt(0)=='0'?false:true,addr.charAt(1)=='0'?false:true,addr.charAt(2)=='0'?false:true,addr.charAt(3)=='0'?false:true,
          w.charAt(0)=='0'?false:true,
          di.charAt(0)=='0'?false:true,di.charAt(1)=='0'?false:true,di.charAt(2)=='0'?false:true,di.charAt(3)=='0'?false:true,
          di.charAt(4)=='0'?false:true,di.charAt(5)=='0'?false:true,di.charAt(6)=='0'?false:true,di.charAt(7)=='0'?false:true);
  }
  
  void display(){
    //System.out.println("------------");
    r0.display();
    r1.display();
    //System.out.println("------------");
  }
  
  public static void main(String[] args){
    RAM16x8 r=new RAM16x8();
    r.feed("0000","1","10101100");r.display();
    r.feed("0010","1","10100000");
    r.feed("0100","1","00111100");
    r.feed("0110","1","11101110");r.display();
    r.feed("1000","1","10001000");
    r.feed("1010","1","00100100");r.display();
    r.feed("1100","1","10110100");
    r.feed("1110","1","11011011");r.display();
  }
}
