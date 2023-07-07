package com.fractalautomatawaveband.marga.edg.ca;

public class region implements keysensor
{
  int r;
  int c;
  int w;
  int h;
  grid parent;
  
  public region(grid p,int rr,int cc,int ww,int hh)
  {
    r=rr;
    c=cc;
    w=ww;
    h=hh;
    parent=p;
  }
  
  public void keydown(String kn)
  {}
  
  public void keyup(String kn)
  {}
}

