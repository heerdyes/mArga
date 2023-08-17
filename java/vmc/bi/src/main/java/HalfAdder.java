public class HalfAdder{
  boolean a,b;
  boolean s,co;
  
  boolean xor(boolean x,boolean y){
    return (x|y)&(!(x&y));
  }
  
  void add(){
    s=xor(a,b);// xor
    co=a&b;
  }
  
  void feed(int a,int b){
    feed(a==0?false:true,b==0?false:true);
  }
  
  void feed(boolean a,boolean b){
    this.a=a;
    this.b=b;
    add();
  }
  
  void display(){
    System.out.printf("%d %d %d %d\n",a?1:0,b?1:0,s?1:0,co?1:0);
  }
  
  public static void main(String[] args){
    HalfAdder ha=new HalfAdder();
    System.out.println("A B S CO");
    System.out.println("--------");
    ha.feed(0,0);
    ha.display();
    ha.feed(0,1);
    ha.display();
    ha.feed(1,0);
    ha.display();
    ha.feed(1,1);
    ha.display();
  }
}
