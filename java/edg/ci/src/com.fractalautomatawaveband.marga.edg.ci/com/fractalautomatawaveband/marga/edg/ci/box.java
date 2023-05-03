package com.fractalautomatawaveband.marga.edg.ci;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class box
{
  double x,y,w,h;
  Color fg,bg;
  boolean shouldrndr;
  
  public box(double xx,double yy,double ww,double hh)
  {
    x=xx;
    y=yy;
    w=ww;
    h=hh;
    fg=Color.rgb(0,255,0);
    bg=Color.rgb(0,0,0);
    shouldrndr=true;
  }
  
  public void rndr(GraphicsContext gc)
  {
    if(!shouldrndr)
    {
      return;
    }
    gc.setFill(bg);
    gc.setStroke(fg);
    gc.strokeRect(x,y,w,h);
    shouldrndr=false;
  }
  
  public void enablerndr()
  {
    shouldrndr=true;
  }
}

