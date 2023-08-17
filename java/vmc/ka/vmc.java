public class vmc
{
  static char[] tape;
  static char r1, r2, r3, r4;
  static char pc;
  
  static void tinit(int n)
  {
    tape = new char[n];
    for(int i = 0; i < n; i++)
    {
      tape[i] = (char)i;
    }
  }
  
  static void tdump()
  {
    for(int i = 0; i < tape.length; i++)
    {
      System.out.printf("%03d  ", (int)tape[i]);
      if((i+1) % 8 == 0)
      {
        System.out.println();
      }
    }
  }
  
  static void regdump()
  {
    System.out.println("--- registers ---");
    System.out.println("r1 = " + (int) r1);
    System.out.println("r2 = " + (int) r2);
    System.out.println("r3 = " + (int) r3);
    System.out.println("r4 = " + (int) r4);
  }
  
  static void cycle()
  {
    for(; pc < tape.length; )
    {
      int cmd = (int) tape[pc];
      int arg0 = (int) tape[pc+1];
      int arg1 = (int) tape[pc+2];
      int arg2 = (int) tape[pc+3];
      switch(cmd)
      {
        case 20: // movi
          if(arg0 == 1) r1 = (char) arg1;
          else if(arg0 == 2) r2 = (char) arg1;
          else if(arg0 == 3) r3 = (char) arg1;
          else if(arg0 == 4) r4 = (char) arg1;
          break;
      }
      pc = (char) ((int)pc + 4);
    }
  }

  public static void main(String... args)
  {
    tinit(24);
    tdump();
  }
}

