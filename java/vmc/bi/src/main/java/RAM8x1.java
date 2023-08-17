public class RAM8x1{
  boolean a2,a1,a0;
  boolean din,dout;
  boolean w;
  Decoder3to8 dec;
  Selector8to1 sel;
  DFlipFlop dq0,dq1,dq2,dq3,dq4,dq5,dq6,dq7;
  
  public RAM8x1(){
    dec=new Decoder3to8();
    sel=new Selector8to1();
    dq0=new DFlipFlop();
    dq1=new DFlipFlop();
    dq2=new DFlipFlop();
    dq3=new DFlipFlop();
    dq4=new DFlipFlop();
    dq5=new DFlipFlop();
    dq6=new DFlipFlop();
    dq7=new DFlipFlop();
  }
  
  void feed(boolean a2,boolean a1,boolean a0,
            boolean w,boolean di){
    this.a2=a2;this.a1=a1;this.a0=a0;
    this.din=di;
    this.w=w;
    // decoder circuit
    dec.feed(a2,a1,a0,w);
    // mem circuit
    dq7.d=di; dq7.setClk(dec.o7);
    dq6.d=di; dq6.setClk(dec.o6);
    dq5.d=di; dq5.setClk(dec.o5);
    dq4.d=di; dq4.setClk(dec.o4);
    dq3.d=di; dq3.setClk(dec.o3);
    dq2.d=di; dq2.setClk(dec.o2);
    dq1.d=di; dq1.setClk(dec.o1);
    dq0.d=di; dq0.setClk(dec.o0);
    // selector circuit
    sel.feed(a2,a1,a0,
            dq7.q,dq6.q,dq5.q,dq4.q,
            dq3.q,dq2.q,dq1.q,dq0.q);
    dout=sel.q;
  }
  
  void feed(String addr,String w,String di){
    if(addr.length()!=3 || w.length()!=1 || di.length()!=1){
      throw new RuntimeException("illegal feed in data!");
    }
    feed(addr.charAt(0)=='0'?false:true,addr.charAt(1)=='0'?false:true,addr.charAt(2)=='0'?false:true,
          w.charAt(0)=='0'?false:true,di.charAt(0)=='0'?false:true);
  }
  
  void display(String prefix){
    System.out.println(prefix+"_RAM8x1");
    System.out.println("| "+(dq0.q?"1":"0")+" |");
    System.out.println("| "+(dq1.q?"1":"0")+" |");
    System.out.println("| "+(dq2.q?"1":"0")+" |");
    System.out.println("| "+(dq3.q?"1":"0")+" |");
    System.out.println("| "+(dq4.q?"1":"0")+" |");
    System.out.println("| "+(dq5.q?"1":"0")+" |");
    System.out.println("| "+(dq6.q?"1":"0")+" |");
    System.out.println("| "+(dq7.q?"1":"0")+" |");
  }
  
  void display(){
    System.out.println("| "+(dq0.q?"1":"0")+" |");
    System.out.println("| "+(dq1.q?"1":"0")+" |");
    System.out.println("| "+(dq2.q?"1":"0")+" |");
    System.out.println("| "+(dq3.q?"1":"0")+" |");
    System.out.println("| "+(dq4.q?"1":"0")+" |");
    System.out.println("| "+(dq5.q?"1":"0")+" |");
    System.out.println("| "+(dq6.q?"1":"0")+" |");
    System.out.println("| "+(dq7.q?"1":"0")+" |");
  }
  
  public static void main(String[] args){
    RAM8x1 r=new RAM8x1();
    r.feed("000","1","1");
    r.feed("000","0","0");
    r.feed("111","1","1");
    r.feed("100","1","1");
    r.feed("101","1","1");
    r.feed("000","1","0");
    r.display();
  }
}
