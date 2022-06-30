import java.awt.geom.*;
import java.awt.*;

public class T
{
  double x;
  double y;
  double angle;
  boolean isdown;
  Graphics2D g;
  
  T()
  {
    this(0,0,0);
  }
  
  T(Graphics2D g2)
  {
    this(0,0,0);
    setg(g2);
  }
  
  T(double xx,double yy,double a)
  {
    x=xx;
    y=yy;
    angle=a;
    isdown=true;
    g=null;
  }
  
  void setg(Graphics2D gg)
  {
    g=gg;
  }
  
  private Line2D _fd(double r)
  {
    double x_=x+r*Math.cos(Math.toRadians(angle));
    double y_=y-r*Math.sin(Math.toRadians(angle));
    Line2D.Double ln=new Line2D.Double(x,y,x_,y_);
    x=x_;
    y=y_;
    return ln;
  }
  
  void fd(double r)
  {
    if(g==null)
    {
      throw new RuntimeException("[fd] turtle graphics: g is null!");
    }
    fd(r,g);
  }
  
  void fd(double r,Graphics2D g2)
  {
    if(isdown)
    {
      g2.draw(_fd(r));
    }
    else
    {
      _fd(r);
    }
  }
  
  void bk(double r,Graphics2D g2)
  {
    fd(-r,g2);
  }
  
  void bk(double r)
  {
    fd(-r);
  }
  
  void lt(double a)
  {
    angle+=a;
  }
  
  void rt(double a)
  {
    lt(-a);
  }
  
  void setxy(double xx,double yy)
  {
    x=xx;
    y=yy;
  }
  
  void seta(double a)
  {
    angle=a;
  }
  
  void pu()
  {
    isdown=false;
  }
  
  void pd()
  {
    isdown=true;
  }
}
