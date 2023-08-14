import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

public class buf extends box
{
  node head, tail;
  cursor cr;
  Color hg, lg;
  GraphicsContext ctx;
  Font fnt;
  boolean capslock = false;
  String mode = "Normal";
  String file;
  double icg = 16;
  double irg = 26;
  double crtht = 20;
  double mx = 50;
  double my = 30;
  double mln = 4;
  
  void savebuf()
  {
    if(file == null)
    {
      throw new RuntimeException("buffer backing file not set!");
    }
    try(FileWriter fw = new FileWriter(file))
    {
      for(node p = head; p != null; p = p.r)
      {
        fw.write((int) p.c);
      }
    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();
    }
  }
  
  FileReader surefireFileReader()
  {
    File f = new File(file);
    FileReader fr = null;
    try
    {
      if(!f.exists())
      {
        f.createNewFile();
      }
      fr = new FileReader(f);
    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();
    }
    return fr;
  }
  
  void loadbuf()
  {
    if(file == null)
    {
      throw new RuntimeException("file not set!");
    }
    
    try(FileReader fr = surefireFileReader())
    {
      int n;
      while((n = fr.read()) != -1)
      {
        char a = (char) n;
        if(head == null && tail == null)
        {
          head = tail = new node(a);
        }
        else
        {
          node p = new node(a, tail, null);
          tail.r = p;
          tail = p;
        }
      }
      cr = new cursor(null, head);
    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();
    }
  }
  
  void dumplist()
  {
    for(node p = head; p != null; p = p.r)
    {
      u.d(p.c);
    }
  }
  
  public buf(double x, double y, double w, double h, GraphicsContext gc, String gf)
  {
    super(x, y, w, h);
    hg = Color.rgb(23, 202, 232);
    lg = Color.rgb(0, 72, 0);
    ctx = gc;
    fnt = new Font("OCRA", 16);
    gc.setFont(fnt);
    file = gf;
    loadbuf();
  }
  
  void bksptail()
  {
    node _d = tail;
    tail = tail.l;
    tail.r = null;
    _d.l = _d.r = null;
    cr.l = tail;
    cr.r = null;
  }
  
  void bksphead()
  {
    node _d = head;
    head = head.r;
    head.l = null;
    _d.l = _d.r = null;
    cr.l = null;
    cr.r = head;
  }
  
  void bkspmid()
  {
    node _d = cr.l;
    cr.l = _d.l;
    _d.l = _d.r = null;
    cr.l.r = cr.r;
    cr.r.l = cr.l;
  }
  
  public void bksp()
  {
    if(head == null && tail == null)
    {
      u.d("no more chars to backspace!");
      return; // flatten if blocks as much as possible
    }
    if(cr.l == null && cr.r != null)
    {
      u.d("already at head!");
    }
    else if(cr.l != null && cr.r == null)
    {
      bksptail();
    }
    else if(cr.l == head)
    {
      bksphead();
    }
    else
    {
      bkspmid();
    }
  }
  
  void inshead(char c)
  {
    node p = new node(c, null, head);
    head.l = p;
    head = p;
    cr.l = head;
    cr.r = head.r;
  }
  
  void instail(char c)
  {
    node p = new node(c, tail, null);
    tail.r = p;
    tail = p;
    cr.l = tail;
    cr.r = null;
  }
  
  void insmid(char c)
  {
    node p = new node(c, cr.l, cr.r);
    cr.l.r = p;
    cr.r.l = p;
    cr.l = p;
  }
  
  public void insch(char c)
  {
    if(head == null && tail == null)
    {
      head = tail = new node(c);
      cr = new cursor(tail, null);
      return; // flatten if blocks as much as possible
    }
    if(cr.l == null && cr.r != null)
    {
      inshead(c);
    }
    else if(cr.l != null && cr.r == null)
    {
      instail(c);
    }
    else
    {
      insmid(c);
    }
  }
  
  public void handlenav(String kn)
  {
    if(mode.equals("Normal") || mode.equals("Shift"))
    {
      if(kn.equals("Right"))
      {
        cr.movr();
      }
      else if(kn.equals("Left"))
      {
        cr.movl();
      }
      else if(kn.equals("Up"))
      {
        u.d("unhandled");
      }
      else if(kn.equals("Down"))
      {
        u.d("unhandled");
      }
    }
    else if(mode.equals("Ctrl"))
    {
      System.out.println("[TODO] implement this!");
    }
  }
  
  public void handlespace()
  {
    if(mode.equals("Normal"))
    {
      insch(' ');
    }
    else if(mode.equals("Shift"))
    {
      System.out.println("[TODO] implement this!");
    }
  }
  
  public void handleenter()
  {
    if(mode.equals("Normal"))
    {
      insch('\n');
    }
    else if(mode.equals("Shift"))
    {
      System.out.println("[TODO] implement this!");
    }
  }
  
  public void handlecomma()
  {
    if(mode.equals("Normal"))
    {
      insch(',');
    }
    else if(mode.equals("Shift"))
    {
      insch('<');
    }
  }
  
  public void handleperiod()
  {
    if(mode.equals("Normal"))
    {
      insch('.');
    }
    else if(mode.equals("Shift"))
    {
      insch('>');
    }
  }
  
  public void handleslash()
  {
    if(mode.equals("Normal"))
    {
      insch('/');
    }
    else if(mode.equals("Shift"))
    {
      insch('?');
    }
  }
  
  public void handlebackslash()
  {
    if(mode.equals("Normal"))
    {
      insch('\\');
    }
    else if(mode.equals("Shift"))
    {
      insch('|');
    }
  }
  
  public void handleminus()
  {
    if(mode.equals("Normal"))
    {
      insch('-');
    }
    else if(mode.equals("Shift"))
    {
      insch('_');
    }
  }
  
  public void handlesemicolon()
  {
    if(mode.equals("Normal"))
    {
      insch(';');
    }
    else if(mode.equals("Shift"))
    {
      insch(':');
    }
  }
  
  public void handlequote()
  {
    if(mode.equals("Normal"))
    {
      insch('\'');
    }
    else if(mode.equals("Shift"))
    {
      insch('"');
    }
  }
  
  public void handleequals()
  {
    if(mode.equals("Normal"))
    {
      insch('=');
    }
    else if(mode.equals("Shift"))
    {
      insch('+');
    }
  }
  
  public void handlebackspace()
  {
    if(mode.equals("Normal"))
    {
      bksp();
    }
    else if(mode.equals("Shift"))
    {
      System.out.println("[TODO] unimplemented");
    }
  }
  
  public void handleopenbracket()
  {
    if(mode.equals("Normal"))
    {
      insch('[');
    }
    else if(mode.equals("Shift"))
    {
      insch('{');
    }
  }
  
  public void handleclosebracket()
  {
    if(mode.equals("Normal"))
    {
      insch(']');
    }
    else if(mode.equals("Shift"))
    {
      insch('}');
    }
  }
  
  public void handleletter(String z)
  {
    if(mode.equals("Shift") ^ capslock)
    {
      insch(z.charAt(0));
    }
    else
    {
      insch(z.toLowerCase().charAt(0));
    }
  }
  
  public void handledigit(String z)
  {
    if(mode.equals("Normal"))
    {
      insch(z.charAt(0));
    }
    else if(mode.equals("Shift"))
    {
      char m=z.charAt(0);
      switch(m)
      {
      case '1':
        insch('!');
        break;
      case '2':
        insch('@');
        break;
      case '3':
        insch('#');
        break;
      case '4':
        insch('$');
        break;
      case '5':
        insch('%');
        break;
      case '6':
        insch('^');
        break;
      case '7':
        insch('&');
        break;
      case '8':
        insch('*');
        break;
      case '9':
        insch('(');
        break;
      case '0':
        insch(')');
        break;
      }
    }
  }
  
  public void handlefnkey(String fk)
  {
    if(fk.equals("F2"))
    {
      u.d("saving buffer to file " + file);
      savebuf();
    }
  }
  
  public void handledelete()
  {
    if(mode.equals("Normal"))
    {
      u.d("unhandled");
    }
    else if(mode.equals("Shift"))
    {
      System.out.println("[TODO] unimplemented");
    }
  }
  
  public void handletab()
  {
    if(mode.equals("Normal"))
    {
      insch('\t');
    }
    else if(mode.equals("Shift"))
    {
      System.out.println("[TODO] unimplemented");
    }
  }
  
  public void onkeyup(String kn)
  {
    if(kn.equals("Esc"))
    {
      System.out.println("bye!");
      System.exit(0);
    }
    else if(kn.equals("Shift"))
    {
      mode="Normal";
    }
  }

  public void onkeydown(String kn)
  {
    if(kn.equals("Right") || kn.equals("Left") || kn.equals("Up") || kn.equals("Down"))
    {
      handlenav(kn);
    }
    else if(u.sin(kn, new String[]{"F1","F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12"}))
    {
      handlefnkey(kn);
    }
    else if(kn.equals("Space"))
    {
      handlespace();
    }
    else if(kn.equals("Enter"))
    {
      handleenter();
    }
    else if(kn.equals("Shift"))
    {
      mode="Shift";
    }
    else if(kn.equals("Caps Lock"))
    {
      capslock=!capslock; // should this be permutable with Shift?
    }
    else if(kn.equals("Comma"))
    {
      handlecomma();
    }
    else if(kn.equals("Period"))
    {
      handleperiod();
    }
    else if(kn.equals("Slash"))
    {
      handleslash();
    }
    else if(kn.equals("Back Slash"))
    {
      handlebackslash();
    }
    else if(kn.equals("Minus"))
    {
      handleminus();
    }
    else if(kn.equals("Semicolon"))
    {
      handlesemicolon();
    }
    else if(kn.equals("Quote"))
    {
      handlequote();
    }
    else if(kn.equals("Equals"))
    {
      handleequals();
    }
    else if(kn.equals("Backspace"))
    {
      handlebackspace();
    }
    else if(kn.equals("Delete"))
    {
      handledelete();
    }
    else if(kn.equals("Open Bracket"))
    {
      handleopenbracket();
    }
    else if(kn.equals("Close Bracket"))
    {
      handleclosebracket();
    }
    else if(kn.equals("Esc"))
    {
      // handled by keyup. do nothing here
    }
    else if(kn.equals("Tab"))
    {
      handletab();
    }
    else if(kn.length()==1)
    {
      if(u.cin(kn.charAt(0),"ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
      {
        handleletter(kn);
      }
      else if(u.cin(kn.charAt(0),"0123456789"))
      {
        handledigit(kn);
      }
      else
      {
        u.a("how to handle: "+kn);
      }
    }
    else
    {
      u.a("how to handle: "+kn);
    }
    rndr();
  }
  
  public void rndr()
  {
    if(ctx == null)
    {
      throw new RuntimeException("[BOOM] GraphicsContext ctx was null!");
    }
    rndr(ctx);
  }
  
  public void rndrchar(GraphicsContext gc, char cc, double xx, double yy)
  {
    gc.setStroke(fg);
    if(cc == '\n')
    {
      double px = xx;
      double py = yy - 2;
      gc.strokeLine(px, py, px + 9, py);
      gc.strokeLine(px + 9, py, px + 9, py - 9);
      gc.strokeLine(px, py, px + 3, py - 2);
      gc.strokeLine(px, py, px + 3, py + 2);
    }
    else if(cc == ' ')
    {
      // do nothing
    }
    else if(cc == '\t')
    {
      double px = xx;
      double py = yy - 2;
      gc.strokeLine(px, py, px + 9, py);
      gc.strokeLine(px, py, px + 3, py - 2);
      gc.strokeLine(px, py, px + 3, py + 2);
      gc.strokeLine(px, py - 5, px + 9, py - 5);
      gc.strokeLine(px + 9, py - 5, px + 6, py - 7);
      gc.strokeLine(px + 9, py - 5, px + 6, py - 3);
    }
    else
    {
      gc.setFill(fg);
      gc.fillText("" + cc, xx, yy);
    }
  }
  
  void rndrlnno(int ln, double xx, double yy)
  {
    ctx.setFill(hg);
    ctx.fillText(String.format("%03d", ln), xx, yy);
  }
  
  void rndrcaret(GraphicsContext gc, double px, double py)
  {
    gc.setStroke(hg);
    gc.strokeLine(px, py + 2, px, py - crtht);
  }
  
  public void rndr(GraphicsContext gc)
  {
    super.rndr(gc);
    gc.setFill(fg);
    
    double cx = mx, cy = my;
    int lnctr = 1;
    boolean lnbeg = true;
    for(node p = head; p != null; p = p.r)
    {
      if(lnbeg)
      {
        rndrlnno(lnctr, mln, cy);
        lnbeg = false;
      }
      rndrchar(gc, p.c, cx, cy);
      if(p.c == '\n')
      {
        cx = mx;
        cy += irg;
        lnctr += 1;
        lnbeg = true;
      }
      else
      {
        cx += icg;
      }
      if(cr.r == null && p == cr.l)
      {
        rndrcaret(gc, cx, cy);
      }
      else if(cr.l == null && p == cr.r)
      {
        rndrcaret(gc, cx - icg, cy);
      }
      else if(p == cr.l)
      {
        rndrcaret(gc, cx, cy);
      }
    }
  }
}

