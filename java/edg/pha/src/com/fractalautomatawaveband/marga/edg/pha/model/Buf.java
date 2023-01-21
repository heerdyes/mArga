package com.fractalautomatawaveband.marga.edg.pha.model;

import java.util.HashSet;

import com.fractalautomatawaveband.marga.edg.pha.event.BufObs;

public class Buf
{
  protected CList data;
  protected CNode cursor;
  protected HashSet<BufObs> observers;

  public Buf()
  {
    data = new CList();
    cursor = null;
    observers = new HashSet<>();
  }

  public CNode getCursor()
  {
    return cursor;
  }

  public void setCursor(CNode cursor)
  {
    this.cursor = cursor;
  }

  public CList getData()
  {
    return data;
  }

  public void insch(char c)
  {
    if (cursor == null)
    {
      cursor = data.addToTail(c);
    }
    else
    {
      cursor = data.addAfterNode(cursor, c);
    }
  }

  void rwTillChar(char c)
  {
    for (; cursor.hasPrev(); cursor = cursor.getPrev())
    {
      if (cursor.getSymbol() == c)
      {
        cursor = cursor.getPrev();
        break;
      }
    }
  }

  void ffTillChar(char c)
  {
    for (; cursor.hasNext(); cursor = cursor.getNext())
    {
      if (cursor.getSymbol() == c)
      {
        cursor = cursor.getNext();
        break;
      }
    }
  }

  char delnode()
  {
    char x = cursor.getSymbol();
    CNode old = cursor;
    if (cursor.hasNext())
    {
      cursor = cursor.getNext();
    }
    else
    {
      cursor = cursor.getPrev();
    }
    data.deleteNode(old);
    return x;
  }

  public void splkeydown(int kc)
  {
    System.out.println("kc -> " + kc);
    if (kc == 37 && cursor.hasPrev())
    {
      cursor = cursor.getPrev();
    }
    else if (kc == 39 && cursor.hasNext())
    {
      cursor = cursor.getNext();
    }
    else if (kc == 38 && cursor.hasPrev())
    {
      rwTillChar('\n');
    }
    else if (kc == 40 && cursor.hasNext())
    {
      ffTillChar('\n');
    }
    else if (kc == 127)
    {
      delnode();
    }
  }

  public boolean issplkey(int kc)
  {
    return kc == 16 || kc == 18 || (kc >= 37 && kc <= 40) || kc == 127;
  }

  public void keydown(int kc, char c)
  {
    System.out.println("kc: " + kc);
    if (issplkey(kc))
    {
      splkeydown(kc);
    }
    else
    {
      insch(c);
    }
    broadcastSignal(c);
  }

  public void broadcastSignal(Object msg)
  {
    for (BufObs bo : observers)
    {
      bo.signal(this, msg);
    }
  }

  public void addObs(BufObs bo)
  {
    observers.add(bo);
  }

  public void rmObs(BufObs bo)
  {
    if (observers.size() > 0)
    {
      observers.remove(bo);
    }
  }

  public void dump()
  {
    data.dump();
    System.out.println();
  }
}
