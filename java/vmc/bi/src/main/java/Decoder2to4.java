public class Decoder2to4{
  boolean s1,s0;
  boolean di;
  boolean o3,o2,o1,o0;
  
  public Decoder2to4(){}
  
  void feed(boolean s1,boolean s0,boolean dit){
    this.s1=s1;this.s0=s0;
    boolean ns0=!s0;
    boolean ns1=!s1;
    di=dit;
    o0=di&ns1&ns0;
    o1=di&ns1& s0;
    o2=di& s1&ns0;
    o3=di& s1& s0;
  }
  
  void feed(String s,String d){
    if(s.length()!=2 || d.length()!=1){
      throw new RuntimeException("illegal length of s or d!");
    }
    feed(s.charAt(0)=='0'?false:true,
        s.charAt(1)=='0'?false:true,
        d.charAt(0)=='0'?false:true);
  }
  
  void display(){
    System.out.println("D2x4 -> "+Functions.bitseq(new boolean[]{o3,o2,o1,o0}));
  }
  
  public static void main(String[] args){
    Decoder2to4 dx=new Decoder2to4();
    dx.feed("00","1");dx.display();
    dx.feed("01","1");dx.display();
    dx.feed("10","1");dx.display();
    dx.feed("11","1");dx.display();
  }
}
