import java.util.Timer;
import java.util.TimerTask;

public class Ctr4b extends TimerTask
{
  long T;
  volatile boolean clk;
  DFlipFlop f1, f2, f3;

  public Ctr4b(long millis)
  {
    T = millis;
    clk = false;
    f1 = new DFlipFlop();
    f2 = new DFlipFlop();
    f3 = new DFlipFlop();
  }

  public void run()
  {
    clk = !clk;
    f1.setClk(clk);
    f2.setClk(!f1.q);
    f3.setClk(!f2.q);
    System.out.println((f3.q?"1":"0") + (f2.q?"1":"0") + (f1.q?"1":"0") + (!clk?"1":"0"));
  }

  /////////////////////////////////////
  // --------- for testing ----------//
  /////////////////////////////////////

  public static void main(String[] args)
  {
    Ctr4b c = new Ctr4b(400);
    Timer t = new Timer();

    t.scheduleAtFixedRate(c, c.T, c.T);
  }
}

