import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class box
{
  protected double x, y, w, h;
  protected Color fg, bg;
  
  public box(double xx, double yy, double ww, double hh)
  {
    x = xx;
    y = yy;
    w = ww;
    h = hh;
    fg = Color.rgb(0, 255, 0);
    bg = Color.rgb(0, 0, 0);
  }
  
  public void rndr(GraphicsContext gc)
  {
    gc.setFill(bg);
    gc.fillRect(x, y, w, h);
  }
}

