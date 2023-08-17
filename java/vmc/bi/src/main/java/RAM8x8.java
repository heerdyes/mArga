public class RAM8x8{
  boolean a2,a1,a0;
  boolean di7,di6,di5,di4,di3,di2,di1,di0;
  boolean do7,do6,do5,do4,do3,do2,do1,do0;
  boolean w;
  RAM8x1 r0,r1,r2,r3,r4,r5,r6,r7;
  
  RAM8x8(){
    r0=new RAM8x1();
    r1=new RAM8x1();
    r2=new RAM8x1();
    r3=new RAM8x1();
    r4=new RAM8x1();
    r5=new RAM8x1();
    r6=new RAM8x1();
    r7=new RAM8x1();
  }
  
  void feed(boolean a2,boolean a1,boolean a0,
            boolean w,
            boolean di7,boolean di6,boolean di5,boolean di4,boolean di3,boolean di2,boolean di1,boolean di0){
    this.a2=a2;this.a1=a1;this.a0=a0;
    this.w=w;
    this.di0=di0;
    this.di1=di1;
    this.di2=di2;
    this.di3=di3;
    this.di4=di4;
    this.di5=di5;
    this.di6=di6;
    this.di7=di7;
    r0.feed(a2,a1,a0,w,di0);
    r1.feed(a2,a1,a0,w,di1);
    r2.feed(a2,a1,a0,w,di2);
    r3.feed(a2,a1,a0,w,di3);
    r4.feed(a2,a1,a0,w,di4);
    r5.feed(a2,a1,a0,w,di5);
    r6.feed(a2,a1,a0,w,di6);
    r7.feed(a2,a1,a0,w,di7);
    do0=r0.dout;
    do1=r1.dout;
    do2=r2.dout;
    do3=r3.dout;
    do4=r4.dout;
    do5=r5.dout;
    do6=r6.dout;
    do7=r7.dout;
  }
  
  void printDO(){
    System.out.println("RAM8x8.DO-> "+(do7?"1":"0")+(do6?"1":"0")+(do5?"1":"0")+(do4?"1":"0")+(do3?"1":"0")+(do2?"1":"0")+(do1?"1":"0")+(do0?"1":"0"));
  }
  
  void feed(String addr,String w,String di){
    if(addr.length()!=3 || w.length()!=1 || di.length()!=8){
      throw new RuntimeException("illegal feed in data!");
    }
    feed(addr.charAt(0)=='0'?false:true,addr.charAt(1)=='0'?false:true,addr.charAt(2)=='0'?false:true,
          w.charAt(0)=='0'?false:true,
          di.charAt(0)=='0'?false:true,di.charAt(1)=='0'?false:true,di.charAt(2)=='0'?false:true,di.charAt(3)=='0'?false:true,
          di.charAt(4)=='0'?false:true,di.charAt(5)=='0'?false:true,di.charAt(6)=='0'?false:true,di.charAt(7)=='0'?false:true);
  }
  
  void display(){
    //System.out.println("RAM8x8");
    //System.out.println("------------");
    System.out.println("| "+(r7.dq0.q?"1":"0")+(r6.dq0.q?"1":"0")+(r5.dq0.q?"1":"0")+(r4.dq0.q?"1":"0")+(r3.dq0.q?"1":"0")+(r2.dq0.q?"1":"0")+(r1.dq0.q?"1":"0")+(r0.dq0.q?"1":"0")+" |");
    System.out.println("| "+(r7.dq1.q?"1":"0")+(r6.dq1.q?"1":"0")+(r5.dq1.q?"1":"0")+(r4.dq1.q?"1":"0")+(r3.dq1.q?"1":"0")+(r2.dq1.q?"1":"0")+(r1.dq1.q?"1":"0")+(r0.dq1.q?"1":"0")+" |");
    System.out.println("| "+(r7.dq2.q?"1":"0")+(r6.dq2.q?"1":"0")+(r5.dq2.q?"1":"0")+(r4.dq2.q?"1":"0")+(r3.dq2.q?"1":"0")+(r2.dq2.q?"1":"0")+(r1.dq2.q?"1":"0")+(r0.dq2.q?"1":"0")+" |");
    System.out.println("| "+(r7.dq3.q?"1":"0")+(r6.dq3.q?"1":"0")+(r5.dq3.q?"1":"0")+(r4.dq3.q?"1":"0")+(r3.dq3.q?"1":"0")+(r2.dq3.q?"1":"0")+(r1.dq3.q?"1":"0")+(r0.dq3.q?"1":"0")+" |");
    System.out.println("| "+(r7.dq4.q?"1":"0")+(r6.dq4.q?"1":"0")+(r5.dq4.q?"1":"0")+(r4.dq4.q?"1":"0")+(r3.dq4.q?"1":"0")+(r2.dq4.q?"1":"0")+(r1.dq4.q?"1":"0")+(r0.dq4.q?"1":"0")+" |");
    System.out.println("| "+(r7.dq5.q?"1":"0")+(r6.dq5.q?"1":"0")+(r5.dq5.q?"1":"0")+(r4.dq5.q?"1":"0")+(r3.dq5.q?"1":"0")+(r2.dq5.q?"1":"0")+(r1.dq5.q?"1":"0")+(r0.dq5.q?"1":"0")+" |");
    System.out.println("| "+(r7.dq6.q?"1":"0")+(r6.dq6.q?"1":"0")+(r5.dq6.q?"1":"0")+(r4.dq6.q?"1":"0")+(r3.dq6.q?"1":"0")+(r2.dq6.q?"1":"0")+(r1.dq6.q?"1":"0")+(r0.dq6.q?"1":"0")+" |");
    System.out.println("| "+(r7.dq7.q?"1":"0")+(r6.dq7.q?"1":"0")+(r5.dq7.q?"1":"0")+(r4.dq7.q?"1":"0")+(r3.dq7.q?"1":"0")+(r2.dq7.q?"1":"0")+(r1.dq7.q?"1":"0")+(r0.dq7.q?"1":"0")+" |");
    //System.out.println("------------");
  }
  
  public static void main(String[] args){
    RAM8x8 r=new RAM8x8();
    r.feed("000","1","10101100");
    r.feed("100","1","00110110");
    r.feed("111","1","10101111");
    r.display();
  }
}
