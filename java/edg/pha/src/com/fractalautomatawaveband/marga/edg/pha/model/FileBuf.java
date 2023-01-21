package com.fractalautomatawaveband.marga.edg.pha.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileBuf extends Buf
{
  protected File f;

  public FileBuf(String fn)
  {
    f = new File(fn);
  }

  public void loadbuf()
  {
    if (f == null)
    {
      return;
    }
    try (FileReader fr = new FileReader(f))
    {
      for (int x = fr.read(); x != -1; x = fr.read())
      {
        insch((char) x);
      }
    }
    catch (IOException ioe)
    {
      System.out.println(ioe.getMessage());
    }
  }
}
