import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TuringMachineDashboard
{

  private TuringMachine tm;

  public TuringMachineDashboard(TuringMachine tm)
  {
    this.tm = tm;
  }

  // capabilities
  public void dump()
  {
    // push old headpos
    int oldheadpos = tm.getheadpos();

    tm.sh2a();
    System.out.printf("[");
    while (!tm.shrImpossible())
    {
      try
      {
        System.out.printf(tm.read() ? "1" : "0");
        tm.shr();
      }
      catch (Exception e)
      {
        System.err.println("[TM] -> caught exception: " + e.getMessage());
      }
    } // end while
    System.out.printf("]\n");

    // pop old headpos
    tm.setheadpos(oldheadpos);

    // show location of machine head
    System.out.print(" ");
    for (int i = 0; i < tm.getheadpos(); i++, System.out.print(" "))
      ;
    System.out.println("^");
  } // end dump

  // a control panel shell
  public void shell() throws IOException
  {
    System.out.println("starting tmsh...");
    BufferedReader shbr = null;
    try
    {
      shbr = new BufferedReader(new InputStreamReader(System.in));
      for (;;)
      {
        System.out.print("tmsh >> ");
        int procresult = process(shbr.readLine());
        if (procresult == -1)
        {
          System.out.println("\nexiting tmsh...");
          break;
        }
        if (procresult == 1)
        {
          System.out.println("an exception occurred!");
        }
      }
    }
    catch (Exception e)
    {
      System.out.println("caught exception: " + e.getMessage());
    }
    finally
    {
      if (shbr != null)
      {
        shbr.close();
      }
    }
  }

  private int process(String cmd)
  {
    // cmd result
    int result = 0;
    // process all shell commands
    if (cmd.equals("exit"))
    {
      result = -1;
    }
    if (cmd.equals("dump"))
    {
      dump();
      result = 0;
    }

    // machine head movements
    if (cmd.equals("shr"))
    {
      try
      {
        tm.shr();
        result = 0;
      }
      catch (Exception e)
      {
        System.err
            .println("TuringMachineDashboard->process(): caught exception: "
                + e.getMessage());
        result = 1;
      }
    }
    if (cmd.equals("shl"))
    {
      try
      {
        tm.shl();
        result = 0;
      }
      catch (Exception e)
      {
        System.err
            .println("TuringMachineDashboard->process(): caught exception: "
                + e.getMessage());
        result = 1;
      }
    }
    if (cmd.equals("sh2a"))
    {
      tm.sh2a();
      result = 0;
    }
    if (cmd.equals("sh2z"))
    {
      tm.sh2z();
      result = 0;
    }

    // read from tape
    if (cmd.equals("r"))
    {
      System.out.println(tm.read());
      result = 0;
    }

    // write to tape
    if (cmd.equals("w 0"))
    {
      tm.write(false);
      result = 0;
    }
    if (cmd.equals("w 1"))
    {
      tm.write(true);
      result = 0;
    }

    // return the result
    return result;
  }

}
