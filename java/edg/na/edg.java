import javax.swing.*;
import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class edg extends JFrame implements DocumentListener, KeyListener
{
  private JLabel stat;
  private JScrollPane sp;
  private JTextArea ta;
  private File file = null;
  private boolean pristine = true;
  private Font fnt = null;
  private Font ocra = null;
  
  private void init()
  {
    ta = new JTextArea();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    ta.setColumns(80);
    ta.setLineWrap(true);
    ta.setRows(30);
    grepocra();
    ta.setFont(ocra.deriveFont(22f));
    
    sp = new JScrollPane(ta);
    
    stat = new JLabel("ready");
    stat.setFont(ocra.deriveFont(14f));
    
    getContentPane().setLayout(new BorderLayout());
    add(sp, BorderLayout.CENTER);
    add(stat, BorderLayout.SOUTH);
    pack();
  }
  
  public edg()
  {
    super("edg");
    init();
    ta.getDocument().addDocumentListener(this);
    ta.addKeyListener(this);
  }
  
  public edg(String fn)
  {
    this();
    file = new File(fn);
    mkifnonexistent(file);
    loadfile(file);
  }
  
  // [fsops
  private void mkifnonexistent(File x)
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
  
  private void loadfile(File f)
  {
    try(InputStreamReader isr = new InputStreamReader(new FileInputStream(file)))
    {
      String cpath = file.getCanonicalPath();
      if(file.length() == 0)
      {
        ta.setText("");
      }
      else
      {
        StringBuffer sb = new StringBuffer();
        int x = isr.read();
        while(x != -1)
        {
          char c = (char) x;
          sb.append(c);
          x = isr.read();
        }
        ta.setText(sb.toString());
      }
      setTitle(cpath);
      stat.setText("loaded file: " + cpath);
      pristine = true;
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
  }
  
  private void savefile()
  {
    try(PrintWriter pw = new PrintWriter(file))
    {
      pw.print(ta.getText());
      pw.flush();
      stat.setText("saved to file: " + file.getCanonicalPath());
      pristine = true;
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
  }
  
  private void savefile(File f)
  {
    try(PrintWriter pw = new PrintWriter(f))
    {
      pw.print(ta.getText());
      pw.flush();
      stat.setText("saved to file: " + f.getCanonicalPath());
      file = f;
      pristine = true;
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
  }
  
  private File selectfile2save()
  {
    File sf = null;
    try
    {
      JFileChooser fc = new JFileChooser();
      int option = fc.showSaveDialog(this);
      if(option == JFileChooser.APPROVE_OPTION)
      {
        sf = fc.getSelectedFile();
        String cpath = sf.getCanonicalPath();
        setTitle("| " + cpath + " |");
      }
      else
      {
        stat.setText("file select cancelled!");
      }
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
    return sf;
  }
  
  private File selectfile2open()
  {
    File sf = null;
    try
    {
      JFileChooser fc = new JFileChooser();
      int option = fc.showOpenDialog(this);
      if(option == JFileChooser.APPROVE_OPTION)
      {
        sf = fc.getSelectedFile();
        String cpath = sf.getCanonicalPath();
        setTitle("| " + cpath + " |");
      }
      else
      {
        stat.setText("file select cancelled!");
      }
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
    return sf;
  }
  // ]
  
  // [styleops
  private void grepocra()
  {
    Font[] fnts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    for(Font f : fnts)
    {
      if("OCRA".equals(f.getFamily()))
      {
        ocra = f;
        return;
      }
    }
    throw new RuntimeException("no ocra font found!");
  }
  // ]
  
  private void dirtybuffer()
  {
    if(pristine)
    {
      stat.setText("buffer modified");
      pristine = false;
    }
  }
  
  // [eventlisteners
  public void changedUpdate(DocumentEvent ev)
  {}
  
  public void removeUpdate(DocumentEvent ev)
  {
    dirtybuffer();
  }
  
  public void insertUpdate(DocumentEvent ev)
  {
    dirtybuffer();
  }
  
  public void keyPressed(KeyEvent ke) {}
  
  public void keyReleased(KeyEvent ke)
  {
    int kc = ke.getKeyCode();
    int offmask = InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK;
    int onmask = InputEvent.CTRL_DOWN_MASK;
    if((ke.getModifiersEx() & (onmask | offmask)) == onmask)
    {
      if(kc == KeyEvent.VK_S)
      {
        stat.setText("saving to file...");
        if(file == null)
        {
          savefile(selectfile2save());
        }
        else
        {
          savefile();
        }
      }
      else if(kc == KeyEvent.VK_O)
      {
        stat.setText("loading file...");
        loadfile(selectfile2open());
      }
    }
    else if(kc == KeyEvent.VK_ESCAPE)
    {
      System.out.println("escaping...");
      System.exit(0);
    }
  }
  
  public void keyTyped(KeyEvent ke)
  {}
  // ]
  
  public static void main(String args[])
  {
    SwingUtilities.invokeLater(new edgw(args));
  }
}

