public class RAM8x2{
  boolean a2,a1,a0;
  boolean di1,di0;
  boolean do1,do0;
  boolean w;
  RAM8x1 r0,r1;
  
  RAM8x2(){
    r0=new RAM8x1();
    r1=new RAM8x1();
  }
  
  void feed(boolean a2,boolean a1,boolean a0,
            boolean w,boolean di1,boolean di0){
    this.a2=a2;this.a1=a1;this.a0=a0;
    this.w=w;this.di1=di1;this.di0=di0;
    r0.feed(a2,a1,a0,w,di0);
    r1.feed(a2,a1,a0,w,di1);
    do0=r0.dout;
    do1=r1.dout;
  }
  
  void feed(String addr,String w,String di){
    if(addr.length()!=3 || w.length()!=1 || di.length()!=2){
      throw new RuntimeException("illegal feed in data!");
    }
    feed(addr.charAt(0)=='0'?false:true,addr.charAt(1)=='0'?false:true,addr.charAt(2)=='0'?false:true,
          w.charAt(0)=='0'?false:true,di.charAt(0)=='0'?false:true,di.charAt(1)=='0'?false:true);
  }
  
  void display(){
    //System.out.println("RAM8x2");
    //System.out.println("------");
    System.out.println("| "+(r1.dq0.q?"1":"0")+(r0.dq0.q?"1":"0")+" |");
    System.out.println("| "+(r1.dq1.q?"1":"0")+(r0.dq1.q?"1":"0")+" |");
    System.out.println("| "+(r1.dq2.q?"1":"0")+(r0.dq2.q?"1":"0")+" |");
    System.out.println("| "+(r1.dq3.q?"1":"0")+(r0.dq3.q?"1":"0")+" |");
    System.out.println("| "+(r1.dq4.q?"1":"0")+(r0.dq4.q?"1":"0")+" |");
    System.out.println("| "+(r1.dq5.q?"1":"0")+(r0.dq5.q?"1":"0")+" |");
    System.out.println("| "+(r1.dq6.q?"1":"0")+(r0.dq6.q?"1":"0")+" |");
    System.out.println("| "+(r1.dq7.q?"1":"0")+(r0.dq7.q?"1":"0")+" |");
    //System.out.println("------");
  }
  
  public static void main(String[] args){
    RAM8x2 r=new RAM8x2();
    r.feed("000","1","10");
    r.feed("111","1","01");
    r.display();
  }
}
