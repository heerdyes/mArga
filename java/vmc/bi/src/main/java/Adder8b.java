public class Adder8b{
  boolean a0,a1,a2,a3,a4,a5,a6,a7;
  boolean b0,b1,b2,b3,b4,b5,b6,b7;
  boolean s0,s1,s2,s3,s4,s5,s6,s7;
  boolean co,ci;
  FullAdder fa0,fa1,fa2,fa3,fa4,fa5,fa6,fa7;
  
  Adder8b(){
    fa0=new FullAdder();
    fa1=new FullAdder();
    fa2=new FullAdder();
    fa3=new FullAdder();
    fa4=new FullAdder();
    fa5=new FullAdder();
    fa6=new FullAdder();
    fa7=new FullAdder();
  }
  
  void feed(byte a0,byte a1,byte a2,byte a3,byte a4,byte a5,byte a6,byte a7,
            byte b0,byte b1,byte b2,byte b3,byte b4,byte b5,byte b6,byte b7,
            byte ci){
    feed(a0==0?false:true,a1==0?false:true,a2==0?false:true,a3==0?false:true,a4==0?false:true,a5==0?false:true,a6==0?false:true,a7==0?false:true,
         b0==0?false:true,b1==0?false:true,b2==0?false:true,b3==0?false:true,b4==0?false:true,b5==0?false:true,b6==0?false:true,b7==0?false:true,
         ci==0?false:true);
  }
  
  void feed(boolean a0,boolean a1,boolean a2,boolean a3,boolean a4,boolean a5,boolean a6,boolean a7,
            boolean b0,boolean b1,boolean b2,boolean b3,boolean b4,boolean b5,boolean b6,boolean b7,
            boolean ci){
    this.a0=a0;
    this.a1=a1;
    this.a2=a2;
    this.a3=a3;
    this.a4=a4;
    this.a5=a5;
    this.a6=a6;
    this.a7=a7;
    this.b0=b0;
    this.b1=b1;
    this.b2=b2;
    this.b3=b3;
    this.b4=b4;
    this.b5=b5;
    this.b6=b6;
    this.b7=b7;
    this.ci=ci;
    add();
  }
  
  void add(){
    fa0.feed(a0,b0,ci);
    s0=fa0.s;
    fa1.feed(a1,b1,fa0.co);
    s1=fa1.s;
    fa2.feed(a2,b2,fa1.co);
    s2=fa2.s;
    fa3.feed(a3,b3,fa2.co);
    s3=fa3.s;
    fa4.feed(a4,b4,fa3.co);
    s4=fa4.s;
    fa5.feed(a5,b5,fa4.co);
    s5=fa5.s;
    fa6.feed(a6,b6,fa5.co);
    s6=fa6.s;
    fa7.feed(a7,b7,fa6.co);
    s7=fa7.s;
    co=fa7.co;
  }
  
  void display(){
    System.out.printf("%d %d %d %d %d %d %d %d\n",s7?1:0,s6?1:0,s5?1:0,s4?1:0,s3?1:0,s2?1:0,s1?1:0,s0?1:0);
  }
}
