import java.io.*;

public class programr
{
  static char[] tape;
  static BufferedReader br;
  static InputStreamReader isr;
  static String tapefile = "tmp.tape";
  
  static void tinit(int n, char x)
  {
    tape = new char[n];
    for(int i = 0; i < n; i++)
    {
      tape[i] = x;
    }
  }
  
  static void tdump()
  {
    for(int i = 0; i < tape.length; i++)
    {
      System.out.printf("%03d  ", (int) tape[i]);
      if((i + 1) % 4 == 0)
      {
        System.out.println();
      }
    }
    System.out.println();
  }
  
  static void tsave(String fn)
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
  
  static void tload(String fn)
  {
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
  }
  
  static String input(String prompt) throws IOException
  {
    System.out.print(prompt);
    isr = new InputStreamReader(System.in);
    br = new BufferedReader(isr);
    return br.readLine();
  }
  
  static void repl()
  {
    reploop:
    for(;;)
    {
      try
      {
        String s = input("tape> ");
        String[] parts = s.split(" ");
        int loc;
        switch(parts[0])
        {
          case "mem":
            int n = Integer.parseInt(parts[1]);
            tinit(n, '\0');
            break;
          case "t":
            tdump();
            break;
          case "w":
            loc = Integer.parseInt(parts[1]);
            int payload = Integer.parseInt(parts[2]);
            tape[loc] = (char) payload;
            break;
          case "r":
            loc = Integer.parseInt(parts[1]);
            System.out.printf("%d\n", (int) tape[loc]);
            break;
          case "s":
            if(parts.length == 1) { tsave(tapefile); }
            else { tsave(parts[1]); }
            break;
          case "l":
            if(parts.length == 1) { System.out.println("[error] need 1 arg: filename!"); }
            else { tload(parts[1]); }
            break;
          case "q":
            System.out.println("bye!");
            break reploop;
          default:
            System.out.println("unknown cmd: "+parts[0]);
        }
      }
      catch(IOException ioe)
      {
        ioe.printStackTrace();
      }
    }
  }
  
  public static void main(String... args)
  {
    repl();
  }
}

