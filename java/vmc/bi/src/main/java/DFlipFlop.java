public class DFlipFlop{
  boolean clk,d,q; // d->DI, q->DO, clk->w

  public DFlipFlop() {
    clk=d=q=false;
  }

  public void setClk(boolean v){
    // modeling d-type flip flop requires advanced math modeling
    // and there are atleast a couple of papers on this
    // hacking this from truth table for now
    clk=v;
    if(clk){
      q=d;
    }
  }
  
  // trigger to write
  void w(boolean f){
    d=f;
    setClk(true);setClk(false);
  }
  
  void w(int f){
    w(f==0?false:true);
  }
  
  void display(){
    System.out.printf("[%s\n]",this);
  }
  
  public String toString(){
    return String.format("%s",q?"1":"0");
  }
  
  void truth(){
    System.out.println("D Clk Q Q-bar");
    System.out.println("-------------");
    d=false;setClk(true);
    System.out.printf("%d %d   %d %d\n",d?1:0,clk?1:0,q?1:0,q?0:1);
    d=true;setClk(true);
    System.out.printf("%d %d   %d %d\n",d?1:0,clk?1:0,q?1:0,q?0:1);
    d=false;setClk(false);
    System.out.printf("%d %d   %d %d\n",d?1:0,clk?1:0,q?1:0,q?0:1);
    d=true;setClk(false);
    System.out.printf("%d %d   %d %d\n",d?1:0,clk?1:0,q?1:0,q?0:1);
    d=false;setClk(true);
    System.out.printf("%d %d   %d %d\n",d?1:0,clk?1:0,q?1:0,q?0:1);
  }
  
  // testing //
  public static void main(String[] args){
    DFlipFlop d1=new DFlipFlop();
    d1.truth();
  }
}
