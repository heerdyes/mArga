public class Decoder3to8{
  boolean s2,s1,s0;
  boolean di;
  boolean o7,o6,o5,o4,o3,o2,o1,o0;
  
  public Decoder3to8(){}
  
  void feed(boolean s2,boolean s1,boolean s0,boolean dit){
    this.s2=s2;this.s1=s1;this.s0=s0;
    boolean ns0=!s0;
    boolean ns1=!s1;
    boolean ns2=!s2;
    di=dit;
    o0=di&ns2&ns1&ns0;
    o1=di&ns2&ns1& s0;
    o2=di&ns2& s1&ns0;
    o3=di&ns2& s1& s0;
    o4=di& s2&ns1&ns0;
    o5=di& s2&ns1& s0;
    o6=di& s2& s1&ns0;
    o7=di& s2& s1& s0;
  }
  
  void feed(String s,String d){
    if(s.length()!=3 || d.length()!=1){
      throw new RuntimeException("illegal length of s or d!");
    }
    feed(s.charAt(0)=='0'?false:true,
        s.charAt(1)=='0'?false:true,
        s.charAt(2)=='0'?false:true,
        d.charAt(0)=='0'?false:true);
  }
  
  void display(){
    System.out.println("D3x8 -> "+Functions.bitseq(new boolean[]{o7,o6,o5,o4,o3,o2,o1,o0}));
  }
  
  public static void main(String[] args){
    Decoder3to8 dx=new Decoder3to8();
    dx.feed("000","1");dx.display();
    dx.feed("001","1");dx.display();
    dx.feed("010","1");dx.display();
    dx.feed("011","1");dx.display();
    dx.feed("100","1");dx.display();
    dx.feed("101","1");dx.display();
    dx.feed("110","1");dx.display();
    dx.feed("111","1");dx.display();
  }
}
