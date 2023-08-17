public class Decoder1to2{
  boolean s0;
  boolean di;
  boolean o1,o0;
  
  public Decoder1to2(){}
  
  void feed(boolean s0,boolean dit){
    this.s0=s0;
    boolean ns0=!s0;
    di=dit;
    o0=di&ns0;
    o1=di& s0;
  }
  
  void feed(String s,String d){
    if(s.length()!=1 || d.length()!=1){
      throw new RuntimeException("illegal length of s or d!");
    }
    feed(s.charAt(0)=='0'?false:true,
        d.charAt(0)=='0'?false:true);
  }
  
  void display(){
    System.out.println("D1x2 -> "+Functions.bitseq(new boolean[]{o1,o0}));
  }
  
  public static void main(String[] args){
    Decoder1to2 dx=new Decoder1to2();
    dx.feed("0","1");dx.display();
    dx.feed("1","1");dx.display();
    dx.feed("0","0");dx.display();
    dx.feed("1","0");dx.display();
  }
}
