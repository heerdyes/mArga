public class Selector8to1{
  boolean d7,d6,d5,d4,d3,d2,d1,d0;
  boolean s0,s1,s2;
  boolean q;
  
  Selector8to1(){}
  
  boolean selector8to1(boolean s2,boolean s1,boolean s0,
                              boolean d7,boolean d6,boolean d5,boolean d4,boolean d3,boolean d2,boolean d1,boolean d0){
    boolean ns0=!s0;
    boolean ns1=!s1;
    boolean ns2=!s2;
    boolean a0=d0&ns2&ns1&ns0;
    boolean a1=d1&ns2&ns1& s0;
    boolean a2=d2&ns2& s1&ns0;
    boolean a3=d3&ns2& s1& s0;
    boolean a4=d4& s2&ns1&ns0;
    boolean a5=d5& s2&ns1& s0;
    boolean a6=d6& s2& s1&ns0;
    boolean a7=d7& s2& s1& s0;
    return a0|a1|a2|a3|a4|a5|a6|a7;
  }
  
  boolean selector8to1(String s,String d){
    if(s.length()!=3 || d.length()!=8){
      throw new RuntimeException("illegal data/select inputs!");
    }
    // selection bits
    boolean s2=s.charAt(0)=='0'?false:true;
    boolean s1=s.charAt(1)=='0'?false:true;
    boolean s0=s.charAt(2)=='0'?false:true;
    // data bits
    boolean d7=d.charAt(0)=='0'?false:true;
    boolean d6=d.charAt(1)=='0'?false:true;
    boolean d5=d.charAt(2)=='0'?false:true;
    boolean d4=d.charAt(3)=='0'?false:true;
    boolean d3=d.charAt(4)=='0'?false:true;
    boolean d2=d.charAt(5)=='0'?false:true;
    boolean d1=d.charAt(6)=='0'?false:true;
    boolean d0=d.charAt(7)=='0'?false:true;
    return selector8to1(s2,s1,s0,d7,d6,d5,d4,d3,d2,d1,d0);
  }
  
  void feed(boolean s2,boolean s1,boolean s0,
            boolean i7,boolean i6,boolean i5,boolean i4,boolean i3,boolean i2,boolean i1,boolean i0){
    this.s2=s2;
    this.s1=s1;
    this.s0=s0;
    d7=i7;
    d6=i6;
    d5=i5;
    d4=i4;
    d3=i3;
    d2=i2;
    d1=i1;
    d0=i0;
    q=selector8to1(s2,s1,s0,d7,d6,d5,d4,d3,d2,d1,d0);
  }
  
  void display(){
    System.out.println("S8x1.out -> "+(q?"1":"0"));
  }
  
  // testing //
  public static void main(String[] args){
    Selector8to1 s=new Selector8to1();
    System.out.println("> "+s.selector8to1("000","00110001"));
    System.out.println("> "+s.selector8to1("001","00110001"));
    System.out.println("> "+s.selector8to1("010","00110001"));
    System.out.println("> "+s.selector8to1("011","00110001"));
    System.out.println("> "+s.selector8to1("100","00110001"));
    System.out.println("> "+s.selector8to1("101","00110001"));
    System.out.println("> "+s.selector8to1("110","00110001"));
    System.out.println("> "+s.selector8to1("111","00110001"));
  }
}
