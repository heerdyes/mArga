public class Selector4to1{
  boolean d3,d2,d1,d0;
  boolean s0,s1;
  boolean q;
  
  Selector4to1(){}
  
  boolean selector4to1(boolean s1,boolean s0,
                      boolean d3,boolean d2,boolean d1,boolean d0){
    boolean ns0=!s0;
    boolean ns1=!s1;
    boolean a0=d0&ns1&ns0;
    boolean a1=d1&ns1& s0;
    boolean a2=d2& s1&ns0;
    boolean a3=d3& s1& s0;
    return a0|a1|a2|a3;
  }
  
  boolean selector4to1(String s,String d){
    if(s.length()!=2 || d.length()!=4){
      throw new RuntimeException("illegal data/select inputs!");
    }
    // selection bits
    boolean s1=s.charAt(0)=='0'?false:true;
    boolean s0=s.charAt(1)=='0'?false:true;
    // data bits
    boolean d3=d.charAt(0)=='0'?false:true;
    boolean d2=d.charAt(1)=='0'?false:true;
    boolean d1=d.charAt(2)=='0'?false:true;
    boolean d0=d.charAt(3)=='0'?false:true;
    return selector4to1(s1,s0,d3,d2,d1,d0);
  }
  
  void feed(boolean s1,boolean s0,
            boolean i3,boolean i2,boolean i1,boolean i0){
    this.s1=s1;
    this.s0=s0;
    d3=i3;
    d2=i2;
    d1=i1;
    d0=i0;
    q=selector4to1(s1,s0,d3,d2,d1,d0);
  }
  
  void display(){
    System.out.println("S4x1.out -> "+(q?"1":"0"));
  }
  
  // testing //
  public static void main(String[] args){
    Selector4to1 s=new Selector4to1();
    System.out.println("> "+s.selector4to1("00","0011"));
    System.out.println("> "+s.selector4to1("01","0011"));
    System.out.println("> "+s.selector4to1("10","0011"));
    System.out.println("> "+s.selector4to1("11","0011"));
  }
}
