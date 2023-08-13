import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class u
{
  public static boolean cin(char c, String set)
  {
    for(int i = 0; i < set.length(); i++)
    {
      if(set.charAt(i) == c)
      {
        return true;
      }
    }
    return false;
  }
  
  public static boolean sin(String s, String[] arr)
  {
    for(int i = 0; i < arr.length; i++)
    {
      if(s.equals(arr[i]))
      {
        return true;
      }
    }
    return false;
  }
  
  public static void d(String msg)
  {
    System.out.println("[DEBUG] "+msg);
  }
  
  public static void d(char msg)
  {
    System.out.printf("%c", msg);
  }
  
  public static void d(String[] msgs)
  {
    d("listing string array contents...");
    for(int i = 0; i < msgs.length; i++)
    {
      d(String.format("[%02d] %s", i, msgs[i]));
    }
  }
  
  public static void a(String msg)
  {
    System.out.println("[ALERT] "+msg);
  }
  
  public static void e(Exception exc)
  {
    System.out.println("[EXCEPTION] " + exc.getMessage());
  }
}

