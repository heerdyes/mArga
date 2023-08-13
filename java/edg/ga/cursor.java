public class cursor
{
  node l;
  node r;
  
  public cursor(node ll, node rr)
  {
    l = ll;
    r = rr;
  }
  
  public void movr()
  {
    if(r != null)
    {
      l = r;
      r = r.r;
    }
  }
  
  public void movl()
  {
    if(l != null)
    {
      r = l;
      l = l.l;
    }
  }
}

