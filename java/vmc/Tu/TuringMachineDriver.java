import java.io.IOException;

public class TuringMachineDriver
{

  public static void main(String[] args)
  {

    TuringMachine tm = new TuringMachine(50);
    TuringMachineDashboard tmdash = new TuringMachineDashboard(tm);
    tmdash.dump();
    try
    {
      tmdash.shell();
    }
    catch (IOException e)
    {
      System.out.println("caught exception: " + e.getMessage());
    }

  }

}
