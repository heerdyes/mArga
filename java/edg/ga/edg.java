import java.io.*;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import static java.lang.Math.sqrt;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.log;
import static java.lang.Math.exp;

public class edg extends Application
{
  protected GraphicsContext gc;
  protected Canvas c;
  protected Scene s;
  protected String dlevel = "INFO";
  protected volatile boolean dragging = false;
  protected double pmouseX = -1.0;
  protected double pmouseY = -1.0;
  protected double t = 0.0;
  protected double dt = 0.05;
  protected long frameinterval;
  protected int mouseX = 0;
  protected int mouseY = 0;
  double cx = 0;
  double cy = 0;
  double r = 300;
  double z = 7;
  double d = 4;
  double ouf = 0.125;
  double inf = 0.125;
  boolean sighalt = false;
  buf g;
  static String file;
  static final int WIDTH = 1280;
  static final int HEIGHT = 720;
  
  void setupMouseSensor()
  {
    if(c==null)
    {
      throw new RuntimeException("canvas found to be null!");
    }
    
    c.setOnMousePressed(e->mousePressed(e));
    c.setOnMouseReleased(e->mouseReleased(e));
    c.setOnMouseMoved(e->mouseMoved(e));
    c.setOnMouseDragged(e->mouseDragged(e));
    
    c.setFocusTraversable(true);
  }

  void setupKeyboardSensor()
  {
    s.setOnKeyPressed(e->keyPressed(e));
    s.setOnKeyReleased(e->keyReleased(e));
  }
  
  void info()
  {
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    u.d("[java] "+javaVersion);
    u.d("[javafx] "+javafxVersion);
    if(javaVersion == null || javafxVersion == null)
    {
      throw new RuntimeException("java or javafx version is empty!");
    }
  }

  @Override
  public void start(Stage stage)
  {
    info();
    setup();
    Group root = new Group();
    root.getChildren().add(c);
    s = new Scene(root);
    setupMouseSensor();
    setupKeyboardSensor();
    stage.initStyle(StageStyle.UNDECORATED);
    stage.setScene(s);
    stage.show();
  }

  void setup()
  {
    frameinterval = 40;
    c = new Canvas(WIDTH, HEIGHT);
    gc = c.getGraphicsContext2D();
    cx = WIDTH / 2;
    cy = HEIGHT / 2;
    dt = 0.075;
    gc.setFill(Color.rgb(0, 0, 0));
    gc.fillRect(0, 0, WIDTH, HEIGHT);
    gc.setStroke(Color.rgb(0, 255, 0));
    gc.strokeRect(1, 1, WIDTH-2, HEIGHT-2);
    g = new buf(5, 5, WIDTH-10, HEIGHT-10, gc, file);
    g.rndr();
  }
  
  void wipe()
  {
    gc.setFill(Color.rgb(0, 0, 0));
    gc.fillRect(0, 0, WIDTH, HEIGHT);
    gc.setStroke(Color.rgb(255, 192, 0, 0.4));
    gc.strokeRect(1, 1, WIDTH - 2, HEIGHT - 2);
  }

  void keyPressed(KeyEvent e)
  {
    KeyCode kc = e.getCode();
    g.onkeydown(kc.getName());
  }
  
  void keyReleased(KeyEvent e)
  {
    KeyCode kc = e.getCode();
    g.onkeyup(kc.getName());
  }

  void mousePressed(MouseEvent e){}

  void mouseReleased(MouseEvent e){}

  void mouseMoved(MouseEvent e){}

  void mouseDragged(MouseEvent e){}

  public static void main(String[] args)
  {
    file = "tmp.txt";
    if(args.length >= 1)
    {
      file = args[0];
    }
    launch();
  }

}

