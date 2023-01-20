package com.fractalautomatawaveband.marga.wmg;

import java.util.*;
import java.io.*;
import java.awt.*;

public class DirWnd extends CookedWnd
{
  protected ArrayList<String> fnmlist;
  protected File cwd;
  
  public DirWnd(int x,int y,int w,int h,String t)
  {
    super(x,y,w,h,t);
    cwd=new File(".");
  }
  
  public void rndr(Graphics2D g2,boolean infocus)
  {
    super.rndr(g2,infocus);
    if(!minified)
    {
      int ctr=0;
      for(String f:cwd.list())
      {
        g2.drawString(f,x+mrgnleft,y+mrgntop+tbarht+ctr*12);
        ctr++;
      }
    }
  }
}

