package com.fractalautomatawaveband.marga.edg;

import java.util.LinkedList;

public class Buf
{
  LinkedList<Character> data;
  Character cursor;

  public Buf()
  {
    data = new LinkedList<>();
    cursor = null;
  }

  public void insch(char c)
  {
    if (cursor == null)
    {
      cursor = c;
      data.add(cursor);
    }
  }
}
