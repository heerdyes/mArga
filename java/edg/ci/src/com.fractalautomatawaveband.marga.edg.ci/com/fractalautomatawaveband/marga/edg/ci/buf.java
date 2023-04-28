package com.fractalautomatawaveband.marga.edg.ci;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class buf
{
  String name;
  String filepath;
  StringBuffer data;
  ArrayList<Integer> newlines;
  int ptr;
  boolean capslock=false;
  
  public buf(String nm,String fp)
  {
    name=nm;
    filepath=fp;
    data=new StringBuffer("");
    newlines=new ArrayList<>();
    ptr=-1;
  }
  
  public void insc(KeyCode kc)
  {
    String c=kc.getName();
    String ac=c;
    if(c.equals("Caps Lock"))
    {
      capslock=!capslock;
      return;
    }
    
    if(c.equals("Space"))
    {
      ac=" ";
    }
    else if(c.equals("Enter"))
    {
      ac="\n";
      newlines.add(ptr);
    }
    else if(c.equals("Period"))
    {
      ac=".";
    }
    else if(c.equals("Comma"))
    {
      ac=",";
    }
    else if(c.equals("Slash"))
    {
      ac="/";
    }
    else if(kc.isLetterKey() && !capslock)
    {
      ac=c.toLowerCase();
    }
    data.append(ac);
    ptr+=1;
  }
    
  public void gotoln(int n)
  {
    //
  }
  
  public void cat()
  {
    System.out.println(data.toString());
  }
  
  public void rndr(GraphicsContext gc)
  {
    int prev=0;
    int lgap=25;
    int ctr=0;
    int mleft=80;
    int mlnum=12;
    int mtop=50;
    gc.setFill(Color.rgb(0,255,0));
    for(int nx : newlines)
    {
      gc.fillText(String.format("%03d: ", ctr), mlnum, mtop+lgap*ctr);
      gc.fillText(data.substring(prev,nx), mleft, mtop+lgap*ctr);
      prev=nx+1;
      ctr+=1;
    }
    gc.fillText(String.format("%03d: ", ctr), mlnum, mtop+lgap*ctr);
    gc.fillText(data.substring(prev, data.length()), mleft, mtop+lgap*ctr);
  }
}

