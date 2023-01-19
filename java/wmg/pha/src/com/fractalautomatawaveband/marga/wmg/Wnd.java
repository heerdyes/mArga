package com.fractalautomatawaveband.marga.wmg;

import java.awt.Graphics2D;

interface Wnd
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
  boolean containsPoint(int x,int y);
}

