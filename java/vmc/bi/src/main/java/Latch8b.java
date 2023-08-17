public class Latch8b{
  boolean clk;
  DFlipFlop dq0,dq1,dq2,dq3,dq4,dq5,dq6,dq7;
  
  public Latch8b(){
    dq0=new DFlipFlop();
    dq1=new DFlipFlop();
    dq2=new DFlipFlop();
    dq3=new DFlipFlop();
    dq4=new DFlipFlop();
    dq5=new DFlipFlop();
    dq6=new DFlipFlop();
    dq7=new DFlipFlop();
  }
  
  void feed(int clk,
            int d7,int d6,int d5,int d4,int d3,int d2,int d1,int d0){
    feed(clk==0?false:true,
         d7==0?false:true,d6==0?false:true,d5==0?false:true,d4==0?false:true,d3==0?false:true,d2==0?false:true,d1==0?false:true,d0==0?false:true);
  }
  
  void feed(boolean clk,
            boolean d7,boolean d6,boolean d5,boolean d4,boolean d3,boolean d2,boolean d1,boolean d0){
    if(clk){
      dq0.w(d0);
      dq1.w(d1);
      dq2.w(d2);
      dq3.w(d3);
      dq4.w(d4);
      dq5.w(d5);
      dq6.w(d6);
      dq7.w(d7);
    }
  }
  
  void display(){
    System.out.println("L8b -> "+dq7+dq6+dq5+dq4+dq3+dq2+dq1+dq0);
  }
  
  // testing //
  public static void main(String[] args){
    Latch8b mem8b=new Latch8b();
    mem8b.feed(1,1,0,1,0,1,1,0,0);
    mem8b.display();
    mem8b.feed(0,0,0,0,0,0,0,0,0);
    mem8b.display();
    mem8b.feed(1,0,0,0,0,0,0,0,0);
    mem8b.display();
  }
}
