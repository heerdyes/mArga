public class RAM16x1{
  boolean a3,a2,a1,a0;
  boolean din,dout;
  boolean w;
  Decoder1to2 dec;
  RAM8x1 r0,r1;
  Selector2to1 sel;
  
  public RAM16x1(){
    dec=new Decoder1to2();
    r0=new RAM8x1();
    r1=new RAM8x1();
    sel=new Selector2to1();
  }
  
  void feed(boolean a3,boolean a2,boolean a1,boolean a0,
            boolean w,boolean di){
    this.a3=a3;this.a2=a2;this.a1=a1;this.a0=a0;
    this.din=di;this.w=w;
    dec.feed(a3,di);
    r0.feed(a2,a1,a0,w&!a3,dec.o0);
    r1.feed(a2,a1,a0,w&a3,dec.o1);
    sel.feed(a3,r1.dout,r0.dout);
    dout=sel.q;
  }
  
  void feed(String addr,String w,String di){
    if(addr.length()!=4 || w.length()!=1 || di.length()!=1){
      throw new RuntimeException("illegal feed in data!");
    }
    feed(addr.charAt(0)=='0'?false:true,addr.charAt(1)=='0'?false:true,addr.charAt(2)=='0'?false:true,addr.charAt(3)=='0'?false:true,
          w.charAt(0)=='0'?false:true,di.charAt(0)=='0'?false:true);
  }
  
  void display(){
    //System.out.println("-----");
    r0.display();
    r1.display();
    //System.out.println("-----");
  }
  
  public static void main(String[] args){
    RAM16x1 r=new RAM16x1();
    r.feed("0000","1","1");
    r.feed("0001","1","1");
    r.feed("0010","1","1");
    r.feed("0011","1","1");r.display();
    r.feed("0100","1","1");
    r.feed("0101","1","1");
    r.feed("0110","1","1");
    r.feed("0111","1","1");r.display();
    r.feed("1000","1","1");
    r.feed("1001","1","1");
    r.feed("1010","1","1");
    r.feed("1011","1","1");r.display();
    r.feed("1100","1","1");
    r.feed("1101","1","1");
    r.feed("1110","1","1");
    r.feed("1111","1","1");r.display();
  }
}
