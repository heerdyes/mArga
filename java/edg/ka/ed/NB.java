package ed;

import java.util.*;

public class NB
{
  List<SBuf> pgs;
  int currpg;
  
  NB()
  {
    pgs=new ArrayList<SBuf>();
    currpg=-1;
  }
  
  void addpg(SBuf pg)
  {
    pgs.add(pg);
  }
  
  void nextpg()
  {
    currpg+=1;
  }
  
  void gotopg(int pgno)
  {
    if(pgno<0 || pgno>=pgs.size())
    {
      throw new RuntimeException("page "+pgno+" does not exist");
    }
    currpg=pgno;
  }
}

