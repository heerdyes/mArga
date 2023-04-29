package com.fractalautomatawaveband.marga.edg.ci;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class buf
{
  String name;
  String filepath;
  StringBuffer data;
  int ptr;
  boolean capslock=false;
  boolean shouldrndr=true;
  Font fnt;
  int fntsz;
  
  public buf(String nm,String fp,String f)
  {
    name=nm;
    filepath=fp;
    data=new StringBuffer();
    ptr=0;
    fntsz=20;
    fnt=Font.font(f, fntsz);
  }
  
  void dumpfonts()
  {
    for(String fn:Font.getFontNames())
    {
      util.d(fn);
    }
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
    else if(c.equals("Backspace"))
    {
      ptr-=1;
      ac="";
    }
    else if(kc.isLetterKey() && !capslock)
    {
      ac=c.toLowerCase();
    }
    data.insert(ptr, ac);
    ptr+=ac.length();
  }
    
  public void gotoln(int n)
  {
    //
  }
  
  public void cat()
  {
    System.out.println(data.toString());
  }
  
  public void clear(GraphicsContext gc)
  {
    gc.setFill(Color.rgb(0,0,0));
    gc.fillRect(0,0,1280,720);
    gc.setStroke(Color.rgb(255,192,0,0.4));
    gc.strokeRect(1,1,1278,718);
  }
  
  public void rndr(GraphicsContext gc)
  {
    if(!shouldrndr)
    {
      return;
    }
    clear(gc);
    int prev=0;
    int lgap=25;
    int ctr=0;
    int mleft=80;
    int mlnum=12;
    int mtop=50;
    int nlcur=0;
    int nlnxt=data.indexOf("\n");
    
    gc.setFill(Color.rgb(0,255,0));
    gc.setFont(fnt);
    while(nlnxt!=-1)
    {
      gc.fillText(String.format("%03d: ", ctr), mlnum, mtop+lgap*ctr);
      gc.fillText(data.substring(nlcur, nlnxt), mleft, mtop+lgap*ctr);
      nlcur=nlnxt+1;
      nlnxt=data.indexOf("\n", nlcur);
      ctr+=1;
    }
    gc.fillText(String.format("%03d: ", ctr), mlnum, mtop+lgap*ctr);
    gc.fillText(data.substring(nlcur), mleft, mtop+lgap*ctr);
    shouldrndr=false;
  }
  
  public void enablerndr()
  {
    shouldrndr=true;
  }
}

