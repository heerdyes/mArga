package com.fractalautomatawaveband.marga.wmg.wnd;

import java.awt.Graphics2D;
import com.fractalautomatawaveband.marga.wmg.evt.*;

public interface Wnd
{
  void onkeyup(char c,int kc);
  void onkeydown(char c,int kc);
  void onclick(int x,int y);
  void rndr(Graphics2D g2,boolean infocus);
  int getX();
  int getY();
  int getW();
  int getH();
  void setXY(int x,int y);
  void setWH(int w,int h);
  boolean containsPoint(int x,int y);
  void cleanup();
  void addWndObserver(WndObs wo);
  void removeWndObserver(WndObs wo);
  String getID();
  void setID(String id);
}

