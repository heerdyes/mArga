package com.fractalautomatawaveband.marga.wmg;

import java.awt.Graphics2D;

interface Wnd {
  void sendkey(char c,int kc);
  void rndr(Graphics2D g2,boolean infocus);
  boolean containsPoint(int x,int y);
}

