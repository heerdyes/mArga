public class FullAdder{
  boolean ci,a,b;
  boolean s,co;
  HalfAdder ha1,ha2;
  
  FullAdder(){
    ha1=new HalfAdder();
    ha2=new HalfAdder();
  }
  
  void add(){
    ha1.feed(a,b);
    ha2.feed(ci,ha1.s);
    s=ha2.s;
    co=ha2.co|ha1.co;
  }
  
  void feed(int a,int b,int cin){
    feed(a==0?false:true,b==0?false:true,cin==0?false:true);
  }
  
  void feed(boolean a,boolean b,boolean cin){
    ci=cin;
    this.a=a;
    this.b=b;
    add();
  }
  
  void display(){
    System.out.printf("%d %d %d %d %d\n",a?1:0,b?1:0,ci?1:0,s?1:0,co?1:0);
  }
  
  public static void main(String[] args){
    FullAdder fa=new FullAdder();
    System.out.println("A B CI S CO");
    System.out.println("-----------");
    fa.feed(0,0,0);
    fa.display();
    fa.feed(0,1,0);
    fa.display();
    fa.feed(1,0,0);
    fa.display();
    fa.feed(1,1,0);
    fa.display();
    fa.feed(0,0,1);
    fa.display();
    fa.feed(0,1,1);
    fa.display();
    fa.feed(1,0,1);
    fa.display();
    fa.feed(1,1,1);
    fa.display();
  }
}
