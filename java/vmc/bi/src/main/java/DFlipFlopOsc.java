public class DFlipFlopOsc{
  boolean clk,d,q; // d->DI, q->DO

  public DFlipFlopOsc() {
    clk=d=q=false;
  }

  public void setClk(boolean v){
    boolean oldv=clk;
    clk=v;
    if(!oldv && v){
      q=d;
      d=!q;
    }
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
  }
  
  public static void main(String[] args){
    DFlipFlopOsc d1=new DFlipFlopOsc();
    d1.truth();
  }
}
