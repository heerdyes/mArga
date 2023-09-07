import javax.swing.*;

class edgw implements Runnable
{
  String[] args;
  
  edgw(String[] _args)
  {
    args = _args;
  }
  
  public void run()
  {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    edg e = args.length == 0 ? new edg() : new edg(args[0]);
    e.setVisible(true);
  }
}

