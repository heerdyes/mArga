package ed;

import java.awt.*;

public class T extends B
{
  SBuf sb;
  int mtp,mlt;
  int cw,ch;
  W w;
  
  public T(int ncols,String nm,int x_,int y_,int w_,int h_,String sfnt)
  {
    super(nm,x_,y_,w_,h_,sfnt);
    sb=new SBuf(ncols);
    mtp=15;
    mlt=3;
    cw=12;
    ch=12;
  }
  
  public void render(Graphics gx)
  {
    super.render(gx);
    gx.setFont(tf);
    gx.drawString(sb.str(),x+mlt,y+lnmrgn+mtp);
  }
  
  public void insch(char c)
  {
    sb.append(c);
  }
}

