package ed;

public class SBuf implements Buf
{
  StringBuffer buf;
  int cr;
  int cc;
  int rwhead;
  
  public SBuf(int cols)
  {
    buf=new StringBuffer();
    cr=cc=rwhead=0;
  }
  
  public String str()
  {
    return buf.toString();
  }
  
  public void append(char c)
  {
    if(c=='\n')
    {
      cc=0;
      cr+=1;
    }
    else
    {
      cc+=1;
    }
    buf.append(c);
    rwhead+=1;
  }
  
  public void append(String s)
  {
    for(char x:s.toCharArray())
    {
      append(x);
    }
  }
  
  public void clear()
  {
    buf=new StringBuffer();
    cr=cc=0;
  }
  
  public void init(String s)
  {
    clear();
    append(s);
  }
  
  public void insert(int pos,String s)
  {
    buf.insert(pos,s);
  }
  
  public void insert(int pos,char c)
  {
    buf.insert(pos,c);
  }
}

