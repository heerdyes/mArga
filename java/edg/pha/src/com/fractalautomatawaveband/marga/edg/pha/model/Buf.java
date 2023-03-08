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

  public void owrch(char c)
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

  public void insch(char c)
  {
    if (cursor == null)
    {
      cursor = data.addToTail(c);
    }
    else
    {
      data.addBeforeNode(cursor, c);
    }
  }

  void delnode()
  {
    if (cursor.hasNext())
    {
      CNode tmp = cursor.getNext();
      data.deleteNode(tmp);
    }
  }

  public void splkeydown(int kc)
  {
    System.out.printf("kc -> %d, hasprev: %s\n", kc, cursor.hasPrev());
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
      cursor = data.rwTillChar(cursor, '\n');
    }
    else if (kc == 40 && cursor.hasNext())
    {
      cursor = data.ffTillChar(cursor, '\n');
    }
    else if (kc == 127)
    {
      delnode();
    }
    System.out.printf("cursor symbol: %c\n", cursor.getSymbol());
  }

  public boolean issplkey(int kc)
  {
    return kc == 16 || kc == 18 || (kc >= 37 && kc <= 40) || kc == 127;
  }

  public void keydown(int kc, char c)
  {
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
