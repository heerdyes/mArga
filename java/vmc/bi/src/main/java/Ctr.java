import java.util.Timer;
import java.util.TimerTask;

public class Ctr extends TimerTask
{
  long T;
  volatile boolean clk;
  DFlipFlop bit;

  public Ctr(long millis)
  {
    T = millis;
    clk = false;
    bit = new DFlipFlop();
  }

  public void run()
  {
    clk = !clk;
    bit.setClk(clk);
    System.out.println((bit.q?"1":"0") + (!clk?"1":"0"));
  }

  /////////////////////////////////////
  // --------- for testing ----------//
  /////////////////////////////////////

  public static void main(String[] args)
  {
    Ctr c = new Ctr(500);
    Timer t = new Timer();

    t.scheduleAtFixedRate(c, c.T, c.T);
  }
}

