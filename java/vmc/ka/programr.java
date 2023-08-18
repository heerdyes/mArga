import java.io.*;

public class programr
{
  static char[] tape;
  static String tapefile = "tmp.tape";
  
  static void repl()
  {
    reploop:
    for(;;)
    {
      try
      {
        String s = u.input("tape> ");
        String[] parts = s.split(" ");
        int loc;
        switch(parts[0])
        {
          case "mem":
            int n = Integer.parseInt(parts[1]);
            tape = u.tinit(n, '\0');
            break;
          case "t":
            u.tdump(tape, 4);
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
            if(parts.length == 1) { u.tsave(tape, tapefile); }
            else { u.tsave(tape, parts[1]); }
            break;
          case "l":
            if(parts.length == 1) { System.out.println("[error] need 1 arg: filename!"); }
            else { tape = u.tload(parts[1]); }
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

