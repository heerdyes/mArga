package com.fractalautomatawaveband.marga.wmg.evt;

import com.fractalautomatawaveband.marga.wmg.wnd.Wnd;

public interface WndObs
{
  void signal(Wnd src,String msg);
}

