import java.io.*;

public class u
{
  static BufferedReader br = null;
  static InputStreamReader isr = null;
  
  static BufferedReader getBR()
  {
    if(isr == null) isr = new InputStreamReader(System.in);
    if(br == null) br = new BufferedReader(isr);
    return br;
  }
  
  public static char[] tinit(int n, char x)
  {
    char[] tape = new char[n];
    for(int i = 0; i < n; i++)
    {
      tape[i] = x;
    }
    return tape;
  }
  
  public static void tdump(char[] tape, int cols)
  {
    for(int i = 0; i < tape.length; i++)
    {
      if(i % cols == 0) System.out.printf("[%03d:%03d]  ", i, i+cols-1);
      System.out.printf("%04d ", (int) tape[i]);
      if((i + 1) % cols == 0)
      {
        System.out.println();
      }
    }
    System.out.println();
  }
  
  public static void tsave(char[] tape, String fn)
  {
    try(FileWriter fw = new FileWriter(fn))
    {
      fw.write(tape.length);
      for(int i = 0; i < tape.length; i++)
      {
        fw.write(tape[i]);
      }
      fw.flush();
    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();
    }
  }
  
  public static char[] tload(String fn)
  {
    char[] tape = null;
    try(FileReader fr = new FileReader(fn))
    {
      int sz = fr.read();
      tape = new char[sz];
      for(int i = 0; i < sz; i++)
      {
        int z = fr.read();
        tape[i] = (char) z;
      }
    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();
    }
    finally
    {
      return tape;
    }
  }
  
  public static String input(String prompt) throws IOException
  {
    System.out.print(prompt);
    return getBR().readLine();
  }
}

