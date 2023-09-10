import javax.swing.*;
import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.tools.*;

public class edg extends JFrame implements DocumentListener, KeyListener
{
  private JLabel stat;
  private JScrollPane sp;
  private JTextArea ta;
  private boolean pristine = true;
  private Font tafont = null;
  private Font sbfont = null;
  private Color fg, bg, cg;
  private int rr = 8;
  private int cc = 32;
  private float tafsz = 22f;
  private float sbfsz = 14f;
  private String taff = "OCRA";
  private String sbff = "Courier";
  private ArrayList<buf> bufs;
  private int curbuf = -1;
  private int ctr = 0;
  private File pwd;
  
  private void setupta()
  {
    ta = new JTextArea();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    ta.setColumns(cc);
    ta.setLineWrap(true);
    ta.setRows(rr);
    
    tafont = grepfamily(taff);
    ta.setFont(tafont.deriveFont(tafsz));
    ta.setForeground(fg);
    ta.setBackground(bg);
    ta.setCaretColor(cg);
    
    sp = new JScrollPane(ta);
  }
  
  private void setupstat()
  {
    stat = new JLabel("-> ready");
    sbfont = grepfamily(sbff);
    stat.setFont(sbfont.deriveFont(sbfsz));
    stat.setOpaque(true);
    stat.setForeground(fg);
    stat.setBackground(bg);
  }
  
  private String genid()
  {
    return String.format("b_%02d", ctr++);
  }
  
  private void init()
  {
    pwd = new File(".");
    bufs = new ArrayList<>();
    bufs.add(new buf(null, genid()));
    curbuf = 0;

    setupta();
    setupstat();
    
    getContentPane().setLayout(new BorderLayout());
    add(sp, BorderLayout.CENTER);
    add(stat, BorderLayout.SOUTH);
    
    pack();
  }
  
  // call loadcfg before init
  private void loadcfg()
  {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("edg.cfg"))))
    {
      String ln = br.readLine();
      while(ln != null)
      {
        String[] cadr = u.eattillspace(ln);
        String car = cadr[0], cdr = cadr[1];
        if("fg".equals(car))
        {
          fg = u.parsecolor(cdr);
        }
        else if("bg".equals(car))
        {
          bg = u.parsecolor(cdr);
        }
        else if("cur".equals(car))
        {
          cg = u.parsecolor(cdr);
        }
        else if("rows".equals(car))
        {
          rr = Integer.parseInt(cdr);
        }
        else if("cols".equals(car))
        {
          cc = Integer.parseInt(cdr);
        }
        else if("tafsz".equals(car))
        {
          tafsz = Float.parseFloat(cdr);
        }
        else if("sbfsz".equals(car))
        {
          sbfsz = Float.parseFloat(cdr);
        }
        else if("sbff".equals(car))
        {
          sbff = cdr;
        }
        else if("taff".equals(car))
        {
          taff = cdr;
        }
        else
        {
          u.d("unknown command: " + car);
        }
        ln = br.readLine();
      }
    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();
      System.exit(0);
    }
  }
  
  public edg()
  {
    super("edg");
    loadcfg();
    init();
    ta.getDocument().addDocumentListener(this);
    ta.addKeyListener(this);
  }
  
  void ta_populate()
  {
    buf cb = bufs.get(curbuf);
    ta.setText(cb.data);
    stat.setText(String.format("-> buf[%s] <%s>", cb.id, cb.file==null?"null":cb.file.getName()));
  }
  
  public edg(String fn)
  {
    this();
    buf b = bufs.get(curbuf);
    b.setf(fn);
    b.load();
    ta_populate();
  }
  
  // [styleops
  private Font grepfamily(String ff)
  {
    Font[] fnts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    for(Font f : fnts)
    {
      if(ff.equals(f.getFamily()))
      {
        return f;
      }
    }
    throw new RuntimeException("no "+ff+" font family found!");
  }
  // ]
  
  private void dirtybuffer()
  {
    if(pristine)
    {
      stat.setText("-> buffer modified");
      pristine = false;
    }
    bufs.get(curbuf).data = ta.getText();
  }
  
  void jrun(String clsnm)
  {
    try
    {
      ProcessBuilder pb = new ProcessBuilder("java", clsnm);
      pb.directory(bufs.get(curbuf).file.getAbsoluteFile().getParentFile());
      pb.inheritIO();
      Process p = pb.start();
      int result = p.waitFor();
      String msg = "[jrun] process exited with status: " + result;
      stat.setText(msg);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
  }
  
  void dosave()
  {
    stat.setText("-> saving to file...");
    buf cb = bufs.get(curbuf);
    if(cb.file == null)
    {
      JFileChooser fc = new JFileChooser();
      fc.setCurrentDirectory(pwd);
      int option = fc.showSaveDialog(this);
      if(option == JFileChooser.APPROVE_OPTION)
      {
        cb.setf(fc.getSelectedFile());
      }
      else
      {
        stat.setText("!! aborting save !!");
        return;
      }
    }
    cb.setdata(ta.getText());
    cb.save();
    stat.setText("-> saved " + cb.file.getName());
  }
  
  void doopen()
  {
    stat.setText("-> loading file...");
    buf cb = bufs.get(curbuf);
    JFileChooser fc = new JFileChooser();
    fc.setCurrentDirectory(pwd);
    int option = fc.showOpenDialog(this);
    if(option == JFileChooser.APPROVE_OPTION)
    {
      cb.setf(fc.getSelectedFile());
      cb.load();
      ta.setText(cb.data);
      stat.setText("-> loaded " + cb.file.getName());
    }
    else
    {
      stat.setText("-> open action cancelled!");
    }
  }
  
  void dojcompile()
  {
    stat.setText("-> compiling this java file...");
    File file = bufs.get(curbuf).file;
    String fn = file.getName();
    u.d("[javac file='"+fn+"'");
    JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
    int res = jc.run(null, System.out, System.err, "-sourcepath", file.getAbsoluteFile().getParent(), fn);
    stat.setText(res == 0 ? "[javac] compilation success!" : "[javac] compilation failed! check console.");
    u.d("]");
  }
  
  void dojrun()
  {
    stat.setText("-> running this java file...");
    File file = bufs.get(curbuf).file;
    String clnm = file.getName().split("\\.")[0];
    u.d("[java class='" + clnm + "'");
    jrun(clnm);
    u.d("]");
  }
  
  void doquit()
  {
    stat.setText("!! sayonara !!");
    u.d("escaping...");
    System.exit(0);
  }
  
  void nextbuf()
  {
    if(bufs.size() == 1)
    {
      stat.setText("[warn] only 1 buffer exists.");
    }
    else
    {
      stat.setText("-> switching to next buffer");
      curbuf = (curbuf + 1) % bufs.size();
      ta_populate();
    }
  }
  
  void prevbuf()
  {
    if(bufs.size() == 1)
    {
      stat.setText("[warn] only 1 buffer exists.");
    }
    else
    {
      stat.setText("-> switching to previous buffer");
      curbuf = curbuf == 0 ? bufs.size() - 1 : curbuf - 1;
      ta_populate();
    }
  }
  
  // [eventlisteners
  public void changedUpdate(DocumentEvent ev) {}
  
  public void removeUpdate(DocumentEvent ev)
  {
    dirtybuffer();
  }
  
  public void insertUpdate(DocumentEvent ev)
  {
    dirtybuffer();
  }
  
  public void keyPressed(KeyEvent ke) {}
  
  void ta_keyup(KeyEvent ke)
  {
    int kc = ke.getKeyCode();
    int offmask = InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK;
    int onmask = InputEvent.CTRL_DOWN_MASK;
    if((ke.getModifiersEx() & (onmask | offmask)) == onmask)
    {
      if(kc == KeyEvent.VK_S) { dosave(); }
      else if(kc == KeyEvent.VK_O) { doopen(); }
      else if(kc == KeyEvent.VK_BACK_QUOTE)
      {
        stat.setText("[TODO] -> toggling terminal emulator...");
      }
      else if(kc == KeyEvent.VK_T)
      {
        buf nb = new buf(null, genid());
        bufs.add(nb);
        curbuf = bufs.size() - 1;
        ta_populate();
      }
      else if(kc == KeyEvent.VK_PAGE_DOWN) { nextbuf(); }
      else if(kc == KeyEvent.VK_PAGE_UP) { prevbuf(); }
    }
    else if(kc == KeyEvent.VK_F9) { dojcompile(); }
    else if(kc == KeyEvent.VK_F5) { dojrun(); }
    else if(kc == KeyEvent.VK_ESCAPE) { doquit(); }
  }
  
  public void keyReleased(KeyEvent ke)
  {
    ta_keyup(ke);
  }
  
  public void keyTyped(KeyEvent ke)
  {}
  // ]
  
  public static void main(String args[])
  {
    SwingUtilities.invokeLater(new edgw(args));
  }
}

class edgw implements Runnable
{
  String[] args;
  
  edgw(String[] _args)
  {
    args = _args;
  }
  
  public void run()
  {
    edg e = args.length == 0 ? new edg() : new edg(args[0]);
    e.setVisible(true);
  }
}

class buf
{
  File file;
  String id;
  String data;

  buf(File f, String s)
  {
    file = f;
    id = s;
    data = "";
  }
  
  public String toString()
  {
    return String.format("[buf [id '%s'] [file '%s'] [data '%s']]", id, file==null?"null":file.getName(), data);
  }
  
  void load() { data = fs.cat(file); }
  void setf(String fn) { file = new File(fn); }
  void setf(File f) { file = f; }
  void save() { fs.save(file, data); }
  void setdata(String content) { data = content; }
}

class u
{
  static String[] eattillspace(String s)
  {
    return eattillchar(s, ' ');
  }
  
  static String[] eattillchar(String s, char c)
  {
    int pos = s.indexOf(c);
    if(pos == -1)
    {
      return new String[] {s, ""};
    }
    return new String[] {s.substring(0, pos), s.substring(pos + 1)};
  }
  
  static Color parsecolor(String rgb)
  {
    String[] r_gb = eattillspace(rgb);
    int r = Integer.parseInt(r_gb[0]);
    String[] g_b = eattillspace(r_gb[1]);
    int g = Integer.parseInt(g_b[0]);
    int b = Integer.parseInt(g_b[1]);
    return new Color(r, g, b);
  }
  
  static void d(String msg)
  {
    System.out.println(msg);
  }
  
  static void d(ArrayList<buf> bs)
  {
    u.d("[buflist");
    for(buf b : bs)
    {
      d("  " + b.toString());
    }
    u.d("]");
  }
}

class fs
{
  static String cat(File f)
  {
    mkifnonexistent(f);
    try(InputStreamReader isr = new InputStreamReader(new FileInputStream(f)))
    {
      String cpath = f.getCanonicalPath();
      if(f.length() == 0)
      {
        return "";
      }
      StringBuffer sb = new StringBuffer();
      int x = isr.read();
      while(x != -1)
      {
        char c = (char) x;
        sb.append(c);
        x = isr.read();
      }
      return sb.toString();
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
    return "";
  }
  
  static void save(File f, String data)
  {
    try(PrintWriter pw = new PrintWriter(f))
    {
      pw.print(data);
      pw.flush();
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
  }
  
  static void mkifnonexistent(File x)
  {
    if(!x.exists())
    {
      try
      {
        x.createNewFile();
      }
      catch (IOException ioe)
      {
        ioe.printStackTrace();
      }
    }
  }
}

