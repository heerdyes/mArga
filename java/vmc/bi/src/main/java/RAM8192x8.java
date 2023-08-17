public class RAM8192x8{
  boolean a12,a11,a10,a9,a8,a7,a6,a5,a4,a3,a2,a1,a0;
  boolean di7,di6,di5,di4,di3,di2,di1,di0;
  boolean do7,do6,do5,do4,do3,do2,do1,do0;
  boolean w;
  Decoder3to8 wd;
  Decoder3to8 d7,d6,d5,d4,d3,d2,d1,d0;
  Selector8to1 s7,s6,s5,s4,s3,s2,s1,s0;
  RAM1024x8 r0,r1,r2,r3,r4,r5,r6,r7;
  
  RAM8192x8(){
    r0=new RAM1024x8();
    r1=new RAM1024x8();
    r2=new RAM1024x8();
    r3=new RAM1024x8();
    r4=new RAM1024x8();
    r5=new RAM1024x8();
    r6=new RAM1024x8();
    r7=new RAM1024x8();
    wd=new Decoder3to8();
    d7=new Decoder3to8();
    d6=new Decoder3to8();
    d5=new Decoder3to8();
    d4=new Decoder3to8();
    d3=new Decoder3to8();
    d2=new Decoder3to8();
    d1=new Decoder3to8();
    d0=new Decoder3to8();
    s7=new Selector8to1();
    s6=new Selector8to1();
    s5=new Selector8to1();
    s4=new Selector8to1();
    s3=new Selector8to1();
    s2=new Selector8to1();
    s1=new Selector8to1();
    s0=new Selector8to1();
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
  
  void feed(boolean a12,boolean a11,boolean a10,boolean a9,boolean a8,boolean a7,boolean a6,boolean a5,boolean a4,boolean a3,boolean a2,boolean a1,boolean a0,
            boolean w,
            boolean di7,boolean di6,boolean di5,boolean di4,boolean di3,boolean di2,boolean di1,boolean di0){
    this.a12=a12;this.a11=a11;this.a10=a10;
    this.a9=a9;this.a8=a8;this.a7=a7;this.a6=a6;this.a5=a5;
    this.a4=a4;this.a3=a3;this.a2=a2;this.a1=a1;this.a0=a0;
    this.w=w;
    this.di0=di0;
    this.di1=di1;
    this.di2=di2;
    this.di3=di3;
    this.di4=di4;
    this.di5=di5;
    this.di6=di6;
    this.di7=di7;
    d7.feed(a12,a11,a10,di7);
    d6.feed(a12,a11,a10,di6);
    d5.feed(a12,a11,a10,di5);
    d4.feed(a12,a11,a10,di4);
    d3.feed(a12,a11,a10,di3);
    d2.feed(a12,a11,a10,di2);
    d1.feed(a12,a11,a10,di1);
    d0.feed(a12,a11,a10,di0);
    wd.feed(a12,a11,a10,w);
    // feed 8 1024x8 RAMs
    r0.feed(a9,a8,a7,a6,a5,a4,a3,a2,a1,a0,wd.o0,d7.o0,d6.o0,d5.o0,d4.o0,d3.o0,d2.o0,d1.o0,d0.o0);
    r1.feed(a9,a8,a7,a6,a5,a4,a3,a2,a1,a0,wd.o1,d7.o1,d6.o1,d5.o1,d4.o1,d3.o1,d2.o1,d1.o1,d0.o1);
    r2.feed(a9,a8,a7,a6,a5,a4,a3,a2,a1,a0,wd.o2,d7.o2,d6.o2,d5.o2,d4.o2,d3.o2,d2.o2,d1.o2,d0.o2);
    r3.feed(a9,a8,a7,a6,a5,a4,a3,a2,a1,a0,wd.o3,d7.o3,d6.o3,d5.o3,d4.o3,d3.o3,d2.o3,d1.o3,d0.o3);
    r4.feed(a9,a8,a7,a6,a5,a4,a3,a2,a1,a0,wd.o4,d7.o4,d6.o4,d5.o4,d4.o4,d3.o4,d2.o4,d1.o4,d0.o4);
    r5.feed(a9,a8,a7,a6,a5,a4,a3,a2,a1,a0,wd.o5,d7.o5,d6.o5,d5.o5,d4.o5,d3.o5,d2.o5,d1.o5,d0.o5);
    r6.feed(a9,a8,a7,a6,a5,a4,a3,a2,a1,a0,wd.o6,d7.o6,d6.o6,d5.o6,d4.o6,d3.o6,d2.o6,d1.o6,d0.o6);
    r7.feed(a9,a8,a7,a6,a5,a4,a3,a2,a1,a0,wd.o7,d7.o7,d6.o7,d5.o7,d4.o7,d3.o7,d2.o7,d1.o7,d0.o7);
    // selection
    s7.feed(a12,a11,a10,r7.do7,r6.do7,r5.do7,r4.do7,r3.do7,r2.do7,r1.do7,r0.do7);
    s6.feed(a12,a11,a10,r7.do6,r6.do6,r5.do6,r4.do6,r3.do6,r2.do6,r1.do6,r0.do6);
    s5.feed(a12,a11,a10,r7.do5,r6.do5,r5.do5,r4.do5,r3.do5,r2.do5,r1.do5,r0.do5);
    s4.feed(a12,a11,a10,r7.do4,r6.do4,r5.do4,r4.do4,r3.do4,r2.do4,r1.do4,r0.do4);
    s3.feed(a12,a11,a10,r7.do3,r6.do3,r5.do3,r4.do3,r3.do3,r2.do3,r1.do3,r0.do3);
    s2.feed(a12,a11,a10,r7.do2,r6.do2,r5.do2,r4.do2,r3.do2,r2.do2,r1.do2,r0.do2);
    s1.feed(a12,a11,a10,r7.do1,r6.do1,r5.do1,r4.do1,r3.do1,r2.do1,r1.do1,r0.do1);
    s0.feed(a12,a11,a10,r7.do0,r6.do0,r5.do0,r4.do0,r3.do0,r2.do0,r1.do0,r0.do0);
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
    if(addr.length()!=13 || w.length()!=1 || di.length()!=8){
      throw new RuntimeException("illegal feed in data!");
    }
    feed(addr.charAt(0)=='0'?false:true,addr.charAt(1)=='0'?false:true,addr.charAt(2)=='0'?false:true,addr.charAt(3)=='0'?false:true,addr.charAt(4)=='0'?false:true,
          addr.charAt(5)=='0'?false:true,addr.charAt(6)=='0'?false:true,addr.charAt(7)=='0'?false:true,addr.charAt(8)=='0'?false:true,addr.charAt(9)=='0'?false:true,
          addr.charAt(10)=='0'?false:true,addr.charAt(11)=='0'?false:true,addr.charAt(12)=='0'?false:true,
          w.charAt(0)=='0'?false:true,
          di.charAt(0)=='0'?false:true,di.charAt(1)=='0'?false:true,di.charAt(2)=='0'?false:true,di.charAt(3)=='0'?false:true,
          di.charAt(4)=='0'?false:true,di.charAt(5)=='0'?false:true,di.charAt(6)=='0'?false:true,di.charAt(7)=='0'?false:true);
  }
  
  void display(){
    //System.out.println("------------");
    r0.display();
    r1.display();
    r2.display();
    r3.display();
    r4.display();
    r5.display();
    r6.display();
    r7.display();
    //System.out.println("------------");
  }
  
  public static void main(String[] args){
    RAM8192x8 r=new RAM8192x8();
    r.feed("0000000000000","1","10101100");
    r.feed("0000000000100","1","10100000");
    r.feed("0000000010000","1","00111100");
    r.feed("0000100001000","1","11101110");
    r.feed("0010000000000","1","10001000");
    r.feed("0010000000010","1","00100100");
    r.feed("1000010000000","1","10110100");
    r.feed("1010000010100","1","11011011");r.display();
  }
  
}
