package com.fractalautomatawaveband.marga.edg.ci;

import java.util.TimerTask;

public class kAlapAlaka extends TimerTask
{
  edg mref;
  long T;

  kAlapAlaka(edg mref,long millis)
  {
    this.mref=mref;
    T=millis;
  }

  public void run()
  {
    mref.draw();
  }

}

