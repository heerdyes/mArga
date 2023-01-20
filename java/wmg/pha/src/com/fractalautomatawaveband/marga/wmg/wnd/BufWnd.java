package com.fractalautomatawaveband.marga.wmg.wnd;

import java.util.*;
import java.io.*;
import java.awt.*;
import static com.fractalautomatawaveband.marga.wmg.util.L.d;
import com.fractalautomatawaveband.marga.wmg.evt.*;

public class BufWnd extends CookedWnd
{
  protected File f;
  protected ArrayList<String> lines;
  
  public BufWnd(String id,int x,int y,int w,int h,String fpath)
  {
    super(id,x,y,w,h,id);
    lines=new ArrayList<>();
    f=new File(fpath);
    loadlines();
  }
  
  public void loadlines()
  {
    if(f==null) { return; }
    try(FileReader fr=new FileReader(f);
        BufferedReader br=new BufferedReader(fr))
    {
      lines.clear();
      for(String ln=br.readLine(); ln!=null; ln=br.readLine())
      {
        lines.add(ln);
      }
    }
    catch(IOException ioe)
    {
      d("IOException", ioe.getMessage());
    }
  }
  
  public void rndr(Graphics2D g2,boolean infocus)
  {
    super.rndr(g2,infocus);
    if(!minified && f!=null)
    {
      int ctr=0;
      for(String ln:lines)
      {
        g2.drawString(ln,x+mrgnleft,y+mrgntop+tbarht+ctr*12);
        ctr++;
      }
    }
  }
  
  public void setFile(File x) { f=x; }
}
