public class node
{
  char c;
  node l;
  node r;
  
  public node(char cc, node ll, node rr)
  {
    c = cc;
    l = ll;
    r = rr;
  }
  
  public node(char cc)
  {
    this(cc, null, null);
  }
}

