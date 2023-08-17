public class Selector2to1{
  boolean d1,d0;
  boolean s0;
  boolean q;
  
  Selector2to1(){}
  
  boolean selector2to1(boolean s0,
                      boolean d1,boolean d0){
    boolean ns0=!s0;
    boolean a0=d0&ns0;
    boolean a1=d1& s0;
    return a0|a1;
  }
  
  boolean selector2to1(String s,String d){
    if(s.length()!=1 || d.length()!=2){
      throw new RuntimeException("illegal data/select inputs!");
    }
    // selection bits
    boolean s0=s.charAt(0)=='0'?false:true;
    // data bits
    boolean d1=d.charAt(0)=='0'?false:true;
    boolean d0=d.charAt(1)=='0'?false:true;
    return selector2to1(s0,d1,d0);
  }
  
  void feed(boolean s0,
            boolean i1,boolean i0){
    this.s0=s0;
    d1=i1;
    d0=i0;
    q=selector2to1(s0,d1,d0);
  }
  
  void display(){
    System.out.println("S2x1.out -> "+(q?"1":"0"));
  }
  
  // testing //
  public static void main(String[] args){
    Selector2to1 s=new Selector2to1();
    System.out.println("> "+s.selector2to1("0","01"));
    System.out.println("> "+s.selector2to1("1","10"));
    System.out.println("> "+s.selector2to1("1","01"));
    System.out.println("> "+s.selector2to1("0","10"));
  }
}
