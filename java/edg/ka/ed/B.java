package ed;

import java.awt.*;

public class B
{
  int x,y,w,h;
  int lnmrgn,tpmrgn;
  Color c;
  String name;
  Font tf;
  
  public B(String nm,int x_,int y_,int w_,int h_,String sfnt)
  {
    name=nm;
    x=x_;
    y=y_;
    w=w_;
    h=h_;
    lnmrgn=15;
    tpmrgn=12;
    c=new Color(26,202,230);
    tf=new Font(sfnt,Font.PLAIN,12);
  }
  
  public void render(Graphics gx)
  {
    gx.setColor(c);
    gx.drawRect(x,y,w,h);
    gx.setFont(tf);
    gx.drawString(name,x+3,y+tpmrgn);
    gx.drawLine(x,y+lnmrgn,x+w,y+lnmrgn);
  }
}

