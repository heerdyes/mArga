package com.fractalautomatawaveband.marga.edg.pha.model;

public class CList
{
  protected CNode head, tail;

  public CList()
  {
    head = tail = null;
  }

  public CNode getHead()
  {
    return head;
  }

  public CNode getTail()
  {
    return tail;
  }

  public CList(String s)
  {
    for (int i = 0; i < s.length(); i++)
    {
      addToTail(new CNode(s.charAt(i)));
    }
  }

  public void dump()
  {
    for (CNode x = head; x != null; x = x.getNext())
    {
      x.dump();
    }
  }

  public CNode addToTail(char c)
  {
    return addToTail(new CNode(c));
  }

  public CNode addToHead(char c)
  {
    return addToHead(new CNode(c));
  }

  public CNode addToTail(CNode cn)
  {
    if (head == null)
    {
      // trust that cn does not have spurious links!
      head = tail = cn;
    }
    else
    {
      tail.setNext(cn);
      cn.setPrev(tail);
      tail = cn;
    }
    return tail;
  }

  public CNode addToHead(CNode cn)
  {
    if (head == null)
    {
      // trust that cn does not have spurious links!
      head = tail = cn;
    }
    else
    {
      cn.setNext(head);
      head.setPrev(cn);
      head = cn;
    }
    return head;
  }

  public CNode addAfterNode(CNode cn, char c)
  {
    if (cn == null)
    {
      return null;
    }
    CNode tmp = cn.getNext();
    if (tmp == null)
    {
      return addToTail(c);
    }
    CNode ins = new CNode(c);
    cn.setNext(ins);
    ins.setPrev(cn);
    ins.setNext(tmp);
    tmp.setPrev(ins);
    return ins;
  }

  public void addBeforeNode(CNode cn, char c)
  {
    if (cn == null)
    {
      addToHead(c);
      return;
    }
    CNode tmp = cn.getPrev();
    if (tmp == null)
    {
      addToHead(c);
      return;
    }
    CNode ins = new CNode(c);
    cn.setPrev(ins);
    ins.setPrev(tmp);
    ins.setNext(cn);
    tmp.setNext(ins);
  }

  public char popHead()
  {
    if (head == null)
    {
      return '\0';
    }
    if (head == tail)
    {
      char x = head.getSymbol();
      head = tail = null;
      return x;
    }
    char x = head.getSymbol();
    CNode tmp = head.getNext();
    tmp.setPrev(null);
    head.setNext(null);
    head = tmp;
    return x;
  }

  public char popTail()
  {
    if (head == null)
    {
      return '\0';
    }
    if (head == tail)
    {
      char x = tail.getSymbol();
      head = tail = null;
      return x;
    }
    char x = tail.getSymbol();
    CNode tmp = tail.getPrev();
    tmp.setNext(null);
    tail.setPrev(null);
    tail = tmp;
    return x;
  }

  public CNode rwTillChar(CNode cursor, char c)
  {
    for (; cursor.hasPrev(); cursor = cursor.getPrev())
    {
      if (cursor.getSymbol() == c)
      {
        cursor = cursor.getPrev();
        break;
      }
    }
    return cursor;
  }

  public CNode ffTillChar(CNode cursor, char c)
  {
    for (; cursor.hasNext(); cursor = cursor.getNext())
    {
      if (cursor.getSymbol() == c)
      {
        cursor = cursor.getNext();
        break;
      }
    }
    return cursor;
  }

  public char deleteNode(CNode cn)
  {
    char x = cn.getSymbol();
    if (!cn.hasPrev())
    {
      return popHead();
    }
    else if (!cn.hasNext())
    {
      return popTail();
    }
    else
    {
      CNode p = cn.getPrev();
      CNode n = cn.getNext();
      p.setNext(n);
      n.setPrev(p);
      cn.setNext(null);
      cn.setPrev(null);
    }
    return x;
  }
}
