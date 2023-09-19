package com.fractalautomatawaveband.marga.edg.pha.model;

public class CNode
{
  protected char symbol;
  protected CNode prev, next;

  public CNode(char sym)
  {
    symbol = sym;
    prev = next = null;
  }

  public boolean hasPrev()
  {
    return prev != null;
  }

  public boolean hasNext()
  {
    return next != null;
  }

  public void dump()
  {
    System.out.printf("[%c] ", symbol);
  }

  public char getSymbol()
  {
    return symbol;
  }

  public void setSymbol(char s)
  {
    symbol = s;
  }

  public CNode getPrev()
  {
    return prev;
  }

  public void setPrev(CNode prev)
  {
    this.prev = prev;
  }

  public CNode getNext()
  {
    return next;
  }

  public void setNext(CNode next)
  {
    this.next = next;
  }
}
