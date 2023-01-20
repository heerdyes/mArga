package com.fractalautomatawaveband.marga.wmg.wnd;

import java.util.*;
import java.io.*;
import java.awt.*;
import static com.fractalautomatawaveband.marga.wmg.util.L.d;
import com.fractalautomatawaveband.marga.wmg.evt.*;

public class DirWnd extends CookedWnd
{
  protected ArrayList<File> flist;
  protected File cwd;
  protected int cursor;
  
  public DirWnd(String id,int x,int y,int w,int h,String t)
  {
    super(id,x,y,w,h,t);
    flist=new ArrayList<>();
    cwd=new File(".");
    cursor=0;
    reloaddir();
  }
  
  public void rndr(Graphics2D g2,boolean infocus)
  {
    super.rndr(g2,infocus);
    if(!minified)
    {
      int ctr=0;
      for(File f:flist)
      {
        String fn=f.getName();
        String prefix=cursor==ctr?"*":" ";
        g2.drawString(prefix+fn,x+mrgnleft,y+mrgntop+tbarht+ctr*12);
        ctr++;
      }
    }
  }
  
  public void reloaddir()
  {
    flist.clear();
    for(File s:cwd.listFiles())
    {
      flist.add(s);
    }
  }
  
  void broadcastMsg()
  {
    for(WndObs wo:observers)
    {
      try
      {
        String fcpath=flist.get(cursor).getCanonicalPath();
        wo.signal(this,"editfile:"+fcpath);
      }
      catch(IOException ioe)
      {
        d("IOException", ioe.getMessage());
      }
    }
  }
  
  public void onkeydown(char c,int kc)
  {
    if(cursor<flist.size()-1 && kc==40)
    {
      cursor++;
    }
    else if(cursor>0 && kc==38)
    {
      cursor--;
    }
    else if(kc==116)
    {
      reloaddir();
    }
    else if(kc==10)
    {
      File sel=flist.get(cursor);
      if(sel.isDirectory())
      {
        cwd=sel;
        reloaddir();
        cursor=0;
      }
      else
      {
        broadcastMsg();
      }
    }
  }
}

