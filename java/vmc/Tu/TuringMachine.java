public class TuringMachine
{

  // the binary tape
  private boolean[] tape;

  // the machine head position
  private int headpos;

  // construction
  public TuringMachine(boolean[] tape)
  {
    super();
    this.tape = tape;
    headpos = 0;
  }

  public TuringMachine(int size)
  {
    super();
    tape = new boolean[size];
    wipe();
    headpos = 0;
  }

  // abilities
  public int size()
  {
    return tape.length;
  }

  private void wipe()
  {
    for (int i = 0; i < tape.length; i++)
    {
      tape[i] = false;
    }
  }

  public boolean read()
  {
    return tape[headpos];
  }

  public void write(boolean bit)
  {
    tape[headpos] = bit;
  }

  public void shl() throws Exception
  {
    if (shlImpossible())
    {
      throw new Exception("[TM] -> tried to shl while at A (head)");
    }
    headpos -= 1;
  }

  public void shr() throws Exception
  {
    if (shrImpossible())
    {
      throw new Exception("[TM] -> tried to shr while at Z (tail)");
    }
    headpos += 1;
  }

  // shift head to beginning
  public void sh2a()
  {
    headpos = 0;
  }

  // shift head to end
  public void sh2z()
  {
    headpos = size() - 1;
  }

  public boolean shlImpossible()
  {
    return (headpos == 0);
  }

  public boolean shrImpossible()
  {
    return (headpos == (size() - 1));
  }

  public int getheadpos()
  {
    return headpos;
  }

  public void setheadpos(int pos)
  {
    headpos = pos;
  }

}
